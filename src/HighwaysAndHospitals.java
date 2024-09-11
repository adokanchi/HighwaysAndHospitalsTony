import java.util.LinkedList;
import java.util.Queue;

/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *
 * Completed by: [YOUR NAME HERE]
 *
 */

public class HighwaysAndHospitals {

    /**
     * TODO: Complete this function, cost(), to return the minimum cost to provide
     *  hospital access for all citizens in Menlo County.
     */
    public static long cost(int n, int hospitalCost, int highwayCost, int cities[][]) {
        // If it's cheapest to just give everyone a hospital
        if (hospitalCost <= highwayCost) {
            // One hospital in each city
            return n * hospitalCost;
        }

        // One hospital per disconnected subgraph, connect the rest with roads
        int numHospitals = 0;
        int numHighways = 0;

        boolean[] seen = new boolean[n+1];
        for (int i = 1; i <= n; i++) {
            if (!seen[i]) {
                seen[i] = true;
                numHospitals++;

                // Do BFS on node i to find the size of the full subgraph
                Queue<Integer> toExplore = new LinkedList<Integer>();
                toExplore.add(i);
                while (!toExplore.isEmpty()) {
                    int startNode = toExplore.remove();
                    for (int[] highway : cities) {
                        if (highway[0] == startNode) {
                            toExplore.add(highway[1]);
                            seen[highway[1]] = true;
                            numHighways++;
                        }
                        else if (highway[1] == startNode) {
                            toExplore.add(highway[0]);
                            seen[highway[0]] = true;
                            numHighways++;
                        }
                    }
                }
            }
        }
        return highwayCost * numHighways + hospitalCost * numHospitals;
    }
}
