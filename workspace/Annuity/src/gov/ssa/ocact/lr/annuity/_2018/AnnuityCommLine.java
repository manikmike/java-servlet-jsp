package gov.ssa.ocact.lr.annuity._2018;

import java.io.*;
import java.util.*;

public class AnnuityCommLine
{
   public static void main(String[] args)
   {
	   if (args.length != 2)
	   {
		   System.err.println("Error: Insufficient number of command line arguments.");
		   System.err.println("Usage: java -jar annuity.jar <inputFilename> <outputFilename>");
		   System.exit(-1);
	   }
	   try
	   {
		   CommandLineRequest request = getParameters(args);
		   PrintWriter out = new PrintWriter(new FileWriter(args[1]), true);
		   Annuity annuity = new Annuity(request, out);
		   annuity.run();
	   }
	   catch(Exception e)
	   {
		   e.printStackTrace();
	   }
   }
   
   private static CommandLineRequest getParameters(String[] args) throws FileNotFoundException, IOException
   {
	   CommandLineRequest request = new CommandLineRequest();
	   BufferedReader in = new BufferedReader(new FileReader(args[0]));
	   
	   request.setParameter("ALT", in.readLine().substring(40));
	   request.setParameter("TYPE", in.readLine().substring(40));
	   request.setParameter("ANT",  in.readLine().substring(40));
	   String line = in.readLine().substring(40);
	   String[] tokens = line.split("\\D");
	   request.setParameter("STEP", tokens[0]);
       request.setParameter("STACEN", "0");
       request.setParameter("STADEC", "0");
       request.setParameter("STAYER", tokens[1]);
       request.setParameter("ENDCEN", "0");
       request.setParameter("ENDDEC", "0");
       request.setParameter("ENDYER", tokens[2]);
       request.setParameter("INT", in.readLine().substring(40));
       line = in.readLine().substring(40);
       if (line.equals("T")) request.setParameter("MALE", "1");
       line = in.readLine().substring(40);
       if (line.equals("T")) request.setParameter("FEMALE", "1");
       line = in.readLine().substring(40);
       if (line.equals("T")) request.setParameter("UNISEX", "1");
       request.setParameter("BEGINAGE", in.readLine().substring(40));
       request.setParameter("ENDAGE", in.readLine().substring(40));
       line = in.readLine().substring(40);
       if (line.equals("1")) request.setParameter("FORMAT", "1");
       
       in.close();
	   return request;
   }
}

class CommandLineRequest
{
	HashMap<String, String> parameters = new HashMap<String, String>();
	
	public String getParameter(String parameter)
	{
		return parameters.get(parameter);
	}
	
	public void setParameter(String parameter, String value)
	{
		parameters.put(parameter, value);
	}
}