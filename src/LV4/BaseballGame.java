package LV4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class BaseballGame {
    BaseballGameDisplay baseballGameDisplay = new BaseballGameDisplay();
    private static List<Integer> tryCountList = new ArrayList<>();  // 시도횟수를 누적시키는 리스트
    int againDigits;    // answerArray 배열 크기 초기화 위해 만듦,,
    int[] answerArray;  // 정답 배열(해시셋 통해서 가져옴)

    // 생성자
    public BaseballGame() {}

    /**
     * 다른 클래스에서 캡슐화 된 tryCountList 값에 접근할 때 사용하는 메소드입니다.
     * @return 시도횟수를 누적시킨 tryCountList
     */
    public List<Integer> getTryCountList() {
        return tryCountList;
    }

    /**
     * 자릿수에 맞는 크기의 배열을 만들어 정답을 저장합니다.
     * 자릿수가 3이상 5이하의 숫자가 아닌 경우 던집니다.
     *
     * @param digits 유저가 입력한 자릿수를 받은 int 타입의 입력값
     * @throws IncorrectNumberException
     *         digits < 3 또는 digits > 5 인 경우
     */
    public void makeAnswerArray(int digits) throws IncorrectNumberException {
        if (digits < 3 || digits > 5) {
            throw new IncorrectNumberException();
        }

        HashSet<Integer> hashSet = new HashSet(digits);  // Set은 중복 없음!
        Random random = new Random();
        againDigits = digits;


        /** 랜덤 answer 생성합니다. */
        while (hashSet.size() != digits) {
            hashSet.add(random.nextInt(1,9));  // 1~9 사이의 랜덤 숫자를 뽑아서 hashSet에 추가한다.
        }

        // hashSet의 데이터를 배열 answerArray 로 옮긴다.
        answerArray = new int[digits];  // 배열 크기 초기화
        Iterator<Integer> iterator = hashSet.iterator();    // iterator를 통해 인덱스 없는 set에 접근한다.
        for (int j = 0; j < digits; j++) {
            answerArray[j] = iterator.next();
        }
        //System.out.println(hashSet);  // 개발자 확인용
    }

    /** 유저에게 입력을 받아 게임이 진행되고 결과를 보여주는 메소드입니다. */
    public void play() {
        int tryCount = 0;

        while (true) {
            // 중복이 허용되는 리스트에 값을 입력받는다.
            List<Integer> list = new ArrayList<>();    // 올바르지 않은 입력값으로 인해 다시 입력받을 경우 생각하여 돌 때마다 list 초기화

            /** 1. 유저에게 입력값을 받습니다. */
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

            /** 2. 올바른 입력값을 받았는지 검증합니다. */
            if (!validateInput(list)) { // 값이 3자리 수가 아니거나 중복이 있으면 다시 돌아간다.
                continue;
            }

            /** 3. 게임 진행횟수를 증가시킵니다. */
            tryCount++;
            //System.out.println("tryCount: "+ tryCount);   // 개발자 확인용

            /** 4. 스트라이크 갯수를 계산합니다. */
            int countStrike = countStrike(list);

            /** 5. 정답여부 확인, 만약 정답이면 break 를 이용해 반복문 탈출합니다. */
            if (countStrike == againDigits) {
                System.out.println("정답입니다!");
                break;
            }

            /** 6. 볼 갯수를 계산합니다. */
            int countBall = countBall(list);

            /** 7. 힌트(스트라이크와 볼 갯수)를 출력합니다. */
            baseballGameDisplay.displayHint(countStrike, countBall);
        }
        // 게임 시도횟수 리스트에 추가
        tryCountList.add(tryCount);
    }

    /**
     * List가 3가지(세자리 수, 0의 유무, 중복) 검사를 만족한다면 true를 리턴합니다.
     *
     * @param list 유저에게 입력받은 입력값을 담은 Integer 타입의 List
     * @return List가 3가지 조건을 만족하면 true, 하나라도 만족하지 않다면 false
     */
    protected boolean validateInput(List<Integer> list) {
        /** 세자리 수인지 검사합니다. */
        if (list.size() != againDigits) {
            System.out.println("올바르지 않은 입력값입니다. " + againDigits + "자리를 입력하세요!");
            return false;
        }

        /** 0이 있는지 검사합니다. */
        for (int i = 0; i < againDigits; i++) {
            if (list.get(i) == 0) {
                System.out.println("올바르지 않은 입력값입니다. 0이 아닌 숫자를 입력하세요!");
                return false;
            }
        }

        /** 중복을 검사합니다. */
        Set<Integer> set = new HashSet<>(list); // 중복이 허용되는 list를 중복이 허용되지 않는 set으로 바꾸기
        if (set.size() !=  list.size()) { // list와 set의 크기가 다르면 중복값이 있다!
            System.out.println("올바르지 않은 입력값입니다. 중복되지 않는 숫자를 입력하세요!");
            return false;
        }
        return true;
    }

    /**
     * List가 정답을 담고 있는 answerArray 배열과의 스트라이크(동일한 숫자&위치) 갯수를 계산하여 리턴합니다.
     *
     * @param list 유저에게 입력받은 입력값을 담은 Integer 타입의 List
     * @return 스트라이크 갯수
     */
    private int countStrike(List<Integer> list) {
        int countStrike = 0;

        for (int i = 0; i < againDigits; i++) {
            if (list.get(i) == answerArray[i]) {
                countStrike++;
            }
        }
        return countStrike;
    }

    /**
     * List가 정답을 담고 있는 answerArray 배열과의 볼(동일한 숫자) 갯수를 계산하여 리턴합니다.
     *
     * @param list 유저에게 입력받은 입력값을 담은 Integer 타입의 List
     * @return 볼 갯수
     */
    private int countBall(List<Integer> list) {
        int countBall = 0;

        for (int i = 0; i < againDigits; i++) {
            for (int j = 0; j < againDigits; j++) {
                if (list.get(i) == answerArray[j] && i != j) {    //스트라이크가 아니고 볼이면
                    countBall++;
                }
            }
        }
        return countBall;
    }
}
