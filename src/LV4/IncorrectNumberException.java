package LV4;

public class IncorrectNumberException extends Exception {
    public IncorrectNumberException() {
        super("올바르지 않은 입력값입니다. 자리수는 3, 4, 5자리 숫자 중에서 선택할 수 있습니다.");
    }
}
