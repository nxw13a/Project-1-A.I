/*
Nattapat White and Darius Bell
Proffessor: Dr. Pettit
Languages: Java
Artificial Intelligent

note: this will work from 22 items or less. Since my laptop don't have alot of memory.
*/

package implementation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.util.*;



public class Main{
	
	public static void main(String[] args)
    {
    	Knapsack a = new Knapsack();
        BinaryTree b = new BinaryTree();
        System.out.println(a.return_filename()); // file name
        System.out.println(a.return_capacity()); // capacity
        System.out.println(a.return_lowerbound()); // lower bound
        System.out.println(a.return_upperbound()); //upper bound
        System.out.println(b.return_optimal());   // optimal
        System.out.println("????????????????");   //dumb search time -- still working on this
        System.out.println(b.Smart_time() + "m"); //smart search time
    }
}