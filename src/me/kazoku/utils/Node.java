package me.kazoku.utils;

public class Node<E> {
    protected Node<E> previous;
    protected E item;
    protected Node<E> next;

    public Node(Node<E> previous, E item, Node<E> next) {
        this.previous = previous;
        this.item = item;
        this.next = next;
    }

    public Node(E item) {
        this(null, item, null);
    }

    public Node(Node<E> previous, E item) {
        this(previous, item, null);
    }

    public void setItem(E item) {
        this.item = item;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public void setPrevious(Node<E> previous) {
        this.previous = previous;
    }

    public E get() {
        return item;
    }

    public Node<E> getNext() {
        return next;
    }

    public Node<E> getPrevious() {
        return previous;
    }

    public boolean hasPrevious() {
        return getPrevious() != null;
    }

    public boolean hasNext() {
        return getNext() != null;
    }
}
