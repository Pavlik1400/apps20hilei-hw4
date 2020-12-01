package ua.edu.ucu.immutable;

import java.util.Iterator;

public class Queue {
    private ImmutableLinkedList container;

    public Queue() {
        container = new ImmutableLinkedList();
    }

    public Queue(Object[] objects) {
        container = new ImmutableLinkedList(objects);
    }

    public int size() {
        return container.size();
    }

    public Object peek() {
        return container.getFirst();
    }

    public Object last() {
        return container.getLast();
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
                return (T) dequeue();
            }
        };
    }
}
