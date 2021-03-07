package ru.clevertec.check.utils.mylinkedlist;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static ru.clevertec.check.exception.ProductExceptionConstants.METHOD_NOT_SUPPORTED;

public class MyLinkedList<E> implements List<E> {

    private Node<E> first;
    private Node<E> last;
    int size;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

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
        writeLock.lock();
        try {
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
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void add(int index, E element) {
        writeLock.lock();
        try {
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
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public int size() {
        readLock.lock();
        try {
            return size;
        } finally {
            readLock.unlock();
        }

    }

    @Override
    public E remove(int index) {
        writeLock.lock();
        try {
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
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public E get(int index) {
        readLock.lock();
        try {
            if (index < 0 || index >= size) {
                int ind = size - 1;
                throw new IndexOutOfBoundsException("Only " + ind + " indexes in list, you try to find " + index + "th");
            }
            Node<E> res = first;
            for (int i = 0; i < index; i++) {
                res = res.next;
            }
            return res.element;
        } finally {
            readLock.unlock();
        }
    }

    public void addFirst(E e) {
        writeLock.lock();
        try {
            Node<E> tmp = first;
            Node<E> newNode = new Node<>(null, e, tmp);
            first = newNode;
            if (tmp == null)
                last = newNode;
            else tmp.previous = newNode;
            size++;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Object[] toArray() {
        readLock.lock();
        try {
            Object[] objects = new Object[size];
            for (int i = 0; i < size; i++) {
                objects[i] = get(i);
            }
            return objects;
        } finally {
            readLock.unlock();
        }
    }

    public void addLast(E e) {
        writeLock.lock();
        try {
            add(e);
        } finally {
            writeLock.unlock();
        }
    }

    public void removeFirst() {
        writeLock.lock();
        try {
            remove(0);
        } finally {
            writeLock.unlock();
        }
    }

    public E getFirstElem() {
        readLock.lock();
        try {
            return get(0);
        } finally {
            readLock.unlock();
        }
    }

    public E getLastElem() {
        readLock.lock();
        try {
            return get(size - 1);
        } finally {
            readLock.unlock();
        }
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

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(METHOD_NOT_SUPPORTED);
    }
}
