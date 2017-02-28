/*
Nattapat White and Darius Bell
Proffessor: Dr. Pettit
Languages: Java
Artificial Intelligent

note: this will work from 24 items or less. Since my laptop don't have alot of memory.
*/

package implementation;

import java.io.*;
import java.lang.*;
import java.util.*;



public class Main{
    private static Knapsack a;
    private static BinaryTree b;
    private static String file;

    public static String return_file()
    {
        return file;
    }

    public static void Output_file()
    {
        try {
                PrintStream out = new PrintStream(new FileOutputStream("OutFile.txt"));
                out.println("Filename: " + a.return_filename());
                out.println("Capacity: " + a.return_capacity());
                out.println("Lower_Bound: " + a.return_lowerbound());
                out.println("Upper_Bound: " + a.return_upperbound());
                out.println("Optimal: " + b.return_optimal());
                out.println("Dumb search time: " + b.dumb_time());
                out.println("Smart search time: " + b.smart_time());
                out.close();
                System.out.println("Output complete"); 
        } 
        catch (FileNotFoundException e) {
                e.printStackTrace();
        }
    }

	public static void main(String[] args)
    {
        Scanner user_input = new Scanner(System.in);
        System.out.println("ENTER FILE NAME: ");
        file = user_input.next();

        System.out.println();
        a = new Knapsack();
        b = new BinaryTree();
        Output_file();
    }
}