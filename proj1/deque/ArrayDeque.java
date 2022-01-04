package deque;

import java.util.Iterator;
import java.util.LinkedList;

public class ArrayDeque<Item> implements Iterable<Item>, Deque<Item> {
    Item[] array;
    private int size;
    private int nextFront = 0;
    private int nextBack = 1;
    public ArrayDeque() {
        array = (Item[]) new Object[8];
        size = 0;
    }
    public void addFirst(Item item) {
        if (size == array.length) {
            resizeUp();
            array[nextFront] = item;
            size++;
        } else if (nextFront == 0) {
            array[nextFront] = item;
            size++;
            nextFront = array.length - 1;
        } else {
            array[nextFront] = item;
            size++;
            nextFront--;
        }
    }
    public void addLast(Item item) {
        if (size == array.length) {
            resizeUp();
            array[nextBack] = item;
            nextBack++;
            size++;
        } else if (nextBack == array.length - 1) {
            array[nextBack] = item;
            size++;
            nextBack = 0;
        } else {
            array[nextBack] = item;
            size++;
            nextBack++;
        }
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        int i = nextFront + 1;
        if (i == array.length) {
            i = 0;
        }
        do {
            System.out.print(array[i] + " ");
            if (i == array.length - 1) {
                i = 0;
            } else {
                i++;
            }
        } while (i != nextBack);
        System.out.println("");
    }
    public Item removeFirst() {
        if (nextFront == array.length - 1) {
            nextFront = 0;
        } else if (array[nextFront + 1] != null){
            nextFront++;
        }

        Item first = array[nextFront];

        if (first != null) {
            array[nextFront] = null;
            size--;
            if (size < (double) array.length / 4 && array.length >= 16) {
                resizeDown();
            }
        }

        return first;
    }
    public Item removeLast() {
        if (nextBack == 0) {
            nextBack = array.length - 1;
        } else if (array[nextBack - 1] != null){
            nextBack--;
        }
        Item last = array[nextBack];
        if (last != null) {
            array[nextBack] = null;
            size--;
            if (size < (double) array.length / 4 && array.length >= 16) {
                resizeDown();
            }
        }

        return last;
    }
    public Item get(int index) {
        if (index > array.length - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return array[(nextFront + 1 + index) % array.length];
    }
    private void resizeUp() {
        Item[] newArray = (Item[]) new Object[size * 2];
        if (nextFront != array.length - 1) {
            System.arraycopy(array, nextFront + 1, newArray, 0, array.length - 1 - nextFront);
            System.arraycopy(array, 0, newArray, array.length - 1 - nextFront, nextBack);
            nextFront = newArray.length - 1;
            nextBack = array.length;
        } else {
            System.arraycopy(array, 1, newArray, 1, array.length - 1);
            newArray[array.length] = array[0];
            nextBack = array.length + 1;
            nextFront = 0;
        }
        array = newArray;
    }
    private void resizeDown() {
        Item[] newArray = (Item[]) new Object[array.length / 2];
        if (nextFront != 0 && nextFront != array.length - 1) {
            System.arraycopy(array, nextFront + 1, newArray, 0, array.length - 1 - nextFront);
            System.arraycopy(array, 0, newArray, array.length - 1 - nextFront, nextBack);
            nextFront = newArray.length - 1;
        } else {
            System.arraycopy(array, 0, newArray, 0, newArray.length);
            nextFront = newArray.length - 1;
        }
        array = newArray;
    }
    public Iterator<Item> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<Item> {
        private int wizPos;
        public ArrayDequeIterator() {
            wizPos = 0;
        }
        public boolean hasNext() {
            return wizPos != size;
        }
        public Item next() {
            Item returnItem = array[wizPos];
            wizPos++;
            return returnItem;
        }
    }
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        ArrayDeque<Item> other = (ArrayDeque<Item>) o;
        Iterator<Item> seer = other.iterator();
        for (int i = 0; i < array.length; i++) {
            if (this.get(i) != other.get(i)) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        ArrayDeque<Integer> test = new ArrayDeque<>();
        ArrayDeque<Integer> test2 = new ArrayDeque<>();
        test.addLast(1);
        test.addLast(2);
        test.addLast(3);
        test2.addLast(1);
        test2.addLast(2);
        test2.addLast(4);

        System.out.print(test.equals(test2));
    }
}
