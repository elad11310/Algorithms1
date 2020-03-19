package LOS;


import java.util.Arrays;

/**
 * this class represents algorithms for finding LOS in 1D array and in 2D array(biggest square of ones and biggest rectangle of ones).
 * LOS - longest ones sub array.
 * first method in 1D is full search we will make all possible sub arrays that are sequential which means from [1,1],[1,2]...[1,n]
 * and [2,2][2,3]....[2,n] which means n ,n-1,n-2... options , total: O(N^2) , total: O(N^2 * N) , N for checking if its all ones.
 * second method in 1D is greedy - in O(n) we iterate through the array and with counter we count number of sequential ones and each time we reach 0,
 * we compare with max variable and return count to 0 and resume checking. we can improve the checking by comparing the max variable to the amount of
 * cells left in the array to iterate. for instance if we have an array of 15 length and we are in index 10 and so far we have max of 6 ones so we can stop the checking
 * because in the good case from index 10 to 15 will be another 6 ones and its not larger then max. total O(N-max).
 * third method in 1D is dynamic search - its very similar to greedy method , infact greedy method is private case of dynamic search.
 * we will create a new arr , iterate on them both and ask if in the original arr[i] == 1 so in our new arr we take arr[i] = arr[i-1]+1. total : O(N)
 * <p>
 * now finding largest square of ones in 2D array.
 * first method : Greedy method - starts from 0,0 checks if we can expand to 1,1 and 2,2 ... n,n. complexity : N^2 for iterating and another N^2 for checking ones.
 * usually it wont be N^2 to check all ones . total: O(n^4) but actually it will be less.
 * second method : dynamic search - we will create a new mat , copy the first row and col of the original mat. start running for 1,1
 * if original mat[i][j] ==1 then new mat [i][j] = min(new mat[i-1][j-1],new mat[i-1][j],new mat[i][j-1]). complexity O(n*m)
 * <p>
 * now finding largest rectangle in the mat. we divide each row to 1D arr. first arr we copy from the first row in the mat.
 * the other ones we make based on the ones before it (col ones sequential). then we check every 1D separately  - we take the longest sequential of numbers and multiple
 * it in the minimum number of them.
 */
public class Main {

    public static void main(String[] args) {
        int[] arr = {0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0};
     /*   int ans = fullSearchIn1D(arr);
        System.out.println(ans);
        ans = greedySearch(arr);
        System.out.println(ans);
        ans = dynamicSearch(arr);
        System.out.println(ans);*/

        int[][] mat =
                {{1,0,0,1,1,1},
                        {1,0,1,1,1,1},
                        {0, 1,1,1,1,1},
                        {0, 1, 1, 1,1, 1}};


        int ans = greedySearch2D(mat);
        System.out.println(ans);

    }

