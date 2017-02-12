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



public class BinaryTree{

    private static Knapsack library;
    private static List<Node> tree;
    private static int num_item;
    private static int limit;
    private static String optimal_choice;

    public static int maximum(int x)
    {
        if(x == 1)
            return 2;
        return (int)Math.pow(2,x) + maximum(x-1);
    }

    public static int find_cost(String a)
    {
        int store = 0;
        List<String> name = new ArrayList<String>(library.return_name());
        for(int x = 0; x < name.size(); x++)
        {
            if(name.get(x) == a)
                store = x;
        }
        List<Integer> cost = new ArrayList<Integer>(library.return_cost());
        return cost.get(store);
    }
    public static int find_value(String b)
    {
        int store = 0;
        List<String> name = new ArrayList<String>(library.return_name());
        for(int x = 0; x < name.size(); x++)
        {
            if(name.get(x) == b)
                store = x;
        }
        List<Integer> value = new ArrayList<Integer>(library.return_value());
        return value.get(store);
    }
    // (rightchild - 2) / 2 = parent
    // (leftchild - 1) / 2 = parent
    /*
    public BinaryTree()
    {
        Build_Tree();
    }
    */

    public static void Build_Tree() {

            //temporary data
            num_item = library.return_numberItem();
            limit = library.return_capacity();


            List<String> c = new ArrayList<String>();
            Node a = new Node("","",-1,c,false);
    		tree = new ArrayList<Node>();
            List<String> temp1 = new ArrayList<String>(library.return_name());
            tree.add(a);

            int y = 1;

            for(int x = 0; x < num_item; x++)
            {
                while(y <= maximum(x+1))
                {
                    String hold;
                    if(y % 2 == 0)
                        hold = "T";
                    else
                        hold = "F";
                    List<String> temp2 = new ArrayList<String>();
                    Node temp = new Node(temp1.get(x),hold,x,temp2,false);
                    tree.add(temp);
                    y++;
                }
            }

            for(int x = 1;x < tree.size();x++)
            {
                int t = x;
                while((((2*t)+1) < tree.size()) && (((2*t)+2) < tree.size()) && (tree.get(t).access == false))
                {
                    if(tree.get(t).path.size() == 0 && tree.get(t).depth == 0)
                    {
                        if((tree.get(t).choice == "T") && (tree.get(t).path.contains(tree.get(t).item) == false))
                            tree.get(t).path.add(tree.get(t).item);
                    }
                    if((tree.get(t).choice == "T") && (tree.get((2*t)+2).choice == "T"))
                    {
                        for(int p = 0; p < tree.get(t).path.size();p++)
                        {
                            if(tree.get((2*t)+2).path.contains(tree.get(t).path.get(p)) == false)
                                tree.get((2*t)+2).path.add(tree.get(t).path.get(p));
                        }
                        if(tree.get((2*t)+2).path.contains(tree.get((2*t)+2).item) == false)
                            tree.get((2*t)+2).path.add(tree.get((2*t)+2).item);
                    }
                    if((tree.get(t).choice == "T") && (tree.get((2*t)+1).choice == "F"))
                    {
                        for(int p = 0; p < tree.get(t).path.size();p++)
                        {
                            if(tree.get((2*t)+1).path.contains(tree.get(t).path.get(p)) == false)
                                tree.get((2*t)+1).path.add(tree.get(t).path.get(p));
                        }
                    }

                    if((tree.get(t).choice == "F") && (tree.get((2*t)+2).choice == "T"))
                    {
                        for(int p = 0; p < tree.get(t).path.size();p++)
                        {
                            if(tree.get((2*t)+2).path.contains(tree.get(t).path.get(p)) == false)
                                tree.get((2*t)+2).path.add(tree.get(t).path.get(p));
                        }
                        if(tree.get((2*t)+2).path.contains(tree.get((2*t)+2).item) == false)
                            tree.get((2*t)+2).path.add(tree.get((2*t)+2).item);
                    }
                    if((tree.get(t).choice == "F") && (tree.get((2*t)+1).choice == "F"))
                    {
                        for(int p = 0; p < tree.get(t).path.size();p++)
                        {
                             if(tree.get((2*t)+1).path.contains(tree.get(t).path.get(p)) == false)
                                tree.get((2*t)+1).path.add(tree.get(t).path.get(p));
                        }
                    }
                    tree.get(t).access = true;
                    t = (2*t) + 1;
                }
                //System.out.println(x+" / "+tree.size());
            }
            dumb_search();                              
    }
    public static void dumb_search()
    {
        List<Integer> optimal = new ArrayList<Integer>();
        List<Integer> optimal_cost = new ArrayList<Integer>();
        List<Integer> optimal_value = new ArrayList<Integer>();
        for(int q = (tree.size() - (int)Math.pow(2,num_item)); q < tree.size();q++)
        {
            //System.out.println(tree.get(q).item+tree.get(q).choice+tree.get(q).depth+tree.get(q).path);
            int sum = 0;
            int sum_value = 0;
            for(int x = 0; x < tree.get(q).path.size(); x++)
            {
                sum += find_cost(tree.get(q).path.get(x));
                sum_value += find_value(tree.get(q).path.get(x));

            }
            if((sum <= limit)&&(tree.get(q).path.size() > 0)&&(sum >= library.return_lowercost())&&(sum_value >= library.return_lowervalue()))
            {
                optimal.add(q);
                optimal_cost.add(sum);
                optimal_value.add(sum_value);
            }
        }
        int temp = 0;
        for(int x = 1; x < optimal.size();x++)
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
        optimal_choice = "Optimal: (" + (double)optimal_value.get(temp) + ", " + (double)optimal_cost.get(temp) + ", " + (tree.get(optimal.get(temp)).path)+")";
        System.out.println(optimal_choice);
        //System.out.println(temp + " " +tree.get(optimal.get(temp)).path);
        
    }
    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();
        library = new Knapsack();
        Build_Tree();
        //System.out.println(library.return_name());
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("run in "+ estimatedTime +" millisecond");
    }
}

class Node {

	String item;
	String choice;
	int depth;
    List<String> path;
    boolean access;


	Node(String item, String choice, int depth, List<String> path, boolean access) {

		this.item = item;
		this.choice = choice;
		this.depth = depth;
        this.path = path;
        this.access = access;
	}
}