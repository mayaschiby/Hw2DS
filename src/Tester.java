import java.util.*;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

public class Tester {

    public static final int TreeSize = 170;

    public static final int LOW_RANGE = 1000;

    public static final int UPPER_RANGE = 2000;

    public static void main(String[] args) {

//        for (int i = 1; i < 6; i++) {
//            int n = (int) Math.pow(3, 5 + i) - 1;
//            BinomialHeap heap = new BinomialHeap();
//            ArrayList<Integer> lst = new ArrayList<>(0);
//            for (int p = 1; p < n + 1; p++) {
//                lst.add(p);
//            }
//            Collections.shuffle(lst);
//            double t1 = System.currentTimeMillis();
//            int numLinks = 0;
//            for (int j = 0; j < n; j++) {
//                heap.insert(lst.get(j), "hoi");
//                numLinks += heap.numOfLinks;
//            }
//            int rankCnt = 0;
//            for (int k = 0; k < n/2; k++) {
//                rankCnt += heap.deleteMin();
//                numLinks += heap.numOfLinks;
//            }
//            while (heap.size != ((int) Math.pow(2, 5)) - 1) {
//                rankCnt += heap.deleteMin();
//                numLinks += heap.numOfLinks;
//            }
//            double t2 = System.currentTimeMillis();
//            System.out.println("i = " + i);
//            System.out.println("Running time: " + (t2-t1));
//            System.out.println("Number of trees: " + heap.numTrees());
//            System.out.println("Number of links: " + numLinks);
//            System.out.println("Sum of ranks that we deleted: " + rankCnt);
////            System.out.println("Sum of ranks - Num of links: " + (numLinks - rankCnt));
//            System.out.println(" ");
//        }


        // ###TEST MELD### //

        for(int i = 0; i < 100; i++) {
            int t = createRandInt(LOW_RANGE, UPPER_RANGE, i);
            int l = createRandInt(LOW_RANGE, UPPER_RANGE, i);
//            System.out.println("t: " + t + " l: " + l);
            boolean y = test(t, l,i);
            if (!y) {
                System.out.println("no");
            }
        }
        System.out.println("Done!");

        // ### TEST INSERT ### //

//        for (int i = 0; i < 100; i++) {
//            int k = createRandInt(LOW_RANGE, UPPER_RANGE, i);
//            double t1 = System.currentTimeMillis();
//            boolean test = testInsert(k);
//            double t2 = System.currentTimeMillis();
//            System.out.println("the time it took to insert " + k + " nodes is: " + (t2-t1) + " millisecond");
//            if (!test) {
//                System.out.println(k);
//            }
//        }
//        System.out.println("All Done!");


        // ### TEST DELETE_MIN ### //

//        boolean good = true;
//        for(int i = 300; i < 500; i++) {
//            good = testDeleteMin(i);
//            if (!good) {
//                System.out.println(i);
//            }
//        }


        // ### TEST DECREASE_KEY ### //
//        BinomialHeap heap = new BinomialHeap();
//        ArrayList <Integer> lst = createRandArr(TreeSize, LOW_RANGE, UPPER_RANGE);
//        for(int i = 0; i < lst.size(); i++) {
//            heap.insert(lst.get(i), "hi");
//        }
//        ArrayList<Integer> lst1 = new ArrayList<>(0);
//        while (heap.last != null) {
//            BinomialHeap.HeapItem item = getRandItem(heap.last, 0, heap);
//            lst1.add(item.key);
//            heap.delete(item);
//        }
//        System.out.println(lst1);
    }

    public static int calc(int n) {
        double log = Math.log(n) / Math.log(2) + 2;
        for (int i = 0; i < log; i++) {
            if ((int) Math.pow(2, i) == n) {
                return i;
            }
        }
        for (int j = 0; j < log; j++) {
            if ((int) Math.pow(2, j) < n && (int) Math.pow(2, j + 1) > n) {
                return calc(n - (int) Math.pow(2, j));
            }
        }
        return 0;
    }
    public static int createRandInt(int m, int k, int seed) {
        RandomGenerator generator = new Random(seed);
        int num = generator.nextInt(LOW_RANGE, UPPER_RANGE);
        return num;
    }

    public static int createRandInt(int m, int k) {
        long rndInt = m + Math.round(Math.random()*k);
        int num = (int) rndInt;
        return num;
    }

