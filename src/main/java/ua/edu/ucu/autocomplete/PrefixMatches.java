package ua.edu.ucu.autocomplete;

import ua.edu.ucu.immutable.Queue;
import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.Iterator;

/**
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public PrefixMatches() {
        this.trie = new RWayTrie();
    }

    public int load(String... strings) {
        String[] splitted;
        for (String str : strings) {
            splitted = str.split(" ");
            for (String splittedStr : splitted) {
                if (splittedStr.length() > 2) {
                    trie.add(new Tuple(splittedStr, splittedStr.length()));
                }
            }
        }
        return size();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        Iterator<String> allKQ = trie.wordsWithPrefix(pref).iterator();
        Queue filtered = new Queue();

        String word;
        while (allKQ.hasNext()) {
            word = allKQ.next();
            if (word.length() - pref.length() < k) {
                filtered.enqueue(word);
            }
        }
        return filtered::getIterator;
    }

    public int size() {
        return trie.size();
    }
}
