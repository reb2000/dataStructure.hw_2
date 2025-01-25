package datastructure_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.*;


public class experiment {

    public static void main(String[] args) {
        // Experiment for different n values
        int[] nValues = {6560, 19682, 59048, 177146, 531440}; // n = 3^(i+7) - 1 for i = 1, 2, 3, 4, 5
        int numExperiments = 20; // Number of identical experiments for averaging

        System.out.printf("%-20s%-25s%-25s%-25s%-25s%-25s%n", "n", "Avg Exec Time (ms)", "Avg Heap Size", "Avg Links", "Avg Cuts", "Avg Trees");

        for (int n : nValues) {
            long totalExecutionTime = 0;
            long totalHeapSize = 0;
            long totalLinks = 0;
            long totalCuts = 0;
            long totalTrees = 0;

            for (int exp = 0; exp < numExperiments; exp++) {
                // Create a new Fibonacci heap
                FibonacciHeap heap = new FibonacciHeap();

                // Generate a list of elements from 1 to n in random order
                List<Integer> elements = new ArrayList<>();
                for (int i = 1; i <= n; i++) {
                    elements.add(i);
                }
                Collections.shuffle(elements);

                // Step 1: Create an array to hold pointers to the nodes
                FibonacciHeap.HeapNode[] pointers = new FibonacciHeap.HeapNode[n]; // Array to hold pointers to the nodes

                // Insert elements into the heap and store their pointers in the array
                for (int i = 0; i < n; i++) {
                    // Insert the element and get the pointer to the new node
                    FibonacciHeap.HeapNode node = heap.insert(elements.get(i), Integer.toString(elements.get(i)));
                    // Save the pointer at the i-th index of the array
                    pointers[node.key -1] = node;
                }
                // Step 1: Perform one deleteMin to remove the current min
                heap.deleteMin();
                System.out.println("Heap size after deleteMin: " + heap.size());

                
                // Maintain the pointer array, remove the min node (first element)
                pointers = Arrays.copyOfRange(pointers, 1, pointers.length); // Remove first element after deleteMin

                // Step 2: Decrease key of the max node to make it the root
                // Max node is at the last index in the array after the first deleteMin
                FibonacciHeap.HeapNode maxNode = pointers[pointers.length - 1];
                System.out.println("Initial max node: " + maxNode);
                
                

                // Step 3: Perform deleteMin until we have 2^5 - 1 elements left in the heap
                int targetSize = 31; // 2^5 - 1 elements remaining
                int targetDecreases = n - targetSize; // The number of times we need to call deleteMin

                long startTime = System.currentTimeMillis();

                // Loop to perform deleteMin and decreaseKey on the max node
                for (int i = 0; i < targetDecreases; i++) {
                    // Perform decrease-key on the current max node to make it the root
                    heap.decreaseKey(maxNode, maxNode.key + Integer.MIN_VALUE);
                  
                    // Perform deleteMin (removes the root, which is now the max node)
                    heap.deleteMin();
                    System.out.println("Heap size after deleteMin: " + heap.size());
                  
                    
                    // Maintain the pointer array, remove the max node
                    pointers = Arrays.copyOfRange(pointers, 0, pointers.length - 1);

                }

                long endTime = System.currentTimeMillis();

                // Collect statistics
                totalExecutionTime += (endTime - startTime);
                totalHeapSize += heap.size();
                totalLinks += heap.totalLinks();
                totalCuts += heap.totalCuts();
                totalTrees += heap.numTrees();
            }

            // Calculate averages
            double avgExecutionTime = totalExecutionTime / (double) numExperiments;
            double avgHeapSize = totalHeapSize / (double) numExperiments;
            double avgLinks = totalLinks / (double) numExperiments;
            double avgCuts = totalCuts / (double) numExperiments;
            double avgTrees = totalTrees / (double) numExperiments;

            // Print results for this n
            System.out.printf("%-20d%-25.2f%-25.2f%-25.2f%-25.2f%-25.2f%n", n, avgExecutionTime, avgHeapSize, avgLinks, avgCuts, avgTrees);
        }
        
    }	
}
    

