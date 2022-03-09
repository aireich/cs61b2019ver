package byow.Core;

public class StringText extends SuperText {

    private String s;

    public StringText(String s) {
        this.s = s;
    }
    @Override
    public boolean hasNext() {
        return index < s.length();
    }
    @Override
    public char getNext() {
        if (hasNext()) {
            char result = s.toCharArray()[index];
            index += 1;
            return result;
        }
        return 0;
    }
}
