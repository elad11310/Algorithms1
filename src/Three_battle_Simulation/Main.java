package Three_battle_Simulation;

import java.util.Collection;
import java.util.HashMap;

/**
 * this class represents a simulation for three battle , we take 3 persons with each one's percentage of hit,we calculate all the options for
 * a person to start (ABC,ACB,BAC,BCA,CAB,CBA) and it continues the  circle of turns until only 1 person stays alive and wins, we make a simulation for million rounds,
 * usually person C with lowest percentage of hit is the winner above 50% of the times. probably because as long as there are 3 alive C will miss his hit and let A and B
 * kill each other (because that what they will try to do-kill the one with the higher percentage first),then the mood is C against A or B and C is the fist to start.
 */


public class Main {


    public static final double aPercentage=0.8;
    public static final double bPercentage=0.7;
    public static final double cPercentage=0.5;
    public static void main(String[] args) {
            int i = 0;
            Collection<String> winner=null; // setting collection for the player that will win each round
            HashMap<Integer, String> players = new HashMap<>();
            int countA = 0 , countB=0, countC=0;

            while (i < 1000000) { // simulation count
                int rand = (int) (Math.random() * 6); // generate a random number between 0-5

                switch (rand) {
                    case 0: // ABC
                        players.put(0, "A");
                        players.put(1, "B");
                        players.put(2, "C");
                       winner=threeBattle(players);
                    case 1: // ACB
                        players.put(0, "A");
                        players.put(1, "C");
                        players.put(2, "B");
                        winner=threeBattle(players);

                        break;
                    case 2: // BAC
                        players.put(0, "B");
                        players.put(1, "A");
                        players.put(2, "C");
                        winner=threeBattle(players);

                        break;
                    case 3: // BCA

                        players.put(0, "B");
                        players.put(1, "C");
                        players.put(2, "A");
                        winner=threeBattle(players);
                        break;
                    case 4: // CAB
                        players.put(0, "C");
                        players.put(1, "A");
                        players.put(2, "B");
                        winner=threeBattle(players);

                        break;
                    case 5: // CBA
                        players.put(0, "C");
                        players.put(1, "B");
                        players.put(2, "A");
                        winner=threeBattle(players);

                        break;
                }
                //System.out.println(winner);

                if(winner.contains("A")){
                    countA++;
                }
                else if(winner.contains("B")){
                    countB++;
                }
                else{
                    countC++;
                }
                i++;
               // System.out.println(rand);
            }

        System.out.println("A " + countA);
        System.out.println("B " + countB);
        System.out.println("C " + countC);

    }

    private static Collection<String> threeBattle(HashMap<Integer, String> players) {
        double isHit;
        int i = 0;
        String turn="";
        while (players.size() != 1) { // the last survivor
            if(i==3){
                i=0;
            }
            if(players.get(i)!=null) { // as long as the player alive
                 turn = players.get(i);
            }
            else{ // in a case some one is out skip his turn
                i++;
                continue;
            }
            isHit = Math.random(); // generate random number of hit percentage

            switch (turn) {
                case "A":

                    if (isHit <= aPercentage) { // means first hits the person he had to.
                        if (players.containsValue("B")) { // if B alive
                            players.values().remove("B"); // remove B
                        } else
                            players.values().remove("C"); // remove C
                    }
                    break;
                case "B":

                    if (isHit <= bPercentage ) {
                        if (players.containsValue("A")) { // if A alive
                            players.values().remove("A");
                        } else
                            players.values().remove("C");
                    }

                    break;
                case "C":
                    if (players.size() == 2) { // only if C against A or B so he tries to attack , if not he skipped his turn

                        if (isHit <= cPercentage) {
                            if (players.containsValue("A")) { // if A alive
                                players.values().remove("A");
                            } else
                                players.values().remove("B");
                        }
                    }
                    break;

            }
        i++;

        }
    return players.values(); // returns winner

    }

}