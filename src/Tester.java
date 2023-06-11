

public class Tester {
    public static void main(String[] args) {
        BinomialHeap heap = new BinomialHeap();
        for(int i = 0; i < 16; i++) {
            heap.insert(16-i, "hi");
        }
//        heap.print();




        System.out.println("first row: ");
        BinomialHeap.HeapNode node = heap.last.next;
        System.out.println(node.item.key);
        node = node.next;
        while (node != heap.last) {
            System.out.print(node.item.key + " ");
            node = node.next;
        }
        System.out.println("second row: ");
        node = heap.last.child.next;
        System.out.print(node.item.key + " ");
        node = node.next;
        while (node != heap.last.child.next) {
            System.out.print(node.item.key + " ");
            node = node.next;
        }
        System.out.println();
        System.out.println("third row: ");
        BinomialHeap.HeapNode upperNode = heap.last.child.next.next;
        while (upperNode != heap.last.child.next) {
            node = upperNode.child.next;
            System.out.print(node.item.key + " ");
            node = node.next;
            while (node != upperNode.child.next) {
                System.out.print(node.item.key + " ");
                node = node.next;
            }
            upperNode = upperNode.next;
        }

    }


}