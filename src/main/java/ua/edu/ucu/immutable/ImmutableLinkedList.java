package ua.edu.ucu.immutable;


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
    public int size() {
        return size;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /* ****************************
    Methods for only LinkedList
     */
    public ImmutableLinkedList addLast(Object e) {
        return (ImmutableLinkedList) addAll(size, new Object[]{e});
    }

    public Object getFirst() {
        return get(0);
    }

    public ImmutableLinkedList removeFirst() {
        return (ImmutableLinkedList) remove(0);
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
}

