package LV4;

import java.util.*;

public class BaseballGame {
    BaseballGameDisplay baseballGameDisplay = new BaseballGameDisplay();
    private static List<Integer> tryCountList = new ArrayList<>();  // 시도횟수를 누적시키는 리스트
    int againDigits;    // answerArray 배열 크기 초기화 위해 만듦,,
    int[] answerArray;  // 정답 배열(해시셋 통해서 가져옴)

    // 객체 생성시 정답을 만들도록 함
    public BaseballGame() {
        //makeAnswerArray();
    }

    public List<Integer> getTryCountList() {
        return tryCountList;
    }

    public void makeAnswerArray(int digits) {
        HashSet<Integer> hashSet = new HashSet(digits);  // Set은 중복 없음!
        Random random = new Random();
        againDigits = digits;
        System.out.println("againDigits: "+againDigits);

        //랜덤 answer
        while (hashSet.size() != digits) {
            hashSet.add(random.nextInt(1,9));  // 1~9 사이의 랜덤 숫자를 뽑아서 hashSet에 추가한다.
        }
        System.out.println(hashSet);

        //hashSet의 데이터를 배열 answerArray 로 옮긴다.
        answerArray = new int[digits];  // 배열 크기 초기화
        Iterator<Integer> iterator = hashSet.iterator();    // iterator를 통해 인덱스 없는 set에 접근한다.
        for (int j = 0; j < digits; j++) {
            answerArray[j] = iterator.next();
        }
        System.out.println(hashSet);

        //this.answerList.addAll((hashSet.stream().toList())); // hashset을 list로 바꾸고 answerlist에 정답을 누적시킨다.(정답이 계속 바뀌게 하려고)
        //return answerArray;
    }

    public void play() {
        int tryCount=0;
        while (true) {
            // 중복이 허용되는 리스트에 값을 입력받는다.
            List<Integer> list = new ArrayList<>();    // 올바르지 않은 입력값으로 인해 다시 입력받을 경우 생각하여 돌 때마다 list 초기화

            // 1. 유저에게 입력값을 받음
            Scanner s = new Scanner(System.in);
            System.out.println("숫자를 입력하세요.");

            int number;
            try {
                number = s.nextInt();   // 세자릿수를 하나의 숫자로 한번에 입력받는다. ex)452
            } catch (InputMismatchException e) {
                throw new IllegalArgumentException("올바르지 않은 입력값입니다. 숫자를 입력하세요!");
            }

            // 각 자릿수 비교를 위하여 먼저 list에 한자리씩 숫자를 담는다.
            String numToString = String.valueOf(number);    // number를 문자열로 바꾼다.
            for (String sss : numToString.split("")) {    // 문자열을 쪼갠 후 하나씩 리스트에 넣는다.
                list.add(Integer.parseInt(sss));
            }

            // 2. 올바른 입력값을 받았는지 검증
            if (!validateInput(list)) { // 값이 3자리 수가 아니거나 중복이 있으면 다시 돌아간다.
                continue;
            }

            // 3. 게임 진행횟수 증가
            tryCount++;
            System.out.println("tryCount: "+ tryCount);

            // 4. 스트라이크 개수 계산
            int countStrike = countStrike(list);

            // 5. 정답여부 확인, 만약 정답이면 break 를 이용해 반복문 탈출
            if (countStrike == againDigits){
                System.out.println("정답입니다!");
                break;
            }

            // 6. 볼 개수 계산
            int countBall = countBall(list);

            // 7. 힌트 출력
            baseballGameDisplay.displayHint(countStrike, countBall);
        }
        // 게임 진행횟수 반환
        tryCountList.add(tryCount);
        //return tryCount;
    }

    protected boolean validateInput(List<Integer> list) {
        //세자리 수인지 검사
        if (list.size() != againDigits) {
            System.out.println("올바르지 않은 입력값입니다. " + againDigits + "자리를 입력하세요!");
            return false;
        }

        //0이 있는지 검사
        for (int i = 0; i < againDigits; i++) {
            if (list.get(i) == 0) {
                System.out.println("올바르지 않은 입력값입니다. 0이 아닌 숫자를 입력하세요!");
                return false;
            }
        }

        //중복 검사
        Set<Integer> set = new HashSet<>(list); // 중복이 허용되는 list를 중복이 허용되지 않는 set으로 바꾸기
        if (set.size() !=  list.size()) { // list와 set의 크기가 다르면 중복값이 있다!
            System.out.println("올바르지 않은 입력값입니다. 중복되지 않는 숫자를 입력하세요!");
            return false;
        }
        return true;    // 위에 조건들에 해당하지 않고 모두 만족하면 true를 반환한다.
    }

    private int countStrike(List<Integer> list) {   // 스트라이크 갯수 세는 메소드
        int countStrike = 0;

        for (int i = 0; i < againDigits; i++) {
            if (list.get(i) == answerArray[i]) {
                countStrike++;
            }
        }
        return countStrike;
    }

    private int countBall(List<Integer> list) { // 볼 갯수 세는 메소드
        int countBall = 0;

        for (int i = 0; i < againDigits; i++) {
            for (int j = 0; j < againDigits; j++) {
                if (list.get(i) == answerArray[j] && i != j){    //스트라이크가 아니고 볼이면
                    countBall++;
                }
            }
        }
        return countBall;
    }
}
