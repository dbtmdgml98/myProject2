import LV3.BaseballGame;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BaseballGame game = new BaseballGame();  // 객체 생성과 동시에 랜덤정답 생김!

        int times = 0;  // 몇번째 게임
        int checkout = 0;   // do~while문 나오기 위한 조건변수

        do {
            System.out.println("환영합니다! 원하시는 번호를 입력해주세요");
            System.out.println("1. 게임 시작하기 2. 게임 기록 보기 3. 종료하기");

            Scanner sc = new Scanner(System.in);
            int selectNumber = sc.nextInt();

            switch (selectNumber) {
                case 1:
                    game.makeAnswerArray();     //새로운 정답 랜덤값을 만든다.
                    System.out.println("< 게임을 시작합니다. >");
                    int k = 0;
                    do {
                        try {
                            game.play();    // play()를 실행하다가
                            times++;
                            break;
                        } catch (IllegalArgumentException e) {  // IllegalArgumentException 예외가 발생하면
                            System.out.println(e.getMessage()); // 예외메세지 출력
                            k++;
                        }
                    } while (k != 0);   // play()가 잘 실행되지 않고 예외가 발생했으면 다시 처음부터 시작한다.
                    break;
                case 2:
                    System.out.println("< 게임 기록 보기 >");

                    for (int i = 0; i < times; i++) {
                        System.out.println((i+1) + "번째 게임 : 시도 횟수 - " + game.getTryCountList().get(i));
                    }
                    break;
                case 3:
                    System.out.println("< 숫자 야구 게임을 종료합니다 >");
                    checkout++;
                    break;
                default:
                    System.out.println("올바른 숫자를 입력해주세요!");
            }
        } while(checkout == 0);
    }
}