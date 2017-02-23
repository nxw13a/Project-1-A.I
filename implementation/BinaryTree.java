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
import java.lang.*;
import java.util.*;



public class BinaryTree{

    private static Knapsack library;  //access knapsack contents

    private static int num_item; //number of items
    private static int limit; //the capacity limit
    private static String optimal_choice; //stored optimal choice
    private static String B_optimal_time; //Bad time for dumb search 
    private static String G_optimal_time; //Good time for smart search in Binary tree
    private static String SG_optimal_time; //Good time for smart search in Binary tree
   
    private static long B_build_time; //Good time for smart search

    private static boolean Ncontains(String word, char a) 
    {
    	// return true of false if the String match char a
        for(int x = 0; x < word.length(); x++)
        {
            if(word.charAt(x) == a)
                return false;
        }
        return true;
    }

    public static String return_optimal()
    {
        return optimal_choice; //return optimal choice 
    }

    public static int maximum(int x)
    {
        if(x == 1)
            return 2;
        return (int)Math.pow(2,x) + maximum(x-1); //the number of node in the tree at specific depth
    }

    private static int find_cost(String a)
    {
    	//find cost giving the item
        int store = 0;
        List<String> name = new ArrayList<String>(library.return_name());
        for(int x = 0; x < name.size(); x++)
        {
            if(name.get(x).charAt(0) == a.charAt(0))
                store = x;
        }
        List<Integer> cost = new ArrayList<Integer>(library.return_cost());
        return cost.get(store); 
    }
    private static int find_value(String b)
    {
    	//find value giving the item
        int store = 0;
        List<String> name = new ArrayList<String>(library.return_name());
        //System.out.println(name);
        for(int x = 0; x < name.size(); x++)
        {
            if(name.get(x).charAt(0) == b.charAt(0))
                store = x;
        }
        List<Integer> value = new ArrayList<Integer>(library.return_value());
        return value.get(store);
    }
    private static boolean above_limit(String a)
    {
    	//return true or false if exceed giving constraint
        int sum = 0;
        for(int x = 0; x < a.length(); x++)
        {
            sum += (find_cost(""+a.charAt(x)));
        }
        if(sum >= library.return_capacity() || sum <= library.return_lowercost())
            return false;
        return true;
    }

    private static boolean special_optimization(String a)
    {
        //return true or false if exceed giving constraint
        int sum = 0;
        for(int x = 0; x < a.length(); x++)
        {
            sum += (find_value(""+a.charAt(x)));
        }
        if(sum <= library.return_uppervalue() && sum >= library.return_lowervalue())
            return false;
        return true;
    }
    private static int total_cost(String a)
    {
    	//output total_cost giving string of items
        int sum = 0;
        for(int x = 0; x < a.length(); x++)
        {
            sum += (find_cost(""+a.charAt(x)));
        }
        return sum;
    }
    private static int total_value(String a)
    {
    	//output total_value giving string of items
        int sum = 0;
        for(int x = 0; x < a.length(); x++)
        {
            sum += (find_value(""+a.charAt(x)));
        }
        return sum;
    }
    private static List<String> sort(String a)
    {
    	//sort items: insertion sort
        String temp;
        List<String> list = new ArrayList<String>();
        for(int t = 0; t < a.length(); t++)
        {
            list.add(""+a.charAt(t));
        }
        for(int i = 1; i < list.size(); i++)
        {
            for(int j = i ; j > 0; j--)
            {
                if(list.get(j).charAt(0) < list.get(j-1).charAt(0))
                {
                    temp = list.get(j);
                    list.set(j,list.get(j-1));
                    list.set(j-1,temp);
                }
                
            }
        }
        return list;
    }

    public BinaryTree()
    {
    	//Pre set the Binary Tree
    	System.out.println("Knapsack calculation complete");
        library = new Knapsack();
        Build_Tree();
    }

