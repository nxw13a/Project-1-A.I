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
                out.println(a.return_filename());
                out.println(a.return_capacity());
                out.println(a.return_lowerbound());
                out.println(a.return_upperbound());
                out.println(b.return_optimal());
                out.println(b.dumb_time());
                out.println(b.smart_time());
                out.close();
                System.out.println("Done"); 
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

        a = new Knapsack();
        b = new BinaryTree();
        Output_file();
    }
}