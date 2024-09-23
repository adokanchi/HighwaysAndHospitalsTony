/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 * Completed by: Tony Dokanchi
 */
public class HighwaysAndHospitals {
    public static long cost0(int n, int hospitalCost, int highwayCost, int[][] cities) {
        // If it's cheapest to just give everyone a hospital
        if (hospitalCost <= highwayCost) return (long) n * hospitalCost;
        int[] rootNodes = new int[n+1];

        long numSubgraphs = n;
        // For each edge AB:
        for (int[] highway : cities) {
            int start = highway[0], target = highway[1];

            // Path Compression
            int startRoot = start;
            while (rootNodes[startRoot] > 0) {
                startRoot = rootNodes[startRoot];
            }
            while (rootNodes[start] > 0) {
                // Make start point at its base root, move start up the tree one layer
                int temp = rootNodes[start];
                rootNodes[start] = startRoot;
                start = temp;
            }

            int targetRoot = target;
            while (rootNodes[targetRoot] > 0) {
                targetRoot = rootNodes[targetRoot];
            }
            while (rootNodes[target] > 0) {
                int temp = rootNodes[target];
                rootNodes[target] = targetRoot;
                target = temp;
            }

            // If A != B:
            if (start != target) {
                // Combine graphs

                int startOrder = rootNodes[start];
                int targetOrder = rootNodes[target];

                if (startOrder > targetOrder) {
                    rootNodes[start] += targetOrder - 1;
                    rootNodes[target] = start;
                }
                else {
                    rootNodes[target] += startOrder - 1;
                    rootNodes[start] = target;
                }
                numSubgraphs--;
            }
        }
        // Calculate cost and return
        return numSubgraphs * hospitalCost + (n - numSubgraphs) * highwayCost;
    }

    public static long cost(int n, int hospitalCost, int highwayCost, int[][] cities) {
        // If it's cheapest to just give everyone a hospital
        if (hospitalCost <= highwayCost) return (long) n * hospitalCost;
        int[] rootNodes = new int[n+1];
        long numSubgraphs = n;
        for (int[] highway : cities) {
            int target = highway[0], start = highway[1];
            while (rootNodes[start] != 0) start = rootNodes[start];
            while (rootNodes[target] != 0) target = rootNodes[target];
            // Merge the subgraphs across the roots if not already part of the same subgraph
            if (start != target) {
                rootNodes[target] = start;
                numSubgraphs--;
            }
        }
        return numSubgraphs * hospitalCost + (n - numSubgraphs) * highwayCost;
    }
}