/*
Nattapat White and Darius Bell
Proffessor: Dr. Pettit
Languages: Java
Artificial Intelligent

note: this will work from 24 items or less. Since my laptop don't have alot of memory.
*/

package implementation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.*;
import java.util.*;



public class Main{
    private static Knapsack a;
    private static BinaryTree b;

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
        } 
        catch (FileNotFoundException e) {
                e.printStackTrace();
        }
    }

	public static void main(String[] args)
    {
    
        a = new Knapsack();
        b = new BinaryTree();
        Output_file();
        /*
        System.out.println(a.return_filename()); // file name
        System.out.println(a.return_capacity()); // capacity
        System.out.println(a.return_lowerbound()); // lower bound
        System.out.println(a.return_upperbound()); //upper bound
        System.out.println(b.return_optimal());   // optimal
        System.out.println(b.dumb_time());   //dumb search time 
        System.out.println(b.smart_time()); //smart search time
        */

   

    }
}