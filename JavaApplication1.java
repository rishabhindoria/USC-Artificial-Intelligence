import java.util.*;
import java.lang.*;
import java.io.*;
class homework
{
	static String algorithm;
	static String start;
	static String goal;
	static int n;
	static int h[];
	static LinkedList matrix[];
	static ArrayList<String> list;
	static int h_value[];
	static int child_index;
	static ArrayList<String> input;
	static LinkedList node_list[];
	static Map<String,String> parent;
	static int dist[];	
	static void A_Star()
	{
		dist=new int[list.size()];
		parent = new LinkedHashMap<String,String>();
		h_value=new int[list.size()];
		String mkey="",key="";
		int mvalue=0,value=0;
       	Map<String,Integer> open = new LinkedHashMap<String,Integer>();
       	Queue<String> children=new LinkedList<String>();
       	Map<String,Integer> closed = new LinkedHashMap<String,Integer>();
       	open.put(start,dist[list.indexOf(start)]);
        dist[list.indexOf(start)]=0;
       	while(!open.isEmpty())
       	{
       		String current_node=open.keySet().toArray()[0].toString();
       		open.remove(current_node);
       		if(current_node.equalsIgnoreCase(goal))
       		{
       		    break;
       		}
       		int index_current_node=list.indexOf(current_node);
       		Iterator<Integer> childs=node_list[index_current_node].listIterator();
       		while(childs.hasNext())
       		{
       			children.add(list.get(childs.next()));
       		}
       		while(!children.isEmpty())
       		{
       		    String child=children.poll();
       		    child_index=list.indexOf(child);
       		   	dist[child_index]=dist[index_current_node]+Integer.parseInt(matrix[index_current_node].get(node_list[index_current_node].indexOf(child_index)).toString());
       		    h_value[child_index]=dist[child_index]+h[child_index];
       		    if((!open.containsKey(child))&&(!closed.containsKey(child)))
       		    {
       		        open.put(child,h_value[child_index]);
       		        parent.put(child,current_node);
       		    }
       		    else if(open.containsKey(child))
       		    {
       		    	for(Map.Entry<String,Integer> entry : open.entrySet())
       		    	{
  						key = entry.getKey();
  						value = entry.getValue();
  						if(key.equalsIgnoreCase(child))
  						{
  							mkey=key;
  							mvalue=value;
  						}
       		    	}
       		        if(mvalue>h_value[child_index])
       		        {
       		        	open.remove(mkey);
       		          	open.put(child,h_value[child_index]);
       		          	parent.put(child,current_node);
       		        }
       		        else
       		        {
       		        	h_value[child_index]=mvalue;
       		        	dist[child_index]=h_value[child_index]-h[child_index];
       		        }
       		    }
       		    else if(closed.containsKey(child))
       		    {
       		    	for(Map.Entry<String,Integer> entry : closed.entrySet())
       		    	{
  						key = entry.getKey();
  						value = entry.getValue();
  						if(key.equalsIgnoreCase(child))
  						{
  							mkey=key;
  							mvalue=value;
  						}
       		    	}
       		        if(mvalue>h_value[child_index])
       		        {	
       		        	closed.remove(mkey);
       		          	open.put(child,h_value[child_index]);
       		          	parent.put(child,current_node);
       		        }
       		        else
       		        {
       		        	h_value[child_index]=mvalue;
       		        	dist[child_index]=h_value[child_index]-h[child_index];
       		        }
       		    }
       		}
       		open=sortByValue(open);
       		closed.put(current_node,h_value[index_current_node]);
       		mkey="";
       		mvalue=0;
       	}
       	print_result();
	}
	static void UCS()
	{
		dist=new int[list.size()];
		parent = new LinkedHashMap<String,String>();
		String mkey="",key="";
		int mvalue=0,value=0;
       	Map<String,Integer> open = new LinkedHashMap<String,Integer>();
       	Queue<String> children=new LinkedList<String>();
       	Map<String,Integer> closed = new LinkedHashMap<String,Integer>();

       	open.put(start,dist[list.indexOf(start)]);
        dist[list.indexOf(start)]=0;
       	while(!open.isEmpty())
       	{
       		String current_node=open.keySet().toArray()[0].toString();
       		open.remove(current_node);
       		if(current_node.equalsIgnoreCase(goal))
       		{
       		    break;
       		}
       		int index_current_node=list.indexOf(current_node);
       		Iterator<Integer> childs=node_list[index_current_node].listIterator();
       		while(childs.hasNext())
       		{
       			children.add(list.get(childs.next()));
       		}
       		while(!children.isEmpty())
       		{
       		    String child=children.poll();
       		    child_index=list.indexOf(child);
       		    dist[child_index]=dist[index_current_node]+Integer.parseInt(matrix[index_current_node].get(node_list[index_current_node].indexOf(child_index)).toString());
       		    if((!open.containsKey(child))&&(!closed.containsKey(child)))
       		    {
       		        open.put(child,dist[child_index]);
       		        parent.put(child,current_node);
       		    }
       		    else if(open.containsKey(child))
       		    {
       		    	for(Map.Entry<String,Integer> entry : open.entrySet())
       		    	{
  						key = entry.getKey();
  						value = entry.getValue();
  						if(key.equalsIgnoreCase(child))
  						{
  							mkey=key;
  							mvalue=value;
  						}
       		    	}
       		        if(mvalue>dist[child_index])
       		        {
       		        	open.remove(mkey);
       		          	open.put(child,dist[child_index]);
       		          	parent.put(child,current_node);
       		        }
       		        else
       		        	dist[child_index]=mvalue;
       		    }
       		    else if(closed.containsKey(child))
       		    {
       		    	for(Map.Entry<String,Integer> entry : closed.entrySet())
       		    	{
  						key = entry.getKey();
  						value = entry.getValue();
  						if(key.equalsIgnoreCase(child))
  						{
  							mkey=key;
  							mvalue=value;
  						}
       		    	}
       		        if(mvalue>dist[child_index])
       		        {
       		        	closed.remove(mkey);
       		          	open.put(child,dist[child_index]);
       		          	parent.put(child,current_node);
       		        }
       		        else
       		        	dist[child_index]=mvalue;
       		    }
       		}
       		open=sortByValue(open);
       		closed.put(current_node,dist[index_current_node]);
       		mkey="";
       		mvalue=0;
       	}
       	print_result();
	}
	private static Map<String, Integer> sortByValue(Map<String, Integer> myMap) {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(myMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    static void print_result()
    {
    	try {
       	File f=new File("C:\\Users\\risha\\OneDrive\\Documents\\output.txt");
       	f.createNewFile();
       	FileWriter fileWriter=new FileWriter(f);
        BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);
       	if(start.equalsIgnoreCase(goal))
       	{
       		bufferedWriter.write(start+" "+dist[list.indexOf(start)]);
       		bufferedWriter.close();
       		return;
       	}
       	String store=goal;
       	ArrayList print=new ArrayList();
       	while(!(store=parent.get(store)).equalsIgnoreCase(start)){
       			print.add(store);	
       	}
       	bufferedWriter.write(start+" "+0);
       	bufferedWriter.newLine();
       	Collections.reverse(print);
       	while(!print.isEmpty())
       	{
       		bufferedWriter.write(print.get(0)+" "+dist[list.indexOf(print.get(0))]);
       		print.remove(0);
       		bufferedWriter.newLine();
       	}
       	bufferedWriter.write(goal+" "+dist[list.indexOf(goal)]);
       	bufferedWriter.close();
       	}
       	catch(Exception ex){
       	}
    }
    static void BFS()
    {
    	dist=new int[list.size()];
    	parent = new LinkedHashMap<String,String>();
        for(int i=0;i<list.size();i++)
        {
        	dist[i]=-1;
        }
       	Queue<Integer> queue=new LinkedList<Integer>();
       	queue.add(list.indexOf(start));
       	dist[list.indexOf(start)]=0;
       	while(!queue.isEmpty())
       	{
       		int parent_track=queue.poll();
       		Iterator<Integer> nodes=node_list[parent_track].listIterator();
       		while(nodes.hasNext())
       		{
       			int child=nodes.next();
       			if(dist[child]==-1)
       			{
       				queue.add(child);
       				parent.put(list.get(child),list.get(parent_track));
       				dist[child]=dist[parent_track]+1;
       			}
       			if(goal.equalsIgnoreCase(list.get(child)))
       			{
       				print_result();
       				return;
       			}
       		}
       	}
       	print_result();
    }
    static void DFS()
    {
    	dist=new int[list.size()];
        parent = new LinkedHashMap<String,String>();
        for(int i=0;i<list.size();i++)
        {
        	dist[i]=-1;
        }
       	Stack<Integer> stack=new Stack<Integer>();
       	stack.push(list.indexOf(start));
       	dist[list.indexOf(start)]=0;
		while(!stack.empty())
       	{
       		int parent_track=stack.pop();
       		if(goal.equalsIgnoreCase(list.get(parent_track)))
       		{
       			break;
       		}
       		Iterator<Integer> nodes=node_list[parent_track].listIterator();       
       		ArrayList<Integer> copy=new ArrayList<Integer>();
       		while(nodes.hasNext())
       			copy.add(nodes.next());
       		Collections.reverse(copy);
       		nodes=copy.listIterator();
       		while(nodes.hasNext())
       		{
       			int child=nodes.next();
       			if(dist[child]==-1)
       			{
       				parent.put(list.get(child),list.get(parent_track));
       				dist[child]=dist[parent_track]+1;
       				stack.push(child);
       			}
       		}
       	}
       	print_result();
    }
	public static void main (String[] args) throws java.lang.Exception
	{
		int cost=0;
		String input_Line="";
		String input_Tokens[]=new String[3];
		input=new ArrayList<String>();
		FileReader fileReader = new FileReader("C:\\Users\\risha\\OneDrive\\Documents\\input.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
		while((input_Line = bufferedReader.readLine()) != null)
		{
        	input.add(input_Line);
        }
      	int i=0;
		algorithm=input.get(i++);
		start=input.get(i++);
		goal=input.get(i++);
		int n=Integer.parseInt(input.get(i++));
		node_list=new LinkedList[n+1];
		matrix=new LinkedList[n+1];
        for(int k=0;k<=n;k++)
        {
        	node_list[k]=new LinkedList();
        	matrix[k]=new LinkedList();
        }
		list=new ArrayList<String>();
		n=n*3;
		while(n>0)
		{
			input_Tokens=input.get(i++).split(" ");
			String x=input_Tokens[0];
			if(list.indexOf(x)==-1)
				list.add(x);
			String y=input_Tokens[1];
			if(list.indexOf(y)==-1)
				list.add(y);
			node_list[list.indexOf(x)].add(list.indexOf(y));
			cost=Integer.parseInt(input_Tokens[2]);
			matrix[list.indexOf(x)].add(cost);
			n-=3;
		}
		n=Integer.parseInt(input.get(i++));
		if(algorithm.equalsIgnoreCase("A*"))
		{
			h=new int[n];
			while(n>0)
			{
				input_Tokens=input.get(i++).split(" ");
				String x=input_Tokens[0];
				cost=Integer.parseInt(input_Tokens[1]);
				h[list.indexOf(x)]=cost;
				n--;
			}
			A_Star();
		}
		if(algorithm.equalsIgnoreCase("UCS"))
		{
			UCS();
		}
		if(algorithm.equalsIgnoreCase("BFS"))
		{
			BFS();
		}
		if(algorithm.equalsIgnoreCase("DFS"))
		{
			DFS();
		}
	}
}