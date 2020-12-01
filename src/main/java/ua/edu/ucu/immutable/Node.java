package ua.edu.ucu.immutable;

import lombok.Getter;
import lombok.Setter;

public final class Node {
    @Setter
    private Node next = null;
    @Setter @Getter
    private Object value = null;

    public Node() { }
    public Node(Object value, Node next) {
        this.next = next;
        this.value = value;
    }
    public Node(Object value) {
        this.value = value;
    }

    public Node next() {
        return next;
    }
}