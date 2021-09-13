package deque;

import java.util.*;
// bro idek
public class MaxArrayDeque<Item, T> extends ArrayDeque<Item> implements Iterable<Item>, Comparable<T> {
        Item[] array;
        private int size;
        private int nextFront = 0;
        private int nextBack = 1;
        public MaxArrayDeque(Comparator<Item> c) {
            array = (Item[]) new Object[8];
            size = 0;
        }

    @Override
    public int compareTo(T o) {
        return 0;
    }

    private static class maxComparator<Z> implements Comparator<Z> {
            public int compare(Z o1, Z o2) {
                return o1.compareTo(o2);
            }
        }
        public static Comparator<T> getMaxComparator() {
            return new maxComparator<Z>();
        }
        public int compareTo() {
            return 1;
        }

        public Item max() {
            return c.compare();
        }
        public Item max() {
            return c.compare();
        }
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (o.getClass() != this.getClass()) {
                return false;
            }
            deque.MaxArrayDeque<Item> other = (deque.MaxArrayDeque<Item>) o;
            Iterator<Item> seer = other.iterator();
            for (int i = 0; i < array.length; i++) {
                if (this.get(i) != other.get(i)) {
                    return false;
                }
            }
            return true;
        }
        public static void main(String[] args) {
            MaxArrayDeque<Integer> test = new MaxArrayDeque<Integer>(getMaxComparator());
            test.addLast(1);
            test.addLast(2);
            test.addLast(3);

        }
    }

