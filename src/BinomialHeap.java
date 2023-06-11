import java.util.HashSet;
import java.util.Set;

/**
 * BinomialHeap
 * <p>
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */


public class BinomialHeap {
    public static void main(String[] args) {
        BinomialHeap o = new BinomialHeap();
        for (int i = 0; i < 10; i++) {
            o.insert(i, "hi");
        }
        BinomialHeap heap = new BinomialHeap();
//        heap.meld(o);
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
        HeapNode firstNode = last.next;

        // create the node we want to insert
        HeapNode newNode = new HeapNode(null, null, firstNode, null);
        HeapItem newItem = new HeapItem(key, info, newNode);
        newNode.item = newItem;

        // insert when there is only one tree in the heap or when linking isn't necessary.
        if (firstNode.rank != 0 || last.next == last) {
            if (firstNode.rank == 0) {
                HeapNode x = link(firstNode, newNode);
                min = x;
                last = x;
                return newItem;
            }
            updateMin(key, newNode);  // updates min if necessary
            HeapNode first = last.next;
            last.next = newNode;
            newNode.next = first;
        } else {
            while (firstNode != newNode && firstNode.rank == newNode.rank) {
                HeapNode node = link(firstNode, newNode);
                newNode = node;
                firstNode = node.next;
                last.next = node;
                updateMin(key, node);  // updates min if necessary
            }
        }
        return newItem;
    }

    public HeapNode link(HeapNode x, HeapNode y) {
        if (x.item.key > y.item.key) {
            if (last == x) {
                last = y;
            } else {
                last.next = y;
            }
            if (x.next == x) {
                y.next = y;
            }else {
                y.next = x.next;
            }
            HeapNode tmp = x;
            x = y;
            y = tmp;
        }
        if (x == last) {
            x.next = x;
        }
        if (x.child == null) {
            y.next = y;
        } else {
            HeapNode tmp = x.child.next;
            x.child.next = y;
            y.next = tmp;
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

    public boolean isEmpty() {
        return this.min == null;
    }

    private HeapItem insertToEmpty(int key, String info) {
        HeapNode newNode = new HeapNode(null, null, null, null);
        HeapItem newItem = new HeapItem(key, info, newNode);
        newNode.item = newItem;
        newNode.next = newNode;
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
        return min.item;
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
//        int minimum = Math.min(this.min.item.key, heap2.min.item.key);
//        if (minimum == heap2.min.item.key) {
//            min = heap2.min;
//        }
        HeapNode thisNode = this.last.next;
        HeapNode otherNode = heap2.last.next;
        int thisLargestRank = last.rank;
        int otherLargestRank = heap2.last.rank;
        if (otherLargestRank == 1) {
            insert(heap2.min.item.key, heap2.min.item.info);
            heap2 = null;
            return;
        } else if (thisLargestRank == 1) {
            BinomialHeap tmp = heap2;
            heap2.insert(min.item.key, min.item.info);
            size = heap2.size;
            min = heap2.min;
            last = heap2.last;
            heap2 = null;
            return;
        }
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

    public void print() {
        System.out.println("Binomial Heap:");
        System.out.println("Size: " + size);

        if (min != null) {
            System.out.println("Minimum Node: " + min.item.key);
        } else {
            System.out.println("No minimum node.");
        }

        System.out.println("Heap Nodes:");
        if (last != null) {
            Set<HeapNode> visited = new HashSet<>();
            printHeapNode(last, 0, visited);
        } else {
            System.out.println("No heap nodes.");
        }
    }

    private void printHeapNode(HeapNode node, int indentLevel, Set<HeapNode> visited) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            indent.append("    ");
        }

        System.out.println(indent + "Key: " + node.item.key);
        System.out.println(indent + "Info: " + node.item.info);
        System.out.println(indent + "Rank: " + node.rank);

        visited.add(node);

        if (node.child != null && !visited.contains(node.child)) {
            System.out.println(indent + "Child:");
            printHeapNode(node.child, indentLevel + 1, visited);
        }

        if (node.next != null && !visited.contains(node.next)) {
            System.out.println(indent + "Sibling:");
            printHeapNode(node.next, indentLevel, visited);
        }
    }

}