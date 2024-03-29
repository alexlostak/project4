/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4; // cannot be in default package
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.Method;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        
        CritterWorld.makeCritterWorld();
        boolean dontQuit = true;
        while (dontQuit) {
        	System.out.print("critters> ");
        	String userInput = kb.nextLine();
        	String[] splitUserInput = userInput.split("\\s+");
        	int splitIndex = 0;
        	if (splitUserInput[splitIndex].equals("quit")) {
        		if (splitUserInput.length > 1) {
        			System.out.print("error processing: " + userInput + "\n");

        		} else {
        		dontQuit = false;
        		}
        	} else if (splitUserInput[splitIndex].equals("show")) {
        		if (splitUserInput.length > 1) {
        			System.out.println("error processing: " + userInput);
        		} else {
        			Critter.displayWorld();
        		}
        	} else if (splitUserInput[splitIndex].equals("step")) {
        		if (splitUserInput.length > 2) {
        			System.out.println("error processing: " + userInput);
        		} else {
	        		splitIndex++;
	        		if (splitUserInput.length > 1) {
	        			try {
		        			int stepCount = Integer.parseInt(splitUserInput[splitIndex]);
		        			if (stepCount < 1) { System.out.print("error processing: " + userInput + "\n"); }
		        			for (int i = 0; i < stepCount; i++) {
		        				Critter.worldTimeStep();
		        			}
	        			} catch (Exception e1) {
	        				System.out.print("error processing: " + userInput + "\n");
	        			}
	        		} else {
	        			Critter.worldTimeStep();
	        		}
        		}
        	} else if (splitUserInput[splitIndex].equals("seed")) {
        		if (splitUserInput.length == 2) {
        			try {
		        		splitIndex++;
		        		long seedNumber = Long.valueOf(splitUserInput[splitIndex]);
		        		if (seedNumber > 0) {
		        			Critter.setSeed(seedNumber);
		        		} else {
	            			System.out.print("error processing: " + userInput + "\n");
		        		}
        			} catch (Exception e1) {
            			System.out.print("error processing: " + userInput + "\n");
        			}
        		} else {
        			System.out.print("error processing: " + userInput + "\n");
        		}
        		
        	} else if (splitUserInput[splitIndex].equals("make")) {
        		if (splitUserInput.length <= 1) {
        			System.out.print("error processing: " + userInput + "\n");
        		} else {
	        		splitIndex++;
	        		String className = new String(splitUserInput[splitIndex]);
	        		splitIndex++;
	        		if (splitUserInput.length == 3) {
	        			try {
		            		String rawCount = new String(splitUserInput[splitIndex]);
		            		int count = Integer.parseInt(rawCount);
		            		if (count < 0) {
		            			System.out.print("error processing: " + userInput + "\n");
		            		} else {
		            			for (int i = 0; i < count; i++)
			            			Critter.makeCritter(className);
		            		}
		            		
	        			} catch (InvalidCritterException e1) {
	        				System.out.print("error processing: " + userInput + "\n");
	        			} catch (Exception e2) {
	        				System.out.print("error processing: " + userInput + "\n");
	        			}
	        		} else if (splitUserInput.length > 3) {
	        			System.out.print("error processing: " + userInput + "\n");
	        		} else {
	        			try {
	        				Critter.makeCritter(className);
	        			} catch (InvalidCritterException e1) {
	        				System.out.print("error processing: " + userInput + "\n");
	        			}
	        		}
        		}
        		
        	} else if (splitUserInput[splitIndex].equals("stats")) {		
        		if ((splitUserInput.length > 2) || (splitUserInput.length < 2)) { System.out.print("error processing: " + userInput + "\n"); }
        		else {
        			splitIndex += 1;
        			String statClass = splitUserInput[splitIndex];
        			try {
        				Class<?> className = Class.forName(myPackage + "." + statClass);		// Get class of input
        				Method runStatsMethod = className.getMethod("runStats", new Class<?>[]{List.class});
        				runStatsMethod.invoke(null, Critter.getInstances(statClass));	// Input null because static method, second parameter is the list
        			} catch (Exception e){
        				System.out.print("error processing: " + userInput + "\n");
        			}
        		}
       		} else {
        		System.out.print("invalid command: " + userInput + "\n");
        	}
        		
        }
        System.out.flush();
    }
}
