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

        int[] rootNodes = new int[n+1];

        for (int[] highway : cities) {
            // Merge the subgraphs across the highway
            int start = findRoot(highway[1], rootNodes);
            int target = findRoot(highway[0], rootNodes);
            // If not already part of the same subgraph
            if (start != target) {
                rootNodes[target] = start;
            }
        }

        long cost = 0;
        for (int node = 1 ; node <= n; node++) {
            if (rootNodes[node] == 0) {
                cost += hospitalCost;
            }
            else {
                cost += highwayCost;
            }
        }
        return cost;
    }

    public static int findRoot(int node, int[] rootNodes) {
        while (rootNodes[node] != 0) {
            node = rootNodes[node];
        }
        return node;
    }

    public static long cost1(int n, int hospitalCost, int highwayCost, int cities[][]) {
        // If it's cheapest to just give everyone a hospital
        if (hospitalCost <= highwayCost) {
            // One hospital in each city
            return (long) n * hospitalCost;
        }

        // One hospital per disconnected subgraph, connect the rest with roads
        long cost = 0;

        boolean[] seen = new boolean[n+1];

        // Create the map connections, where connections[i] is an int array of all the cities that can be connected to i.
        ArrayList<ArrayList<Integer>> connections = new ArrayList<ArrayList<Integer>>();
        connections.add(null);

        // O(m*n)
        for (int node = 1; node <= n; node++) { // O(n)
            connections.add(new ArrayList<Integer>());
            for (int[] highway : cities) { // O(m)
                if (highway[0] == node) {
                    connections.get(node).add(highway[1]);
                }
                else if (highway[1] == node) {
                    connections.get(node).add(highway[0]);
                }
            }
        }

        for (int i = 1; i <= n; i++) { // O(n)
            if (seen[i]) {
                continue;
            }

            // If it's on a new subgraph, add a hospital
            seen[i] = true;
            cost += hospitalCost;

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
                for (int destination : connections.get(startNode)) { // O(m) ??
                    if (seen[destination]) {
                        continue;
                    }
                    toExplore.add(destination);
                    seen[destination] = true;
                    cost += highwayCost;
                }
            }
        }
        return cost;
    }

    public static long cost0(int n, int hospitalCost, int highwayCost, int cities[][]) {
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
            if (seen[i]) {
                continue;
            }

            // If it's on a new subgraph, add a hospital
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
        return highwayCost * numHighways + hospitalCost * numHospitals;
    }
}
