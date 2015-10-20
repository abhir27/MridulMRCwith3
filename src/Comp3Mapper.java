
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class Comp3Mapper extends Mapper<LongWritable, Text, IntWritable, Text> {
	//We are dividing data into 2 parts this mapper separates second half of vertices
			@Override
       public void map(LongWritable key, Text value, Context output) throws IOException,
            InterruptedException {
		String[] s=value.toString().split("\t");
		
		if(Integer.parseInt(s[0])==0 && Integer.parseInt(s[1])>2*(int)Math.floor(MridulMRCwith3.v/3))
		{
			output.write(new IntWritable(Integer.parseInt(s[1])), new Text(s[2]+"\t"+s[3]+"\t"+s[4]+"\t"+s[5]+"\t"+s[6]+"\t"+s[7]));
		}
		
    }
}

