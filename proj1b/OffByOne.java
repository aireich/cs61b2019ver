public class OffByOne implements CharacterComparator {

    /**Check if two chars are off by one*/
    @Override
    public boolean equalChars(char x, char y) {
        int diff = y - x;
        if (diff == 1 || diff == -1) {
            return true;
        }
        return false;
    }
}
