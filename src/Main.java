import LV4.BaseballGame;
import LV4.IncorrectNumberException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BaseballGame game = new BaseballGame();  // 객체 생성과 동시에 랜덤정답 생김!

        /** 게임 횟수 변수 */
        int times = 0;

        /** do~while문 나오기 위한 조건 변수 */
        int checkout = 0;

        /** 자릿수 설정 변수 */
        int digits = 0;

        do {
            System.out.println("환영합니다! 원하시는 번호를 입력해주세요");
            System.out.println("0. 자리수 설정 1. 게임 시작하기 2. 게임 기록 보기 3. 종료하기");

            Scanner sc = new Scanner(System.in);
            int selectNumber = sc.nextInt();

            switch (selectNumber) {
                case 0:
                    System.out.println("설정하고자 하는 자리수를 입력하세요.");
                    digits = sc.nextInt();

                    System.out.println(digits + "자리수 난이도로 설정되었습니다.");

                case 1:
                    /**
                     * 새로운 정답 랜덤값을 만듭니다.
                     * 실행 중에 예외가 발생하면 핸들링하여 예외문구를 출력합니다.
                     */
                    try {
                        game.makeAnswerArray(digits);
                    } catch (IncorrectNumberException e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    System.out.println("< 게임을 시작합니다. >");

                    /**
                     * 실행 중에 예외가 발생하면 핸들링하여 예외문구를 출력합니다.
                     * 예외가 발생했으면 다시 처음부터 시작합니다.
                     */
                    int k = 0;
                    do {
                        try {
                            game.play();
                            times++;
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            k++;
                        }
                    } while (k != 0);
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
        } while (checkout == 0);
    }
}