//package VoteCountApplication;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class OneReducer extends Reducer< IntWritable, Text, IntWritable, Text> {
	//public static int e=OneMapper.newedges;
	//public static int[][] ver=new int[2*e][2];
	public static int[][] ver=new int[MridulMRCwith3.v][2];
	//public static int vertices =0;
	
	//Here we are calculating min and max values for each unmatched edge's u
    public void reduce(IntWritable key, Iterable<Text> values, Context output)
            throws IOException, InterruptedException {
    	int min=999;int max=0;
   for(Text value:values)
 {
	String s[]=value.toString().split("\t");
	int n=Integer.parseInt(s[1]);
	if(min>n)min=n;
	if(max<n)max=n;
	ver[key.get()][0]=min;
	ver[key.get()][1]=max;
	output.write(key, value);
 }
      //  vertices++;
    }
}