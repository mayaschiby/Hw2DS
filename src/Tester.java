
public class Tester {
    public static void main(String[] args) {
        BinomialHeap heap = new BinomialHeap();
        int[] arr = new int[] {12, 11, 31, 33, 17, 1, 50, 40, 77, 20, 20, 88, 90, 99, 98, 97};
            // 1, 11, 12, 17, 20, 20, 31, 33, 40, 50, 77, 88, 90, 97, 98, 99
        for (int i = 0; i < 16; i++) {
            heap.insert(arr[i], "hi");
        }

        BinomialHeap heap2 = new BinomialHeap();
        heap2.insert(7, "hi");

        heap.print();
    }
}





//        System.out.println("first row: ");
//        BinomialHeap.HeapNode node = heap.last.next;
//        System.out.println(node.item.key);
//        node = node.next;
//        while (node != heap.last) {
//            System.out.print(node.item.key + " ");
//            node = node.next;
//        }
//        System.out.println("second row: ");
//        node = heap.last.child.next;
//        System.out.print(node.item.key + " ");
//        node = node.next;
//        while (node != heap.last.child.next) {
//            System.out.print(node.item.key + " ");
//            node = node.next;
//        }
//        System.out.println();
//        System.out.println("third row: ");
//        BinomialHeap.HeapNode upperNode = heap.last.child.next.next;
//        while (upperNode != heap.last.child.next) {
//            node = upperNode.child.next;
//            System.out.print(node.item.key + " ");
//            node = node.next;
//            while (node != upperNode.child.next) {
//                System.out.print(node.item.key + " ");
//                node = node.next;
//            }
//            upperNode = upperNode.next;
//        }
