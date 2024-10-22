import LV1.BaseballGame;

public class Main {
    public static void main(String[] args){
        BaseballGame game = new BaseballGame();

        System.out.println("< 게임을 시작합니다. >");

        int k=0;
        do {
            try {
                game.play();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                k++;
            }
        }while(k!=0);
    }
}