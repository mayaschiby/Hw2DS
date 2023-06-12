import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Tester {
    public static void main(String[] args) {
        for(int i = 0; i < 300; i++) {
        int t = createRandInt();
        int l = createRandInt();
            test(t, l, i);
        }
    }
    public static int createRandInt() {
        int randomNum = 300 + (int)(Math.random() * 300);
        return randomNum;
    }
    public static void test(int t, int l, int p) {
        if(p%300 == 10){
            System.out.print((300-p)/300 + "% left");
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
            lst1.add(l + j);
        }
        heap.meld(heap2);
        ArrayList<Integer> lst2 = new ArrayList<>(0);
        scanTree(heap.last, false, lst2);
        Collections.sort(lst1);
        Collections.sort(lst2);
        System.out.println(" ");
        for(int i = 0; i < lst1.size(); i++) {
            if(!lst1.get(i).equals(lst2.get(i))){
                System.out.println("You have entered " + i + " but it was not found in your heap");
            }
        }
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

    public static boolean scanTree(BinomialHeap.HeapNode root, boolean inBros, ArrayList<Integer> lst) {
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
