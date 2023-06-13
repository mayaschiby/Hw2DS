import java.util.HashMap;
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
        BinomialHeap heap1 = new BinomialHeap();
        for (int i = 0; i < 5; i++) {
            heap1.insert(i, "hi");
        }
        BinomialHeap heap2 = new BinomialHeap();
        for(int j = 0; j < 3; j++) {
            heap2.insert(j, "hi");
        }

        heap1.meld(heap2);
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
//    public HeapItem insert1(int key, String info) {
//        size += 1;
//        if (this.isEmpty()) {
//            HeapItem insertedItem = insertToEmpty(key, info);
//            return insertedItem;
//
//        }
//        // now assume that the heap is not empty
//        HeapNode firstNode = last.next;
//
//        // create the node we want to insert
//        HeapNode newNode = new HeapNode(null, null, firstNode, null);
//        HeapItem newItem = new HeapItem(key, info, newNode);
//        newNode.item = newItem;
//
//        // insert when there is only one tree in the heap or when linking isn't necessary.
//        if (firstNode.rank != 0 || last.next == last) {
//            if (firstNode.rank == 0) {
//                HeapNode x = link(firstNode, newNode, false);
//                min = x;
//                last = x;
//                return newItem;
//            }
//            updateMin(key, newNode);  // updates min if necessary
//            HeapNode first = last.next;
//            last.next = newNode;
//            newNode.next = first;
//        } else {
//            while (firstNode != newNode && firstNode.rank == newNode.rank) {
//                HeapNode node = link(firstNode, newNode, false);
//                newNode = node;
//                firstNode = node.next;
//                last.next = node;
//                updateMin(key, node);  // updates min if necessary
//            }
//        }
//        return newItem;
//    }
//
//    public HeapNode link(HeapNode x, HeapNode y, boolean meld) {
//        if (x.rank != y.rank) {
//            System.out.println("TWO TREES' NOT WITH THE SAME RANK ##LINK##  -->> KEYS ARE: " + x.item.key + y.item.key);
//            System.exit(1);
//        }
//        if (x.item.key > y.item.key) {
//            if (last == x) {
//                last = y;
//            } else if (!meld){
//                last.next = y;
//            }else {
//
//            }
//            if (x.next == x) {
//                y.next = y;
//            }else {
//                y.next = x.next;
//            }
//            HeapNode tmp = x;
//            x = y;
//            y = tmp;
//        }
//        if (x == last) {
//            x.next = x;
//        }
//        if (x.child == null) {
//            y.next = y;
//        } else {
//            HeapNode tmp = x.child.next;
//            x.child.next = y;
//            y.next = tmp;
//        }
//        x.child = y;
//        y.parent = x;
//        if (!meld) {
//            last.next = x;
//        }
//        x.rank += 1;
//        return x;
//    }

    public HeapItem insert(int key, String info) {
        HeapNode newNode = new HeapNode(null, null, null, null);
        HeapItem newItem = new HeapItem(key, info, newNode);
        newNode.item = newItem;
        newNode.next = newNode;

        BinomialHeap heap2 = new BinomialHeap();
        heap2.size = 1;
        heap2.last = newNode;
        heap2.min = newNode;
        meld(heap2);
        return newItem;
    }

    /**
    * @ pre: x.item.key < y.item.key
     * @ pre: x.next = y
     */
    public HeapNode linkCurry(HeapNode x, HeapNode y, HeapNode AfterY) {
        x.rank += 1;
        x.next = y.next;
        HeapNode xChildNext = x.child.next;
        y.next = xChildNext;
        y.parent = x;
        x.child.next = y;
        if(last == y) {
            last = x;
        }
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
        if (size == 1 || size == 0) {
            min = null;
            size = 0;
            last = null;
            return;
        }
        if (last == last.next) {
            last = last.child;
            size -= 1;
            updateHeap();
            return;
        }
        HeapNode BeforeMin = findBeforeMin();
        HeapNode AfterMin = min.next;
        if (min.child == null) {
            BeforeMin.next = AfterMin;
        }else {
            if (min == last) {
                last = AfterMin;
            }
            BeforeMin.next = AfterMin;
            size = size - (int) Math.pow(2, min.rank);
            BinomialHeap heap2 = new BinomialHeap();
            heap2.last = min.child;
            heap2.size = (int) Math.pow(2, min.rank) - 1;
            heap2.updateHeap();
            this.updateHeap();
            meld(heap2);
        }
    }
    public void updateHeap() {  // removes all parents and apply min
        HeapNode node = last;
        min = last;
        node = node.next;
        while (node != last) {
            if (node.item.key < min.item.key) {
                min = node;
            }
            node = node.next;
        }
        node = last.next;
        while (node != last) {
            node.parent = null;
            node = node.next;
        }
        last.parent = null;
    }

    public HeapNode findBeforeMin(){
        if (min.next == min) {
            return min;
        }
        HeapNode node = min.next;
        while (node.next != min) {
            node = node.next;
        }
        return node;
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



    public void meld(BinomialHeap heap2) {
        if (heap2.isEmpty()) {
            return;
        }
        if (isEmpty()) {
            min = heap2.min;
            size = heap2.size;
            last = heap2.last;
            return;
        }
        HeapNode minimum;
        int mini = Math.min(min.item.key, heap2.min.item.key);
        if (mini == min.item.key) {
            minimum = min;
        }else {
            minimum = heap2.min;
        }

        int sizesSum = size + heap2.size;

        int thisSize = last.rank + 1;
        int otherSize = heap2.last.rank + 1;
        int maxLength = Math.max(thisSize, otherSize);

        HeapNode[] thisArr = new HeapNode[maxLength + 1];
        HeapNode thisNode = last.next;
        insertToArr(thisNode, thisArr, this);

        HeapNode otherNode = heap2.last.next;
        HeapNode[] otherArr = new HeapNode[maxLength + 1];
        insertToArr(otherNode, otherArr, heap2);

        HeapNode[] allArr = new HeapNode[maxLength + 1];

        HeapNode curry = null;

        for (int i = 0; i < allArr.length; i++) {
            if (thisArr[i] == null && otherArr[i] == null && curry == null) {
                continue;
            }
            if (curry == null) {
                if (thisArr[i] == null && otherArr[i] != null) {
                    allArr[i] = otherArr[i];
                } else if (thisArr[i] != null && otherArr[i] == null) {
                    allArr[i] = thisArr[i];
                }else {
                    curry = linkmeld(thisArr[i], otherArr[i]);
                }
            }else{
                if (thisArr[i] == null && otherArr[i] == null) {
                    allArr[i] = curry;
                    curry = null;

                } else if (thisArr[i] == null && otherArr[i] != null) {
                    curry = linkmeld(curry, otherArr[i]);
                } else if (thisArr[i] != null && otherArr[i] == null) {
                    curry = linkmeld(curry, thisArr[i]);
                }else {
                    allArr[i] = curry;
                    curry = linkmeld(thisArr[i], otherArr[i]);
                }
            }
        }

        int howManyNodes = 0;
        for (int p = 0; p < allArr.length; p++) {
            if (allArr[p] != null) {
                howManyNodes += 1;
            }
        }
        HeapNode[] finalArr = new HeapNode[howManyNodes];
        int whereAmI = 0;
        for (int i = 0; i < allArr.length; i++) {
            if(allArr[i] != null) {
                finalArr[whereAmI] = allArr[i];
                whereAmI += 1;
            }
        }
        for (int k = 0; k < finalArr.length - 1; k++) {
            finalArr[k].next = finalArr[k+1];
        }
        if (finalArr.length == 1) {
            finalArr[0].next = finalArr[0];
        }else{
            finalArr[finalArr.length - 1].next = finalArr[0];
        }
        last = finalArr[finalArr.length - 1];
        min = minimum;
        size = sizesSum;
    }

    public static HeapNode linkmeld(HeapNode node1, HeapNode node2) {
        if (node1.item.key < node2.item.key) {
            HeapNode node1Child = node1.child;
            if (node1Child != null) {
                HeapNode tmp  = node1Child.next;
                node1Child.next = node2;
                node2.next = tmp;
            }else {
                node2.next = node2;
            }
            node1.child = node2;
            node2.parent = node1;
            node1.rank += 1;
            return node1;
        }else {
            HeapNode node2Child = node2.child;
            if (node2Child != null) {
                HeapNode tmp = node2Child.next;
                node2Child.next = node1;
                node1.next = tmp;
            }else {
                node1.next = node1;
            }
            node2.child = node1;
            node1.parent = node2;
            node2.rank += 1;
            return node2;
        }
    }

    public static void insertToArr(HeapNode node, HeapNode[] arr, BinomialHeap heap) {
        for (int i = 0; i < arr.length; i++) {
            if (i == node.rank) {
                arr[i] = node;
                node = node.next;
            }else {
                arr[i] = null;
            }
        }

    }
    /**
     * Meld the heap with heap2
     */
//    public void meld(BinomialHeap heap2) {
//        if (size < heap2.size) {
//            int sizeTmp = heap2.size;
//            HeapNode minTmp = heap2.min;
//            HeapNode lastTmp = heap2.last;
//            heap2.size = size;
//            heap2.last = last;
//            heap2.min = min;
//            size = sizeTmp;
//            min = minTmp;
//            last = lastTmp;
//
//        }
//        size += heap2.size;
//        // now we can assume this has a binomial tree that is of rank bigger than the biggest rank of heap2
//        if (heap2.isEmpty()) {
//            return;
//        }
//        if (isEmpty()) {
//            min = heap2.min;
//            size = heap2.size;
//            last = heap2.last;
//        }
//        if (heap2.size == 1 || size == 1) {
//            meldCaseOne(heap2);
//            return;
//        }
//        HeapNode thisCurr = last.next;
//        HeapNode otherCurr = heap2.last.next;
//        HeapNode thisBefore = last;
//        HeapNode otherBefore = heap2.last;
//        HeapNode curry = null;
//        while (otherCurr != heap2.last) {
//            if (curry != null) {
//                if (curry.rank < thisCurr.rank && curry.rank < otherCurr.rank) {  // both zero in the binary view
//                    thisBefore.next = curry;
//                    curry.next = thisCurr;
//                    curry = null;  // ### CONNECTED CURRY AND NOW WANTS TO "SHUT IT DOWN" -- MAY NOT WORK!!!!!
//                    //  !!!!!!!!!!!!!!!!!!!! LOOK AT THE COMMENT ABOVE
//
//                }
//            }
//            if (thisCurr.rank < otherCurr.rank) {
//                if (curry == null) {
//                    HeapNode thisTmp = thisCurr.next;
//                    HeapNode otherTmp = otherCurr.next;
//                    thisCurr.next = otherCurr;
//                    otherCurr.next = thisTmp;
//                    otherBefore.next = otherTmp;
//                    thisBefore = thisBefore.next;
//                    while (otherCurr.rank == otherCurr.next.rank) {
//                        if(otherCurr.item.key < otherCurr.next.item.key) {
//                            otherCurr = linkCurry(otherCurr, otherCurr.next, otherCurr.next.next);
//                        }else {
//                            HeapNode tmp = otherCurr.next.next;
//                            thisBefore.next = otherCurr.next;
//                            otherCurr.next.next = otherCurr;
//                            otherCurr.next = tmp;
//                            otherCurr = linkCurry(tmp, otherCurr, otherCurr.next);
//                        }
//                        thisBefore = thisBefore.next;  //MAYBE NOT GOOD!!!!!!!
//                    }
//                    thisCurr = otherCurr;
//                    otherCurr = otherTmp;
//                    continue;
//                }else {
//                    HeapNode thisTmp = thisCurr.next;
//                    curry = link(curry, thisCurr, true);
//                    thisBefore.next = thisTmp;
//                    thisCurr = thisTmp;
//                    continue;
//                }
//            }
//            if (otherCurr.rank < thisCurr.rank) {
//                if (curry == null) {
//                    HeapNode tmp = otherCurr.next;
//                    thisBefore.next = otherCurr;
//                    otherCurr.next = thisCurr;
//                    otherBefore.next = tmp;
//                    otherCurr = tmp;
//                    continue;
//                }else {
//                    HeapNode otherTmp = otherCurr.next;
//                    curry = link(curry, otherCurr, true);
//                    otherBefore.next = otherTmp;
//                    otherCurr = otherTmp;
//                    continue;
//                }
//            }
//            if (curry != null) {
//                HeapNode thisTmp = thisCurr.next;
//                HeapNode otherTmp = otherCurr.next;
//                HeapNode thisFirst = last.next;
//                curry = link(thisCurr, otherCurr, true);
//                otherBefore.next = otherTmp;
//                thisCurr = thisTmp;
//                otherCurr = otherTmp;
//                thisBefore.next = thisFirst;
//            } else { // then thisCurr and otherCurr has the same rank
//                HeapNode thisTmp = thisCurr.next;
//                HeapNode otherTmp = otherCurr.next;
//                curry = link(thisCurr, otherCurr, true);
//                otherBefore.next = otherTmp;
//                thisBefore.next = curry;
//                curry.next = thisTmp;
//                otherCurr = otherTmp;
//                thisCurr = thisTmp;
//            }
//        }
//        thisBefore = thisBefore.next;
//        endMeld(thisCurr, otherCurr, heap2, thisBefore, otherBefore, curry);
//        HeapNode minimum = last.next;
//        HeapNode curr = last.next;
//        while (curr != last) {
//            if (curr.item.key < minimum.item.key) {
//                minimum = curr;
//            }
//            curr = curr.next;
//        }
//        if(last.item.key < minimum.item.key) {
//            min = last;
//        }else{
//            min = minimum;
//        }
//    }
//
//
//    public void endMeld(HeapNode thisCurr, HeapNode otherCurr, BinomialHeap heap2,
//                        HeapNode thisBefore, HeapNode otherBefore, HeapNode curry) {
//        if (thisCurr.rank == otherCurr.rank) {
//            if(curry == null) {
//                HeapNode First = thisCurr.next;
//                curry = link(thisCurr, otherCurr, true);
//                thisBefore.next = curry;
//                curry.next = First;
//                last = curry;
//            }else { // curry != null and the ranks are the same
//
//                HeapNode First = curry;
//                curry = link(thisCurr, otherCurr, true);
//                thisCurr = thisCurr.next;
////                last.next = First;
//                while(thisCurr.rank == curry.rank) {
//                    HeapNode tmp = thisCurr.next;
//                    curry = link(thisCurr, curry, true);
//                    First.next = curry;
//                    curry.next = tmp;
//                    thisCurr = thisCurr.next;
//                }
//
//            }
//        }else if(thisCurr.rank > otherCurr.rank) {
//            meldCaseTwo(curry, otherCurr, thisBefore, thisCurr);
//        }else { // thisCurr.rank < otherCurr.rank
//            HeapNode tmp = thisCurr;
//            boolean firstIt = true;
//            while ((tmp != thisCurr && thisCurr.rank < otherCurr.rank) || firstIt) {
//                thisCurr = thisCurr.next;
//                thisBefore = thisBefore.next;
//                firstIt = false;
//            }
//            if(thisCurr.rank == otherCurr.rank) {
//                meldCaseThree(curry, thisCurr, thisBefore, otherCurr);
//            }else{
//                meldCaseTwo(curry, otherCurr, thisBefore, thisCurr);
//            }
//        }
//
//    }
//
//    public void meldCaseThree(HeapNode curry, HeapNode thisCurr, HeapNode thisBefore, HeapNode otherCurr) {
//        if(curry == null) {
//            // thisCurr = 52, otherCurr = 0, last = 20, thisBefore = 16
//            thisBefore.next = otherCurr;
//            otherCurr.next = thisCurr;
//            if(thisCurr.item.key > otherCurr.item.key) {
//                curry = linkCurry(otherCurr, thisCurr, thisCurr.next);
//            }else {
//                otherCurr.next = thisCurr.next;
//                thisCurr.next = otherCurr;
//                thisBefore.next = thisCurr;
//                curry = linkCurry(otherCurr, thisCurr, thisCurr.next);
//            }
//            while (curry != curry.next && curry.rank == curry.next.rank) {
//                thisCurr = curry.next;
//                if(curry.item.key < curry.next.item.key) {
//                    curry = linkCurry(curry, thisCurr, thisCurr.next);
//                }else {
//                    curry.next = thisCurr;
//                    thisCurr.next = curry;
//                    thisBefore.next = thisCurr;
//                }
//            }
//        }else { // curry != null and the ranks are the same
//            HeapNode First = thisCurr.next;
//            thisBefore.next = curry;
//            last = link(thisCurr, otherCurr, true);
//            curry.next = last;
//            last.next = First;
//        }
//    }
//
//    public void meldCaseTwo(HeapNode curry, HeapNode otherCurr, HeapNode thisBefore, HeapNode thisCurr) {
//        if (curry == null) {
//            thisBefore.next = otherCurr;
//            otherCurr.next = thisCurr;
//        }else {
//            if(curry.rank == otherCurr.rank) {
//                curry = link(curry, otherCurr, true);
//                curry.next = thisCurr.next;
//                thisCurr = thisCurr.next;
//                thisBefore.next = curry;
//                while (curry.rank == thisCurr.rank) {
//                    HeapNode thisTmp = thisCurr.next;
//                    curry = link(curry, thisCurr, true);
//                    thisBefore.next = curry;
//                    curry = thisTmp;
//                }
//            }else{
//                thisBefore.next = curry;
//                curry.next = otherCurr;
//                otherCurr.next = thisCurr;
//            }
//
//        }
//    }


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