    public static int fullSearchIn1D(int arr[]) {
        int max = 0, count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                count = checkOnes(arr, i, j);
                if (count > max) {
                    max = count;
                }
            }
        }


        return max;
    }

    public static int checkOnes(int arr[], int start, int end) {
        int count = 0;
        while (start <= end) {
            if (arr[start] == 0) {
                break;
            } else {
                count++;
            }
            start++;
        }
        return count;
    }

    public static int greedySearch(int arr[]) {
        int max = 0, count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0 && arr.length - i <= max) {
                break;
            }
            if (arr[i] == 1) {
                count++;
            }
            if (arr[i] == 0 && count > 0) {
                if (count > max) {
                    max = count;
                }
                count = 0;
            }
        }
        return max;
    }

    public static int dynamicSearch(int[] arr) {
        int i = 0, max = 0;
        int[] arrNew = new int[arr.length]; // creating new arr
        for (i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                arrNew[i] = arrNew[i - 1] + 1;
            }
        }
        // now iterate on the arr to find the max length of ones.
        for (i = 0; i < arrNew.length; i++) {
            if (arrNew[i] > max) {
                max = arrNew[i];
            }
        }
        return max;
    }

    public static int greedySearch2D(int[][] arr) {
        int N = arr.length;
        int M = arr[0].length;
        boolean flag;
        int min = N<M?N:M; // check the maximum size of the square.
        int i, j,k,q,p,m,ans=0,max=0;
        for (i = 0; i <N;i++){
            for(j=0;j<M;j++){
                if(arr[i][j]!=1) // only if we are on 1,
                    continue;
                if(ans>max){
                    max=ans;
                }
                p=i;
                q=j;
                flag=true;
                ans=1;
                for(k=1;k<=min && flag;k++){
                    if(p+1<N && q+1<M && arr[p+1][q+1]==1){ // if there is a chance for a square.
                        p=p+1;
                        q=q+1;
                        for(m=1;m<=k;m++){ // checking the row and cols from the most right down variable of the square
                            if(arr[p-m][q]==0 || arr[p][q-m]==0){
                                flag = false;
                                break;
                            }


                        }
                        if(flag)
                            ans++;
                    }
                    else
                        break;
                }


            }
        }
        return max;
    }

    public static int fullSearch2d(int[][] mat) {
        int N = mat.length;
        int M = mat[0].length;
        int max = 0;
        // we will make all the possible sub mats 1X1 2X2 ..NXN
        int i, k, j;
        for (k = 1; k <= N; k++) {
            for (i = 0; i <= N - k; i++) {
                for (j = 0; j <= M - k; j++) {
                    if (checkOnesFullSearch(mat, i, j, i + k - 1, j + k - 1)) {
                        max = k;
                    }

                }
            }
        }
        return max;
    }

    private static boolean checkOnesFullSearch(int[][] mat, int i, int j, int p, int q) {
        for (int I = i; I <= p; I++) {
            for (int J = j; J <= q; J++) {
                if (mat[I][J] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int dynamicSearch2D(int[][] arr) {
        int i, j, max = 0;
        int newMat[][] = new int[arr.length][arr.length];
        // copy first row and col
        for (i = 0, j = 0; j < arr[0].length; j++) {
            newMat[i][j] = arr[i][j];
        }
        for (i = 0, j = 0; i < arr.length; i++) {
            newMat[i][j] = arr[i][j];
        }
        // now iterating through the rest of the mat from 1,1 and active the algorithm.

        for (i = 1; i < newMat.length; i++) {
            for (j = 1; j < newMat[i].length; j++) {
                if (arr[i][j] == 1) {
                    newMat[i][j] = min(newMat, i, j) + 1;
                    if (newMat[i][j] > max) { // checking max
                        max = newMat[i][j];
                    }
                }
            }
        }


        return max;
    }

    public static int min(int[][] newMat, int i, int j) {
        int a = newMat[i - 1][j - 1];
        int b = newMat[i - 1][j];
        int c = newMat[i][j - 1];
        int d = minHelp(a, b);
        return minHelp(d, c);

    }

    public static int minHelp(int i, int j) {
        return i <= j ? i : j;
    }

    public static int findRectangle(int[][] mat) {
        int i, j, count = 0, max = 1, maxRec = 0, startSec = 0, min = 0;
        int[][] newMat = new int[mat.length][mat[0].length];
        // copy first row
        for (i = 0, j = 0; j < mat[0].length; j++) {
            newMat[i][j] = mat[i][j];
            if (newMat[i][j] != 0) { // start counting sequential numbers except 0
                count++;

            }
            if (newMat[i][j] == 0 || j == newMat[i].length - 1) { // checks the length of the sequence till now , compare with max also
                if (count > max) {
                    startSec = j - count + 1; // saving the index of the sequence start.
                    max = count;
                }

                count = 0;

            }
        }

        maxRec = max * 1; // first row max sequential *1 because there are only ones in the first row
        for (i = 1; i < mat.length; i++) { // now checking the rest of the rows.
            count = 0;
            max = 1;
            for (j = 0; j < mat[0].length; j++) {
                if (mat[i][j] == 1) {
                    newMat[i][j] = newMat[i - 1][j] + 1;
                    count++;

                }
                if (mat[i][j] == 0 || j == newMat[i].length - 1) {
                    if (count > max) {
                        startSec = (j == newMat[i].length - 1) ? j - count + 1 : j - count;
                        max = count;
                    }

                    count = 0;
                }
            }
            // now checking the minimum number in the longest sequential in that row
            min = newMat[i][startSec];
            int y = max - 1;
            int k = startSec + 1;
            while (y > 0) {
                if (newMat[i][k] < min) {
                    min = newMat[i][k];
                }
                y--;
                k++;

            }


            int temp = min * max;
            if (temp > maxRec) {
                maxRec = temp;
            }
        }

        System.out.println(Arrays.deepToString(newMat));


        return maxRec;

    }
}

