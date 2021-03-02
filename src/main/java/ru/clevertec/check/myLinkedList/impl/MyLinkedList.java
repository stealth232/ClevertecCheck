package ru.clevertec.check.myLinkedList.impl;

import ru.clevertec.check.myLinkedList.MyLinked;

public class MyLinkedList<E> implements MyLinked<E> {

    private Node<E> first;
    private Node<E> last;
    int size;

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> previous;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.previous = prev;
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            return element.toString();
        }
    }

    @Override
    public void add(E element) {
        Node <E> newNode = new Node(null,element,null);
        if (first == null) {
            newNode.next = null;
            newNode.previous = null;
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void addByIndex(int index, E element) {
        if (index < 0 || index >= size) {
            int ind = size-1;
            throw new IndexOutOfBoundsException("Size of list only " + ind + ", you try put element on " + index + "th position");
        }
        Node <E> newNode = new Node(null,element,null);
        if (index == 0) {
            add(element);
        }
        if (index == size) {
            last.next = newNode;
            last = newNode;
        }
        Node oldNode = first;
        for (int i = 0; i < index; i++) {
            oldNode = oldNode.next;
        }
        Node <E> oldPrevious = oldNode.previous;
        oldPrevious.next = newNode;
        oldNode.previous = newNode;

        newNode.previous = oldPrevious;
        newNode.next = oldNode;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index > size - 1) {
            int ind = size-1;
            throw new IndexOutOfBoundsException("Only " + ind + " indexes in list, you try to delete " + index + "th");
        }
        if (index == 0) {
            E x = first.element;
            first = first.next;
            size--;
            return x;

        } else {
            Node <E> node = getNodeBeforeByIndex(index);
            Node <E> tmp = getByIndex(index);
            node.next = tmp.next;
            size--;
            return tmp.element;
        }
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            int ind = size-1;
            throw new IndexOutOfBoundsException("Only " + ind + " indexes in list, you try to find " + index + "th");
        }
        Node <E> res = first;
        for (int i = 0; i < index; i++) {
            res = res.next;
        }
        return  res.element;
    }

    @Override
    public void addFirst(E e){
        Node<E> tmp = first;
        Node<E> newNode = new Node<>(null,e,tmp);
        first = newNode;
        if (tmp == null)
            last = newNode;
        else tmp.previous = newNode;
        size++;
    }

    @Override
    public void addLast(E e) {
        add(e);
    }

    @Override
    public void removeFirst() {
        remove(0);
    }

    @Override
    public E getFirstElem() {
        return  get(0);
    }

    @Override
    public E getLastElem() {
        return get(size-1);
    }

    public Node <E> getByIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        int tmpIndex = 0;
        if (first == null) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return first;
        }
        Node <E> node = first;
        while (node.next != null) {
            node = node.next;
            tmpIndex++;
            if (tmpIndex == index) {
                return node;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public Node <E> getNodeBeforeByIndex(int index) {
        if (index <= 0 || index > size - 1) {
            return null;
        }

        int count = 0;
        Node node = first;
        while (node.next != null) {
            if (count == index - 1) {
                return node;
            }
            count++;
            node = node.next;
        }
        return null;
    }

    @Override
    public String toString() { StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MyLinkedList.class.getSimpleName());
        stringBuilder.append("[values=");
        for (int i = 0; i < size; i++) {
            stringBuilder.append(get(i));
                if(i < size - 1){
                    stringBuilder.append(", ");
                }
            }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
