import java.util.Date;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MridulMRCwith3 extends Configured implements Tool{
	 public static Configuration conf=new Configuration();
	public static  int loopid=0;
	public static int lid=0;
	public static int status=0;
	public static int v;
	public static void main(String[] args) throws Exception {
    	System.out.println("Started");
    	int res = ToolRunner.run(conf, new MridulMRCwith3(), args);
        System.exit(res);       
    }
       @Override
    public int run(String[] args) throws Exception {
    	System.out.println("in run");
       if (args.length != 3) {
            System.out.println("usage: [input] [output] [output1].");
            System.exit(-1);
        }
     
       long start,end;
       //In this job we are are duplicating edges
       //If we given with edge u,v,w as start of edge,end of edge,weight we add a duplicate edge v,u,w
        Job job = Job.getInstance(conf);
        start = new Date().getTime();
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
        job.setMapperClass(EdgeMapper.class);
        job.setReducerClass(EdgeReducer.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.setJarByClass(MridulMRCwith3.class);
        job.waitForCompletion(true);
        
        Job joba = Job.getInstance(conf);
         joba.setOutputKeyClass(IntWritable.class);
         joba.setOutputValueClass(Text.class);
         joba.setMapperClass(Edge1Mapper.class);
         joba.setReducerClass(Edge1Reducer.class);
         joba.setInputFormatClass(TextInputFormat.class);
         joba.setOutputFormatClass(TextOutputFormat.class);
         FileInputFormat.setInputPaths(joba, new Path(args[1]));
         FileOutputFormat.setOutputPath(joba, new Path(args[1]+"xa"));
         joba.setJarByClass(MridulMRCwith3.class);
         joba.submit();
        
        Job jobb = Job.getInstance(conf);
             jobb.setOutputKeyClass(IntWritable.class);
        jobb.setOutputValueClass(Text.class);
        jobb.setMapperClass(Edge2Mapper.class);
        jobb.setReducerClass(Edge2Reducer.class);
        jobb.setInputFormatClass(TextInputFormat.class);
        jobb.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.setInputPaths(jobb, new Path(args[1]));
        FileOutputFormat.setOutputPath(jobb, new Path(args[1]+"xb"));
        jobb.setJarByClass(MridulMRCwith3.class);
        jobb.submit();
        
        Job jobc = Job.getInstance(conf);
         jobc.setOutputKeyClass(IntWritable.class);
         jobc.setOutputValueClass(Text.class);
         jobc.setMapperClass(Edge3Mapper.class);
         jobc.setReducerClass(Edge3Reducer.class);
         jobc.setInputFormatClass(TextInputFormat.class);
         jobc.setOutputFormatClass(TextOutputFormat.class);
         FileInputFormat.setInputPaths(jobc, new Path(args[1]));
         FileOutputFormat.setOutputPath(jobc, new Path(args[1]+"xc"));
         jobc.setJarByClass(MridulMRCwith3.class);
         jobc.submit();
        
         while(!joba.isComplete() && !jobb.isComplete() && !jobc.isComplete())
         {
         	Thread.sleep(1000);
         }
               
        //In this job we are converting out input data into a desired format of:
        // u,v,wt,min,max,u,v
        String input=args[2]+loopid;
        String pathprefix=args[2];
        Job job1 = Job.getInstance(conf);
        job1.setOutputKeyClass(IntWritable.class);
        job1.setOutputValueClass(Text.class);
        job1.setMapperClass(EdgeMapper2.class);
        job1.setInputFormatClass(TextInputFormat.class);
        job1.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.setInputPaths(job1, new Path(args[1]));
        FileOutputFormat.setOutputPath(job1, new Path(input));
        job1.setJarByClass(MridulMRCwith3.class);
        job1.waitForCompletion(true);
        
        v=EdgeReducer.vertices;
        //System.out.println("v:"+v);
        
        Job job2 ;
        Job job4 ;
        Job job5 ;
        Job job21;Job job21a;Job job21b,job21c;
        Job job211a,job211b,job211c;
        Job job212,job212b,job212c;
        Job job213a;//job213b;
        Job job22;Job job2a;
        //here status is a boolean variable that deals with status of passes.
        //If no. of components==1 status=1 else status=0
        while(status!=1)
        {
        	//From previous input we are taking data and check for each edge if its weight==min if yes add its status=0
        	//else add 1 as status
        	//If status==0 it will be stored as intermediate input and remaining data marked with 1 is sent to next pass.
        	//Edges with status==0 is called marked edges
        	//Edges with status==1 are unmarked edges.
        	//P.S. this status is different from variable status.
            job2 = Job.getInstance(conf);
            job2.setOutputKeyClass(IntWritable.class);
            job2.setOutputValueClass(Text.class);
            job2.setMapperClass(MatchMapper.class);
            job2.setInputFormatClass(TextInputFormat.class);
            job2.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job2, new Path(input));
            FileOutputFormat.setOutputPath(job2, new Path(input+"o1"));
            job2.setJarByClass(MridulMRCwith3.class);
            job2.waitForCompletion(true);
            
            //Here we are separating matched edges to do further computation 
            //To find connected components we use matched edges only.
            job21 = Job.getInstance(conf);
            job21.setOutputKeyClass(IntWritable.class);
            job21.setOutputValueClass(Text.class);
            job21.setMapperClass(CompMapper.class);
            job21.setInputFormatClass(TextInputFormat.class);
            job21.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job21, new Path(input+"o1"));
            FileOutputFormat.setOutputPath(job21, new Path(input+"o2"));
            job21.setJarByClass(MridulMRCwith3.class);
            job21.waitForCompletion(true);
           
            //cc routine start
            //Now we are taking 2 subsidiary reducers for doing Connected Components computation 
            //So we are submitting 2 jobs instead of waiting for completion so that these subsidiary
            //reducers can work in parallel. 
            
          //Here we are calculating minimum vertex connected with a given vertex
          //P(u)=min(u,min(v|v is neighbor of u))
            //Next 2 jobs job21a and job21b find min vertex connected with each vertex.
            long start1=new Date().getTime();
            String ccinput=input+"o1";
            job21a = Job.getInstance(conf);
            job21a.setOutputKeyClass(IntWritable.class);
            job21a.setOutputValueClass(Text.class);
            job21a.setMapperClass(Comp1Mapper.class);
            job21a.setReducerClass(Comp1Reducer.class);
            job21a.setInputFormatClass(TextInputFormat.class);
            job21a.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job21a, new Path(ccinput));
            FileOutputFormat.setOutputPath(job21a, new Path(input+lid+"p2a"));
            job21a.setJarByClass(MridulMRCwith3.class);
            job21a.submit();
            
            job21b = Job.getInstance(conf);
            job21b.setOutputKeyClass(IntWritable.class);
            job21b.setOutputValueClass(Text.class);
            job21b.setMapperClass(Comp2Mapper.class);
            job21b.setReducerClass(Comp2Reducer.class);
            job21b.setInputFormatClass(TextInputFormat.class);
            job21b.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job21b, new Path(ccinput));
            FileOutputFormat.setOutputPath(job21b, new Path(input+lid+"p2b"));
            job21b.setJarByClass(MridulMRCwith3.class);
            job21b.submit();
            
            job21c = Job.getInstance(conf);
            job21c.setOutputKeyClass(IntWritable.class);
            job21c.setOutputValueClass(Text.class);
            job21c.setMapperClass(Comp3Mapper.class);
            job21c.setReducerClass(Comp3Reducer.class);
            job21c.setInputFormatClass(TextInputFormat.class);
            job21c.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job21c, new Path(ccinput));
            FileOutputFormat.setOutputPath(job21c, new Path(input+lid+"p2c"));
            job21c.setJarByClass(MridulMRCwith3.class);
            job21c.submit();
            
            while(!job21a.isComplete() && !job21b.isComplete() && !job21c.isComplete())
            {
            	Thread.sleep(100);
            }
            long end1 = new Date().getTime();
            System.out.println("Total time "+(end1-start1) + "milliseconds");
            
            int olid=lid;
            boolean ccstat=false;
            while(!ccstat)
            {
            //next 3 jobs find min value among parent of neighbors

            	//here we are copying data from P to another array OldP
            	//Also we are finding P' where
            	//P'(u)= P (min{P(u),min{P(v)|vertex v is adjacent to vertex u in G }}
            job211a = Job.getInstance(conf);
            job211a.setOutputKeyClass(IntWritable.class);
            job211a.setOutputValueClass(Text.class);
            job211a.setMapperClass(MinMapper.class);
            job211a.setReducerClass(MinReducer.class);
            job211a.setInputFormatClass(TextInputFormat.class);
            job211a.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job211a, new Path(input+olid+"p2a"));
            FileOutputFormat.setOutputPath(job211a, new Path(input+lid+"p2a1"));
            job211a.setJarByClass(MridulMRCwith3.class);
            job211a.submit();
            
            job211b = Job.getInstance(conf);
            job211b.setOutputKeyClass(IntWritable.class);
            job211b.setOutputValueClass(Text.class);
            job211b.setMapperClass(MinMapper.class);
            job211b.setReducerClass(Min2Reducer.class);
            job211b.setInputFormatClass(TextInputFormat.class);
            job211b.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job211b, new Path(input+olid+"p2b"));
            FileOutputFormat.setOutputPath(job211b, new Path(input+lid+"p2b1"));
            job211b.setJarByClass(MridulMRCwith3.class);
            job211b.submit();
            
            job211c = Job.getInstance(conf);
            job211c.setOutputKeyClass(IntWritable.class);
            job211c.setOutputValueClass(Text.class);
            job211c.setMapperClass(MinMapper.class);
            job211c.setReducerClass(Min3Reducer.class);
            job211c.setInputFormatClass(TextInputFormat.class);
            job211c.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job211c, new Path(input+olid+"p2c"));
            FileOutputFormat.setOutputPath(job211c, new Path(input+lid+"p2c1"));
            job211c.setJarByClass(MridulMRCwith3.class);
            job211c.submit();
            
            while(!job211a.isComplete() && !job211b.isComplete() && !job211c.isComplete())
            {
            	Thread.sleep(100);
            }
            
