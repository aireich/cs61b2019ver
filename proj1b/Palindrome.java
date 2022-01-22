/**Proj1b check if a word is palindrome*/
public class Palindrome {

    /** Convert a string to a deque*/
    public Deque<Character> wordToDeque(String word) {
        Deque result = new ArrayDeque();
        for (int i = 0; i < word.length(); i++) {
            result.addLast(word.charAt(i));
        }
        return result;
    }

    /** Check if a word is palindrome. Need to use private helper */
    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindromeHelper(wordDeque, true);
    }

    /** A private helper for isPalindrome. Use a recursion method*/
    private boolean isPalindromeHelper(Deque wordDeque, boolean result) {
        if (wordDeque.size() == 0 || wordDeque.size() == 1) {
            return result;
        }
        result = (wordDeque.removeFirst() == wordDeque.removeLast()) && result;
        return isPalindromeHelper(wordDeque, result);
    }

    /** Check if a word is a palindrome but off by one*/
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindromeHelperOffByOne(wordDeque, true, cc);
    }

    /**private helper for isPalindrome but off by one*/
    private boolean isPalindromeHelperOffByOne(Deque wordDeque,
                                               boolean result, CharacterComparator cc) {
        if (wordDeque.size() == 0 || wordDeque.size() == 1) {
            return result;
        }
        result = (cc.equalChars((char) wordDeque.removeFirst(),
                (char) wordDeque.removeLast())) && result;
        return isPalindromeHelperOffByOne(wordDeque, result, cc);
    }

    /** Check if a word is a palindrome but off by N*/
    public boolean isPalindrome(String word, CharacterComparator cc, int N) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindromeHelperOffByN(wordDeque, true, cc, N);
    }

    /**private helper for isPalindrome but off by one*/
    private boolean isPalindromeHelperOffByN(Deque wordDeque, boolean result,
                                             CharacterComparator cc, int N) {
        if (wordDeque.size() == 0 || wordDeque.size() == 1) {
            return result;
        }
        result = (cc.equalChars((char) wordDeque.removeFirst(),
                (char) wordDeque.removeLast())) && result;
        return isPalindromeHelperOffByN(wordDeque, result, cc, N);
    }
}
