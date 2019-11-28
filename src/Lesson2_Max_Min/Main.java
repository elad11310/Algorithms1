package Lesson2_Max_Min;

import java.util.Arrays;

/**
 * this class represents an efficient way to find Max and Min
 * we will use 3 methods :
 * first : find max and min in O(n-1) total for both : O(2n-2)
 * second: we will define max and min and we will iterate through the arr and if we find a variable that is greater then max ,
 * we will change only the max and the min will stay as is. if we find a variable smaller then max so we will compare it with the min ,
 * total 2 comparisons. so in this case we will usually make 1 compare , maximum 2 so in the best case we find max and min in O(n-1), worst case: O(2n).
 * in general this algorithm is better because in his worst case its like the first algorithm so it can only be better.
 * third :  first we will compare the first 2 variables to determine max and min , afterwards we will compare each 2 variables and if the greater between them is larger then max
 * so min and the variable that was smaller then the other of the comparision will fight on min , if the the greater between them is smaller then max , so max and the greater will fight on max
 * so total O(3n/2) because 3 comparisons and divide by two because of the pairs
 */
public class Main {
    public static void main(String[] args) {

        final int SIZE = 100;
        int count = 0;
        int[] A = new int[SIZE];
        for (int i = 0; i < A.length; i++) {
            A[i] = (int) (Math.random() * 500 + 1); // generate random numbers
        }
        System.out.println(Arrays.toString(A)); // priting ar

        int max = A[0], min = A[0];

        for (int i = 1; i < A.length; i++) {
            count++;
            if (A[i] > max) {
                max = A[i];
            }

        }
        for (int i = 1; i < A.length; i++) {
            count++;
            if (A[i] < min) {
                min = A[i];
            }
        }


        System.out.println("Max is " + max);
        System.out.println("Min is " + min);
        System.out.println("Total comparisons " + count);

        ///////////////////////////////////////////// first method.

        count = 0;
        min = A[0];
        max = A[1];
        if (min > max) { // we swap
            int temp = max;
            max = min;
            min = temp;
            count++;

        }

        for (int i = 2; i < A.length; i++) {
            if (A[i] > max) {
                max = A[i];
                count++;
            } else if (A[i] < max) {
                count++;
                if (A[i] < min) {
                    count++;
                    min = A[i];
                }

            }
        }
        System.out.println("Max is " + max);
        System.out.println("Min is " + min);
        System.out.println("Total comparisons " + count);

        ////////////////////////////////////////////////////// second method

        count = 0;
        max= (A[0] > A[1]) ? A[0] : A[1]; // insert to max the bigger varible.
        min = (max == A[0]) ? A[1] : A[0]; // insert to min the smaller varible.
        count++;

        for (int i = 2; i < A.length && i + 1 < A.length; i+=2) {
            if (A[i] > A[i + 1]) {
                if (A[i] > max) { // if yes so max2 going out of the game because arr[i] and max1 are bigger
                   max=A[i];
                    if (A[i+1] <min) {
                        min = A[i+1];
                    }
                } else if (A[i] < max) { // if so arr[i+1] going out of the game because arr[i] and max1 are bigger
                    if (A[i+1]<min){
                        min =A[i+1];
                    }

                }
                count+=3;
            } else if (A[i] < A[i + 1]) {
                if (A[i + 1] > max) { // if so max2 is going out of the game because arr[i+1] and max1 are bigger
                    max = A[i + 1];
                    if(A[i]<min)
                        min =A[i];
                } else if (A[i + 1] < max) { // if so arr[i] going out of the game because arr[i+1] and max1 are bigger
                    if(A[i]<min)
                        min = A[i];

                }
                count+=3;
            }
        }

        System.out.println("Max is " + max);
        System.out.println("Min is " + min);
        System.out.println("Total comparisons " + count);

    }
}

