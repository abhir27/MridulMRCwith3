//package VoteCountApplication;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Min3Reducer extends Reducer< IntWritable, Text, IntWritable, Text> {
public static int l=(int)Math.floor(MridulMRCwith3.v/3);
	public static int[] Pdash=new int[MridulMRCwith3.v-2*l];
public static int[] oldP=new int[MridulMRCwith3.v-2*l];
//here we are copying data from P to another array OldP
//Also we are finding P' where
//P'(u)= P (min{P(u),min{P(v)|vertex v is adjacent to vertex u in G }}
    public void reduce(IntWritable key, Iterable<Text> values, Context output)
            throws IOException, InterruptedException {
    	 	int vmin=Comp3Reducer.P[key.get()-2*l-1];//u
    	 	for(Text value:values)
 {
	    String[] s=value.toString().split("\t");
	    int v=Integer.parseInt(s[0]);
	    int n;
	    if(v>l && v<=2*l)
	    {
	    	n=Comp2Reducer.P[v-l-1];//v
	    }
	    else if(v>2*l)
	    {
	 n=Comp3Reducer.P[v-2*l-1];//v
	    }
	    else
	    {
	    	n=Comp1Reducer.P[v-1];
	    }
	if(n<vmin)vmin=n;
	output.write(key,value);
 }
   Pdash[key.get()-2*l-1]=vmin;
  oldP[key.get()-2*l-1]=Comp3Reducer.P[key.get()-2*l-1];
    }
}