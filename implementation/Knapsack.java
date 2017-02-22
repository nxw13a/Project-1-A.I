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
import java.util.*;


public class Knapsack {

	public static String file_name;

	private static int capacity_limit;
	private static int number_of_item;

	private static List<String> name;
	private static List<Integer> cost;
	private static List<Integer> value;

	private static List<String> full_name;
	private static List<Double> full_cost;
	private static List<Double> full_value;

	private static String lowerBound;
	private static String upperBound;
	private static double lower_cost;
	private static double upper_cost;
	private static double lower_value;
	private static double upper_value;

	public static String return_filename()
	{
		return file_name;
	}

	public static List<String> return_name()
	{
		return name;
	}
	public static List<Integer> return_cost()
	{
		return cost;
	}
	public static List<Integer> return_value()
	{
		return value;
	}
	public static String return_lowerbound()
	{
		return lowerBound;
	}
	public static String return_upperbound()
	{
		return upperBound;
	}
	public static int return_numberItem()
	{
		return number_of_item;
	}
	public static int return_capacity()
	{
		return capacity_limit;
	}
	public static double return_lowercost()
	{
		return lower_cost;
	}
	public static double return_uppercost()
	{
		return upper_cost;
	}
	public static double return_lowervalue()
	{
		return lower_value;
	}
	public static double return_uppervalue()
	{
		return upper_value;
	}


