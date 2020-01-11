package MazeProblem;


/**
 * this class represents the maze problem which means we got matrix with 1,0 and we check if there is a path of 1 from start (0,0) to end (N-1,N-1)
 * if do we return true and prtins the path. the checking is made recursively.
 */
public class Main {

    public static final int N = 4;
    public static final int M = 4;
    public static void main(String[] args) {


        int maze[][] = { { 1, 0, 0, 0 },
            { 1, 1, 0, 1 },
            { 0, 1, 0, 0 },
            { 1, 1, 1, 1 } };



        System.out.println( solveMaze(maze));


    }
   public static boolean solveMaze(int maze[][])
    {
        int sol[][] = new int [N][N];

        if (solveMazeUtil(maze, 0, 0, sol) == false) {
            System.out.println("Solution doesn't exist");
            return false;
        }

        printSolution(sol);
        return true;
    }
   public static boolean solveMazeUtil(int maze[][], int x, int y, int sol[][])
    {
        // if (x, y is goal) return true
        if (x == N - 1 && y == N - 1) {
            sol[x][y] = 1;
            return true;
        }

        // Check if maze[x][y] is valid
        if (isSafe(maze, x, y) == true) {
            // mark x, y as part of solution path
            sol[x][y] = 1;

            /* Move forward in x direction */
            if (solveMazeUtil(maze, x + 1, y, sol) == true)
                return true;

        /* If moving in x direction doesn't give solution then
           Move down in y direction  */
            if (solveMazeUtil(maze, x, y + 1, sol) == true)
                return true;

        /* If none of the above movements work then BACKTRACK:
            unmark x, y as part of solution path */
            sol[x][y] = 0;
            return false;
        }

        return false;
    }

    public static boolean isSafe(int maze[][], int x, int y)
    {
        // if (x, y outside maze) return false
        if (x >= 0 && x < N && y >= 0 && y < N && maze[x][y] == 1)
            return true;

        return false;
    }
    public static void printSolution(int sol[][])
    {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(sol[i][j] +" ");
            }
            System.out.println();
        }

    }

}
