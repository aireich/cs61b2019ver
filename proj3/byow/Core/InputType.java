package byow.Core;

public interface InputType {
    public void initialize(int width, int height);
    public boolean hasNext();
    public char getNext();
    public boolean shouldStart();
    public boolean shouldEnd();
    public boolean checkNextNumber();
    public int getSeed();
    public boolean seedFinished();
    public void addNumber(char c);
    public boolean shouldLoad();
    public void addChar(char c);
    public void addActions(char c);
    public boolean seedShouldFinish();
//    public boolean checkPosition(int i);
//    public char peekAt(int i);

}
