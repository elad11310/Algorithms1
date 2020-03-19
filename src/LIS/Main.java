package LIS;

import java.util.Arrays;

/**
 * this class represents a 3 ways to find LIS - Longest Increasing Subarr, for instance 1,100,101,2,3,4,5,6,7,8
 * for this arr will return 8.
 * as ardush sakrtch says in every sequence of k^2 +1 there is for sure a sub sequence increasing/decreasing in length of minimum k.
 * first method is full search - getting all the possible sub sequences . O(2^n *n)- 2^n for all possible sub sequences , n for to see if each sub arr is sorted.
 * second method is the greedy one - will return us sequential sub arr only and wont always the right answer, for instance in 1,100,101,2,3,4,5,6,7,8 will return 7. O(n)
 * third method is dynamic programing , by using matrix we will find the longest LIS.O(n^2), usually we will do LCS(A,sort(A)), so Log N for the sort and N^2 for iterating through
 * the mat, so in total O(N^2), if we know the range of the numbers in the arr so we can give up on the sort and make it more efficient,but still in N^2.
 * fourth method (improved greedy) is the fastest way to return the length of the LIS but its not the real sub arr only its length. in O(n*logN).
 * fifth method is to return the real sub arr and not its length based on matrix in n(logn +n) , total n^2 +nlogn . n because we iterate on the arr and we make
 * 2 actions on each variable - logn for the binary search , n for copy the i-1 row.
 */


public class Main {

    public static void main(String[] args) {

        int[] a = {1,100,101,2,3,4,5,6,7,8};
        int[] a2 = {1,100,2,300,3,900,50,7,0,0};
        //int[] a = {1,2,100,4};
        //int ans = improvedGreedy(a2);
        int ans = improvedGreedy(a);
        System.out.println(ans);
        //int ans = greedySearch(a);
        //int ans = dynamicPrograming(a, a2);
        //System.out.println(ans);

    }

    public static int fullSearch(int[] arr) {
        int binary[] = new int[arr.length]; // for making all the binary addition.
        int count = 0, max = 0;
        int fullSearchLength = (int) Math.pow(2,binary.length);
        for (int i = 0; i < fullSearchLength; i++) {
            count = fromBinaryToString(arr, binary);

            if (count > max) {
                max = count;
            }
        }
        System.out.println();
        return max ;

    }

    public static int fromBinaryToString(int[] arr, int[] binaryArr) {
        boolean count = true;
        int size = 0;
        int i = 0, k = 0, counter = 1;
        for (i = binaryArr.length - 1; i >= 0 && count; i--) {
            if (binaryArr[i] == 0 && count) {
                binaryArr[i] = 1;
                count = false;
            } else if (binaryArr[i] == 1 && count) {
                binaryArr[i] = 0;
            }
        }
        for (i = 0; i < binaryArr.length; i++) {
            if (binaryArr[i] == 1) {
                size++;
            }
        }
        int[] newArr = new int[size];

        for (i = 0; i < binaryArr.length; i++) {
            if (binaryArr[i] == 1) {
                newArr[k++] = arr[i];
            }
        }
        for (i = 0; i < newArr.length - 1; i++) {
            if (newArr[i] < newArr[i + 1]) {
                counter++;
            } else return counter;
        }
        return counter;
    }

    public static int greedySearch(int[] arr) {

        int count = 0, max = 0;

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] < arr[i + 1]) {
                count++;
            } else if (count > max) {
                max = count + 1;
                count = 0;
            } else
                count = 0;
        }
        if (count > max) {
            max = count + 1;
        }
        return max;
    }

    public static int dynamicPrograming(int a[], int a2[]) {
        int i = 0, j = 0, k = 0;
        Arrays.sort(a2);
        boolean flag = false;
        int mat[][] = new int[a.length][a.length];
        for (i = 0; i < mat.length; i++) {
            if (flag) {
                mat[k][i] = 1;
                continue;
            }
            if (a2[k] == a[i]) {
                mat[k][i] = 1;
                flag = true;
            } else {
                mat[k][i] = 0;
            }


        }
        flag = false;
        for (i = 0; i < mat.length; i++) {
            if (flag) {
                mat[i][k] = 1;
                continue;
            }
            if (a[k] == a2[i]) {
                mat[i][k] = 1;
                flag = true;
            } else {
                mat[i][k] = 0;
            }


        }

        for (i = 1; i < mat.length; i++) {
            for (j = 1; j < mat[i].length; j++) {
                if (a2[i] == a[j]) {
                    mat[i][j] = mat[i - 1][j - 1] + 1;
                } else if (a2[i] != a[j]) {
                    mat[i][j] = (mat[i][j - 1] > mat[i - 1][j]) ? mat[i][j - 1] : mat[i - 1][j];
                }
            }

        }
        for (i = 0; i < mat.length; i++) {
            for (j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j] + "," + " ");
            }
            System.out.println();
        }
        return mat[i][j];
    }

    public static int improvedGreedy(int a[]){
        int i=0,k=0;
        int[] newArr = new int[a.length];
        newArr[k]=a[i];
        for (i=1;i<a.length;i++){
            if(a[i]>newArr[k]){
                newArr[++k]=a[i];
            }
            else{
                newArr[binarySearchBetweenRecursia(newArr,a[i],0,k)]=a[i];
            }

        }
        return k+1;

    }

    public static int binarySearchBetweenRecursia(int [] a,int val,int low,int high){ // binarySearch in between in Recursia

//        if(val<a[0] || val>a[high])
//            return -1;

        if(low==high){
            return low;
        }

        int mid = (high+low)/2;

        if(val==a[mid])
            return mid;
        if(val>a[mid])
            return binarySearchBetweenRecursia(a, val, mid+1, high);
        else
            return binarySearchBetweenRecursia(a, val, low, mid);

    }

    public static int binarySearchBetweenInMatrixRecursia(int [][] a,int val,int low,int high){

//        if(val<a[0] || val>a[high])
//            return -1;

        if(low==high){
            return low;
        }

        int mid = (high+low)/2;

        if(val==a[mid][mid])
            return mid;
        if(val>a[mid][mid])
            return binarySearchBetweenInMatrixRecursia(a, val, mid+1, high);
        else
            return binarySearchBetweenInMatrixRecursia(a, val, low, mid);

    }
    public static String LISUsingMatrix(int []a){
        String temp="";
        int j,p=0;
        int mat [][] = new int[a.length][a.length];
        mat[0][0]=a[0];
        for(int i=1;i<a.length;i++){
            if(a[i]>mat[p][p]){
                p++;
                for( j=0;j<p;j++){
                    mat[p][j]=mat[p-1][j];
                }

                mat[p][j]=a[i];

            }
            else
            {
                int ans = binarySearchBetweenInMatrixRecursia(mat,a[i],0,i);
                mat[ans][ans]=a[i];
                for( j=0;j<ans;j++){
                    mat[ans][j]=mat[ans-1][j];
                }
            }
        }

        for(int i=0;i<=p;i++){
            temp+= mat[p][i] + " ";
        }
        System.out.println();
        return temp;
    }
}

