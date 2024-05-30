package edu.iastate.cs228.hw4;
/**
 * @author Rahul Sudev
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MsgTreeMain {

    /**
     * Main method that outputs the required outputs and message
     *
     * @param args command-line arguments
     * @throws FileNotFoundException if the specified file does not exist
     */
    public static void main(String[] args) throws FileNotFoundException {

        // Declare variables to hold input data
        String[] input = new String[15];
        String encodedData;
        String Code;
        String fileName;
        int numInput = 0;

        // Get the input file name from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter filename to decode:  ");
        fileName = scanner.next();

        // Read the input file into an array of strings
        Scanner inputFileScanner = new Scanner(new File(fileName));
        while (inputFileScanner.hasNextLine()) {
            input[numInput++] = inputFileScanner.nextLine();
        }
        inputFileScanner.close();

        // Determine whether the input file contains two or three lines of data
        if (numInput == 3) {
            Code = input[0] + "\n" + input[1];
            encodedData = input[2];
        } else {
            Code = input[0];
            encodedData = input[1];
        }

        // Create a new message tree using the character codes data
        MsgTree messageTree = new MsgTree(Code);

        // Print out the character codes for each character in the message
        System.out.println("\ncharacter code\r\n" + "-------------------------");
        MsgTree.printCodes(messageTree, "");

        // Decode the encoded message using the message tree
        System.out.println("\nMESSAGE:-");
        MsgTree.decode(messageTree, encodedData);
        System.out.println("\n");

        // Print out some statistics about the message
        System.out.println("\nSTATISTICS:-");
        System.out.printf("Avg bits/char: \t\t%.1f\n", (double) encodedData.length() / MsgTree.countKeeper());
        System.out.printf("Total characters: \t%d\n", MsgTree.countKeeper());
        System.out.printf("Space savings: \t\t%.1f%%\n", (1 - ((float) MsgTree.countKeeper() / (encodedData.length()))) * 100);
        scanner.close();
    }
}
