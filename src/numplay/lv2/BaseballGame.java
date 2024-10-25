package LV2;

import java.util.*;

public class BaseballGame {
    HashSet<Integer> hashSet;   //정답 해시셋
    int[] answerArray = new int[3]; //정답 배열(해시셋에서 가져옴)
    // Iterator<Integer> iterator; //필요한가?????????
    BaseballGameDisplay baseballGameDisplay = new BaseballGameDisplay();

    int number;
    int countStrike;
    int countBall;


    // 객체 생성시 정답을 만들도록 함
    public BaseballGame() {
        hashSet = new HashSet(3);  //초기 용량 : 3 (Set은 중복 없음) 용량 지정할 필요가 있는건가??????????
        Random random = new Random();

        for(int i=0; i<3; i++){
            hashSet.add(random.nextInt(1,9));  //1~9 사이의 랜덤 숫자를 뽑아서 hashSet에 추가한다.
        }

        //hashSet의 데이터를 배열 answerArray 로 옮긴다.
        Iterator<Integer> iterator = hashSet.iterator();    //iterator를 통해 인덱스 없는 set에 접근한다.
        while(iterator.hasNext()){
            for(int j=0; j<3; j++) {
                answerArray[j] = iterator.next();
            }
        }
    }

    public int play() {
        while (true) {
            //중복이 허용되는 리스트에 값을 입력받는다.
            List<Integer> list = new ArrayList<>();    //올바르지 않은 입력값으로 인해 다시 입력받을 경우 생각하여 돌 때마다 list 초기화

            // 1. 유저에게 입력값을 받음
            Scanner s = new Scanner(System.in);

            System.out.println("숫자를 입력하세요.");
            try{
                number = s.nextInt();   //세자릿수를 하나의 숫자로 한번에 입력받는다. ex)452
            }catch (InputMismatchException e){
                throw new IllegalArgumentException("올바르지 않은 입력값입니다. 숫자를 입력하세요!");
            }

            do{ //각 자릿수 비교를 위하여 먼저 list에 한자리씩 숫자를 담는다.
                list.add(number%10);
                number /= 10;
            }while(number!=0);

            // 2. 올바른 입력값을 받았는지 검증 ->레벨2
            if(!validateInput(list)) { //값이 3자리 수가 아니거나 중복이 있으면 다시 돌아간다.
                continue;
            }
            // 3. 게임 진행횟수 증가 ->레벨3
            // 4. 스트라이크 개수 계산
            int countstrike = countStrike(list);
            // 5. 정답여부 확인, 만약 정답이면 break 를 이용해 반복문 탈출
            if(countstrike == 3){
                System.out.println("정답입니다!");
                //System.out.println(" ");
                break;
            }
            // 6. 볼 개수 계산
            int countball = countBall(list);
            // 7. 힌트 출력
            baseballGameDisplay.displayHint(countstrike, countball);
        }
        // 게임 진행횟수 반환
        return 0;
    }

    protected boolean validateInput(List<Integer> list) {

//        if(list.get(0)==0){
//            System.out.println("숫자가 0으로 시작할 수 없습니다!");
//            return false;
//        }

        //0이 있는지 검사
        for(int i=0;i<3;i++) {
            if (list.get(i) == 0) {
                System.out.println("올바르지 않은 입력값입니다. 0이 아닌 숫자를 입력하세요!");
                return false;
            }
        }
        //System.out.println(list.size());
        //세자리 수인지 검사
        if(list.size()<3 || list.size()>3){
            System.out.println("올바르지 않은 입력값입니다. 3자리를 입력하세요!");
            return false;
        }
        //중복 검사
        Set<Integer> set = new HashSet<>(list); //중복이 허용되는 list를 중복이 허용되지 않는 set으로 바꾸기
        if(set.size() !=  list.size()){ //list와 set의 크기가 다르면 중복값이 있다!
            System.out.println("올바르지 않은 입력값입니다. 중복되지 않는 숫자를 입력하세요!");
            return false;
        }

        return true;    //위에 조건들에 해당하지 않고 모두 만족하면 true를 반환한다.
    }


    private int countStrike(List<Integer> list) {//스트라이크 갯수 세는 메소드
        countStrike = 0;
        for(int i=0; i<3; i++){
            if(list.get(i)==answerArray[i]){
                countStrike++;
            }
        }
        return countStrike;
    }

    private int countBall(List<Integer> list) { //볼 갯수 세는 메소드
        countBall = 0;
        for(int i=0; i<3; i++){
            for(int j=0; j<3;j++){
                if(list.get(i)==answerArray[j] && i!=j){    //스트라이크가 아니고 볼이면
                    countBall++;
                }
            }
        }
        return countBall;
    }
}
