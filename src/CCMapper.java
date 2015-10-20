
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class CCMapper extends Mapper<LongWritable, Text, IntWritable, Text> {
public static int l=(int)Math.floor(MridulMRCwith3.v/3);
		@Override
		//forming input as connected component id,vertex
		//this way cc-id will be the key and we can count distinct no. of components.
       public void map(LongWritable key, Text value, Context output) throws IOException,
            InterruptedException {
		String[] s=value.toString().split("\t");
		int u=Integer.parseInt(s[0]);
		if(u<=l)
		{
		output.write(new IntWritable(Comp1Reducer.P[u-1]), new Text(s[0]));
		}
		else if(u>l && u<=2*l)
		{
			output.write(new IntWritable(Comp2Reducer.P[u-l-1]), new Text(s[0]));	
		}
		else
		{
			output.write(new IntWritable(Comp3Reducer.P[u-2*l-1]), new Text(s[0]));
		}
    }
}

