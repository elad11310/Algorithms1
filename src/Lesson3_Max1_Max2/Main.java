package Lesson3_Max1_Max2;


import java.util.*;

/**
 * this class represents three methods to find max 1 and max 2 .
 * first method : find max1 in n-1 comparisons , find max2 in n-1 comparisons . total : 2n-3 comparisons
 * second method is to compare each pair in the arr and compare each pair with the current max1 and max2  number of comparisons:
 * 1 - for the first pair and (3n-2)/2 for the rest  of the pairs to each pair 3 comparisons we divide by two because its pairs. total : 1+(3n-2)/2.
 * third method is an efficiant way to find 2 max varibels in arr in N + Log n comparisons , in arr of objects of nodes that contains data and stack
 * of candidents to max2.
 */
public class Main { /////////////////////
    static class Node {
        int data;
        Stack<Integer> s;

        public Node(int data) {
            this.data = data;
            s = new Stack<>();
        }

        public String toString() {
            return "Data value : " + this.data + " Stack values : " + s.toString();
        }
    }


    public static void main(String[] args) {

        final int SIZE = 10;
        int max1 = 0, max2 = 0;
        int[] arr = new int[SIZE];
        int comparisons = 0;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 500 + 1); // generate random numbers
        }
        System.out.println(Arrays.toString(arr)); // priting ar


        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i] > max1)
                max1 = arr[i];
        }
        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i] > max2 && arr[i] != max1)
                max2 = arr[i];
        }
        System.out.println(" First Method : Max1 is " + max1 + " Max2 is " + max2 + " Number of comparisons " + comparisons);
        // first method
        //////////////////////////////////////////////////////////////

        max1 = (arr[0] > arr[1]) ? arr[0] : arr[1]; // insert to max1 the bigger varible.
        max2 = (max1 == arr[0]) ? arr[1] : arr[0]; // insert to max2 the smaller varible.
        int temp = 0;
        comparisons = 0;
        for (int i = 2; i < arr.length && i + 1 < arr.length; i++) {
            if (arr[i] > arr[i + 1]) {
                comparisons++;
                if (arr[i] > max1) { // if yes so max2 going out of the game because arr[i] and max1 are bigger
                    comparisons++;
                    temp = max1;
                    max1 = arr[i];
                    if (temp > arr[i + 1]) {
                        max2 = temp;
                        comparisons++;
                    } else
                        max2 = arr[i + 1];
                } else if (arr[i] < max1) { // if so arr[i+1] going out of the game because arr[i] and max1 are bigger
                    if (arr[i] > max2)
                        max2 = arr[i];
                }
            } else if (arr[i] < arr[i + 1]) {
                if (arr[i + 1] > max1) { // if so max2 is going out of the game because arr[i+1] and max1 are bigger
                    comparisons++;
                    temp = max1;
                    max1 = arr[i + 1];
                    if (temp > arr[i]) {
                        max2 = temp;
                        comparisons++;
                    } else
                        max2 = arr[i];
                } else if (arr[i + 1] < max1) { // if so arr[i] going out of the game because arr[i+1] and max1 are bigger
                    if (arr[i + 1] > max2)
                        max2 = arr[i + 1];

                }

            }
        }

        System.out.println(" Second Method : Max1 is " + max1 + " Max2 is " + max2 + " Number of  comparisons " + comparisons);

        // second method
        //////////////////////////////////////////////////////////////

        LinkedList<Node> l = new LinkedList<>(); // initialize List of My Node

        for (int i = 0; i < SIZE; i++) {
            l.add(new Node((int) (Math.random() * 500 + 1)));
            System.out.print(l.get(i).data + " ");
        }
            System.out.println();
            int i = 0;
            comparisons = 0;
            while (l.size() > 1) { // while list has elements.

                Node first = l.get(i);
                Node second = l.get(i + 1);

                if (first.data > second.data) {
                    comparisons++;

                    first.s.push(second.data); // putting the "Loser data into the winner stack
                    l.remove(l.indexOf(second)); // removing the loser node


                } else  {

                    second.s.push(first.data); // putting the "Loser data into the max1 stack
                    l.remove(l.indexOf(first)); // removing the loser node


                }
                i++;
                if (i >= l.size() - 1)
                    i = 0;
            }
            Node max = l.get(0); // setting a new node to the vaule of the last node left in the list , the max1 winnter
            max2 = max.s.pop();
            while (!max.s.isEmpty()) { // iterating through the max node stack
                int data = max.s.pop();
                if (data > max2) {
                    comparisons++;
                    max2 = data;
                }
            }

            System.out.println(" Third Method : Max1 is " + max.data + " Max2 is " + max2 + " Number of  comparisons " + comparisons);

        }
    }


