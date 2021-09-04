package deque;

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
        } else if (nextBack == array.length - 1) {
            array[nextBack] = item;
            nextBack = 0;
            size++;
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
        Item first = array[nextBack + 1];
        if (first != null) {
            array[nextFront + 1] = null;
            nextFront++;
            size--;
        }
        return first;
    }
    public Item removeLast() {
        Item last = array[nextBack - 1];
        if (last != null) {
            array[nextBack - 1] = null;
            nextBack--;
            size--;
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
        System.arraycopy(array, 0, newArray, 0, nextBack);
        System.arraycopy(array, nextFront + 1, newArray, newArray.length - (array.length - 1 - nextFront), array.length - 1 - nextFront);
        nextFront = newArray.length - 1 - (array.length - 1 - nextFront);
        // / shrug
    }
    private void resizeDown() {

    }
    public static void main(String[] args) {
        ArrayDeque<Integer> test = new ArrayDeque<>();
        test.addLast(2);
        test.addLast(1);
        test.addLast(0);
        test.addLast(3);
        test.addLast(4);
        test.addLast(5);
        test.addLast(6);
        test.addLast(7);
        test.addLast(8);
        test.removeFirst();
        test.removeLast();
        test.printDeque();
        System.out.print(test.get(4));
    }
}
