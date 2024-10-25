package numplay.lv4;

/** 힌트(스트라이크와 볼 갯수)를 출력하는 메소드입니다. */
public class BaseballGameDisplay {
    public void displayHint(int strike, int ball) {
        if (strike == 0 && ball == 0) {
            System.out.println("아웃");
        } else {
            System.out.println(strike + "스트라이크" + ball + "볼");
        }
    }
}
