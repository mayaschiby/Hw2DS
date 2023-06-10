import java.util.ArrayList;

/**
 * BinomialHeap
 * <p>
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */
public class BinomialHeap {
    public static void main(String[] args) {
        BinomialHeap o = new BinomialHeap();
//        for (int i = 0; i < 10; i++) {
//            o.insert(i, "hi");
//        }
        int[] arr = new int[] {15, 35, 20, 31, 40, 58, 45, 67, 9, 33, 23};
        for (int i = 0; i < arr.length; i++) {
            o.insert(arr[i], "hi");
        }
        int n = 0;

    }
    public int size;
    public HeapNode last;
    public HeapNode min;


    public BinomialHeap() {
        size = 0;
        last = null;
        min = null;
    }

    /**
     * pre: key > 0
     * <p>
     * Insert (key,info) into the heap and return the newly generated HeapItem.
     */
    public HeapItem insert(int key, String info) {
        size += 1;
        if (this.isEmpty()) {
            HeapItem insertedItem = insertToEmpty(key, info);
            return insertedItem;

        }
        // now assume that the heap is not empty
        HeapNode firstNode = this.getFirstNode();

        // insert when there is only one tree in the heap or when linking isn't necessary.
        if (firstNode.rank != 0 || last.next == null) {
            HeapNode newNode = new HeapNode(null, null, firstNode, null);
            HeapItem newItem = new HeapItem(key, info, newNode);
            newNode.item = newItem;
            if (firstNode.rank == 0) {
                link(firstNode, newNode);
                last = min;
                return newItem;
            }
            updateMin(key, newNode);  // updates min if necessary
            HeapNode tmp;
            if (last.next == null) {
                tmp = last;
            } else {
                tmp = last.next;
            }
            last.next = newNode;
            newNode.next = tmp;
            return newItem;
        }
        else {
            HeapNode newNode = new HeapNode(null, null, firstNode, null);
            HeapItem newItem = new HeapItem(key, info, newNode);
            newNode.item = newItem;
            while (firstNode != newNode && firstNode.rank == newNode.rank) {
                HeapNode node = link(firstNode, newNode);
                if (firstNode.next == null) {
                    min = node;
                    last = node;
                    return newItem;
                }
                newNode = firstNode;
                firstNode = node.next;
                updateMin(key, newNode);  // updates min if necessary
            }
            return newItem;
        }
//        return new HeapItem(3, null, null); // should be replaced by student code
    }

    public HeapNode link(HeapNode x, HeapNode y) {
        if (x.item.key > y.item.key) {
            HeapNode tmp = x;
            x = y;
            y = tmp;
        }
        if (x.next == y) {
            x.next = null;
        }
//        y.next = x.child;
        if (x.child == null) {
            y.next = y;
        }
        else {
            if (x.child.next != null) {
                HeapNode tmp = x.child.next;
                x.child.next = y;
                y.next = tmp;
            }
        }
        x.child = y;
        y.parent = x;
        last.next = x;
        x.rank += 1;
        return x;
    }

    public void updateMin(int key, HeapNode node) {
        if (key < min.item.key) {
            min = node;
        }
    }

    public HeapNode getFirstNode() {
        int i = 0;
        if (last.next == null) {
            return min;
        }
        else {
            return last.next;
        }
    }

    public boolean isEmpty() {
        return this.min == null;
    }

    public HeapItem insertToEmpty(int key, String info) {
        HeapNode newNode = new HeapNode(null, null, null, null);
        HeapItem newItem = new HeapItem(key, info, newNode);
        newNode.item = newItem;
        min = newNode;
        last = newNode;
        return newItem;
    }


    /**
     * Delete the minimal item
     */
    public void deleteMin() {
        return; // should be replaced by student code

    }

    /**
     * Return the minimal HeapItem
     */
    public HeapItem findMin() {
        return null; // should be replaced by student code
    }

    /**
     * pre: 0<diff<item.key
     * <p>
     * Decrease the key of item by diff and fix the heap.
     */
    public void decreaseKey(HeapItem item, int diff) {
        return; // should be replaced by student code
    }

    /**
     * Delete the item from the heap.
     */
    public void delete(HeapItem item) {
        return; // should be replaced by student code
    }

    /**
     * Meld the heap with heap2
     */
    public void meld(BinomialHeap heap2) {
        return; // should be replaced by student code
    }

    /**
     * Return the number of elements in the heap
     */
    public int size() {
        return 42; // should be replaced by student code
    }

    /**
     * The method returns true if and only if the heap
     * is empty.
     */
    public boolean empty() {
        return false; // should be replaced by student code
    }

    /**
     * Return the number of trees in the heap.
     */
    public int numTrees() {
        return 0; // should be replaced by student code
    }

    /**
     * Class implementing a node in a Binomial Heap.
     */
    public class HeapNode {

        public HeapItem item;

        public HeapNode child;
        public HeapNode next;
        public HeapNode parent;
        public int rank;
        public HeapNode(HeapItem item, HeapNode child, HeapNode next, HeapNode parent) {
            this.item = item;
            this.child = child;
            this.next = next;
            this.parent = parent;
            rank = 0;
        }
    }

    /**
     * Class implementing an item in a Binomial Heap.
     */
    public class HeapItem {
        public HeapNode node;
        public int key;
        public String info;
        public HeapItem(int key, String info, HeapNode node) {
            this.info = info;
            this.key = key;
            this.node = node;
        }
    }

    public void displayHeap()
    {
        System.out.print("\nHeap : ");
        displayHeap(last, 0);
        System.out.println("\n");
    }

    private void displayHeap(HeapNode r, int i)
    {
        if (i == size) {
            return;
        }
        if (r != last.next && r.child != null) {
            displayHeap(r.child, i += 1);
            System.out.print(r.item.key + " ");
            displayHeap(r.next, i += 1);
        }
    }

}