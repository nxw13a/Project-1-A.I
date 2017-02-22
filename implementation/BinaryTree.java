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

    private static Knapsack library;

    private static int num_item;
    private static int limit;
    private static String optimal_choice;
    private static String B_optimal_time;
    private static String G_optimal_time;
    private static long B_build_time;

    private static boolean Ncontains(String word, char a)
    {
        //int size = (word.length() <= a.length() ? a.length() : word.length());
        for(int x = 0; x < word.length(); x++)
        {
            if(word.charAt(x) == a)
                return false;
        }
        return true;
    }
    public static String return_optimal()
    {
        return optimal_choice;
    }

    public static int maximum(int x)
    {
        if(x == 1)
            return 2;
        return (int)Math.pow(2,x) + maximum(x-1);
    }

    private static int find_cost(String a)
    {
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
        int sum = 0;
        for(int x = 0; x < a.length(); x++)
        {
            sum += (find_cost(""+a.charAt(x)));
        }
        if(sum > library.return_capacity() || sum < library.return_lowercost())
            return false;
        return true;
    }
    private static int total_cost(String a)
    {
        int sum = 0;
        for(int x = 0; x < a.length(); x++)
        {
            sum += (find_cost(""+a.charAt(x)));
        }
        return sum;
    }
    private static int total_value(String a)
    {
        int sum = 0;
        for(int x = 0; x < a.length(); x++)
        {
            sum += (find_value(""+a.charAt(x)));
        }
        return sum;
    }
    private static List<String> sort(String a)
    {
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
        library = new Knapsack();
        Build_Tree();
    }

    private static void Build_Tree() {

        final long startTime = System.currentTimeMillis();


        List<String> tree = new ArrayList<String>();
        int total = maximum(library.return_name().size());
        tree.add("");

        //int treeHeight = 1;
        for(int treeHeight = 1; treeHeight <= library.return_numberItem(); treeHeight++)
        {
	        for(int x = (int)Math.pow(2,treeHeight) -1; x <= (int)Math.pow(2,treeHeight+1)-2; x++)
	        {
	        	if(x % 2 == 1)
	        	{
	        		tree.add(tree.get((x-1)/2));
	        	}
	        	else
	        	{
	        		tree.add(tree.get((x-1)/2) + "" + library.return_name().get(treeHeight-1));
	        	}
	        }
	        //System.out.println(treeHeight);
    	}
        System.out.println("Tree complete");

        final long estimatedTime = System.currentTimeMillis() - startTime;
        B_build_time = estimatedTime;
        dumb_search(tree);
        System.out.println("Dumb search complete");
        /*
        for(int x = 0; x < tree.size(); x++)
        {
            if((above_limit(tree.get(x)) == false))
                tree.set(x,""); 
            //System.out.println(x + " | " + tree.size());
        }
        System.out.println("Inserting complete");
        */

        List<String> tree_temp = new ArrayList<String>();

        for(int x = 0; x < tree.size(); x++)
        {
            if(tree.get(x) != "" && (above_limit(tree.get(x)) == true) )
            {
                tree_temp.add(tree.get(x));
            }
            //System.out.println(x + " | " + tree.size());
        }
   
        smart_search(tree_temp);   
        System.out.println("Smart search complete");                      
    }
    private static void dumb_search(List<String> tree)
    {
        final long startTime = System.currentTimeMillis();

        limit = library.return_capacity();
        num_item = library.return_numberItem();
        List<Integer> optimal = new ArrayList<Integer>();
        List<Integer> optimal_cost = new ArrayList<Integer>();
        List<Integer> optimal_value = new ArrayList<Integer>();
        for(int q = (tree.size() - (int)Math.pow(2,num_item)); q < tree.size(); q++)
        {
            //System.out.println(tree.get(q).item+tree.get(q).choice+tree.get(q).depth+tree.get(q).path);
            int sum = 0;
            int sum_value = 0;
            for(int x = 0; x < tree.get(q).length(); x++)
            {
                sum += find_cost(""+tree.get(q).charAt(x));
                sum_value += find_value(""+tree.get(q).charAt(x));
                
            }
            //System.out.println(limit + " " + sum + " " + q + " "+ tree.size());
            if((sum <= limit)&&(tree.get(q).length() > 0)&&(sum >= library.return_lowercost())&&(sum_value >= library.return_lowervalue()))
            {
                //System.out.println(sum_value);
                optimal.add(q);
                optimal_cost.add(sum);
                optimal_value.add(sum_value);
            }
        }
        int temp = 0;
        for(int x = 1; x < optimal.size(); x++)
        {
            if(optimal_value.get(x) > optimal_value.get(temp))
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
            //System.out.println(tree.get(optimal.get(x)).path);
        }
        //System.out.println(optimal_cost); 
        //optimal_choice = "(" + (double)optimal_value.get(temp) + ", " + (double)optimal_cost.get(temp) + ", " + sort((tree.get(optimal.get(temp))))+")";
        //System.out.println(optimal_choice);


        final long estimatedTime = System.currentTimeMillis() + B_build_time - startTime;
        String t = (estimatedTime*.001) + "s";
        B_optimal_time = t;
        //System.out.println(optimal_choice);
        //System.out.println(temp + " " +tree.get(optimal.get(temp)).path);
        
    }
    public static String dumb_time()
    {
        return B_optimal_time;
    }

    public static void smart_search(List<String> tree)
    {
        final long startTime = System.currentTimeMillis();
        int maximum = 0;
        for(int x = 1; x < tree.size(); x++)
        {
            if(total_value(tree.get(maximum)) < total_value(tree.get(x)))
                maximum = x;
            else if(total_value(tree.get(maximum)) == total_value(tree.get(x)))
            {
                if(total_cost(tree.get(maximum)) > total_cost(tree.get(x)))
                    maximum = x;
            }
        }
        final long estimatedTime = System.currentTimeMillis() + B_build_time - startTime;
        String t = (estimatedTime*.001) + "s";
        G_optimal_time = t;
        //System.out.println(tree.get(maximum));
        //System.out.println(total_value("c"));
        optimal_choice = "(" + (double)total_value(tree.get(maximum)) + ", " + (double)total_cost(tree.get(maximum)) + ", " + sort(tree.get(maximum))+")";
        //System.out.println(ptimal_choice);
    }
    public static String smart_time()
    {
       return G_optimal_time;
    }
}