	private static void CSVparser()
	{
		file_name = "k24.csv";
		String csvFile = new File("resources/"+file_name).getAbsolutePath();
		BufferedReader br = null;
		String line = "";
		int count = 0;
		List<String> list = new ArrayList<String>();
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();

		full_name = new ArrayList<String>();
		full_cost = new ArrayList<Double>();
		full_value = new ArrayList<Double>();
		
		try {

			br = new BufferedReader(new FileReader(csvFile));
			String text = br.readLine();
			capacity_limit = Integer.parseInt(text);
			//System.out.println(capacity_limit);

			while((line = br.readLine()) != null) {
				String hold = "";
				String hold1 = "";
				String hold2 = "";
				String[] data = line.split(",");
				hold = data[0].replace("\"","");
				hold = hold.replaceAll("^\"|\"$", "");
				hold1 = data[1].replace("\"","");
				hold1 = hold1.replaceAll("^\"|\"$", "");
				hold2 = data[2].replace("\"","");
				hold2 = hold2.replaceAll("^\"|\"$", "");
				int num = Integer.parseInt(hold1);
				int num2 = Integer.parseInt(hold2);
				list.add(hold);
				list1.add(num);
				list2.add(num2);
				count++;
			}

			name = new ArrayList<String>(list);
			cost = new ArrayList<Integer>(list1);
			value = new ArrayList<Integer>(list2);
			number_of_item = count;
			
			//System.out.println(count);
			//System.out.println(name);
			//System.out.println(cost);
			//System.out.println(value);
		}
		catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //return list;
        }
		//System.out.println("HI");
	}
	private static List<String> sort(List<String> list)
	{
		String temp;
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
	private static void Highest_value()
	{
		double total_cost = 0;
		double total_value = 0;
		List<String> a = new ArrayList<String>(name);
		List<Integer> b = new ArrayList<Integer>(cost);
		List<Integer> c = new ArrayList<Integer>(value);
		
		String temp1;
		int temp;
		int num;

		for(int i = 1; i < number_of_item; i++)
		{
			for(int j = i ; j > 0; j--)
			{
				if(c.get(j) < c.get(j-1))
				{
					temp = c.get(j);
					c.set(j,c.get(j-1));
					c.set(j-1,temp);

					temp1 = a.get(j);
					a.set(j,a.get(j-1));
					a.set(j-1,temp1);

					num = b.get(j);
					b.set(j,b.get(j-1));
					b.set(j-1,num);
				}
				
			}
		}
		String sent = "";
		for(int x = number_of_item - 1; x >= 0; x--)
		{
			if((total_cost + b.get(x)) <= capacity_limit)
			{
				sent = sent + a.get(x) + " ";
				total_cost = total_cost + b.get(x);
				total_value = total_value + c.get(x);
			}
			else
			{
				break;
			}
		}
		/*
		System.out.println("Highest value first: " + sent);
		System.out.println("total cost: " + total_cost);
		System.out.println("total value: " + total_value);
		*/

		full_name.add(sent);
		full_cost.add(total_cost);
		full_value.add(total_value);
		
		//System.out.println(a);
		//System.out.println(b);
		//System.out.println(c);
	}

	private static void Lowest_cost()
	{
		double total_cost = 0;
		double total_value = 0;
		List<String> a = new ArrayList<String>(name);
		List<Integer> c = new ArrayList<Integer>(cost);
		List<Integer> b = new ArrayList<Integer>(value);
		
		String temp1;
		int temp;
		int num;

		for(int i = 1; i < number_of_item; i++)
		{
			for(int j = i ; j > 0; j--)
			{
				if(c.get(j) < c.get(j-1))
				{
					temp = c.get(j);
					c.set(j,c.get(j-1));
					c.set(j-1,temp);

					temp1 = a.get(j);
					a.set(j,a.get(j-1));
					a.set(j-1,temp1);

					num = b.get(j);
					b.set(j,b.get(j-1));
					b.set(j-1,num);
				}
				
			}
		}
		String sent = "";
		for(int x = 0; x < number_of_item; x++)
		{
			if((total_cost + c.get(x)) <= capacity_limit)
			{
				sent = sent + a.get(x) + " ";
				total_cost = total_cost + c.get(x);
				total_value = total_value + b.get(x);
			}
			else
			{
				break;
			}
			
		}

		/*
		System.out.println("");
		System.out.println("Lowest cost first: " + sent);
		System.out.println("total cost: " + total_cost);
		System.out.println("total value: " + total_value);
		*/

		full_name.add(sent);
		full_cost.add(total_cost);
		full_value.add(total_value);

		//System.out.println(a);
		//System.out.println(b);
		//System.out.println(c);
	}

	private static void Highest_ratio()
	{
		double total_cost = 0;
		double total_value = 0;
		List<String> a = new ArrayList<String>(name);
		List<Integer> b = new ArrayList<Integer>(cost);
		List<Integer> c = new ArrayList<Integer>(value);
		List<Double> d = new ArrayList<Double>();
		for(int x = 0; x < number_of_item; x++)
		{
			double temp = value.get(x);
			double temp1 = cost.get(x);
			d.add(temp/temp1);
		}

		String temp1;
		int temp2;
		int temp3;
		double num;

		for(int i = 1; i < number_of_item; i++)
		{
			for(int j = i ; j > 0; j--)
			{
				if(d.get(j) < d.get(j-1))
				{
					temp1 = a.get(j);
					a.set(j,a.get(j-1));
					a.set(j-1,temp1);

					temp2 = b.get(j);
					b.set(j,b.get(j-1));
					b.set(j-1,temp2);

					temp3 = c.get(j);
					c.set(j,c.get(j-1));
					c.set(j-1,temp3);

					num = d.get(j);
					d.set(j,d.get(j-1));
					d.set(j-1,num);
				}
				
			}
		}

		String sent = "";
		for(int x = number_of_item - 1; x >= 0; x--)
		{
			if((total_cost + b.get(x)) <= capacity_limit)
			{
				sent = sent + a.get(x) + " ";
				total_cost = total_cost + b.get(x);
				total_value = total_value + c.get(x);
			}
			else
			{
				break;
			}
		}

		/*
		System.out.println("");
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
		*/
		
		/*
		System.out.println("");
		System.out.println("Highest ratio: " + sent);
		System.out.println("total cost: " + total_cost);
		System.out.println("total value: " + total_value);
		*/

		full_name.add(sent);
		full_cost.add(total_cost);
		full_value.add(total_value);
	}

	private static void Partial_knap()
	{
		double total_cost = 0;
		double total_value = 0;
		List<String> a = new ArrayList<String>(name);
		List<Integer> b = new ArrayList<Integer>(cost);
		List<Integer> c = new ArrayList<Integer>(value);
		List<Double> d = new ArrayList<Double>();
		for(int x = 0; x < number_of_item; x++)
		{
			double temp = value.get(x);
			double temp1 = cost.get(x);
			d.add(temp/temp1);
		}

		String temp1;
		int temp2;
		int temp3;
		double num;

		for(int i = 1; i < number_of_item; i++)
		{
			for(int j = i ; j > 0; j--)
			{
				if(d.get(j) < d.get(j-1))
				{
					temp1 = a.get(j);
					a.set(j,a.get(j-1));
					a.set(j-1,temp1);

					temp2 = b.get(j);
					b.set(j,b.get(j-1));
					b.set(j-1,temp2);

					temp3 = c.get(j);
					c.set(j,c.get(j-1));
					c.set(j-1,temp3);

					num = d.get(j);
					d.set(j,d.get(j-1));
					d.set(j-1,num);
				}
				
			}
		}

		String sent = "";
		int index = 0;
		for(int x = number_of_item - 1; x >= 0; x--)
		{
			if((total_cost + b.get(x)) <= capacity_limit)
			{
				sent = sent + a.get(x) + " ";
				total_cost = total_cost + b.get(x);
				total_value = total_value + c.get(x);
			}
			else
			{
				index = x;
				break;
			}
		}

		double remain = capacity_limit - total_cost;
		sent = sent + a.get(index);
		total_value += c.get(index)*(remain/b.get(index));
		total_cost = capacity_limit;
		/*
		System.out.println("");
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
		*/
		
		/*
		System.out.println("");
		System.out.println("Partial Knapsack: " + sent);
		System.out.println("total cost: " + total_cost);
		System.out.println("total value: " + total_value);
		*/

		full_name.add(sent);
		full_cost.add(total_cost);
		full_value.add(total_value);
	}
	private static void Knapsack_bound()
	{
		List<String> a = new ArrayList<String>(full_name);
		List<Double> b = new ArrayList<Double>(full_cost);
		List<Double> c = new ArrayList<Double>(full_value);

		String temp1;
		double temp;
		double num;

		for(int i = 1; i < 4; i++)
		{
			for(int j = i ; j > 0; j--)
			{
				if(c.get(j) < c.get(j-1))
				{
					temp = c.get(j);
					c.set(j,c.get(j-1));
					c.set(j-1,temp);

					temp1 = a.get(j);
					a.set(j,a.get(j-1));
					a.set(j-1,temp1);

					num = b.get(j);
					b.set(j,b.get(j-1));
					b.set(j-1,num);
				}
				
			}
		}
		int max_index = 3;
		double max = c.get(max_index);
		for(int x = max_index - 1; x >=0; x--)
		{
			if((c.get(x) == max) && (b.get(x) < b.get(max_index)))
			{
				max = c.get(x);
				max_index = x;
			}
		}

		int min_index = 0;
		double min = c.get(min_index);
		for(int x = min_index + 1; x < 4; x++)
		{
			if((c.get(x) == min) && (b.get(x) < b.get(min_index)))
			{
				min = c.get(x);
				min_index = x;
			}
		}

		String str1 = a.get(min_index);
		String[] splited1 = str1.split("\\s+");
		List<String> name1 = new ArrayList<String>();
		for(int x = 0; x < splited1.length; x++)
		{
			name1.add(splited1[x]);
		}

		String str2 = a.get(max_index);
		String[] splited2 = str2.split("\\s+");
		List<String> name2 = new ArrayList<String>();
		for(int x = 0; x < splited2.length; x++)
		{
			name2.add(splited2[x]);
		}


		String lower_bound = "("+c.get(min_index)+", "+b.get(min_index)+", "+sort(name1)+")";
		String upper_bound = "("+c.get(max_index)+", "+b.get(max_index)+", "+sort(name2)+")";
		lowerBound = lower_bound;
		upperBound = upper_bound;
		lower_cost = b.get(min_index);
		lower_value = c.get(min_index);
		upper_cost = b.get(max_index);
		upper_value = c.get(max_index);

		//System.out.println();
		//System.out.println("Lower Bound: " + lower_bound);
		//System.out.println("Upper Bound: " + upper_bound);
	}
	public Knapsack()
	{
		CSVparser();	
		Highest_value();
		Lowest_cost();
		Highest_ratio();
		Partial_knap();
		Knapsack_bound();
	}

}