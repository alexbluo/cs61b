package deque;

public class LinkedListDeque<Item> {

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
        for (int i = 0; i < index; i++) {

            if (i == index - 1) {
                return p.item;
            }
            p = p.next;
        }
        return null;
    }
    /*public Iterator<Item> iterator() {

    }
    public boolean equals(Object o) {

    }
    public Item getRecursive(int index) {

    }*/
    public static void main(String[] args) {
        LinkedListDeque<Integer> list = new LinkedListDeque<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.removeLast();
        list.printDeque();
        System.out.print(list.get(2));
    }
}