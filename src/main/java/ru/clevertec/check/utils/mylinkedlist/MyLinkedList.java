package ru.clevertec.check.utils.mylinkedlist;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyLinkedList<E> implements List<E> {

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
    public boolean add(E element) {
        Node<E> newNode = new Node(null, element, null);
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
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Method not supported");
    }

    public void addByIndex(int index, E element) {
        if (index < 0 || index >= size) {
            int ind = size - 1;
            throw new IndexOutOfBoundsException("Size of list only " + ind + ", you try put element on " + index + "th position");
        }
        Node<E> newNode = new Node(null, element, null);
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
        Node<E> oldPrevious = oldNode.previous;
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
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index > size - 1) {
            int ind = size - 1;
            throw new IndexOutOfBoundsException("Only " + ind + " indexes in list, you try to delete " + index + "th");
        }
        if (index == 0) {
            E x = first.element;
            first = first.next;
            size--;
            return x;

        } else {
            Node<E> node = getNodeBeforeByIndex(index);
            Node<E> tmp = getByIndex(index);
            node.next = tmp.next;
            size--;
            return tmp.element;
        }
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            int ind = size - 1;
            throw new IndexOutOfBoundsException("Only " + ind + " indexes in list, you try to find " + index + "th");
        }
        Node<E> res = first;
        for (int i = 0; i < index; i++) {
            res = res.next;
        }
        return res.element;
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public void add(int index, E element) {

    }

    public void addFirst(E e) {
        Node<E> tmp = first;
        Node<E> newNode = new Node<>(null, e, tmp);
        first = newNode;
        if (tmp == null)
            last = newNode;
        else tmp.previous = newNode;
        size++;
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[size];
        for (int i = 0; i < size; i++) {
            objects[i] = get(i);
        }
        return objects;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Method not supported");
    }

    public void addLast(E e) {
        add(e);
    }

    public void removeFirst() {
        remove(0);
    }

    public E getFirstElem() {
        return get(0);
    }

    public E getLastElem() {
        return get(size - 1);
    }

    public Node<E> getByIndex(int index) {
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
        Node<E> node = first;
        while (node.next != null) {
            node = node.next;
            tmpIndex++;
            if (tmpIndex == index) {
                return node;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public Node<E> getNodeBeforeByIndex(int index) {
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
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[");
        for (int i = 0; i < size; i++) {
            stringBuilder.append(get(i));
            if (i < size - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
