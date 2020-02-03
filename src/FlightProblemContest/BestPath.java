package FlightProblemContest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BestPath {
    static String[] initArr;
    private Node[][] mat;
    private int teta;


    public BestPath(Node[][] mat, int teta) {
        this.teta = teta;
        this.mat = mat;
        initMat(mat);
    }

    private void initMat(Node[][] mat) {
        initColAndRow(mat); // init first col and first row.
        int i, j;
        double up, right, upSec, rightSec;
        for (i = mat.length - 2; i >= 0; i--) { // initializing the rest of the values from (1,1).
            for (j = 1; j < mat[i].length; j++) {
                upSec = mat[i + 1][j].secondValue + mat[i + 1][j].y;
                rightSec = mat[i][j - 1].secondValue + mat[i][j - 1].x;
                right = mat[i][j - 1].value + mat[i][j - 1].x; // the node from down + its value till this node
                up = mat[i + 1][j].value + mat[i + 1][j].y;
                mat[i][j].value = minForPath(up, right); // updating the shortest value until this node


                if (right < up) {

                    if (rightSec == up && mat[i][j - 1].secondValue != 0) {
                        mat[i][j].secondValue = rightSec;
                        //mat[i][j].allPaths2 = mat[i + 1][i].allPaths + mat[i][j - 1].allPaths2;
                        concatShortestPaths2(i, j - 1, i, j, '0');
                        concatShortestPaths22(i + 1, j, i, j, '1');

                    } else if (rightSec < up && mat[i][j - 1].secondValue != 0) {
                        mat[i][j].secondValue = rightSec;
                        //mat[i][j].allPaths2 = mat[i][j - 1].allPaths2;
                        concatShortestPaths2(i, j - 1, i, j, '0');
                    } else {
                        mat[i][j].secondValue = up;
                        //mat[i][j].allPaths2 = mat[i + 1][j].allPaths;
                        concatShortestPaths22(i + 1, j, i, j, '1');
                    }

                } else if (right > up) {
                    if (upSec == right && mat[i + 1][j].secondValue != 0) {
                        mat[i][j].secondValue = upSec;
                        //mat[i][j].allPaths2 = mat[i + 1][j].allPaths + mat[i][j - 1].allPaths2;
                        concatShortestPaths22(i, j - 1, i, j, '0');
                        concatShortestPaths2(i + 1, j, i, j, '1');

                    } else if (upSec < right && mat[i + 1][j].secondValue != 0) {
                        mat[i][j].secondValue = upSec;
                        //mat[i][j].allPaths2 = mat[i + 1][j].allPaths2;
                        concatShortestPaths2(i + 1, j, i, j, '1');
                    } else {
                        mat[i][j].secondValue = right;
                        //mat[i][j].allPaths2 = mat[i][j - 1].allPaths;
                        concatShortestPaths22(i, j - 1, i, j, '0');
                    }

                } else {
                    if (mat[i + 1][j].secondValue == 0 && mat[i][j - 1].secondValue == 0)
                        mat[i][j].secondValue = 0;

                    else if (mat[i][j - 1].secondValue == 0) {
                        mat[i][j].secondValue = upSec;
                        //mat[i][j].allPaths2 = mat[i + 1][j].allPaths2;
                        concatShortestPaths2(i + 1, j, i, j, '1');
                    } else if (mat[i + 1][j].secondValue == 0) {
                        mat[i][j].secondValue = rightSec;
                        //mat[i][j].allPaths2 = mat[i][j - 1].allPaths2;
                        concatShortestPaths2(i, j - 1, i, j, '0');
                    } else if (rightSec < upSec) {
                        mat[i][j].secondValue = rightSec;
                        //mat[i][j].allPaths2 = mat[i][j - 1].allPaths2;
                        concatShortestPaths2(i, j - 1, i, j, '0');
                    } else if (upSec < rightSec) {
                        mat[i][j].secondValue = upSec;
                        //mat[i][j].allPaths2 = mat[i + 1][j].allPaths2;
                        concatShortestPaths2(i + 1, j, i, j, '1');
                    } else {
                        mat[i][j].secondValue = rightSec;
                        // mat[i][j].allPaths2 = mat[i][j - 1].allPaths2 + mat[i + 1][j].allPaths2;
                        concatShortestPaths2(i, j - 1, i, j, '0');
                        concatShortestPaths2(i + 1, j, i, j, '1');
                    }


                }


                // checking maneuver change and shortest paths.
                if (up > right) { // if we come to this node only from right (value determines)

                    // checkManeuverChange(i, j - 1, i, j, '0');
                    concatShortestPaths(i, j - 1, i, j, '0');


                } else if (right > up) {

                    //checkManeuverChange(i + 1, j, i, j, '1');
                    concatShortestPaths(i + 1, j, i, j, '1');


                } else { // in case of 2 shortest ways come to this node


                    // concat shortest paths.
                    concatShortestPaths(i, j - 1, i, j, '0');
                    concatShortestPaths(i + 1, j, i, j, '1');
                }


            }
        }


        updateFirst();
        updateSecond();

    }


    private void checkManeuverChange(int k, int p, int i, int j, char path) {
        int min, ans;
        if (mat[i][j].lowestDirectionChange != 0) { // if this node list of paths is not empty
            min = mat[i][j].lowestDirectionChange;
        } else
            min = mat[k][p].lowestDirectionChange; // if its empty take the lowest maneuver from the node we came from


        for (int q = 0; q < mat[k][p].lowestManeuver.size(); q++) { // iterating through the list of paths
            // String temp = mat[k][p].lowestManeuver.get(q) + path;


            ans = lowestManeuver(mat[k][p].lowestManeuver.get(q) + path); // calculating num of turns


            if (ans <= min || (q == mat[k][p].lowestManeuver.size() - 1 && mat[i][j].lowestManeuver.size() == 0)) { // if it should get inside this node's list of paths
                min = ans; // update the new min of turns
                // adding the new minimum turns path to the list
                mat[i][j].lowestManeuver.add(mat[k][p].lowestManeuver.get(q) + path);


            }


            if (mat[i][j].lowestManeuver.size() != 0) {  // if the list of paths is empty , take the new minimum of turns
                mat[i][j].lowestDirectionChange = lowestManeuver(mat[i][j].lowestManeuver.get(0));
            } else
                mat[i][j].lowestDirectionChange = min; // take the current minimum of turns path


        }


    }

    private void concatShortestPaths(int k, int p, int i, int j, char path) {
        for (int q = 0; q < mat[k][p].shortestPaths.size() && q <= teta; q++)
            mat[i][j].shortestPaths.add(mat[k][p].shortestPaths.get(q) + path);
    }

    private void concatShortestPaths2(int k, int p, int i, int j, char path) {
        for (int q = 0; q < mat[k][p].shortestPaths2.size() && q <= teta; q++)
            mat[i][j].shortestPaths2.add(mat[k][p].shortestPaths2.get(q) + path);
    }

    private void concatShortestPaths22(int k, int p, int i, int j, char path) {
        for (int q = 0; q < mat[k][p].shortestPaths.size() && q <= teta; q++)
            mat[i][j].shortestPaths2.add(mat[k][p].shortestPaths.get(q) + path);
    }

    private static int lowestManeuver(String s) {
        char current = s.charAt(0);
        int count = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != current) {
                current = s.charAt(i);
                count++;
            }
        }
        return count;
    }

    private static void initColAndRow(Node[][] mat) {
        int i = mat.length - 1, j = 0;

        // initialling the values of the row
        for (j = 1; j < mat[0].length; j++) {
            mat[i][j].value = mat[i][j - 1].value + mat[i][j - 1].x; // add to this value the previous node value + its x value.
            if (mat[i][j - 1].shortestPaths.size() != 0) {
                mat[i][j].shortestPaths.add(mat[i][j - 1].shortestPaths.get(0) + "0"); // adding the previous node path to this node path + 0

            } else {
                mat[i][j].shortestPaths.add("0");
            }
            mat[i][j].leastDirectionChange = mat[i][j - 1].leastDirectionChange + "0"; // adding to the string that hold the path until this node.
            mat[i][j].lowestManeuver.add(mat[i][j].leastDirectionChange);
        }

        /// initializing the values of the col
        j = 0;
        for (i = mat.length - 2; i >= 0; i--) {
            mat[i][j].value = mat[i + 1][j].value + mat[i + 1][j].y;
            if (mat[i + 1][j].shortestPaths.size() != 0) {
                mat[i][j].shortestPaths.add(mat[i + 1][j].shortestPaths.get(0) + "1");
            } else {
                mat[i][j].shortestPaths.add("1");
            }
            mat[i][j].leastDirectionChange = mat[i + 1][j].leastDirectionChange + "1";
            mat[i][j].lowestManeuver.add(mat[i][j].leastDirectionChange);
        }
    }

    private static double minForPath(double up, double right) {
        return (up < right) ? up : right;
    }

    private static double maxForPath(double up, double right) {
        return (up < right) ? right : up;
    }

    public static void flightProbInit(Node[][] mat, String[] initArr) {
        int up, right;
        int i, j, k = 0;


        for (i = mat.length - 1; i >= 0; i--) { // initializing the paths.
            for (j = 0; j < mat[i].length; j++) {

                if (i == 0 && j == mat.length - 1) {
                    mat[i][j] = new Node(0, 0);
                    break;
                }
                if (j == mat.length - 1) {
                    //System.out.println("Please enter down");
                    up = Integer.parseInt(initArr[k++]);
                    mat[i][j] = new Node(0, up);
                } else if (i == 0) {
                    //System.out.println("Please enter right");
                    right = Integer.parseInt(initArr[k++]);
                    mat[i][j] = new Node(right, 0);

                } else {
                    //System.out.println("Please enter down and right");
                    up = Integer.parseInt(initArr[k++]);
                    right = Integer.parseInt(initArr[k++]);
                    mat[i][j] = new Node(right, up);
                }


            }
        }


    } /// dont really need

    private static void readMatPath() throws IOException {
        // We need to provide file path as the parameter:
        // double backquote is to avoid compiler interpret words
        // like \test as \t (ie. as a escape sequence)
        File file = new File("C:\\Users\\elad1\\Desktop\\TestAll.txt");
        // test1 testOne test4 tester test3 test

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            System.out.println(st);
            initArr = st.split(" ");
        }


    } /// dont really need

    /////////////// part A functions \\\\\\\\\\\\\\\\\\\\\\\

    public int getNumOfCheapestPaths() { // return number of cheapest paths.
        return mat[0][mat.length - 1].shortestPaths.size();
    }

    public double getCheapestPrice() { // return cheapest path value
        return mat[0][mat.length - 1].value;
    }

    public int getNumOfOptimalPaths() { // return num of optimal paths(lowest maneuver).
        return mat[0][mat.length - 1].lowestManeuver.size();
    }

    public ArrayList<String> getAllOptimalPaths() { // list with all optimal paths

        return mat[0][mat.length - 1].lowestManeuver;
    }

    public int printNumOfTurns() { // returns num of minimum turns in the optimal path
        return mat[0][mat.length - 1].lowestDirectionChange;

    }

    public ArrayList<String> getAllCheapestPaths() { // returns all the shortest paths.
        return mat[0][mat.length - 1].shortestPaths;
    }


    /////////// part B functions \\\\\\\\\\\\\\\\\\\\

    public int getNumOfOptimalPaths2() { // returns the number of paths with second optimal paths.
        return mat[0][mat.length - 1].secondManeuver.size();
    }

    public ArrayList<String> getAllOptimalPaths2() { // returns all the second optimal paths.
        return mat[0][mat.length - 1].secondManeuver;
    }

    private void updateSecond() {
        int count = 0, min = Integer.MAX_VALUE;
        HashMap<String, Integer> secondManeuverToPrint = new HashMap<>();
        for (int i = 0; i < mat[0][mat.length - 1].shortestPaths2.size() && i <= teta; i++) {
            count = lowestManeuver(mat[0][mat.length - 1].shortestPaths2.get(i));
            secondManeuverToPrint.put(mat[0][mat.length - 1].shortestPaths2.get(i), count);
            if (count < min) {
                min = count;
            }
        }
        for (String s : secondManeuverToPrint.keySet()) {
            if (secondManeuverToPrint.get(s) == min) {
                mat[0][mat.length - 1].secondManeuver.add(s);
            }
        }

        if (min == Integer.MAX_VALUE) {
            mat[0][mat.length - 1].secondDirectionChange = -1;
        } else
            mat[0][mat.length - 1].secondDirectionChange = min;
    }

    private void updateFirst() {
        int count = 0, min = Integer.MAX_VALUE;
        HashMap<String, Integer> firstManeuverToPrint = new HashMap<>();
        for (int i = 0; i < mat[0][mat.length - 1].shortestPaths.size() && i <= teta; i++) {
            count = lowestManeuver(mat[0][mat.length - 1].shortestPaths.get(i));
            firstManeuverToPrint.put(mat[0][mat.length - 1].shortestPaths.get(i), count);
            if (count < min) {
                min = count;
            }
        }
        for (String s : firstManeuverToPrint.keySet()) {
            if (firstManeuverToPrint.get(s) == min) {
                mat[0][mat.length - 1].lowestManeuver.add(s);
            }
        }

        if (min == Integer.MAX_VALUE) {
            mat[0][mat.length - 1].lowestDirectionChange = -1;
        } else
            mat[0][mat.length - 1].lowestDirectionChange = min;
    }


    public int getNumOfTurns2() { // returns the number of turns in the second optimal paths.
        return mat[0][mat.length - 1].secondDirectionChange;
    }

    public double getCheapestPrice2() {
        return mat[0][mat.length - 1].secondValue;
    }

    public int getNumOfCheapestPaths2() {
        return mat[0][mat.length - 1].shortestPaths2.size();
    }

    public ArrayList<String> getAllCheapestPaths2() {
        return mat[0][mat.length - 1].shortestPaths2;
    }


    public static void main(String[] args) {

        Node[][] mat = new Node[8][6];
        try {
            readMatPath(); // reading mat input from file.
        } catch (IOException e) {
            e.printStackTrace();
        }
        long start = System.currentTimeMillis();
        flightProbInit(mat, initArr);
        BestPath b = new BestPath(mat, 100);

        System.out.println("PART A: ---->");
        System.out.println("shortest path " + b.getCheapestPrice());
        System.out.println("num of shortest paths " + b.getNumOfCheapestPaths());
        System.out.println("num of optimal paths " + b.getNumOfOptimalPaths());
        ArrayList<String> s = b.getAllOptimalPaths();
        System.out.println("all the optimal paths " + s);
        System.out.println("num of turns in the optimal paths " + b.printNumOfTurns());
        System.out.println("all shortest paths :" + b.getAllCheapestPaths());
        System.out.println("PART B: ---->");

        System.out.println("Second shortest value " + b.getCheapestPrice2());
        System.out.println("num of second shortest paths " + b.getNumOfCheapestPaths2());
        System.out.println("num of second optimal paths " + b.getNumOfOptimalPaths2());
        s = b.getAllOptimalPaths2();
        System.out.println("all the second optimal paths " + s);
        System.out.println("num of turns in the second optimal paths " + b.getNumOfTurns2());
        System.out.println("all second shortest paths");
        System.out.println(b.getAllCheapestPaths2());


        long end = System.currentTimeMillis();
        System.out.println("Start :" + start + " End : " + end);

    }


}