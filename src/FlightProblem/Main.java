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
        int right; // right value
        int down; // down value
        ArrayList<String> pred; // saving the node we came from
        int allPaths; // how many short ways until here
        boolean isInShortPath; // checking if the current node is in the quick path.


        public Node(int right, int down) {
            this.value = 0;
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

        initColAndRow(mat, 0, 0,true);


    }

    private static void initColAndRow(Node[][] mat, int I, int J, boolean isFirst) {
        int i;
        // initialling the values of the col
        for (i = J + 1; i < mat[0].length; i++) {
            mat[I][i].value = mat[I][i - 1].value + mat[I][i - 1].right;
            if (isFirst)
                mat[I][i].pred.set(0, "R");
        }

        /// initializing the values of the row

        for (i = I + 1; i < mat.length; i++) {
            mat[i][J].value = mat[i - 1][J].value + mat[i - 1][J].down;
            if (isFirst)
                mat[i][J].pred.set(0, "D");
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
        flightProbInit(mat, initArr); // first check from 0,0 to size-1,size-1
        int shortest = shortestPath(mat, 1, 1, size - 1, size - 1, true);
        System.out.println("shortest " + shortest);


        for (i = 0; i < mat.length; i++) {
            for (j = 0; j < mat[0].length; j++) {
                if (i == 0 && j == 0) {
                    System.out.println("here");
                    j++;
                }
                //int temp = mat[i][j].value;


                mat[i][j].value = 0;
                int a = shortestPath(mat, i + 1, j + 1, mat.length - 1, mat.length - 1, false);
                int b = shortestPath(mat, 1, 1, i, j, false);
                System.out.println(a);
                System.out.println(b);
                if (a + b == shortest) {
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


        //List<String> path = new ArrayList<>();

        findPaths(mat, "", 0, 0);


    }

    private static int shortestPath(Node[][] mat, int srcX, int srcY, int desX, int desY, boolean isFirst) {
        int i, j;

        // initialize  the first row and first col
        initColAndRow(mat, srcX - 1, srcY - 1,false);


        if (srcY == size) // means we need to check last row
        {
            for (i = srcX; i < mat.length; i++) {
                mat[i][srcY - 1].value = mat[i - 1][srcY - 1].value + mat[i - 1][srcY - 1].down;

            }
            return mat[size - 1][size - 1].value;
        }

        if (srcX == size) // means we need to check last col
        {
            for (i = srcY; i < mat.length; i++) {
                mat[srcX - 1][srcY].value = mat[srcX - 1][i - 1].value + mat[srcX - 1][i - 1].right;

            }
            return mat[size - 1][size - 1].value;
        }


        for (i = srcX; i < mat.length; i++) { // initializing the rest of the values from (1,1).
            for (j = srcY; j < mat[i].length; j++) {
                int down, right;
                mat[i][j].value = minForPath(mat[i - 1][j].value + mat[i - 1][j].down, mat[i][j - 1].value + mat[i][j - 1].right); // updating the shortest value untill this node
                down = mat[i - 1][j].value + mat[i - 1][j].down;
                right = mat[i][j - 1].value + mat[i][j - 1].right;
                if (isFirst) { // we wanna save the wights from Start to End and keep them for rest of the checks
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
                    if (mat[i - 1][j].value + mat[i - 1][j].down == mat[i][j - 1].value + mat[i][j - 1].right) {
                        mat[i][j].allPaths = mat[i - 1][j].allPaths + mat[i][j - 1].allPaths;

                    } else if (mat[i - 1][j].value + mat[i - 1][j].down < mat[i][j - 1].value + mat[i][j - 1].right) {

                        mat[i][j].allPaths = mat[i - 1][j].allPaths;
                    } else {
                        mat[i][j].allPaths = mat[i][j - 1].allPaths;

                    }


                }


            }
            if (i == desX && j == desY) // if we check from A to each node except lsat.
                break;
        }
        // returns the last node of the mat with the shortest way.
        return mat[desX][desY].value;
    }


    private static int minForPath(int down, int left) {
        return (down < left) ? down : left;
    }


    private static void findPaths(Node[][] mat, String path,
                                  int i, int j) {

        /// this function prints recursively the fastest way to the destination.
        int M = mat.length;
        int N = mat[0].length;

        // if we have reached the last cell, print the route
        if (i == M - 1 && j == N - 1) {
            System.out.println(path);

        } else {

            // move right
            if (i < M && j + 1 < N && mat[i][j + 1].isInShortPath && mat[i][j+1].pred.contains("R")) {
                findPaths(mat, path + "R", i, j + 1);
            }

            // move down
            if (i + 1 < M && j < N && mat[i + 1][j].isInShortPath && mat[i + 1][j].pred.contains("D")) {
                findPaths(mat, path + "D", i + 1, j);
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

