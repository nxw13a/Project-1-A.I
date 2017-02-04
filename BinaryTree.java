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

public static void main(String[] args) {

        int num_item = 2;
        int limit = 5;
        String[] name = {"a","b"};
        int[] cost = {2,3};
        int[] value = {4,5};


		System.out.println("Work on this Nattapat");
        Node a = new Node("","",-1);
		List<Node> b = new ArrayList<Node>();
        b.add(a);
        System.out.println(b.get(0).item);

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

                Node temp = new Node(name[x],hold,x);
                b.add(temp);
                y++;
            }
        }
        for(int x = 1; x < b.size();x++)
            System.out.println(b.get(x).item+b.get(x).choice+b.get(x).depth);
}
}

class Node {

	String item;
	String choice;
	int depth;

	Node(String item, String choice, int depth) {

		this.item = item;
		this.choice = choice;
		this.depth = depth;

	}
}