package datastructure_2;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class experiment {
    public static void main(String[] args) {
        // Initialize the Fibonacci Heap and the array of pointers
        FibonacciHeap heap = new FibonacciHeap();
        List<FibonacciHeap.HeapNode> array = new ArrayList<>();

        // Insert keys into the Fibonacci Heap and populate the array
        List<Integer> keys = new ArrayList<>();
        for (int i = 1; i <= 6560; i++) {
            keys.add(i);
        }
        Collections.shuffle(keys);

        for (int key : keys) {
            FibonacciHeap.HeapNode node = heap.insert(key, "info_" + key); // Insert into the heap with info field
            array.add(node); // Add the pointer to the array
            System.out.println("Inserted key: " + key + " (info: info_" + key + "), Heap size: " + heap.size());
        }

        // Start measuring time
        long startTime = System.currentTimeMillis();

        // Step 1: Perform a single delete-min operation
        System.out.println("Performing delete-min operation");
        heap.deleteMin();
        array.remove(0); // Remove the pointer to the minimum element from the array
        System.out.println("Heap size after delete-min: " + heap.size());

        // Step 2: Perform repeated delete-max operations until only 31 elements remain
        while (heap.size() > 31) {
            // Dynamically find the maximum node in the heap
            FibonacciHeap.HeapNode maxNode = findMaxNode(array);
            System.out.println("Decreasing key of maxNode (original key: " + maxNode.key + ", info: " + maxNode.info + ") to Integer.MIN_VALUE");
            heap.decreaseKey(maxNode, Integer.MIN_VALUE); // Decrease max node's key to negative infinity
            heap.deleteMin(); // Delete the new minimum (originally the max element)

            // Update the array after delete-min (the maxNode has been deleted)
            array.remove(maxNode); // Remove the pointer to the deleted max element
            System.out.println("Heap size after deleting max: " + heap.size());
        }

        // End measuring time
        long endTime = System.currentTimeMillis();

        // Gather the results
        long experimentTime = endTime - startTime; // Time in milliseconds
        int heapSizeAtEnd = heap.size();
        int totalLinks = heap.totalLinks();
        int totalCuts = heap.totalCuts();
        int numTrees = heap.roots_num;

        // Print the results
        System.out.println("Experiment Results:");
        System.out.println("1. Running time: " + experimentTime + " ms");
        System.out.println("2. Heap size at the end: " + heapSizeAtEnd);
        System.out.println("3. Total links: " + totalLinks);
        System.out.println("4. Total cuts: " + totalCuts);
        System.out.println("5. Number of trees in the heap: " + numTrees);
    }

    // Helper method to find the max node in the array
    private static FibonacciHeap.HeapNode findMaxNode(List<FibonacciHeap.HeapNode> array) {
        FibonacciHeap.HeapNode maxNode = array.get(0);
        for (FibonacciHeap.HeapNode node : array) {
            if (node.key > maxNode.key) {
                maxNode = node;
            }
        }
        return maxNode;
    }
}



