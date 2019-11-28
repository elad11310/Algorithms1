package Lesson4_Finding_Median;

/**
 * this class represents two efficient ways to find all the variables in two sorted arrs that are larger then the median one.
 * first is to merge 2 arrs from the end until we reach the n/2 variable, in case the merging creates an odd arr we will take the n/2 variable,
 * in case of even merged arr there will be 2 medians so we will take the bigger one so we merge until n/2 -1. total N comparisions
 * second method is to compare the last variable of the first arr with the first variable of the second arr. if the first variable of the second arr
 * is larger so we know that all the variables after it are bigger then the median for sure,
 */
public class Main {

    public static void main(String args[]) {
        int B[] = {8,50,65,75};
        int A[] = {5,10,52,76,86};
        // 2,3,4,5,6,7,8,10,12
        Merge(A, B);

        ///////////////////////////////////////////////// first method


        int i = 0;
        int k = 0;
        int j = B.length - 1;
        int totalSize = A.length + B.length;
        int count = 0;
        int biggerThen = 0;
        while (i < A.length && j >= 0) {
            biggerThen = 0;
            if (A[i] > B[j]) {
                k = j + 1;
                biggerThen += i + j + 1;
                while (k < B.length && A[i] > B[k]) {
                    biggerThen++;
                    k++;
                }
                if (biggerThen >= totalSize / 2 + 1)
                    System.out.print(A[i] + " ");
            } else if (A[i] < B[j]) {
                biggerThen += j + i + 1;
                k = i + 1;
                while (k < A.length && B[j] > A[k]) {
                    biggerThen++;
                    k++;
                }
                if (biggerThen >= totalSize / 2 + 1)
                    System.out.print(B[j] + " ");

            }
            if (i != A.length)
                i++;
            if (j != 0)
                j--;
            count += 2;
        }
        System.out.println();
        System.out.println("Total comparisons = " + count);

    }

    private static void Merge(int[] A, int[] B) {
        int count = 0;
        int i = A.length - 1;
        int j = B.length - 1;
        int C[] = new int[A.length + B.length];
        int k = C.length - 1;
        while (count < (C.length / 2) - 1) {
            if (A[i] > B[j])
                C[k--] = A[i--];
            else
                C[k--] = B[j--];
            count++;
        }
        if (C.length % 2 != 0) { // in case the length of C is odd , we will make another comparison
            if (A[i] > B[j])
                C[k--] = A[i--];
            else
                C[k--] = B[j--];
            count++;
        }

        i = C.length - 1;
        while (C[i] != 0) {
            System.out.print(C[i--] + " ");

        }
        System.out.println();
        System.out.println("Total comparisons = " + count);
    }
}
