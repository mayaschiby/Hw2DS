import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Tester {

    public static final int NUM_OF_IT = 1;

    public static final int LOW_RANGE = 400;

    public static final int UPPER_RANGE = 50000;

    public static void main(String[] args) {
         // ###TEST MELD### //

//        for(int i = 0; i < NUM_OF_IT; i++) {
//            int t = createRandInt(100, (int) Math.pow(3, 15));
//            int l = createRandInt(100, (int) Math.pow(3, 15));
//            System.out.println("t: " + t + " l: " + l);
//            test(t, l,i);
//        }
//        System.out.println("Done!");

        // ### TEST INSERT ### //

//        for (int i = 0; i < 20; i++) {
//            int k = createRandInt(10000, 300000);
//            testInsert(k);
//        }
//        System.out.println("All Done!");

        // ### TEST DELETEU_MIN ### //

//        boolean good = true;
//        for(int i = 300; i < 500; i++) {
//            good = testDeleteMin(i);
//            if (!good) {
//                System.out.println(i);
//            }
//        }


        testDeleteMin(301);
//        BinomialHeap heap = new BinomialHeap();
//        ArrayList<Integer> lst = new ArrayList<>(0);
//        for (int i = 0; i < 16; i++) {
//            int k = createRandInt(100, 500);
//            lst.add(k);
//            heap.insert(k, "hi");
//        }
//        System.out.println(lst);
//        System.out.println(" ");
//        heap.print();
    }
    public static int createRandInt(int m, int k, int seed) {
        RandomGenerator generator = new Random(seed);
        int num = generator.nextInt(LOW_RANGE, UPPER_RANGE);
        return num;
    }

    public static ArrayList<Integer> createRandArr(int size, int lowRange, int upRange) {
        ArrayList<Integer> lst = new ArrayList<>(0);
        for(int i = 0; i < size; i++) {
            int k = createRandInt(lowRange, upRange, i);
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

    public static void testInsert(int t) {
        ArrayList<Integer> lst1 = new ArrayList<>(0);
        BinomialHeap heap = new BinomialHeap();
        for (int i = 0; i < t; i++) {
            heap.insert(i, "hi");
            lst1.add(i);
        }
        ArrayList<Integer> lst2 = new ArrayList<>(0);
        scanTree(heap.last, false, lst2);
        Collections.sort(lst1);
        Collections.sort(lst2);
        for(int i = 0; i < lst1.size(); i++) {
            if(!lst1.get(i).equals(lst2.get(i))){
                System.out.println("You have entered " + i + " but it was not found in your heap");
            }
        }
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
    public static boolean scanTree(BinomialHeap.HeapNode root, boolean inBros, ArrayList<Integer> lst) {
        if (lst.contains(root.item.key)) {
            return true;
        }
        if(root.rank == 0) {
            lst.add(root.item.key);
            return true;
        }
        BinomialHeap.HeapNode child = root.child;
        if (scanTree(child, false, lst)){
            lst.add(root.item.key);
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
    public static boolean checkBros(BinomialHeap.HeapNode root, ArrayList<Integer> lst) {
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
