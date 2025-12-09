import java.util.Random;

/**
 * SkipList data structure for storing Comparable objects.
 * This implementation uses a probabilistic skip list with random heights.
 *
 * @author Jonah Schepers
 * @author Rowan Muhoberac
 * @version December 8, 2025
 * @param <E>
 *            The type of elements stored in the skip list (must be Comparable)
 */
public class SkipList<E extends Comparable<E>> {
    /**
     * Maximum level for the skip list
     */
    private static final int MAX_LEVEL = 16;

    /**
     * The head node of the skip list
     */
    private SkipNode<E> head;

    /**
     * The current maximum level in the skip list
     */
    private int maxLevel;

    /**
     * Random number generator for determining node heights
     */
    private Random random;

    /**
     * The number of nodes in the skip list (excluding head)
     */
    private int size;

    /**
     * Inner class representing a node in the skip list
     */
    private static class SkipNode<E> {
        /**
         * The data stored in this node
         */
        private E data;

        /**
         * Array of forward pointers to next nodes at each level
         */
        private SkipNode<E>[] forward;

        /**
         * The level of this node (height)
         */
        private int level;

        /**
         * Constructor for SkipNode
         *
         * @param data
         *            The data to store
         * @param level
         *            The level (height) of this node
         */
        @SuppressWarnings("unchecked")
        public SkipNode(E data, int level) {
            this.data = data;
            this.level = level;
            this.forward = new SkipNode[level + 1];
        }
    }

    /**
     * Constructor for SkipList
     *
     * @param random
     *            Random number generator for node heights
     */
    @SuppressWarnings("unchecked")
    public SkipList(Random random) {
        this.random = random;
        if (this.random == null) {
            this.random = new Random();
        }
        this.maxLevel = 0;
        this.size = 0;
        // Create head node with maximum level
        this.head = new SkipNode<E>(null, MAX_LEVEL);
    }


    /**
     * Generate a random level for a new node
     * Uses OpenDSA approach: Pick a level using a geometric distribution
     *
     * @return A random level between 0 and MAX_LEVEL
     */
    private int randomLevel() {
        int lev;
        for (lev = 0; Math.abs(random.nextInt()) % 2 == 0
            && lev < MAX_LEVEL; lev++) {
            // Do nothing - continue loop while random is even
        }
        return lev;
    }


    /**
     * Insert an element into the skip list
     *
     * @param element
     *            The element to insert
     * @return True if inserted successfully, false if element already exists
     */
    @SuppressWarnings("unchecked")
    public boolean insert(E element) {
        if (element == null) {
            return false;
        }

        SkipNode<E>[] update = new SkipNode[MAX_LEVEL + 1];
        SkipNode<E> current = head;

        for (int i = maxLevel; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].data
                .compareTo(element) < 0) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        current = current.forward[0];

        if (current != null && current.data.compareTo(element) == 0) {
            return false;
        }

        int newLevel = randomLevel();
        if (newLevel > maxLevel) {
            for (int i = maxLevel + 1; i <= newLevel; i++) {
                update[i] = head;
            }
            maxLevel = newLevel;
        }

        SkipNode<E> newNode = new SkipNode<E>(element, newLevel);

        for (int i = 0; i <= newLevel; i++) {
            newNode.forward[i] = update[i].forward[i];
            update[i].forward[i] = newNode;
        }

        size++;
        return true;
    }


    /**
     * Remove an element from the skip list
     *
     * @param element
     *            The element to remove
     * @return The removed element, or null if not found
     */
    @SuppressWarnings("unchecked")
    public E remove(E element) {
        if (element == null) {
            return null;
        }

        SkipNode<E>[] update = new SkipNode[MAX_LEVEL + 1];
        SkipNode<E> current = head;

        for (int i = maxLevel; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].data
                .compareTo(element) < 0) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        current = current.forward[0];

        if (current == null || current.data.compareTo(element) != 0) {
            return null;
        }

        for (int i = 0; i <= current.level; i++) {
            update[i].forward[i] = current.forward[i];
        }

        while (maxLevel > 0 && head.forward[maxLevel] == null) {
            maxLevel--;
        }

        size--;
        return current.data;
    }


    /**
     * Find an element in the skip list
     *
     * @param element
     *            The element to find
     * @return The element if found, null otherwise
     */
    public E find(E element) {
        if (element == null) {
            return null;
        }

        SkipNode<E> current = head;

        for (int i = maxLevel; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].data
                .compareTo(element) < 0) {
                current = current.forward[i];
            }
        }

        current = current.forward[0];

        if (current != null && current.data.compareTo(element) == 0) {
            return current.data;
        }

        return null;
    }


    /**
     * Get all elements in a range
     *
     * @param min
     *            The minimum element (inclusive)
     * @param max
     *            The maximum element (inclusive)
     * @return Array of elements in the range, or null if invalid range
     */
    @SuppressWarnings("unchecked")
    public E[] range(E min, E max) {
        if (min == null || max == null) {
            return null;
        }
        if (min.compareTo(max) > 0) {
            return null;
        }

        SkipNode<E> current = head;
        for (int i = maxLevel; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].data
                .compareTo(min) < 0) {
                current = current.forward[i];
            }
        }
        current = current.forward[0];

        int count = 0;
        SkipNode<E> temp = current;
        while (temp != null && temp.data.compareTo(max) <= 0) {
            count++;
            temp = temp.forward[0];
        }

        E[] result = (E[])new Comparable[count];
        int index = 0;
        while (current != null && current.data.compareTo(max) <= 0) {
            result[index++] = current.data;
            current = current.forward[0];
        }

        return result;
    }


    /**
     * Get a string representation of the skip list in alphabetical order
     *
     * @return String representation
     */
    public String toString() {
        if (size == 0) {
            return "SkipList is empty";
        }

        StringBuilder sb = new StringBuilder();

        int actualMaxLevel = -1;
        SkipNode<E> temp = head.forward[0];
        while (temp != null) {
            if (temp.level > actualMaxLevel) {
                actualMaxLevel = temp.level;
            }
            temp = temp.forward[0];
        }

        sb.append("Node has depth ");
        sb.append(actualMaxLevel + 1);
        sb.append(", Value (null)\r\n");

        SkipNode<E> current = head.forward[0];
        int nodeCount = 0;

        while (current != null) {
            nodeCount++;
            sb.append("Node has depth ");
            sb.append(current.level + 1);
            sb.append(", Value (");
            if (current.data == null) {
                sb.append("null");
            }
            else {
                sb.append(current.data.toString());
            }
            sb.append(")\r\n");
            current = current.forward[0];
        }

        sb.append(nodeCount);
        sb.append(" skiplist nodes printed");
        return sb.toString();
    }


    /**
     * Get the size of the skip list
     *
     * @return The number of elements
     */
    public int size() {
        return size;
    }


    /**
     * Check if the skip list is empty
     *
     * @return True if empty
     */
    public boolean isEmpty() {
        return size == 0;
    }
}