    public static ArrayList<Integer> createRandArr(int size, int lowRange, int upRange) {
        ArrayList<Integer> lst = new ArrayList<>(0);
        int k;
        int i = 0;
        while (i < size) {
            k = createRandInt(lowRange, upRange);
            lst.add(k);
            i += 1;
        }
        return lst;
    }
    public static boolean test(int t, int l, int p) {
//        if (p%20 == 0) {
//            double k = (NUM_OF_IT-p)/NUM_OF_IT;
//            System.out.println(k + "% left");
//        }
        ArrayList<Integer> lst1 = new ArrayList<>(0);
        BinomialHeap heap = new BinomialHeap();
        for (int i = 0; i < t; i++) {
            heap.insert(i, "hi");
            lst1.add(i);
        }
        BinomialHeap heap2 = new BinomialHeap();
        for (int j = 0; j < l; j++) {
            heap2.insert(t + j, "hi");
            lst1.add(t + j);
        }
        heap.meld(heap2);
        ArrayList<BinomialHeap.HeapNode> lst2 = new ArrayList<>(0);
        scanTree(heap.last, false, lst2);
        Collections.sort(lst1);
        ArrayList<Integer> lst3 = new ArrayList<>(0);
        for (int i = 0; i < lst2.size(); i++) {
            lst3.add(lst2.get(i).item.key);
        }
        Collections.sort(lst3);
        if (lst1.equals(lst3)) {
            return true;
        }
        return false;

//        heap.print();
//        System.out.println(lst1);
//        System.out.println(lst2);
//        boolean k = false;
//        for(int i = 0; i < lst1.size() && i < lst2.size(); i++) {
//            if(!lst1.get(i).equals(lst2.get(i))){
//                System.out.println("You have entered " + i + " but it was not found in your heap");
//            }
//        }
    }

    public static void testDecreaseKey(int t) {
        BinomialHeap heap = new BinomialHeap();
        ArrayList <Integer> lst = createRandArr(t, LOW_RANGE, UPPER_RANGE);
        for(int i = 0; i < lst.size(); i++) {
            heap.insert(lst.get(i), "hi");
        }
        BinomialHeap.HeapItem item = getRandItem(heap.last, 0, heap);
        heap.delete(item);

    }

    public static BinomialHeap.HeapItem getRandItem(BinomialHeap.HeapNode node, int n, BinomialHeap heap) {
        if (n == heap.size){
            return node.item;
        }
        double rand = Math.random();
        if (node.parent != null && node.child != null) {
            if (rand < 0.3333) {
                return getRandItem(node.next, n + 1, heap);
            } else if (rand > 0.333333 && rand < 0.6666666) {
                return getRandItem(node.child, n + 1, heap);
            }else {
                return getRandItem(node.parent, n + 1, heap);
            }
        } else if (node.child != null) {
            if (rand < 0.5) {
                return getRandItem(node.next, n + 1, heap);
            }else {
                return getRandItem(node.child, n + 1, heap);
            }
        } else if (node.parent != null) {
            if (rand < 0.5) {
                return getRandItem(node.next, n + 1, heap);
            }else {
                return getRandItem(node.parent, n + 1, heap);
            }
        }else {
            return getRandItem(node.next, n + 1, heap);
        }
    }

    public static boolean testInsert(int t) {
        ArrayList<Integer> lst1 = createRandArr(t, LOW_RANGE, UPPER_RANGE);
        BinomialHeap heap = new BinomialHeap();
        for (int i = 0; i < t; i++) {
            heap.insert(lst1.get(i), "hi");
        }

        ArrayList<BinomialHeap.HeapNode> lst2 = new ArrayList<>(0);
        scanTree(heap.last, false, lst2);
//        heap.print();

        ArrayList<Integer> lst3 = new ArrayList<>(0);
        for (int i = 0; i < lst2.size(); i++) {
            lst3.add(lst2.get(i).item.key);
        }
        Collections.sort(lst3);
        Collections.sort(lst1);

        Comparator<BinomialHeap.HeapNode> c = new Comparator<BinomialHeap.HeapNode>() {
            @Override
            public int compare(BinomialHeap.HeapNode o1, BinomialHeap.HeapNode o2) {
                return Integer.compare(o1.item.key, o2.item.key);
            }
        };
        Collections.sort(lst2, c);
        return lst1.equals(lst3);
    }

    public static boolean testDeleteMin(int t) {
        BinomialHeap heap = new BinomialHeap();
        ArrayList<Integer> lst = createRandArr(t, LOW_RANGE, UPPER_RANGE);
        for (int i = 0; i < t; i++) {
            heap.insert(lst.get(i), "hi");
        }
//        System.out.println(lst);
        ArrayList<Integer> checkLst = new ArrayList<>(0);
        while (heap.size > 0) {
            checkLst.add(heap.min.item.key);
            heap.deleteMin();
        }
        Collections.sort(lst);
        Collections.sort(checkLst);
        if (!lst.equals(checkLst)) {
            System.out.println(lst);
            return false;
        }else {
//            System.out.println("Well Done!");
            return true;
        }
    }

    public static boolean scanTree(BinomialHeap.HeapNode root, boolean inBros, ArrayList<BinomialHeap.HeapNode> lst) {
        if (lst.contains(root)) {
            return true;
        }
        if(root.rank == 0) {
            lst.add(root);
            return true;
        }
        BinomialHeap.HeapNode child = root.child;
        if (scanTree(child, false, lst)){
            lst.add(root);
            if(!inBros) {
                if(checkBros(root, lst)) {
                    return true;
                }
            }else {
              return true;
            }
        }
        return false;
    }
    public static boolean checkBros(BinomialHeap.HeapNode root, ArrayList<BinomialHeap.HeapNode> lst) {
        if(root.item.key == 20) {
            int n = 0;
        }
        BinomialHeap.HeapNode node = root.next;
        while (node != root) {
            if(!scanTree(node, true, lst)) {
                return false;
            }
            node = node.next;
        }
        return true;
    }

}
