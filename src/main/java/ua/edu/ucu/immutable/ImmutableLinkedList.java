package ua.edu.ucu.immutable;
import ua.edu.ucu.immutable.Node;


public final class ImmutableLinkedList implements ImmutableList {
    private final Node head;
    private final Node tail;
    private final int size;

    public ImmutableLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public ImmutableLinkedList(Node head) {
        this.head = new Node();
        this.tail = new Node();
        size = copyLinkedList(head, this.head,  this.tail);
    }

    public ImmutableLinkedList(Object[] objects) {
        this.head = new Node();
        this.tail = new Node();
        Node[] converted = arrToLinkedList(objects);
        size = copyLinkedList(converted[0], this.head,  this.tail);
    }

    @Override
    public ImmutableList add(Object e) {
        return addAll(new Object[]{e});
    }

    @Override
    public ImmutableList add(int index, Object e) {
        return addAll(index, new Object[]{e});
    }

    @Override
    public ImmutableList addAll(Object[] c) {
        return addAll(size, c);
    }

    @Override
    public ImmutableList addAll(int index, Object[] c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        // copy list
        Node newHead = new Node();
        copyLinkedList(head, newHead, new Node());

        // 0 - head, 1 - tail
        Node[] convertedHeadTail = arrToLinkedList(c);
        Node current = newHead;

        // if index is 0, we have to update newHead
        if (index == 0) {
            newHead = convertedHeadTail[0];
            // if size is 0 then we will add extra Node to the end
            if (size != 0) {
                convertedHeadTail[1].setNext(current);
            }
        } else {
            // search for node with index <index - 1>
            for (int idx = 0; idx < index-1; idx++) {
                current = current.next();
            }
            // connect right most node
            convertedHeadTail[1].setNext(current.next());
            // connect left most node
            current.setNext(convertedHeadTail[0]);
        }
        return new ImmutableLinkedList(newHead);
    }

    @Override
    public Object get(int index) {
        checkIndex(index);
        Node current = head;
        for (int idx = 0; idx < index; idx++) {
            current = current.next();
        }
        return current.getValue();
    }

    @Override
    public ImmutableList remove(int index) {
        checkIndex(index);
        Node newHead = new Node();
        // copy list
        copyLinkedList(head, newHead, new Node());
        Node current = newHead;
        // go to node that was before on <index> - 1 position
        for (int idx = 0; idx < index-1; idx++) {
            current = current.next();
        }
        // delete node
        if (index == 0) {
            newHead = newHead.next();
        } else {
            current.setNext(current.next().next());
        }
        return new ImmutableLinkedList(newHead);
    }

    @Override
    public ImmutableList set(int index, Object e) {
        checkIndex(index);
        Node newHead = new Node();
        // copy list
        copyLinkedList(head, newHead, new Node());
        Node current = newHead;
        // go to node that was before on <index> position
        for (int idx = 0; idx < index; idx++) {
            current = current.next();
        }
        // set value
        current.setValue(e);
        return new ImmutableLinkedList(newHead);
    }

    @Override
    public int indexOf(Object e) {
        Node current = head;
        int idx = 0;
        while (current != null) {
            if (current.getValue() == e) {
                return idx;
            }
            current = current.next();
            idx++;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ImmutableList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Object[] toArray() {
        Node current = head;
        Object[] res = new Object[size];
        for (int idx = 0; idx < size; idx++) {
            res[idx] = current.getValue();
            current = current.next();
        }
        return res;
    }

    /* ****************************
    Methods for only LinkedList
     */
    public ImmutableLinkedList addFirst(Object e) {
        return (ImmutableLinkedList) addAll(0, new Object[]{e});
    }

    public ImmutableLinkedList addLast(Object e) {
        return (ImmutableLinkedList) addAll(size, new Object[]{e});
    }

    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        return get(size-1);
    }

    public ImmutableLinkedList removeFirst() {
        return (ImmutableLinkedList) remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return (ImmutableLinkedList) remove(size-1);
    }

    /* ****************************
    Additional methods
    */
    // returns size of copied list
    public static int copyLinkedList(Node head, Node newHead, Node newTail) {
        if (head == null) {
            return 0;
        }
        Node current = null;
        Node next = newHead;
        int size = 0;

        Node copied = head;
        while (copied.next() != null) {
            // update nodes
            current = next;
            next = new Node();
            // copy node
            current.setValue(copied.getValue());
            current.setNext(next);
            // move to next in the chain
            copied = copied.next();
            size++;
        }
        // if chain has only one node
        if (current == null) {
            newHead.setValue(copied.getValue());
            newHead.setNext(null);
            newTail.setValue(copied.getValue());
            newTail.setNext(null);
            return 1;
        } else {
            // update tail
            current.setNext(newTail);
            newTail.setValue(copied.getValue());
            return ++size;
        }
    }

    // returns array, where first argument is head, second is tail
    public static Node[] arrToLinkedList(Object[] arr) {
        if (arr.length == 0) {
            throw new NegativeArraySizeException(
                    "Passing empty array as argument"
            );
        }
        Node head = new Node();
        Node current = head;
        Node previous = null;
        for (Object obj: arr) {
            current.setValue(obj);
            current.setNext(new Node());
            previous = current;
            current = current.next();
        }
        previous.setNext(null);
        return new Node[]{head, previous};
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public String toString() {
        StringBuilder res = new StringBuilder("[");
        Node current = head;
        if (size > 0) {
            res.append(current.getValue().toString());
            current = current.next();
        }
        while (current != null) {
            res.append(", ").append(current.getValue().toString());
            current = current.next();
        }
        return res.append("]").toString();

    }
}

