import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
            // One hospital in each city
            return (long) n * hospitalCost;
        }

        // One hospital per disconnected subgraph, connect the rest with roads
        long numHospitals = 0;
        long numHighways = 0;

        boolean[] seen = new boolean[n+1];

        // Create the map connections, where connections[i] is an int array of all the cities that can be connected to i.
        int[][] connections = new int[n+1][];
        for (int node = 1; node <= n; node++) {
            ArrayList<Integer> connectedTo = new ArrayList<Integer>();
            for (int[] highway : cities) {
                if (highway[0] == node) {
                    connectedTo.add(highway[1]);
                }
                else if (highway[1] == node) {
                    connectedTo.add(highway[0]);
                }
            }
            connections[node] = new int[connectedTo.size()];
            for (int i = 0; i < connectedTo.size(); i++) {
                connections[node][i] = connectedTo.get(i);
            }
        }

        for (int i = 1; i <= n; i++) {
            // If it's on a new subgraph, add a hospital
            if (!seen[i]) {
                seen[i] = true;
                numHospitals++;

                // Do BFS on node i to find the full subgraph
                Queue<Integer> toExplore = new LinkedList<Integer>();
                toExplore.add(i);
                while (!toExplore.isEmpty()) {
                    int startNode = toExplore.remove();
                    /*
                    Note: see if running this code here is faster than checking if highway[0 or 1] has been seen below
                    if (seen[startNode]) {
                        continue;
                    }
                     */
                    for (int destination : cities[startNode]) {
                        if (!seen[destination]) {
                            toExplore.add(destination);
                            seen[destination] = true;
                            numHighways++;
                        }
                    }
                }
            }
        }
        return highwayCost * numHighways + hospitalCost * numHospitals;
    }

    public static long cost2(int n, int hospitalCost, int highwayCost, int cities[][]) {
        // If it's cheapest to just give everyone a hospital
        if (hospitalCost <= highwayCost) {
            // One hospital in each city
            return (long) n * hospitalCost;
        }

        // One hospital per disconnected subgraph, connect the rest with roads
        long numHospitals = 0;
        long numHighways = 0;

        boolean[] seen = new boolean[n+1];
        for (int i = 1; i <= n; i++) {
            // If it's on a new subgraph, add a hospital
            if (!seen[i]) {
                seen[i] = true;
                numHospitals++;

                // Do BFS on node i to find the full subgraph
                Queue<Integer> toExplore = new LinkedList<Integer>();
                toExplore.add(i);
                while (!toExplore.isEmpty()) {
                    int startNode = toExplore.remove();
                    /*
                    Note: see if running this code here is faster than checking if highway[0 or 1] has been seen below
                    if (seen[startNode]) {
                        continue;
                    }
                     */
                    for (int[] highway : cities) {
                        if (highway[0] == startNode && !seen[highway[1]]) {
                            toExplore.add(highway[1]);
                            seen[highway[1]] = true;
                            numHighways++;
                        }
                        else if (highway[1] == startNode && !seen[highway[0]]) {
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
