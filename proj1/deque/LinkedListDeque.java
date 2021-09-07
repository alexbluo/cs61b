package deque;
import java.util.*;

public class LinkedListDeque<Item> implements Iterable<Item> {

    private class node {
        public node prev;
        public Item item;
        public node next;
        public node(node p, Item i, node n) {
            prev = p;
            item = i;
            next = n;
        }
    }
    private int size;
    private final node sen;
    public LinkedListDeque() {
        sen = new node(null,  null, null);
        sen.prev = sen;
        sen.next = sen.prev;
    }
    public void addFirst(Item item) {
        node newNode = new node(sen, item, sen.next);
        sen.next.prev = newNode;
        sen.next = newNode;
        size++;
    }
    public void addLast(Item item) {
        node newNode = new node(sen.prev, item, sen);
        sen.prev.next = newNode;
        sen.prev = newNode;

        size++;
    }
    public boolean isEmpty() {
        return sen.next == sen;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        node p = sen.next;
        for (int i = 0; i < size; i++) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println("");
    }
    public Item removeFirst() {
        Item returnThis = sen.next.item;
        sen.next.next.prev = sen;
        sen.next = sen.next.next;
        size--;
        return returnThis;
    }
    public Item removeLast() {
        Item returnThis = sen.prev.item;
        sen.prev.prev.next = sen;
        sen.prev = sen.prev.prev;
        size--;
        return returnThis;
    }
    public Item get(int index) {
        node p = sen.next;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return p.item;
            }
            p = p.next;
        }
        return null;
    }
    public Iterator<Item> iterator() {
        return new LLDequeIterator();
    }

    private class LLDequeIterator implements Iterator<Item> {
        private node wizPos;
        public LLDequeIterator() {
            wizPos = sen.next;
        }
        public boolean hasNext() {
            return wizPos != sen;
        }
        public Item next() {
            Item returnItem = wizPos.item;
            wizPos = wizPos.next;
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
        LinkedListDeque<Item> other = (LinkedListDeque<Item>) o;
        Iterator<Item> seer = other.iterator();
        for (Item item : this) {
            if (!item.equals(seer.next())) {
                return false;
            }
        }
        return true;
    }
    public Item getRecursive(int index) {
        node loc = sen.next;
        if (index == 0) {
            return loc.item;
        } else if (index >= size) {
            return null;
        } else {
            loc = loc.next;
            return getRecursive(index - 1);
        }
    }
    public static void main(String[] args) {
        LinkedListDeque<Integer> list = new LinkedListDeque<>();
        LinkedListDeque<Integer> list2 = new LinkedListDeque<>();
        list.addLast(3);
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        for (int item : list) {
            System.out.println(item);
        }
        System.out.println(list.getRecursive(2));
    }
}