package ua.edu.ucu.tries;

import ua.edu.ucu.immutable.Queue;

import java.util.Iterator;


public class RWayTrie implements Trie {
    private final static int R = 26;                // 26 symbols in alphabet
    private final static int DEFAULT_SHIFT = 97;    // 'a' ascii code
    private Node root = new Node();
    private int size = 0;

    @Override
    public void add(Tuple t) {
        root = put(root, t.term, t.weight, 0);
        size++;
    }

    @Override
    public boolean contains(String word) {
        Node res = get(root, word, 0);
        return res != null && res.value != null;
    }

    @Override
    public boolean delete(String word) {
        if (contains(word)) {
            root = delete(root, word, 0);
            size--;
            return true;
        }
        return false;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
//        Queue<String> q = new LinkedList<>();
        Queue q = new Queue();
        collect(get(root, s, 0), s, q);
        return q::getIterator;
    }

    @Override
    public int size() {
        return size;
    }

    private Node get(Node x, String word, int d) {
        // Return node associated with word in the subtrie rooted at x.
        if (x == null) {
            return null;
        }
        if (d == word.length()) {
            return x;
        }
        char c = word.charAt(d); // Use dth word char to identify subtrie.
        return get(x.next[c - DEFAULT_SHIFT], word, d + 1);
    }

    private Node put(Node x, String word, Object val, int d) {
        // Change value associated with word if in subtrie rooted at x.
        if (x == null) {
            x = new Node();
        }
        if (d == word.length()) {
            x.value = val;
            return x;
        }
        char c = word.charAt(d); // Use dth word char to identify subtrie.
        x.next[c - DEFAULT_SHIFT] = put(x.next[c - DEFAULT_SHIFT], word, val, d + 1);
        return x;
    }

    private void collect(Node x, String pre, Queue q) {
        if (x == null) {
            return;
        }
        if (x.value != null) {
            q.enqueue(pre);
        }
        for (char c = DEFAULT_SHIFT; c < R + DEFAULT_SHIFT; c++) {
            collect(x.next[c - DEFAULT_SHIFT], pre + c, q);
        }
    }

    private Node delete(Node x, String word, int d) {
        if (x == null) {
            return null;
        }
        if (d == word.length())
            x.value = null;
        else {
            char c = word.charAt(d);
            x.next[c] = delete(x.next[c - DEFAULT_SHIFT], word, d + 1);
        }
        if (x.value != null) {
            return x;
        }
        for (char c = DEFAULT_SHIFT; c < R + DEFAULT_SHIFT; c++) {
            if (x.next[c - DEFAULT_SHIFT] != null) {
                return x;
            }
        }
        return null;
    }

    private static class Node {
        private Object value;
        private Node[] next = new Node[R];
    }
}
