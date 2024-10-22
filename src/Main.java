import LV1.BaseballGame;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        BaseballGame game = new BaseballGame();

        int checkout=0;
        do {
            System.out.println("환영합니다! 원하시는 번호를 입력해주세요");
            System.out.println("1. 게임 시작하기 2. 게임 기록 보기 3. 종료하기");

            Scanner sc = new Scanner(System.in);
            int num = sc.nextInt();

            switch (num) {
                case 1:
                    System.out.println("< 게임을 시작합니다. >");
                    int k = 0;
                    do {
                        try {
                            game.play();    //play()를 실행하다가
                            break;
                        } catch (IllegalArgumentException e) {  //IllegalArgumentException 예외가 발생하면
                            System.out.println(e.getMessage()); //예외메세지 출력
                            k++;
                        }
                    } while (k != 0);   //play()가 잘 실행되지 않고 예외가 발생했으면 다시 처음부터 시작한다.
                case 2:
                    break;
                case 3:
                    checkout++;
            }
        }while(checkout==0);
    }
}