//            System.out.println("cc ids: After min reducer");
//            for(int i=0;i<l;i++)
//            {
//            	System.out.print(" "+i+":"+Comp1Reducer.P[i]);
//            }
//            for(int i=0;i<v-l;i++)
//            {
//            	System.out.print(" "+(i+l)+":"+Comp2Reducer.P[i]);
//            }
//            System.out.println();
//            System.out.println("Pdash:");
//            for(int i=0;i<l;i++)
//            {
//            	System.out.print(" "+i+":"+MinReducer.Pdash[i]);
//            }
//            for(int i=0;i<v-l;i++)
//            {
//            	System.out.print(" "+(i+l)+":"+Min2Reducer.Pdash[i]);
//            }
//            System.out.println();
//            System.out.println("P old:");
//            for(int i=0;i<l;i++)
//            {
//            	System.out.print(" "+i+":"+MinReducer.oldP[i]);
//            }
//            for(int i=0;i<v-l;i++)
//            {
//            	System.out.print(" "+(i+l)+":"+Min2Reducer.oldP[i]);
//            }
//            System.out.println();
            
            //Here we are doing tree jumping
        	//P (u) := min{P'(u),min{P'(v)|P (v) = u}}
            //we move to smallest parent of u or any v which earlier had u as a parent.
            job212 = Job.getInstance(conf);
            job212.setOutputKeyClass(IntWritable.class);
            job212.setOutputValueClass(Text.class);
            job212.setMapperClass(ParentMapper.class);
            job212.setReducerClass(ParentReducer.class);
            job212.setInputFormatClass(TextInputFormat.class);
            job212.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job212, new Path(input+lid+"p2a1"));
            FileOutputFormat.setOutputPath(job212, new Path(input+lid+"p2a2"));
            job212.setJarByClass(MridulMRCwith3.class);
            job212.submit();
            
            job212b = Job.getInstance(conf);
            job212b.setOutputKeyClass(IntWritable.class);
            job212b.setOutputValueClass(Text.class);
            job212b.setMapperClass(ParentMapper.class);
            job212b.setReducerClass(Parent2Reducer.class);
            job212b.setInputFormatClass(TextInputFormat.class);
            job212b.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job212b, new Path(input+lid+"p2b1"));
            FileOutputFormat.setOutputPath(job212b, new Path(input+lid+"p2b2"));
            job212b.setJarByClass(MridulMRCwith3.class);
            job212b.submit();
            
            job212c = Job.getInstance(conf);
            job212c.setOutputKeyClass(IntWritable.class);
            job212c.setOutputValueClass(Text.class);
            job212c.setMapperClass(ParentMapper.class);
            job212c.setReducerClass(Parent3Reducer.class);
            job212c.setInputFormatClass(TextInputFormat.class);
            job212c.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job212c, new Path(input+lid+"p2c1"));
            FileOutputFormat.setOutputPath(job212c, new Path(input+lid+"p2c2"));
            job212c.setJarByClass(MridulMRCwith3.class);
            job212c.submit();
            
            while(!job212.isComplete() && !job212b.isComplete() && !job212c.isComplete())
            {
            	Thread.sleep(100);
            }


            //we are finding parent using normal pointer jumping
            //P(u)=P(P(u))
            job213a = Job.getInstance(conf);
            job213a.setOutputKeyClass(IntWritable.class);
            job213a.setOutputValueClass(Text.class);
            job213a.setMapperClass(TreeParent1Mapper.class);
            job213a.setReducerClass(TreeParent1Reducer.class);
            job213a.setInputFormatClass(TextInputFormat.class);
            MultipleInputs.addInputPath(job213a, new Path(input+lid+"p2a2"),TextInputFormat.class);
            MultipleInputs.addInputPath(job213a, new Path(input+lid+"p2b2"),TextInputFormat.class);
            MultipleInputs.addInputPath(job213a, new Path(input+lid+"p2c2"),TextInputFormat.class);
            job213a.setOutputFormatClass(TextOutputFormat.class);
            FileOutputFormat.setOutputPath(job213a, new Path(input+lid+"p2a3"));
            job213a.setJarByClass(MridulMRCwith3.class);
            job213a.waitForCompletion(true);
            lid++;
           ccstat=TreeParent1Reducer.PequalOldP;
           TreeParent1Reducer.PequalOldP=true;

           
        }//end while of connected component
    
           //In this we are calculating number of connected components 
            //If no. of connected components==1 break no. of passes as all vertices are covered in MST
            job2a = Job.getInstance(conf);
            job2a.setOutputKeyClass(IntWritable.class);
            job2a.setOutputValueClass(Text.class);
            job2a.setMapperClass(CCMapper.class);
            job2a.setReducerClass(CCReducer.class);
            job2a.setInputFormatClass(TextInputFormat.class);
            job2a.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job2a, new Path(input+(lid-1)+"p2a3"));
            FileOutputFormat.setOutputPath(job2a, new Path(input+"cc"));
            job2a.setJarByClass(MridulMRCwith3.class);
            job2a.waitForCompletion(true);
        
            if(CCReducer.components==1)
            	{
            	status=1;
            	break;
            	}
            CCReducer.components=0;
            
            //In this job we are changing u and v of edge with its connected component id 
            //Also we drop cases when both u and v are in same component.
            job22 = Job.getInstance(conf);
            job22.setOutputKeyClass(IntWritable.class);
            job22.setOutputValueClass(Text.class);
            job22.setMapperClass(ChangeToCIdMapper.class);
            job22.setInputFormatClass(TextInputFormat.class);
            job22.setOutputFormatClass(TextOutputFormat.class);
            FileInputFormat.setInputPaths(job22, new Path(input+"o1"));
            FileOutputFormat.setOutputPath(job22, new Path(input+"o3"));
            job22.setJarByClass(MridulMRCwith3.class);
            job22.waitForCompletion(true);
             
            //In this we are finding min and max values for unmatched edges
             job4 = Job.getInstance(conf);
             job4.setOutputKeyClass(IntWritable.class);
             job4.setOutputValueClass(Text.class);
             job4.setMapperClass(OneMapper.class);
             job4.setReducerClass(OneReducer.class);
             job4.setInputFormatClass(TextInputFormat.class);
             job4.setOutputFormatClass(TextOutputFormat.class);
             FileInputFormat.setInputPaths(job4, new Path(input+"o3"));
             FileOutputFormat.setOutputPath(job4, new Path(input+"o5"));
             job4.setJarByClass(MridulMRCwith3.class);
             job4.waitForCompletion(true); 	
              loopid++;
             String input2=args[2]+loopid;
             
             //here we are changing min and max value from old input to newer min and max value found in previous step
             job5 = Job.getInstance(conf);
             job5.setOutputKeyClass(IntWritable.class);
             job5.setOutputValueClass(Text.class);
             job5.setMapperClass(NEdgeMapper.class);
             job5.setInputFormatClass(TextInputFormat.class);
             job5.setOutputFormatClass(TextOutputFormat.class);
             FileInputFormat.setInputPaths(job5, new Path(input+"o5"));
             FileOutputFormat.setOutputPath(job5, new Path(input2));
             job5.setJarByClass(MridulMRCwith3.class);
             job5.waitForCompletion(true); 
             
             input=args[2]+loopid;

        }
        //here we are removing duplicate edges from our output formed by joining matched edges from all passes.
        //Then we output these edgespublic static int[][] ver=new int[2*e][2];
        Job job6 = Job.getInstance(conf);
        job6.setOutputKeyClass(Text.class);
        job6.setOutputValueClass(Text.class);
        job6.setMapperClass(OutputMapper.class);
        job6.setReducerClass(OutputReducer.class);
        job6.setInputFormatClass(TextInputFormat.class);
        job6.setOutputFormatClass(TextOutputFormat.class);
        for(int i=0;i<loopid;i++)
        {
        	MultipleInputs.addInputPath(job6, new Path(args[2]+i+"o2"),TextInputFormat.class);
        }        
        FileOutputFormat.setOutputPath(job6, new Path(pathprefix+"final"));
        job6.setJarByClass(MridulMRCwith3.class);
        job6.waitForCompletion(true); 
        
        end = new Date().getTime();
        System.out.println("Total time "+(end-start) + "milliseconds");
        return 0;
        
    }
}