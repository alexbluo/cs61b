package deque;
import java.util.*;

public class LinkedListDeque<Item> implements Iterable<Item>, Deque<Item> {

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
    private node currentNode;
    private int currentIndex;
    public LinkedListDeque() {
        sen = new node(null,  null, null);
        sen.prev = sen;
        sen.next = sen.prev;
        currentNode = sen.next;
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
        if (size != 0) {
            size--;
        }
        return returnThis;
    }
    public Item removeLast() {
        Item returnThis = sen.prev.item;
        sen.prev.prev.next = sen;
        sen.prev = sen.prev.prev;
        if (size != 0) {
            size--;
        }
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
        if (index >= size) {
            return null;
        }
        if (currentIndex == index) {
            Item temp = currentNode.next.item;
            currentNode = sen.next;
            currentIndex = 0;
            return temp;
        } else {
            currentNode = currentNode.next;
            currentIndex++;
            return getRecursive(index);
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

        System.out.println(list.isEmpty());
    }
}