import DP.TSPDynamicProgramming;
import Input.*;
import MST.TSPChristofides;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.print("1. Normal\n2. Coordinates\nWhat is your input type? ");
        Scanner scanner = new Scanner(System.in);
        Input input = null;
        int option = scanner.nextInt();
        switch (option){
            case 1 -> input = InputGetter.getInput();
            case 2 -> input = InputGetter.getInputCoordinate();
        }


        long startTime, endTime, duration;
        System.out.print("1. Brute Force\n2. Dynamic Programming\n3. MST (Christofide's algorithm)\n4. Disjoint Cycle\n5. Amirali Kazerooni's algorithm\nWhich algorithm do you want to run ? ");
        option = scanner.nextInt();

        switch (option) {
            case 1 -> {
                startTime = System.currentTimeMillis();
                TSPBruteForce.main(input);
                endTime = System.currentTimeMillis();
                duration = endTime - startTime;
                System.out.println("Time taken: " + duration + " milliseconds");
            }
            case 2 -> {
                startTime = System.currentTimeMillis();
                TSPDynamicProgramming.main(input);
                endTime = System.currentTimeMillis();
                duration = endTime - startTime;
                System.out.println("Time taken: " + duration + " milliseconds");
            }
            case 3 -> {
                startTime = System.currentTimeMillis();
                TSPChristofides.main(input);
                endTime = System.currentTimeMillis();
                duration = endTime - startTime;
                System.out.println("Time taken: " + duration + " milliseconds");
            }
            case 4 -> {
                startTime = System.currentTimeMillis();
                DisjointCycleTSP.main(input);
                endTime = System.currentTimeMillis();
                duration = endTime - startTime;
                System.out.println("Time taken: " + duration + " milliseconds");
            }
            case 5 -> {
                //startTime = System.currentTimeMillis();
                MyIdea.main(input);
                //endTime = System.currentTimeMillis();
                //duration = endTime - startTime;
                //System.out.println("Time taken: " + duration + " milliseconds");
            }
        }
    }
}
