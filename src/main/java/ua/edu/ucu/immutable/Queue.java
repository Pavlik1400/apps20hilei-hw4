package ua.edu.ucu.immutable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue {
    private ImmutableLinkedList container;

    public Queue() {
        container = new ImmutableLinkedList();
    }

    public void enqueue(Object obj) {
        container = container.addLast(obj);
    }

    public Object dequeue() {
        Object first = container.getFirst();
        container = container.removeFirst();
        return first;
    }

    public <T> Iterator<T> getIterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return !container.isEmpty();
            }

            @Override
            public T next() {
                if (hasNext()) {
                    return (T) dequeue();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
