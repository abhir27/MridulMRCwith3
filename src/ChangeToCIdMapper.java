
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class ChangeToCIdMapper extends Mapper<LongWritable, Text, IntWritable, Text> {
public static int l=(int)Math.floor(MridulMRCwith3.v/3);
//In this job we are changing u and v of edge with its connected component id 
//Also we drop cases when both u and v are in same component.
		@Override
       public void map(LongWritable key, Text value, Context output) throws IOException,
            InterruptedException {
		String[] s=value.toString().split("\t");
		if(Integer.parseInt(s[0])==1)
		{
			int u=Integer.parseInt(s[1]);
			int v=Integer.parseInt(s[2]);
			if(u<=l && v<=l){
			output.write(new IntWritable(Integer.parseInt(s[0])), new Text(Comp1Reducer.P[u-1]
					+"\t"+Comp1Reducer.P[v-1]+"\t"+s[3]+"\t"+s[4]+"\t"+s[5]+"\t"+s[6]+"\t"+s[7]));
		}
			else if(u<=2*l && v<=2*l)
			{
			if(u>l && v>l )
			{
				output.write(new IntWritable(Integer.parseInt(s[0])), new Text(Comp2Reducer.P[u-l-1]
						+"\t"+Comp2Reducer.P[v-l-1]+"\t"+s[3]+"\t"+s[4]+"\t"+s[5]+"\t"+s[6]+"\t"+s[7]));
			}
			else if(u<l && v>l){
				output.write(new IntWritable(Integer.parseInt(s[0])), new Text(Comp1Reducer.P[u-1]
						+"\t"+Comp2Reducer.P[v-l-1]+"\t"+s[3]+"\t"+s[4]+"\t"+s[5]+"\t"+s[6]+"\t"+s[7]));
			}
			else if(u>l && v<l)
			{
				output.write(new IntWritable(Integer.parseInt(s[0])), new Text(Comp2Reducer.P[u-l-1]
						+"\t"+Comp1Reducer.P[v-1]+"\t"+s[3]+"\t"+s[4]+"\t"+s[5]+"\t"+s[6]+"\t"+s[7]));
			}
			}
			else
			{
				if(u<=l && v>2*l)
				{
					output.write(new IntWritable(Integer.parseInt(s[0])), new Text(Comp1Reducer.P[u-1]
							+"\t"+Comp3Reducer.P[v-2*l-1]+"\t"+s[3]+"\t"+s[4]+"\t"+s[5]+"\t"+s[6]+"\t"+s[7]));
				}
				else if(v<=l && u>2*l)
				{
					output.write(new IntWritable(Integer.parseInt(s[0])), new Text(Comp3Reducer.P[u-2*l-1]
							+"\t"+Comp1Reducer.P[v-1]+"\t"+s[3]+"\t"+s[4]+"\t"+s[5]+"\t"+s[6]+"\t"+s[7]));
				}
				else if(v>2*l && u>2*l)
				{
					output.write(new IntWritable(Integer.parseInt(s[0])), new Text(Comp3Reducer.P[u-2*l-1]
							+"\t"+Comp3Reducer.P[v-2*l-1]+"\t"+s[3]+"\t"+s[4]+"\t"+s[5]+"\t"+s[6]+"\t"+s[7]));
				}
				else if(u>2*l && v<=2*l)
				{
					output.write(new IntWritable(Integer.parseInt(s[0])), new Text(Comp3Reducer.P[u-2*l-1]
							+"\t"+Comp2Reducer.P[v-l-1]+"\t"+s[3]+"\t"+s[4]+"\t"+s[5]+"\t"+s[6]+"\t"+s[7]));
				}
				else if(v>2*l && u<=2*l)
				{
					output.write(new IntWritable(Integer.parseInt(s[0])), new Text(Comp2Reducer.P[u-l-1]
							+"\t"+Comp3Reducer.P[v-2*l-1]+"\t"+s[3]+"\t"+s[4]+"\t"+s[5]+"\t"+s[6]+"\t"+s[7]));
				}
				
			}
		}
		
    }
}

