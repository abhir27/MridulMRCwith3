//package VoteCountApplication;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Parent3Reducer extends Reducer< IntWritable, Text, IntWritable, Text> {
    //here we are doing tree jumping
	//P (u) := min{P'(u),min{P'(v)|P (v) = u}}
	public void reduce(IntWritable key, Iterable<Text> values, Context output)
            throws IOException, InterruptedException {
		 int l=(int)Math.floor(MridulMRCwith3.v/3);
		int vmin=Min3Reducer.Pdash[key.get()-2*l-1];
      for(int i=0;i<l;i++)
 {
	   if(Comp1Reducer.P[i]==key.get())
	   {
		  if(vmin>MinReducer.Pdash[i])
		  {
			  vmin=MinReducer.Pdash[i];
		  }
	   }
 }
   //getting data from 2nd subsidary reducer
      for(int i=0;i<l;i++)
      {
     	   if(Comp2Reducer.P[i]==key.get())
     	   {
     		  if(vmin>Min2Reducer.Pdash[i]+l)
     		  {
     			  vmin=Min2Reducer.Pdash[i]+l;
     		  }
     	   }
      }
        
        for(int i=0;i<MridulMRCwith3.v-2*l;i++)
      {
     	   if(Comp2Reducer.P[i]==key.get())
     	   {
     		  if(vmin>Min3Reducer.Pdash[i]+2*l)
     		  {
     			  vmin=Min3Reducer.Pdash[i]+2*l;
     		  }
     	   }
      }
   Comp3Reducer.P[key.get()-2*l-1]=vmin;
   for(Text val:values)
   {
	   output.write(key, val);
   }
   	}
}