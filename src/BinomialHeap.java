import java.util.HashSet;
import java.util.Set;

/**
 * BinomialHeap
 * <p>
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */


public class BinomialHeap{
    public static void main(String[] args) {
        BinomialHeap heap1 = new BinomialHeap();
        for (int i = 0; i < 17; i++) {
            heap1.insert(i, "hi");
        }
        heap1.deleteMin();
        heap1.deleteMin();
        heap1.deleteMin();
        heap1.deleteMin();
        heap1.deleteMin();
    }

    public int numOfLinks;
    public int size;
    public HeapNode last;
    public HeapNode min;
    private int numOfTrees;


    public BinomialHeap() {
        size = 0;
        last = null;
        min = null;
        numOfTrees = 0;
    }

    /**
     * pre: key > 0
     * <p>
     * Insert (key,info) into the heap and return the newly generated HeapItem.
     */

    public HeapItem insert(int key, String info) {
        numOfTrees += 1;
        numOfLinks = 0;
        HeapNode newNode = new HeapNode(null, null, null, null);
        HeapItem newItem = new HeapItem(key, info, newNode);
        newNode.item = newItem;
        newNode.next = newNode;

        if (size == 0) {
            size = 1;
            last = newNode;
            min = newNode;
            return newItem;
        } else{
            if (min.item.key > key) {
                min = newNode;
            }
            if (size == 1) {
                newNode = link(newNode, last);
                min = newNode;
                last = newNode;
                size = 2;
                newNode.next = newNode;
                return newItem;
            }else if (last.next.rank > 0){
                size += 1;
                HeapNode tmp = last.next;
                last.next = newNode;
                newNode.next = tmp;
                return newItem;
            }else {
                size += 1;
                HeapNode nextNode = last.next;
                last.next = newNode;
                newNode.next = nextNode;
                while (newNode.rank == nextNode.rank && newNode != nextNode && nextNode != last) {
                    HeapNode tmp = nextNode.next;
                    newNode = link(newNode, nextNode);
                    newNode.next = tmp;
                    last.next = newNode;
                    nextNode = tmp;
                }
                if (nextNode == last && newNode.rank == nextNode.rank) {
                    HeapNode node = link(newNode, nextNode);
                    min = node;
                    last = node;
                }
            }
        }
        return newItem;
    }

    /**
     * Delete the minimal item
     */
    public int deleteMin() {
        numOfLinks = 0;
        int res = min.rank;
        numOfTrees -= 1;
        if (size == 1 || size == 0) {
            min = null;
            size = 0;
            last = null;
            numOfTrees = 0;
            return res;
        }
        if (last == last.next) {
            numOfTrees = last.rank;
            last = last.child;
            size -= 1;
            updateHeap();
            return res;
        }
        HeapNode BeforeMin = findBeforeMin();
        HeapNode AfterMin = min.next;
        if (min.child == null) {
            BeforeMin.next = AfterMin;
            size -= 1;
            updateHeap();
        }else {
            if (min == last) {
                last = BeforeMin;
            }
            BeforeMin.next = AfterMin;
            size = size - (int) Math.pow(2, min.rank);
            BinomialHeap heap2 = new BinomialHeap();
            heap2.last = min.child;
            heap2.numOfTrees = min.rank;
            heap2.size = (int) Math.pow(2, min.rank) - 1;
            heap2.updateHeap();
            this.updateHeap();
            meld(heap2);
        }
        return res;
    }
    public boolean checkParents() {
        if (last == null) {
            return true;
        }
        int cnt = 0;
        HeapNode node = last.next;
        while (node != last) {
            if (node.parent != null) {
                return false;
            }
            node = node.next;
        }
        return last.parent == null;
    }
    public void updateHeap() {  // removes all parents and apply min
        HeapNode node = last;
        min = last;
        node = node.next;
        while (node != last) {
            if (node.item.key <= min.item.key) {
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
        HeapNode node = item.node;
        item.key -= diff;
        if (node.parent != null) {
            HeapNode parent = node.parent;
            while(node.parent != null && node.item.key < node.parent.item.key){
                switchNodes(node, parent);
                node = parent;
                parent = node.parent;
            }
        }
    }

    public void switchNodes (HeapNode node1, HeapNode node2) {
        HeapItem item1 = node1.item;
        HeapItem item2 = node2.item;
        item1.node = node2;
        item2.node = node1;
        node1.item = item2;
        node2.item = item1;
    }

    /**
     * Delete the item from the heap.
     */
    public void delete(HeapItem item) {
        int curr = item.key - min.item.key + 1;
        decreaseKey(item, curr);
        deleteMin();
    }


    /**
     * Linking to Binomial trees into one.
     * @param node1
     * @param node2
     * @return
     */

    public HeapNode link(HeapNode node1, HeapNode node2) {
        numOfLinks += 1;
        numOfTrees -= 1;
        if (node1.item.key < node2.item.key) {
            return getHeapNode(node1, node2);
        }else {
            return getHeapNode(node2, node1);
        }
    }

    public HeapNode getHeapNode(HeapNode node1, HeapNode node2) {
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
        node1.next = node1;
        return node1;
    }


    /**
     * Meld the heap with heap2
     */
    public void meld(BinomialHeap heap2) {
        if (heap2.empty()) {
            return;
        }
        if (empty()) {
            min = heap2.min;
            size = heap2.size;
            last = heap2.last;
            numOfTrees = heap2.numOfTrees;
            return;
        }
        numOfTrees += heap2.numOfTrees;

        int sizesSum = size + heap2.size;

        int thisSize = last.rank + 1;
        int otherSize = heap2.last.rank + 1;
        int maxLength = Math.max(thisSize, otherSize);

        HeapNode[] thisArr = new HeapNode[maxLength + 1];
        HeapNode thisNode = last.next;
        insertToArr(thisNode, thisArr);

        HeapNode otherNode = heap2.last.next;
        HeapNode[] otherArr = new HeapNode[maxLength + 1];
        insertToArr(otherNode, otherArr);

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
                    curry = link(thisArr[i], otherArr[i]);
                    allArr[i] = null;
                }
            }else{
                if (thisArr[i] == null && otherArr[i] == null) {
                    allArr[i] = curry;
                    curry = null;

                } else if (thisArr[i] == null && otherArr[i] != null) {
                    curry = link(curry, otherArr[i]);
                } else if (thisArr[i] != null && otherArr[i] == null) {
                    curry = link(curry, thisArr[i]);
                }else {
                    allArr[i] = curry;
                    curry = link(thisArr[i], otherArr[i]);
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
//        min = minimum;
        size = sizesSum;
        updateHeap();
    }

    /**
     * inserts the nodes of the heap which are on the first row of the heap to an array.
     * @param node last node
     * @param arr the array that *it inserts to.
     */
    public static void insertToArr(HeapNode node, HeapNode[] arr) {
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
     * Return the number of elements in the heap
     */
    public int size() {
        return size;
    }

    /**
     * The method returns true if and only if the heap
     * is empty.
     */
    public boolean empty() {
        return this.min == null;
    }

    /**
     * Return the number of trees in the heap.
     */
    public int numTrees() { return numOfTrees; }

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
//        System.out.println(indent + "Info: " + node.item.info);
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