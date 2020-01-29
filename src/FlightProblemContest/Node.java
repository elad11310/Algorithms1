package FlightProblemContest;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {
    double y, x;
    double value; // shortest way to reach this node
    double secondValue;
    double reverseValue; // shortest way to reach this node from the reverse check
    ArrayList<String> shortestPaths; // saving the node we came from
    int allPaths; // how many short ways until here
    int allPaths2; // how many second short ways until here
    boolean isInShortPath; // checking if the current node is in the quick path.
    String leastDirectionChange; // saving the lowest maneuver
    int lowestDirectionChange; // saving the number of path with lowest direction change.
    ArrayList<String> lowestManeuver; // saving the lowest maneuver paths
    ArrayList<String> secondManeuver; // saving the second lowest maneuver paths
    int secondDirectionChange; // for counting the second lowest maneuver paths.
    public Node(double x, double y) {
        this.x = x;
        this.y = y;
        this.value = 0;
        this.reverseValue = 0;
        secondValue = 0;
        shortestPaths = new ArrayList<>();
        allPaths = 1;
        isInShortPath = false;
        leastDirectionChange = "";
        lowestManeuver = new ArrayList<>();
        lowestDirectionChange = 0;
        secondManeuver = new ArrayList<>();
        secondDirectionChange = 0;
        allPaths2 = 0;


    }
}
