/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *
 * Completed by: Tony Dokanchi
 *
 */

public class HighwaysAndHospitals {
    public static long cost(int n, int hospitalCost, int highwayCost, int cities[][]) {
        // If it's cheapest to just give everyone a hospital
        if (hospitalCost <= highwayCost) {
            return (long) n * hospitalCost;
        }

        int[] rootNodes = new int[n+1];
        long numSubgraphs = n;

        for (int[] highway : cities) {
            // Merge the subgraphs across the highway
            int start = highway[1];
            while (rootNodes[start] != 0) {
                start = rootNodes[start];
            }
            int target = highway[0];
            while (rootNodes[target] != 0) {
                target = rootNodes[target];
            }
            // If not already part of the same subgraph
            if (start != target) {
                rootNodes[target] = start;
                numSubgraphs--;
            }
        }
        return numSubgraphs * hospitalCost + (n - numSubgraphs) * highwayCost;
    }
}