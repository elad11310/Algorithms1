package Fibonachi_Problem;


/**
 * this class represents in 3 ways to calculate fibonachi problem:
 * first - recursive way O(1.8^n) - not so efficient.
 * second - iterative way - O(n-2), because we already know the first two numbers.
 * third - in a mat way {{1.1,1,0}^n -2- will give us the fibo number in O(n-2). we will improve it to logN because we multiply the mat in its self
 * so its power. we already have efficient way to calculate power in log n .
 */
public class Main {
    public static void main(String[] args) {

     int ans = matFab(14);
        System.out.println(ans);

    }

    private static int fabRecursively(int n) {
        if (n == 0) { // first stopping condition
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fabRecursively(n - 1) + fabRecursively(n - 2);
    }

    private static int fabIterative(int n) {
        int first = 1, second = 1, ans = 1;
        int size = n - 2;
        while (size > 0) {
            ans = first + second;
            first = second;
            second = ans;
            size--;
        }

        return ans;


    }

    private static int matFab(int n) {
        int R[][] = {{1, 1,}, {1, 0}}; // creating help matrix
        int base[][] = {{1, 1,}, {1, 0}}; // creating the base matrix
        int size=n-2;
        int s=0,s1=0,s2=0;
        while (size >0) {
            if (size % 2 !=0) {

                s=R[0][0]; // saving some values because we change them through the multiply proccess.
                s1=R[0][1];
                s2= R[1][0];

                R[0][0] = R[0][0] * base[0][0] + R[0][1] * base[1][0]; // O(1)
                R[0][1] = s * base[0][1] + R[0][1] * base[1][1];
                R[1][0] = R[1][0] * base[0][0]+ R[1][1] * base[1][0];
                R[1][1] = s2 * base[0][1] + R[1][1] * base[1][1];


            }
            s=base[0][0];
            s1=base[0][1];
            s2= base[1][0];

            base[0][0] = base[0][0] * base[0][0] + base[0][1] * base[1][0]; // O(1)
            base[0][1] = s * base[0][1] + base[0][1] * base[1][1];
            base[1][0] = base[1][0] *s + base[1][1] * base[1][0];
            base[1][1] = s2 * s1 + base[1][1] * base[1][1];
            size/=2;

        }
        return R[0][0];
    }
}
