import java.util.*;
import java.util.random.RandomGenerator;

public class Tester {

    public static final int NUM_OF_IT = 100;

    public static final int LOW_RANGE = 100;

    public static final int UPPER_RANGE = 100000;

    public static void main(String[] args) {
         // ###TEST MELD### //

//        for(int i = 0; i < NUM_OF_IT; i++) {
//            int t = createRandInt(LOW_RANGE, UPPER_RANGE, i);
//            int l = createRandInt(LOW_RANGE, UPPER_RANGE, i);
//            System.out.println("t: " + t + " l: " + l);
//            test(t, l,i);
//        }
//        System.out.println("Done!");

        // ### TEST INSERT ### //

        for (int i = 0; i < 100; i++) {
            int k = createRandInt(LOW_RANGE, UPPER_RANGE, i);
            System.out.println(k);
            boolean test = testInsert(k);
            if (!test) {
                System.out.println(k);
            }
        }
        System.out.println("All Done!");

//        testInsert(15);

        // ### TEST DELETE_MIN ### //

//        boolean good = true;
//        for(int i = 300; i < 500; i++) {
//            good = testDeleteMin(i);
//            if (!good) {
//                System.out.println(i);
//            }
//        }

    }
    public static int createRandInt(int m, int k, int seed) {
        RandomGenerator generator = new Random(seed);
        int num = generator.nextInt(LOW_RANGE, UPPER_RANGE);
        return num;
    }

    public static ArrayList<Integer> createRandArr(int size, int lowRange, int upRange) {
        ArrayList<Integer> lst = new ArrayList<>(0);
        int k = 0;
        for(int i = 0; i < size; i++) {
            k = createRandInt(lowRange, upRange, i);
            lst.add(k);
        }
        return lst;
    }
    public static void test(int t, int l, int p) {
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
//        ArrayList<Integer> lst2 = new ArrayList<>(0);
//        scanTree(heap.last, false, lst2);
//        Collections.sort(lst1);
//        Collections.sort(lst2);
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
//        System.out.println(lst3);
//        System.out.println(lst1);
//        System.out.println(lst1.equals(lst3));
        return lst1.equals(lst3);
    }


    public static boolean testDeleteMin(int t) {
        BinomialHeap heap = new BinomialHeap();
        ArrayList<Integer> lst = createRandArr(t, LOW_RANGE, UPPER_RANGE);
        for (int i = 0; i < t; i++) {
            heap.insert(lst.get(i), "hi");
        }
        System.out.println(lst);
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
