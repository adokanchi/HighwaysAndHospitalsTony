/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 * Completed by: Tony Dokanchi
 */
public class HighwaysAndHospitals {
    public static long cost(int n, int hospitalCost, int highwayCost, int[][] cities) {
        // If it's cheapest to just give everyone a hospital
        if (hospitalCost <= highwayCost) return (long) n * hospitalCost;
        int[] rootNodes = new int[n+1];

        long numSubgraphs = n;
        for (int[] highway : cities) {
            int start = highway[0], target = highway[1];

            // Path Compression

            // set startRoot to be the deepest root of start
            int startRoot = start;
            while (rootNodes[startRoot] > 0) {
                startRoot = rootNodes[startRoot];
            }
            while (rootNodes[start] > 0) {
                // Make start point at its root, move start up one layer
                int temp = rootNodes[start];
                rootNodes[start] = startRoot;
                start = temp;
            }

            // set targetRoot to be the deepest root of target
            int targetRoot = target;
            while (rootNodes[targetRoot] > 0) {
                targetRoot = rootNodes[targetRoot];
            }
            while (rootNodes[target] > 0) {
                // Make target point at its root, move target up one layer
                int temp = rootNodes[target];
                rootNodes[target] = targetRoot;
                target = temp;
            }

            // Combine subgraphs if necessary
            if (start != target) {
                int startOrder = rootNodes[start];
                int targetOrder = rootNodes[target];

                // Invert the if statement because negatives
                if (startOrder < targetOrder) {
                    // Minus one because negatives
                    rootNodes[start] += targetOrder - 1;
                    rootNodes[target] = start;
                }
                else {
                    // Minus one because negatives
                    rootNodes[target] += startOrder - 1;
                    rootNodes[start] = target;
                }
                numSubgraphs--;
            }
        }
        // Calculate cost and return
        return numSubgraphs * hospitalCost + (n - numSubgraphs) * highwayCost;
    }

    // Union-Find without weight balancing or path compression
    public static long cost0(int n, int hospitalCost, int highwayCost, int[][] cities) {
        if (hospitalCost <= highwayCost) return (long) n * hospitalCost;
        int[] rootNodes = new int[n+1];
        long numSubgraphs = n;
        for (int[] highway : cities) {
            int target = highway[0], start = highway[1];
            while (rootNodes[start] != 0) start = rootNodes[start];
            while (rootNodes[target] != 0) target = rootNodes[target];
            if (start != target) {
                rootNodes[target] = start;
                numSubgraphs--;
            }
        }
        return numSubgraphs * hospitalCost + (n - numSubgraphs) * highwayCost;
    }
}