package byow.Core;

public class StringInput implements InputType{

    private String s;
    private int index;
    private boolean started;
    private boolean ended;
    private int seed;
    private StringBuilder sb;

    public StringInput(String s) {
        this.s = s;
        this.index = 0;
        this.started = false;
        this.ended = false;
        this.seed = 0;
        this.sb = new StringBuilder();
    }

    @Override
    public boolean hasNext() {
        return index < s.length();
    }

    public boolean checkPosition(int i) {
        return i < s.length();
    }

    public char peekAt(int i) {
        return Character.toLowerCase(s.charAt(i));
    }

    @Override
    public char getNext() {
        char c = Character.toLowerCase(s.charAt(index));
        index += 1;
        return c;
    }

    @Override
    public boolean shouldStart() {
        return Character.toLowerCase(s.charAt(index)) == 'n';
    }

    @Override
    public boolean shouldEnd() {
        if (checkPosition(index + 1)) {
            return Character.toLowerCase(s.charAt(index)) == ':' && Character.toLowerCase(peekAt(index + 1)) == 'q';
        }
        return false;
    }

    @Override
    public boolean checkNextNumber() {
        return peekAt(index) == '1' || peekAt(index) == '2'|| peekAt(index) == '3' || peekAt(index) == '4' || peekAt(index) == '5' ||
                peekAt(index) == '6' ||peekAt(index) == '7' ||peekAt(index) == '8' || peekAt(index) == '9' ||peekAt(index) == '0' ;
    }

    @Override
    public void addNumber(char c) {
        sb.append(c);
    }

    @Override
    public int getSeed() {
        return Integer.parseInt(sb.toString());
    }

    @Override
    public boolean seedFinished() {
        return !checkNextNumber() && seed != 0 && peekAt(index + 1) == 's';
    }

    @Override
    public boolean shouldLoad() {
        return Character.toLowerCase(s.charAt(index)) == 'l';
    }
}
