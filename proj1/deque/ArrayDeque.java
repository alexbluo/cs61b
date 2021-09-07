package deque;

import java.util.Iterator;

//making nextFront and nextLast able to go negative/over array.length - 1 and using array.length - nextFront etc mightve PARTIALLY helped this massive abomination of a headache but too late now
// nearly guaranteed to have a bug in it but please let me escape this nightmare
public class ArrayDeque<Item> {
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
    public boolean isEmpty() {
        return size == 0;
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
    public static void main(String[] args) {
        ArrayDeque<Integer> test = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            test.addLast(i);
        }
        test.removeLast();
        test.removeLast();
        test.removeLast();
        test.removeLast();
        test.removeLast();
        test.removeLast();
        test.removeLast();


        test.printDeque();
        System.out.print(test.get(4));
    }
}
