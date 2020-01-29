package FlightProblemContest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BestPath {
    static String[] initArr;
    Node[][] mat;
    int teta;

    public BestPath(Node[][] mat, int teta) {
        this.teta = teta;
        this.mat = mat;
        initMat(mat);
    }

    private void initMat(Node[][] mat) {
        initColAndRow(mat); // init first col and first row.
        int i, j;
        double up, right;
        for (i = mat.length - 2; i >= 0; i--) { // initializing the rest of the values from (1,1).
            for (j = 1; j < mat[i].length; j++) {

                right = mat[i][j - 1].value + mat[i][j - 1].x; // the node from down + its value till this node
                up = mat[i + 1][j].value + mat[i + 1][j].y;
                mat[i][j].value = minForPath(up, right); // updating the shortest value until this node
                if(i==1 && j==1){ // first node to initialize second value path
                    mat[i][j].secondValue = maxForPath(up,right);
                }
                // if we ask on the secondValue of the first row
                if(mat[i+1][j].secondValue == 0){
                    mat[i][j].secondValue = minForPath(mat[i+1][j].value+mat[i+1][j].y,mat[i][j-1].secondValue+mat[i][j-1].x);
                }
                // if we ask on the secondValue of the first col
                else if (mat[i][j-1].secondValue==0){
                    mat[i][j].secondValue = minForPath(mat[i][j-1].value+mat[i][j-1].x,mat[i+1][j].secondValue+mat[i+1][j].y);
                }
                else{
                    mat[i][j].secondValue = minForPath(mat[i][j-1].secondValue+mat[i][j-1].x,mat[i+1][j].secondValue+mat[i+1][j].y);
                }


                // checking maneuver change and shortest paths.
                if (up > right) { // if we come to this node only from right (value determines)

                    checkManeuverChange(i, j - 1, i, j, '0');
                    concatShortestPaths(i, j - 1, i, j, '0');


                } else if (right > up) {

                    checkManeuverChange(i + 1, j, i, j, '1');
                    concatShortestPaths(i + 1, j, i, j, '1');


                } else { // in case of 2 shortest ways come to this node

                    if (mat[i][j - 1].lowestDirectionChange == mat[i + 1][j].lowestDirectionChange ||
                            Math.abs(mat[i][j - 1].lowestDirectionChange - mat[i + 1][j].lowestDirectionChange) == 1) {
                        if (mat[i + 1][j].lowestDirectionChange >mat[i][j - 1].lowestDirectionChange) {
                            checkManeuverChange(i + 1, j, i, j, '1');
                            checkManeuverChange(i, j - 1, i, j, '0');
                        } else {
                            checkManeuverChange(i, j - 1, i, j, '0');
                            checkManeuverChange(i + 1, j, i, j, '1');
                        }
//                        checkManeuverChange(i, j - 1, i, j, '0');
//                        checkManeuverChange(i + 1, j, i, j, '1');


                    } else if (mat[i][j - 1].lowestDirectionChange > mat[i + 1][j].lowestDirectionChange) {
                        checkManeuverChange(i + 1, j, i, j, '1');
                    } else {
                        checkManeuverChange(i, j - 1, i, j, '0');
                    }


                    // concat shortest paths.
                    concatShortestPaths(i, j - 1, i, j, '0');
                    concatShortestPaths(i + 1, j, i, j, '1');
                }
                System.out.println(mat[i][j].lowestManeuver);
                System.out.println(mat[i][j].lowestDirectionChange);


                /// checking how many same short ways until this node
                if (up == right) {
                    mat[i][j].allPaths = mat[i + 1][j].allPaths + mat[i][j - 1].allPaths;

                } else if (up < right) {

                    mat[i][j].allPaths = mat[i + 1][j].allPaths;
                } else {
                    mat[i][j].allPaths = mat[i][j - 1].allPaths;

                }


            }
        }


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
            // checking the last turn of this path in the list with the next turn towards the next node.
//          if (mat[k][p].lowestManeuver.get(q).endsWith(""+path)) {
//              ans = mat[k][p].lowestDirectionChange;
//          } else {
//              ans = mat[k][p].lowestDirectionChange + 1;
//            }


            if (ans <= min || (q == mat[k][p].lowestManeuver.size() - 1 && mat[i][j].lowestManeuver.size() == 0)) { // if it should get inside this node's list of paths
                min = ans; // update the new min of turns
                // adding the new minimum turns path to the list
                mat[i][j].lowestManeuver.add(mat[k][p].lowestManeuver.get(q) + path);


            }


            if (mat[i][j].lowestManeuver.size() != 0) {  // if the list of paths is empty , take the new minimum of turns
                mat[i][j].lowestDirectionChange = lowestManeuver(mat[i][j].lowestManeuver.get(0));
            } else
                mat[i][j].lowestDirectionChange = min; // take the current minimum of turns path


            ///////////////// second maneuver\\\\\\\\\\\\\\
//            if (i == 0 && j == mat.length - 1) { // only if we are on the last node
//
//                if (ans > min && mat[i][j].secondDirectionChange == 0) {
//                    mat[i][j].secondDirectionChange = ans;
//                    mat[i][j].secondManeuver.add(mat[k][p].lowestManeuver.get(q) + path);
//                }
//                else if(ans == mat[i][j].secondDirectionChange){
//                    mat[i][j].secondManeuver.add(mat[k][p].lowestManeuver.get(q) + path);
//                }
//
//            }


        }


    }

    private void concatShortestPaths(int k, int p, int i, int j, char path) {
        for (int q = 0; q < mat[k][p].shortestPaths.size(); q++)
            mat[i][j].shortestPaths.add(mat[k][p].shortestPaths.get(q) + path);
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
        File file = new File("C:\\Users\\elad1\\Desktop\\tester.txt");
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
        return mat[0][mat.length - 1].allPaths;
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
        //return lowestManeuver(mat[0][mat.length - 1].lowestManeuver.get(0)); // returns numm of

    }

    public ArrayList<String> getAllCheapestPaths() { // returns all the shortest paths.
        return mat[0][mat.length - 1].shortestPaths;
    }



    /////////// part B functions \\\\\\\\\\\\\\\\\\\\

    public int getNumOfOptimalPaths2(){ // returns the number of paths with second optimal paths.
        return mat[0][mat.length-1].secondManeuver.size();
    }
    public ArrayList<String> getAllOptimalPaths2(){ // returns all the second optimal paths.
        return mat[0][mat.length-1].secondManeuver;
    }
    public int getNumOfTurns2(){ // returns the number of turns in the second optimal paths.
        return mat[0][mat.length-1].secondDirectionChange;
    }

    public static void main(String[] args) {

        Node[][] mat = new Node[4][4];
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
//        System.out.println("PART B: ---->");
//        System.out.println("num of second optimal paths " + b.getNumOfOptimalPaths2());
//         s = b.getAllOptimalPaths2();
//        System.out.println("all the second optimal paths " + s);
//        System.out.println("num of turns in the second optimal paths " + b.getNumOfTurns2());


        long end = System.currentTimeMillis();
        System.out.println("Start :" + start + " End : " + end);

    }


}
