package gov.ssa.ocact.lr.population._2009;

import javax.servlet.http.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class Population  {

   private static final int TRYEAR = 2009;
   private static final int NUM_AGES = 101;   // 0 - 100+
   private static final int NUM_CLASSES = 2;  // male, female
   private static final int NUM_MAR_STATS = 4; // single, married, widowed, divorced
   private static final int NUM_ALTS = 14;   // hist, alts 1-3, sensitivities
   private static final int NUM_YEARS = 161;   // 1941 - 2101
   private static final int COMPOP[][][][][] = 
	   new int[NUM_AGES][NUM_CLASSES][NUM_MAR_STATS][NUM_ALTS][NUM_YEARS];
   
   static {

	   String filename[] = {"compop.hist",
			   "compop.alt1", "compop.alt2", "compop.alt3",
			   "compop.fer1", "compop.fer3",
			   "compop.imm1", "compop.imm3",
			   "compop.mor1", "compop.mor3",
			   "compop.mar1", "compop.mar3",
			   "compop.div1", "compop.div3"};

	   int startYear[] =   {0,
			   TRYEAR-1-1941,TRYEAR-1-1941,TRYEAR-1-1941,
			   TRYEAR-1-1941,TRYEAR-1-1941,
			   TRYEAR-1-1941,TRYEAR-1-1941,
			   TRYEAR-1-1941,TRYEAR-1-1941,
			   TRYEAR-1-1941,TRYEAR-1-1941,
			   TRYEAR-1-1941,TRYEAR-1-1941};

	   int endYear[] =     {TRYEAR-2-1941,
			   NUM_YEARS-1, NUM_YEARS-1, NUM_YEARS-1,
			   NUM_YEARS-1, NUM_YEARS-1,
			   NUM_YEARS-1, NUM_YEARS-1,
			   NUM_YEARS-1, NUM_YEARS-1,
			   NUM_YEARS-1, NUM_YEARS-1,
			   NUM_YEARS-1, NUM_YEARS-1};

	   for (int alt = 0; alt < NUM_ALTS; alt++)
	   {
		   PopulationPath pp = new PopulationPath();
		   String path = pp.getPath();
		   path = path.replace("%20", " ");
		   try {
			   RandomAccessFile compopfile = new RandomAccessFile(path + filename[alt], "r");
               FileChannel roChannel = compopfile.getChannel();
               ByteBuffer compopBuffer = roChannel.map(FileChannel.MapMode.READ_ONLY, 0, (int)roChannel.size());
               compopBuffer.order(ByteOrder.LITTLE_ENDIAN);
               
			   for (int year = startYear[alt]; year <= endYear[alt]; year++)
			   {
				   for (int marstat = 0; marstat < NUM_MAR_STATS; marstat++)
				   {
					   for (int sex = 0; sex < NUM_CLASSES; sex++)
					   {
						   for (int age = 0; age < NUM_AGES; age++)
						   {
							   COMPOP[age][sex][marstat][alt][year] = compopBuffer.getInt();
							   if (alt == 0)
							   {
								   for (int sens = 1; sens <= 13; sens++)
								   {
									   COMPOP[age][sex][marstat][sens][year] = 
										   COMPOP[age][sex][marstat][0][year];
								   }
							   }
						   }
					   }
				   }
			   }
			   compopfile.close();
		   }
		   catch (EOFException eof){
			   System.out.println( "EOF reached: " + filename[alt]);
		   }
		   catch (IOException ioe){
			   System.out.println( "IO error: " + filename[alt]);
		   }           
	   }
   }	   
	   
   
   
   private int[][][] pop = new int[101][2][4];
   private int[][][] npop = new int[101][2][4];
   private String month;
   private boolean alt1, alt2, alt3, fer1, fer3,
        imm1, imm3, mor1, mor3, mar1, mar3, div1, div3;
   private int step, begin_year, end_year;
   private int ageStep, begin_age, end_age;
   private int year, age, sex, marStat;
   private StringBuffer sb1 = new StringBuffer(120);
   private StringBuffer sb2 = new StringBuffer(120);
   private static final String SPACES = new String("          ");
   private PrintWriter out;
   private static final String[] ALTLABEL = {"Historical", "Alternative I", "Alternative II", "Alternative III",
                                    "Fertility Sensitivity I",  "Fertility Sensitivity III",
                                    "Immigration Sensitivity I",  "Immigration Sensitivity III",
                                    "Mortality Sensitivity I",  "Mortality Sensitivity III",
                                    "Marriage Sensitivity I",  "Marriage Sensitivity III",
                                    "Divorce Sensitivity I",  "Divorce Sensitivity III"};

   public Population (HttpServletRequest request,
                      PrintWriter out) {

         this.out = out;
         month = request.getParameter("MONTH");
         if (request.getParameter("ALT1") == null) alt1 = false;
         else alt1 = true;
         if (request.getParameter("ALT2") == null) alt2 = false;
         else alt2 = true;
         if (request.getParameter("ALT3") == null) alt3 = false;
         else alt3 = true;
         if (request.getParameter("FER1") == null) fer1 = false;
         else fer1 = true;
         if (request.getParameter("FER3") == null) fer3 = false;
         else fer3 = true;
         if (request.getParameter("IMM1") == null) imm1 = false;
         else imm1 = true;
         if (request.getParameter("IMM3") == null) imm3 = false;
         else imm3 = true;
         if (request.getParameter("MOR1") == null) mor1 = false;
         else mor1 = true;
         if (request.getParameter("MOR3") == null) mor3 = false;
         else mor3 = true;
         if (request.getParameter("MAR1") == null) mar1 = false;
         else mar1 = true;
         if (request.getParameter("MAR3") == null) mar3 = false;
         else mar3 = true;
         if (request.getParameter("DIV1") == null) div1 = false;
         else div1 = true;
         if (request.getParameter("DIV3") == null) div3 = false;
         else div3 = true;
         step = Integer.parseInt(request.getParameter("STEP"));                  
         begin_year = Integer.parseInt(request.getParameter("STACEN") +
              request.getParameter("STADEC")+ request.getParameter("STAYER"));
         end_year = Integer.parseInt(request.getParameter("ENDCEN") +
              request.getParameter("ENDDEC")+ request.getParameter("ENDYER"));
         begin_age = Integer.parseInt(request.getParameter("STARTAGE"));
         end_age = Integer.parseInt(request.getParameter("ENDAGE"));
         if (end_age > 100) {
            end_age = 100;
            out.println("Maximum age is 100.  Please go back and try again.");
         }
         ageStep = Integer.parseInt(request.getParameter("AGESTEP"));
   }
   
   public void run() {

	   out.println("<HTML><HEAD><TITLE>INTERACTIVE POPULATION PROGRAM</TITLE></HEAD><BODY><PRE>");                  
       if (alt1) mainLoop(1);
       if (alt2) mainLoop(2);
       if (alt3) mainLoop(3);
       if (fer1) mainLoop(4);
       if (fer3) mainLoop(5);
       if (imm1) mainLoop(6);
       if (imm3) mainLoop(7);
       if (mor1) mainLoop(8);
       if (mor3) mainLoop(9);
       if (mar1) mainLoop(10);
       if (mar3) mainLoop(11);
       if (div1) mainLoop(12);
       if (div3) mainLoop(13);
       out.println("</PRE></BODY></HTML>");                  
   }
   
   public void mainLoop(int alt) {

      for (year=begin_year; year<=end_year; year=year+step) {
         loadPop(alt);
         writeHeader(alt);
         for (age=begin_age; age<=end_age; age=age+ageStep) {
            writeChart(alt);
         }
         out.println(sb1.toString());
      }
   }

   public void loadPop(int alt) {

      for (int age=0; age < NUM_AGES; age++) {
    	  for (int sex=0; sex < NUM_CLASSES; sex++) {
    		  for (int marstat=0; marstat < NUM_MAR_STATS; marstat++) {
    		      pop[age][sex][marstat] = COMPOP[age][sex][marstat][alt][year-1941];
    		      npop[age][sex][marstat] = COMPOP[age][sex][marstat][alt][year-1941+1];    		      
    		  }
    	  }
      }

      if (month.equals("JUL")) {
         for (age=100; age>=0; age--) {
            for (sex=0; sex<=1; sex++) {
               for (marStat=0; marStat<=3; marStat++) {
                  if ( (age == 0) || (age >= 99) ) 
                     pop[age][sex][marStat] = Math.round(new Integer(pop[age][sex][marStat] + npop[age][sex][marStat]).floatValue()/2f);
                  else
                     pop[age][sex][marStat] = Math.round(new Integer(pop[age][sex][marStat] + pop[age-1][sex][marStat] +
                          npop[age][sex][marStat] + npop[age+1][sex][marStat]).floatValue()/4f);
               }
            }
         }
      }
      
      
   }


   public void writeHeader(int alt) {

      for (int i=0; i<120; i++) {
         if (sb1.length() < 120) sb1.append('-');
         if (sb2.length() < 120) sb2.append(' ');
      }

      String st1 = new String("Total    Single   Married   Widowed  Divorced");

      String line1 = new String("\f" + "Social Security Area Population as of J" + month.substring(1,3).toLowerCase() +
            ". 1, " + year + " under " + TRYEAR + " Trustees Report " + ALTLABEL[alt]);

      String line2 = new String(sb1.toString());

      String line3 = new String(sb2.substring(0,98) + "Sex and Marital Status");

      String line4 = new String(sb2.substring(0,15) + sb1.substring(0,105));

      String line5 = new String(sb2.substring(0,66) + "Male" + sb2.substring(0,44) + "Female");

      String line6 = new String(sb2.substring(0,25) + sb1.substring(0,45) + "     " + sb1.substring(0,45));

      String line7 = new String("       Age     Total     " + st1 + "     " + st1);

      String line8 = line2; 

      String header = new String(line1 + "\n" + line2 + "\n" + line3 + "\n" + line4 +
           "\n" + line5 + "\n" + line6 + "\n" + line7 + "\n" + line8);
 
      out.println(header);

   }

   public void writeChart(int alt) {

      int maxAge = age + ageStep;
      //if (maxAge > 101) maxAge=101;
      if (maxAge > end_age+1) maxAge=end_age+1;
      int prt[] = {0,0,0,0,0,0,0,0,0,0,0,0};
      for (int i=age; i<maxAge; i++) {
         prt[3] = prt[3] + pop[i][0][0];
         prt[4] = prt[4] + pop[i][0][1];
         prt[5] = prt[5] + pop[i][0][2];
         prt[6] = prt[6] + pop[i][0][3];
         prt[8] = prt[8] + pop[i][1][0];
         prt[9] = prt[9] + pop[i][1][1];
         prt[10] = prt[10] + pop[i][1][2];
         prt[11] = prt[11] + pop[i][1][3];
      }
      prt[2] = prt[2] + prt[3] + prt[4] + prt[5] +prt[6];
      prt[7] = prt[7] + prt[8] + prt[9] + prt[10] + prt[11];
      prt[1] = prt[1] + prt[2] + prt[7];
      
      StringBuffer sbout = new StringBuffer(120);
      String stemp;
      if (age+ageStep-1<100) stemp = (age + "-" + (maxAge-1));
      else stemp = (age + "-" + "100+");
      if (age==100) stemp = (age + "-" + "**");
      sbout.append(SPACES.substring(0,10-stemp.length()).concat(stemp));
      for (int i=1; i<12; i++) {
         stemp = String.valueOf(prt[i]);
         sbout.append(SPACES.substring(0,10-stemp.length()).concat(stemp)); 
      }
      out.println(sbout.toString());
   }
}
