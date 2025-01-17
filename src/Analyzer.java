import Input.Input;
import Input.InputGetter;

import java.io.*;
import java.nio.file.*;
import java.time.Duration;
import java.time.Instant;

public class Analyzer {

    public static void main(String[] args) throws IOException {
        String pythonScriptPath = "/Users/amirali/Desktop/algoanalyz/tmp.py"; // Replace with your Python script's path
        String outputFilePath = "/Users/amirali/Desktop/algoanalyz/out.txt";  // Output file to store results
        int n = 50;   // Replace with your desired value
        int count = 0;
        StringBuilder outputText = new StringBuilder();
        double sumF1 = 0, sumF2 = 0;
        while (n < 3000){
            try {
                // Run Python script and wait for completion
                System.out.println("Running Python script...");
                Process process = new ProcessBuilder("python3", pythonScriptPath,"-n", String.valueOf(n), "-o", "/Users/amirali/Desktop/algoproject/algoproject/src/input.txt").start();
                int exitCode = process.waitFor();
                System.out.println("Python script completed with exit code: " + exitCode);

                Input input = InputGetter.getInput();
                // Run function f1 and measure runtime
                System.out.println("Running function f1...");
                Instant startF1 = Instant.now();
                String resultF1 = MST.TSPChristofides.main(input); // Replace with your implementation
                sumF1 += Double.parseDouble(resultF1);
                Instant endF1 = Instant.now();
                long runtimeF1 = Duration.between(startF1, endF1).toMillis();
                System.out.println("Function f1 completed in " + runtimeF1 + " ms");

                // Run function f2 and measure runtime
                System.out.println("Running function f2...");
                Instant startF2 = Instant.now();
                String resultF2 = MyIdea.main(input); // Replace with your implementation
                sumF2 += Double.parseDouble(resultF2);
                Instant endF2 = Instant.now();
                long runtimeF2 = Duration.between(startF2, endF2).toMillis();
                System.out.println("Function f2 completed in " + runtimeF2 + " ms");

                // Write results to output file
                double factor = Double.parseDouble(resultF2)/Double.parseDouble(resultF1)/1.5;
                String flag = factor >= 1.5 ? "TRUE" : "FALSE";
                System.out.println("Writing results to output file...");
                String output = String.format(
                        "Function\tRuntime (ms)\tResult\tNumber of nodes\tFactor bool\n" +
                                "MST\t\t%d\t\t%s\t%s\n" +
                                "My Idea\t\t%d\t\t%s\t%s\t\t%s\n",
                        runtimeF1, resultF1, n, runtimeF2, resultF2, n, flag
                );
                outputText.append(output);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            n += 50;
            count++;
        }
        Files.write(Paths.get(outputFilePath), (outputText + "\n\n" + "MST Avg: " + sumF1/count + "   MyIdea Avg: " + sumF2/count).getBytes());
        System.out.println("Results written to " + outputFilePath);
    }

    // Dummy implementation of function f1
    public static String f1() {
        // Replace this logic with your actual function implementation
        return "ResultF1";
    }

    // Dummy implementation of function f2
    public static String f2() {
        // Replace this logic with your actual function implementation
        return "ResultF2";
    }
}
