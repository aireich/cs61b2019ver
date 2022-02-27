package bearmaps.lab9;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**TrieSe based on HashMap @author aireich**/
public class MyTrieSet implements TrieSet61B{
    private Node root;

    public MyTrieSet() {
        this.root = new Node(false, ';');
    }

    private static class Node{
        private boolean isKey;
        private char value;
        private HashMap<Character, Node> next;

        public Node(boolean isKey, char value) {
            this.isKey = isKey;
            this.value = value;
            this.next = new HashMap<Character, Node>();
        }

        public void setIsKey(boolean b) {
            isKey = b;
        }

        public boolean isKey() {
            return isKey;
        }

        public char getValue() {
            return value;
        }

        public void addNext(char c, Node n, boolean isEnd) {
            if (!containsChar(c)) {
                this.next.put(c, n);
            }
            if (isEnd && containsChar(c)) {
                getNext(c).setIsKey(true);
            }
        }

        public Node getNext(char c) {
            return this.next.get(c);
        }

        public boolean containsChar(char c) {
            return this.next.containsKey(c);
        }

        public HashMap<Character, Node> getAllNext(){
            return next;
        }
    }

    @Override
    public void clear() {
        this.root = new Node(false, ';');
    }

    @Override
    public boolean contains(String key) {
        Node cursor = root;
        boolean found = false;
        int cnt = 0;
        while(cursor != null && !found && cnt < key.length() - 1) {
            found = cursor.containsChar(key.charAt(cnt));
            cnt += 1;
            cursor = cursor.getNext(key.charAt(cnt));
        }
        return found;
    }

    @Override
    public void add(String key) {
        Node cursor = root;
        for(int i = 0;i < key.length() - 1; i++) {
            cursor.addNext(key.charAt(i), new Node(false, key.charAt(i)), false);
            cursor = cursor.getNext(key.charAt(i));
        }
        cursor.addNext(key.charAt(key.length() - 1), new Node(true, key.charAt(key.length() - 1)), true);
    }

    private void colHelp(String s, List<String> l, Node n) {
        if (n.isKey()) {
            l.add(s);
        }
        for (Character c: n.getAllNext().keySet()) {
            colHelp(s + c, l, n.getNext(c));
        }
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        if (!contains(prefix)) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        Node cursor = root;
        int cnt = 0;
        while(cnt < prefix.length()) {
            cursor = cursor.getNext(prefix.charAt(cnt));
            cnt += 1;
        }
        for (Character c : cursor.getAllNext().keySet()) {
            colHelp(prefix + c + "", result, cursor.getNext(c));
        }
        return result;

    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        MyTrieSet t = new MyTrieSet();
        t.add("hello");
        t.add("hi");
        t.add("help");
        t.add("zebra");
    }
}
