
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.util.*;



public class BinaryTree {

public static int maximum(int x)
{
    if(x == 1)
        return 2;
    return (int)Math.pow(2,x) + maximum(x-1);
}
// (rightchild - 2) / 2 = parent
// (leftchild - 1) / 2 = parent

public static void Build_Tree() {

        //temporary data
        int num_item = 11;
        int limit = 5;
        String[] name = {"a","b","c","d","e","f","g","h","i","j","k"};
        int[] cost = {2,3};
        int[] value = {4,5};


		System.out.println("Work on this Nattapat");
        List<String> c = new ArrayList<String>();
        Node a = new Node("","",-1,c);
		List<Node> b = new ArrayList<Node>();
        b.add(a);

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
                Node temp = new Node(name[x],hold,x,temp2);
                b.add(temp);
                y++;
            }
        }

        for(int x = 1;x < b.size();x++)
        {
            int t = x;
            while((((2*t)+1) < b.size()) && (((2*t)+2) < b.size()) )
            {
                if(b.get(t).path.size() == 0 && b.get(t).depth == 0)
                {
                    if((b.get(t).choice == "T") && (b.get(t).path.contains(b.get(t).item) == false))
                        b.get(t).path.add(b.get(t).item);
                }
                if((b.get(t).choice == "T") && (b.get((2*t)+2).choice == "T"))
                {
                    for(int p = 0; p < b.get(t).path.size();p++)
                    {
                        if(b.get((2*t)+2).path.contains(b.get(t).path.get(p)) == false)
                            b.get((2*t)+2).path.add(b.get(t).path.get(p));
                    }
                    if(b.get((2*t)+2).path.contains(b.get((2*t)+2).item) == false)
                        b.get((2*t)+2).path.add(b.get((2*t)+2).item);
                }
                if((b.get(t).choice == "T") && (b.get((2*t)+1).choice == "F"))
                {
                    for(int p = 0; p < b.get(t).path.size();p++)
                    {
                        if(b.get((2*t)+1).path.contains(b.get(t).path.get(p)) == false)
                            b.get((2*t)+1).path.add(b.get(t).path.get(p));
                    }
                }

                if((b.get(t).choice == "F") && (b.get((2*t)+2).choice == "T"))
                {
                    for(int p = 0; p < b.get(t).path.size();p++)
                    {
                        if(b.get((2*t)+2).path.contains(b.get(t).path.get(p)) == false)
                            b.get((2*t)+2).path.add(b.get(t).path.get(p));
                    }
                    if(b.get((2*t)+2).path.contains(b.get((2*t)+2).item) == false)
                        b.get((2*t)+2).path.add(b.get((2*t)+2).item);
                }
                if((b.get(t).choice == "F") && (b.get((2*t)+1).choice == "F"))
                {
                    for(int p = 0; p < b.get(t).path.size();p++)
                    {
                         if(b.get((2*t)+1).path.contains(b.get(t).path.get(p)) == false)
                            b.get((2*t)+1).path.add(b.get(t).path.get(p));
                    }
                }
                t = (2*t) + 1;
            }

       
        }
        /*
        System.out.println(b.size());
        System.out.println();
            for(int q = 1; q < b.size();q++)
            {
                System.out.println(b.get(q).item+b.get(q).choice+b.get(q).depth+b.get(q).path);
            }
        */
}
public static void main(String[] args)
{
    Build_Tree();
}
}

class Node {

	String item;
	String choice;
	int depth;
    List<String> path;


	Node(String item, String choice, int depth, List<String> path) {

		this.item = item;
		this.choice = choice;
		this.depth = depth;
        this.path = path;

	}
}