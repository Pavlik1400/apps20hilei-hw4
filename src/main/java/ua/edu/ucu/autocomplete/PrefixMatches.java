package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
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
        Queue<String> allKQ = (Queue<String>) trie.wordsWithPrefix(pref);
        Queue<String> filtered = new LinkedList<>();
        for (String word : allKQ) {
            if (word.length() - pref.length() < k) {
                filtered.offer(word);
            }
        }
        return filtered;
    }

    public int size() {
        return trie.size();
    }
}