    private static void Build_Tree() {

        final long startTime = System.currentTimeMillis(); //start time of binary tree

        List<String> tree = new ArrayList<String>(); //List array of Binary Tree
        int total = maximum(library.return_name().size()); //the size of the tree giving items
        tree.add(""); //add first empty items since there are no item at depth 0


        for(int treeHeight = 1; treeHeight <= library.return_numberItem(); treeHeight++)
        {
	        for(int x = (int)Math.pow(2,treeHeight) -1; x <= (int)Math.pow(2,treeHeight+1)-2; x++)
	        {
	        	if(x % 2 == 1) //if mod == 1 then is false so don't accept items
	        	{
	        		tree.add(tree.get((x-1)/2)); //get the items from the parent
	        	}
	        	else //if mod == 0 is true so accept
	        	{
	        		tree.add(tree.get((x-1)/2) + "" + library.return_name().get(treeHeight-1)); //get the items from the parent and add the current items
	        	}
	        }
    	}
    	

        System.out.println("Tree complete"); //print out the Binary tree complete

        final long estimatedTime = System.currentTimeMillis() - startTime; //the time for dumb search
        B_build_time = estimatedTime; //pre set time to enter dumb search
        dumb_search(tree); //dumb search start
        System.out.println("Dumb search complete");


        List<String> tree_temp = new ArrayList<String>(); //tree store

        for(int x = 0; x < tree.size(); x++)
        {
            if(tree.get(x) != "" && (above_limit(tree.get(x)) == true) ) //cutting down tree or pruning the tree
            {
                tree_temp.add(tree.get(x)); //stored the items
            }
        }
        

        smart_search(tree_temp);  //start smart search 
        System.out.println("Smart search complete");   //smart search complete
        
        List<String> hold = new ArrayList<String>(); //special optimization
        for(int x = 0; x < tree_temp.size(); x++)
        {
            if(special_optimization(tree_temp.get(x)) == true)
            {
                hold.add(tree_temp.get(x));
            }
        }

        special_search(hold);
        System.out.println("Special search complete");

    }

    private static void dumb_search(List<String> tree)
    {
        final long startTime = System.currentTimeMillis(); //time the dumb search

        limit = library.return_capacity(); //the capacity limit
        num_item = library.return_numberItem(); //number of items
        List<Integer> optimal = new ArrayList<Integer>(); //all possible items array --possible candidate to be optimal--
        List<Integer> optimal_cost = new ArrayList<Integer>(); //all possbile optimal cost array
        List<Integer> optimal_value = new ArrayList<Integer>(); //all possible optimal value array

        for(int q = (tree.size() - (int)Math.pow(2,num_item)); q < tree.size(); q++)
        {
            int sum = 0;
            int sum_value = 0;

            for(int x = 0; x < tree.get(q).length(); x++)
            {
                sum += find_cost(""+tree.get(q).charAt(x)); //stored total cost
                sum_value += find_value(""+tree.get(q).charAt(x)); //stored total value
                
            }

            if((sum <= limit)&&(tree.get(q).length() > 0)) //add items that within the constraint
            {
                optimal.add(q); //add items
                optimal_cost.add(sum); //add cost
                optimal_value.add(sum_value); //add value
            }
        }

        int temp = 0;
        for(int x = 1; x < optimal.size(); x++)
        {
            if(optimal_value.get(x) > optimal_value.get(temp)) //search for optimal choice
            {
                temp = x;
            }
            else if(optimal_value.get(x) == optimal_value.get(temp))
            {
                if(optimal_cost.get(x) < optimal_cost.get(temp))
                {
                    temp = x;
                }
            }
        }


        final long estimatedTime = System.currentTimeMillis() + B_build_time - startTime; //final time of dumb search
        String t = (estimatedTime*.001) + "s";
        B_optimal_time = t;
    }

    public static String dumb_time()
    {
        return B_optimal_time; //return dumb search
    }

    public static void smart_search(List<String> tree)
    {
        final long startTime = System.currentTimeMillis(); //start time of smart search
        int maximum = 0;
        for(int x = 1; x < tree.size(); x++) //find smart seach
        {
            if(total_value(tree.get(maximum)) < total_value(tree.get(x)))
                maximum = x;
            else if(total_value(tree.get(maximum)) == total_value(tree.get(x)))
            {
                if(total_cost(tree.get(maximum)) > total_cost(tree.get(x)))
                    maximum = x;
            }
        }

        final long estimatedTime = System.currentTimeMillis() + B_build_time - startTime; //final time of smart search
        String t = (estimatedTime*.001) + "s";
        G_optimal_time = t;

        optimal_choice = "(" + (double)total_value(tree.get(maximum)) + ", " + (double)total_cost(tree.get(maximum)) + ", " + sort(tree.get(maximum))+")"; //record optimal choice

    }

    public static void special_search(List<String> tree)
    {
        final long startTime = System.currentTimeMillis(); //start time of smart search
        int maximum = 0;
        for(int x = 1; x < tree.size(); x++) //find smart seach
        {
            if(total_value(tree.get(maximum)) < total_value(tree.get(x)))
                maximum = x;
            else if(total_value(tree.get(maximum)) == total_value(tree.get(x)))
            {
                if(total_cost(tree.get(maximum)) > total_cost(tree.get(x)))
                    maximum = x;
            }
        }

        final long estimatedTime = System.currentTimeMillis() + B_build_time - startTime; //final time of smart search
        String t = (estimatedTime*.001) + "s";
        SG_optimal_time = t;
    }

    public static String smart_time()
    {
       return G_optimal_time; //return smart search time
    }
}
