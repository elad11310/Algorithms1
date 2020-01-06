package Pizza_Sumulation;

/**
 * pizza simulation is testing two people eating pizza and the first of them has a normal eating speed and the other is n*the normal eating speed, which means
 * if n==2 the second person eats 2 times faster then the first and if n==3 the second person eats 3 times faster then the first ,etc...
 * so this simulation is providing the optimal amount of slices the pizza needs to be cut for the second person(the faster eater) will eat the most amount of pizza.
 */

public class Main {

    static class Person {
        int eatingSpeed; // determine eating speed
        double slices;//

        public Person(int eatingSpeed) {
            this.eatingSpeed = eatingSpeed;
            this.slices = 0;
        }
    }

    public static void main(String[] args) {

        int ans = pizzaProblem(5);
        System.out.println(ans);
    }

    public static int pizzaProblem(int n) {
        int counter = n + 1; // determine of how many slices the simulation will start , always n+1.
        Person slowEater = new Person(1);
        Person fastEater = new Person((n));
        int temp = counter, optimal = 0;
        double max = 0;
        while (counter < 100) { // determine simulation check.
            temp -= slowEater.eatingSpeed; // eat 1
            slowEater.slices += slowEater.eatingSpeed; // adding the amount of slices he ate to his amount of slices
            temp -= fastEater.eatingSpeed; // eat n
            fastEater.slices += fastEater.eatingSpeed;
            if (temp == 1) { // if only 1 slice left after fasterEater ate so we know they will fight on the last slice.
                slowEater.slices = 0;
                fastEater.slices = 0;
                counter++;
                temp = counter;
            }
            if (temp < 0) {
                fastEater.slices += temp;
            }
            if (temp == 0 || temp<0) { // finished eating
                if (fastEater.slices / counter > max) { //checks if the number of slices faster ate is larger then before
                    max = fastEater.slices / counter;
                    optimal = counter;


                }
                System.out.println(fastEater.slices / counter);
                slowEater.slices = 0;
                fastEater.slices = 0;
                counter++;
                temp = counter;
            }


        }

        return optimal;

    }
}
