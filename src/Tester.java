import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Tester {

    public static final int NUM_OF_IT = 200;

    public static void main(String[] args) {
//        for(int i = 0; i < NUM_OF_IT; i++) {
//            int t = createRandInt();
//            int l = createRandInt();
//            System.out.println(t + " " + l);
//            test(t, l,i);
//        }
//        for (int i = 0; i < 20; i++) {
//            int k = createRandInt(10000, 300000);
//            testInsert(k);
//        }
//        System.out.println("All Done!");
        testDeleteMin(100);

    }
    public static int createRandInt(int m, int k) {
        int randomNum = m + (int)(Math.random() * k);
        return randomNum;
    }
    public static void test(int t, int l, int p) {
        if (p%20 == 0) {
            float k = (NUM_OF_IT-p)/NUM_OF_IT;
            System.out.println(k + "% left");
        }
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


    public static void testDeleteMin(int t) {
        BinomialHeap heap = new BinomialHeap();
        for (int i = 0; i < t; i++) {
            heap.insert(i, "hi");
        }
        while (heap.size > 0) {
            System.out.print(heap.min.item.key + " ");
            heap.deleteMin();
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
