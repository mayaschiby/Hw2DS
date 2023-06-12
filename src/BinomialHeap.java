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
                HeapNode x = link(firstNode, newNode, false);
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
                HeapNode node = link(firstNode, newNode, false);
                newNode = node;
                firstNode = node.next;
                last.next = node;
                updateMin(key, node);  // updates min if necessary
            }
        }
        return newItem;
    }

    public HeapNode link(HeapNode x, HeapNode y, boolean meld) {
        if (x.rank != y.rank) {
            System.out.println("TWO TREES' NOT WITH THE SAME RANK ##LINK##  -->> KEYS ARE: " + x.item.key + y.item.key);
            System.exit(1);
        }
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
        if (!meld) {
            last.next = x;
        }
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
        if (size < heap2.size) {
            int sizeTmp = heap2.size;
            HeapNode minTmp = heap2.min;
            HeapNode lastTmp = heap2.last;
            heap2.size = size;
            heap2.last = last;
            heap2.min = min;
            size = sizeTmp;
            min = minTmp;
            last = lastTmp;

        }
        size += heap2.size;
        // now we can assume this has a binomial tree that is of rank bigger than the biggest rank of heap2
        if (heap2.isEmpty()) {
            return;
        }
        if (isEmpty()) {
            min = heap2.min;
            size = heap2.size;
            last = heap2.last;
        }
        if (heap2.size == 1 || size == 1) {
            meldCaseOne(heap2);
            return;
        }
        HeapNode thisCurr = last.next;
        HeapNode otherCurr = heap2.last.next;
        HeapNode thisBefore = last;
        HeapNode otherBefore = heap2.last;
        HeapNode curry = null;
        while (otherCurr != heap2.last) {
            if (curry != null) {
                if (curry.rank < thisCurr.rank && curry.rank < otherCurr.rank) {  // both zero in the binary view
                    thisBefore.next = curry;
                    curry.next = thisCurr;
                    curry = null;  // ### CONNECTED CURRY AND NOW WANTS TO "SHUT IT DOWN" -- MAY NOT WORK!!!!!
                    //  !!!!!!!!!!!!!!!!!!!! LOOK AT THE COMMENT ABOVE

                }
            }
            if (thisCurr.rank < otherCurr.rank) {
                if (curry == null) {
                    HeapNode thisTmp = thisCurr.next;
                    HeapNode otherTmp = otherCurr.next;
                    thisCurr.next = otherCurr;
                    otherCurr.next = thisTmp;
                    otherBefore.next = otherTmp;
                    thisBefore = thisBefore.next;
                    while (otherCurr.rank == otherCurr.next.rank) {
                        thisBefore = thisBefore.next;  //MAYBE NOT GOOD!!!!!!!
                        HeapNode tmp = otherCurr.next.next;
                        otherCurr = link(otherCurr, otherCurr.next, false);
                        thisCurr.next = otherCurr;
                        otherCurr.next = tmp;
                    }
                    thisCurr = otherCurr;
                    otherCurr = otherTmp;
                    continue;
                }else {
                    HeapNode thisTmp = thisCurr.next;
                    curry = link(curry, thisCurr, true);
                    thisBefore.next = thisTmp;
                    thisCurr = thisTmp;
                    continue;
                }
            }
            if (otherCurr.rank < thisCurr.rank) {
                if (curry == null) {
                    HeapNode tmp = otherCurr.next;
                    thisBefore.next = otherCurr;
                    otherCurr.next = thisCurr;
                    otherBefore.next = tmp;
                    otherCurr = tmp;
                    continue;
                }else {
                    HeapNode otherTmp = otherCurr.next;
                    curry = link(curry, otherCurr, true);
                    otherBefore.next = otherTmp;
                    otherCurr = otherTmp;
                    continue;
                }
            }
            if (curry != null) {
                HeapNode thisTmp = thisCurr.next;
                HeapNode otherTmp = otherCurr.next;
                HeapNode thisFirst = last.next;
                curry = link(thisCurr, otherCurr, true);
                otherBefore.next = otherTmp;
                thisCurr = thisTmp;
                otherCurr = otherTmp;
                thisBefore.next = thisFirst;
            } else { // then thisCurr and otherCurr has the same rank
                HeapNode thisTmp = thisCurr.next;
                HeapNode otherTmp = otherCurr.next;
                curry = link(thisCurr, otherCurr, true);
                otherBefore.next = otherTmp;
                thisBefore.next = curry;
                curry.next = thisTmp;
                otherCurr = otherTmp;
                thisCurr = thisTmp;
            }
        }
        endMeld(thisCurr, otherCurr, heap2, thisBefore, otherBefore, curry);
    }


    public void endMeld(HeapNode thisCurr, HeapNode otherCurr, BinomialHeap heap2,
                        HeapNode thisBefore, HeapNode otherBefore, HeapNode curry) {
        if (thisCurr.rank == otherCurr.rank) {
            if(curry == null) {
                HeapNode First = thisCurr.next;
                curry = link(thisCurr, otherCurr, true);
                thisBefore.next = curry;
                curry.next = First;
                last = curry;
            }else { // curry != null and the ranks are the same
                HeapNode First = curry;
                curry = link(thisCurr, otherCurr, true);
                thisCurr = thisCurr.next;
//                last.next = First;
                while(thisCurr.rank == curry.rank) {
                    HeapNode tmp = thisCurr.next;
                    curry = link(thisCurr, curry, true);
                    First.next = curry;
                    curry.next = tmp;
                    thisCurr = thisCurr.next;
                }

            }
        }else if(thisCurr.rank > otherCurr.rank) {
            meldCaseTwo(curry, otherCurr, thisBefore, thisCurr);
        }else { // thisCurr.rank < otherCurr.rank
            while (thisCurr.rank < otherCurr.rank) {
                thisCurr = thisCurr.next;
                thisBefore = thisBefore.next;
            }
            if(thisCurr.rank == otherCurr.rank) {
                meldCaseThree(curry, thisCurr, thisBefore, otherCurr);
            }else{
                meldCaseTwo(curry, otherCurr, thisBefore, thisCurr);
            }
        }

    }

    public void meldCaseThree(HeapNode curry, HeapNode thisCurr, HeapNode thisBefore, HeapNode otherCurr) {
        if(curry == null) {
            HeapNode thisCurrNext = thisCurr.next;
            thisCurr.next = otherCurr;
            otherCurr.next = thisCurrNext;
            curry = link(thisCurr, otherCurr, true);
            thisBefore.next = curry;
            curry.next = thisCurrNext;
            while (curry.rank == curry.next.rank) {
                HeapNode tmp = curry.next.next;
                curry = link(curry, curry.next, false);
                curry.next = tmp;
                thisBefore.next = curry;
                thisBefore = thisBefore.next;
            }
        }else { // curry != null and the ranks are the same
            HeapNode First = thisCurr.next;
            thisBefore.next = curry;
            last = link(thisCurr, otherCurr, true);
            curry.next = last;
            last.next = First;
        }
    }

    public void meldCaseTwo(HeapNode curry, HeapNode otherCurr, HeapNode thisBefore, HeapNode thisCurr) {
        if (curry == null) {
            thisBefore.next = otherCurr;
            otherCurr.next = thisCurr;
        }else {
            if(curry.rank == otherCurr.rank) {
                curry = link(curry, otherCurr, true);
                curry.next = thisCurr.next;
                thisCurr = thisCurr.next;
                thisBefore.next = curry;
                while (curry.rank == thisCurr.rank) {
                    HeapNode thisTmp = thisCurr.next;
                    curry = link(curry, thisCurr, true);
                    thisBefore.next = curry;
                    curry = thisTmp;
                }
            }else{
                thisBefore.next = curry;
                curry.next = otherCurr;
                otherCurr.next = thisCurr;
            }

        }
    }


    /**
     * @Pre one of the heaps is of size 1
     */
    public void meldCaseOne(BinomialHeap heap2) {
        if (heap2.size == 1) {
            insert(heap2.min.item.key, heap2.min.item.info);
            heap2 = null;
        } else{
            BinomialHeap tmp = heap2;
            heap2.insert(min.item.key, min.item.info);
            size = heap2.size;
            min = heap2.min;
            last = heap2.last;
            heap2 = null;
        }
    }

    /**
     * Return the number of elements in the heap
     */
    public int size() {
        return size; // should be replaced by student code
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