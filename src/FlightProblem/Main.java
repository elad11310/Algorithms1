package FlightProblem;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * this class represents the flight problem. finidng the most shortest way from 0,0 to mat.length,mat.length and finding how many ways like this.
 * in O(mXn).
 */
public class Main {

    public static int size = 5;
    static String[] initArr;


    static class Node {
        int value; // shortest way to reach this node
        int reverseValue; // shortest way to reach this node from the reverse check
        int right; // right value
        int down; // down value
        ArrayList<String> pred; // saving the node we came from
        int allPaths; // how many short ways until here
        boolean isInShortPath; // checking if the current node is in the quick path.


        public Node(int right, int down) {
            this.value = 0;
            this.reverseValue=0;
            this.right = right;
            this.down = down;
            pred = new ArrayList<>();
            pred.add("");
            pred.add("");
            allPaths = 1;
            isInShortPath = false;

        }

    }

    public static void flightProbInit(Node[][] mat, String[] initArr) {
        int down, right;
        int i, j, k = 0;
        // Scanner scanner = new Scanner(System.in);


        for (i = 0; i < mat.length; i++) { // initializing the paths.
            for (j = 0; j < mat[i].length; j++) {

                if (i == mat.length - 1 && j == mat.length - 1) {
                    mat[i][j] = new Node(0, 0);
                    break;
                }
                if (j == mat.length - 1) {
                    //System.out.println("Please enter down");
                    down = Integer.parseInt(initArr[k++]);
                    mat[i][j] = new Node(0, down);
                } else if (i == mat.length - 1) {
                    //System.out.println("Please enter right");
                    right = Integer.parseInt(initArr[k++]);
                    mat[i][j] = new Node(right, 0);

                } else {
                    //System.out.println("Please enter down and right");
                    down = Integer.parseInt(initArr[k++]);
                    right = Integer.parseInt(initArr[k++]);
                    mat[i][j] = new Node(right, down);
                }


            }
        }

        initColAndRow(mat, false);


    }

    private static void initColAndRow(Node[][] mat, boolean isReverse) {
        int i = 0, j = 0;
        if (!isReverse) { // if we initialize the row and col from 0,0
            // initialling the values of the col
            for (j = 1; j < mat[0].length; j++) {
                mat[i][j].value = mat[i][j - 1].value + mat[i][j - 1].right;
                mat[i][j].pred.set(0, "R");
            }

            /// initializing the values of the row
            j = 0;
            for (i = 1; i < mat.length; i++) {
                mat[i][j].value = mat[i - 1][j].value + mat[i - 1][j].down;
                mat[i][j].pred.set(0, "D");
            }
        } else { // if we wanna run on reverse and initialize from size-1 row and col
            i = size - 1;
            for (j = size - 2; j >= 0; j--) {
                mat[i][j].reverseValue = mat[i][j + 1].reverseValue + mat[i][j].right;
            }

            /// initializing the values of the row
            j = size - 1;
            for (i = size - 2; i >= 0; i--) {
                mat[i][j].reverseValue = mat[i + 1][j].reverseValue + mat[i][j].down;

            }
        }

    }


    public static void main(String[] args) {
        int i, j;
        Node mat[][] = new Node[size][size]; // creating size*size mat the will take care in (size-1)*(size-1) flight curse.


        try {
            readMatPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // first check from 0,0 to size-1,size-1
        flightProbInit(mat, initArr);
        int shortest = shortestPath(mat, 1, 1);
        System.out.println("shortest " + shortest);
        // now check from size-1 to 0,0
        int shortestReverse = shortestPathReverse(mat, size - 2, size - 2);
        System.out.println(shortestReverse);

        // checking if a node is in the short way
        for(i=0;i<mat.length;i++){
            for (j=0;j<mat[0].length;j++){
                if(mat[i][j].value +mat[i][j].reverseValue == shortest){
                    mat[i][j].isInShortPath = true;
                }
            }
        }

        for (i = 0; i < mat.length; i++) { // printing the nodes on the shortest ways.
            for (j = 0; j < mat[0].length; j++) {
                if (mat[i][j].isInShortPath) {
                    System.out.println(i + " f" + j);
                }
            }
        }

        // find all short paths and print them recursively
        List<String> allPaths = new ArrayList<>();
        findPaths(mat, "", 0, 0,allPaths);
        FlightPathGUI flightPathGUI = new FlightPathGUI(size,mat,allPaths);


    }


    private static int shortestPathReverse(Node[][] mat, int srcX, int srcY){
        int i,j;
        initColAndRow(mat,true);
        for (i = srcX; i>=0; i--) {
            for (j = srcY; j>=0; j--) {
                mat[i][j].reverseValue = minForPath(mat[i + 1][j].reverseValue + mat[i][j].down, mat[i][j + 1].reverseValue + mat[i][j].right);
            }
        }
        return mat[0][0].reverseValue;

    }
    private static int shortestPath(Node[][] mat, int srcX, int srcY) {
        int i, j;

        for (i = srcX; i < mat.length; i++) { // initializing the rest of the values from (1,1).
            for (j = srcY; j < mat[i].length; j++) {
                int down, right;
                mat[i][j].value = minForPath(mat[i - 1][j].value + mat[i - 1][j].down, mat[i][j - 1].value + mat[i][j - 1].right); // updating the shortest value untill this node
                down = mat[i - 1][j].value + mat[i - 1][j].down;
                right = mat[i][j - 1].value + mat[i][j - 1].right;
                // saving the node we came from
                if (down > right) {
                    mat[i][j].pred.set(0, "R");
                } else if (right > down) {
                    mat[i][j].pred.set(0, "D");
                } else {
                    mat[i][j].pred.set(0, "D");
                    mat[i][j].pred.set(1, "R");
                }

                /// checking how many same short ways until this node
                if (down == right) {
                    mat[i][j].allPaths = mat[i - 1][j].allPaths + mat[i][j - 1].allPaths;

                } else if (down < right) {

                    mat[i][j].allPaths = mat[i - 1][j].allPaths;
                } else {
                    mat[i][j].allPaths = mat[i][j - 1].allPaths;

                }


            }
        }
        // returns the last node of the mat with the shortest way.
        return mat[size - 1][size - 1].value;
    }


    private static int minForPath(int down, int left) {
        return (down < left) ? down : left;
    }


    private static void findPaths(Node[][] mat, String path,
                                  int i, int j, List<String> allPaths) {

        /// this function prints recursively the fastest way to the destination.
        int M = mat.length;
        int N = mat[0].length;

        // if we have reached the last cell, print the route
        if (i == M - 1 && j == N - 1) {
            System.out.println(path);
            allPaths.add(path);
        } else {

            // move right
            if (i < M && j + 1 < N && mat[i][j + 1].isInShortPath && mat[i][j + 1].pred.contains("R")) {
                findPaths(mat, path + "R", i, j + 1,allPaths);
            }

            // move down
            if (i + 1 < M && j < N && mat[i + 1][j].isInShortPath && mat[i + 1][j].pred.contains("D")) {
                findPaths(mat, path + "D", i + 1, j,allPaths);
            }
        }

    }

    private static void readMatPath() throws IOException {
        // We need to provide file path as the parameter:
        // double backquote is to avoid compiler interpret words
        // like \test as \t (ie. as a escape sequence)
        File file = new File("C:\\Users\\elad1\\Desktop\\test.txt");


        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            System.out.println(st);
            initArr = st.split(" ");
        }


    } /// reading from user the mat we wanna check


}

