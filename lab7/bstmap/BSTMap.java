package bstmap;

import edu.princeton.cs.algs4.BST;

import java.util.Iterator;
import java.util.Set;

public class BSTMap <K extends Comparable, V> implements Map61B<K, V> {
    private int size = 0;
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
    private class BSTNode {
        K key;
        V value;
        BSTNode left, right;

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    BSTNode root = null;
    /** Removes all of the mappings from this map. */
    public void clear() {
        root = null;
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return containsKey(key, root);
    }
    private boolean containsKey(K key, BSTNode node) {
        if (node == null) {
            return false;
        }
        if (node.key.equals(key)) {
            return true;
        } else if (key.compareTo(node.key) < 0) {
            return containsKey(key, node.left);
        } else if (key.compareTo(node.key) > 0) {
            return containsKey(key, node.right);
        }
        return false;
    }
    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        return get(key, root);
    }
    private V get(K key, BSTNode node) {
        if (node == null) {
            return null;
        }
        if (node.key.equals(key)) {
            return node.value;
        } else if (key.compareTo(node.key) < 0) {
            return get(key, node.left);
        } else if (key.compareTo(node.key) > 0) {
            return get(key, node.right);
        }
        return null;
    }
    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (size() == 0) {
            root = new BSTNode(key, value);
        } else {
            put(key, value, root);
        }
        size++;
    }

    private BSTNode put(K key, V value, BSTNode node) {
        if (node == null) {
            return new BSTNode(key, value);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = put(key, value, node.left);
        } else if (key.compareTo(node.key) > 0) {
            node.right = put(key, value, node.right);
        }
        return node;
    }
    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
}
