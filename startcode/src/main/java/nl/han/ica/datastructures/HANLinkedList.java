package nl.han.ica.datastructures;

import java.util.Iterator;

public class HANLinkedList<T> implements IHANLinkedList<T> {

    private LinkedListNode<T> head;
    private int size;

    public HANLinkedList() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void addFirst(T value) {
        LinkedListNode<T> node = new LinkedListNode<>(value);
        node.next = head;
        head = node;
        this.size++;
    }

    @Override
    public void clear() {
        head = null;
        this.size = 0;
    }

    @Override
    public void insert(int index, T value) {
        if(index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException();
        }

        if(index == 0) {
            addFirst(value);
            return;
        }

        LinkedListNode<T> current = head;
        for(int i = 0; i < index - 1; i++) {
            current = current.next;
        }

        LinkedListNode<T> newNode = new LinkedListNode<>(value);
        newNode.next = current.next;
        current.next = newNode;

        this.size++;
    }

    @Override
    public void delete(int pos) {
        if(pos < 0 || pos >= this.size) {
            throw new IndexOutOfBoundsException();
        }

        if(pos == 0) {
            removeFirst();
            return;
        }

        LinkedListNode<T> current = head;
        for(int i = 0; i < pos - 1; i++) {
            current = current.next;
        }

        current.next = current.next.next;
        this.size--;
    }

    @Override
    public T get(int pos) {
        if(pos < 0 || pos >= this.size) {
            throw new IndexOutOfBoundsException();
        }

        LinkedListNode<T> current = head;
        for(int i = 0; i < pos; i++) {
            current = current.next;
        }

        return current.value;
    }

    @Override
    public void removeFirst() {
        if(head == null) {
            throw new IllegalStateException("List is empty");
        }

        head = head.next;
        this.size--;
    }

    @Override
    public T getFirst() {
        if(head == null) {
            throw new IllegalStateException("List is empty");
        }

        return head.value;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < size;
            }

            @Override
            public T next() {
                return get(current++);
            }
        };
    }
}
