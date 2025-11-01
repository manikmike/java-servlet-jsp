package gov.ssa.ocact.lr.population._2025;

import javax.servlet.http.*;
import java.io.*;
import java.util.StringTokenizer;

public class Population  {

   private static final int TRYEAR = 2025;
   private static final int NUM_AGES = 101;   // 0 - 100+
   private static final int NUM_CLASSES = 2;  // male, female
   private static final int NUM_MAR_STATS = 4; // single, married, widowed, divorced
   private static final int NUM_ALTS = 28;   // hist, alts 1-3, sensitivities
   private static final int NUM_YEARS = 166;   // 1941 - 2106
   private static final int COMPOP[][][][][] =
       new int[NUM_AGES][NUM_CLASSES][NUM_MAR_STATS][NUM_ALTS][NUM_YEARS];
   private static final int POPJUL[][][][][] =
       new int[NUM_AGES][NUM_CLASSES][NUM_MAR_STATS][NUM_ALTS][NUM_YEARS];
   private static boolean DATA_FILES_READ = false;

   private synchronized boolean readDataFiles() {

       if (DATA_FILES_READ) return true;

       boolean rv = true;
       String filename[] = {"PopDecMS.hist",
               "PopDecMS.alt1", "PopDecMS.alt2", "PopDecMS.alt3",
               "PopDecMS.fer1", "PopDecMS.fer3",
               "PopDecMS.imm1", "PopDecMS.imm3",
               "PopDecMS.mor1", "PopDecMS.mor3",
               "PopDecMS.mar1", "PopDecMS.mar3",
               "PopDecMS.div1", "PopDecMS.div3",
               "PopJulMS.hist",
               "PopJulMS.alt1", "PopJulMS.alt2", "PopJulMS.alt3",
               "PopJulMS.fer1", "PopJulMS.fer3",
               "PopJulMS.imm1", "PopJulMS.imm3",
               "PopJulMS.mor1", "PopJulMS.mor3",
               "PopJulMS.mar1", "PopJulMS.mar3",
               "PopJulMS.div1", "PopJulMS.div3"};

       int startYear[] =   {0,
    		   TRYEAR-1-1941,TRYEAR-1-1941,TRYEAR-1-1941,
    		   TRYEAR-1-1941,TRYEAR-1-1941,
    		   TRYEAR-1-1941,TRYEAR-1-1941,
    		   TRYEAR-1-1941,TRYEAR-1-1941,
    		   TRYEAR-1-1941,TRYEAR-1-1941,
    		   TRYEAR-1-1941,TRYEAR-1-1941,
               0,
               TRYEAR-2-1941,TRYEAR-2-1941,TRYEAR-2-1941,
               TRYEAR-2-1941,TRYEAR-2-1941,
               TRYEAR-2-1941,TRYEAR-2-1941,
               TRYEAR-2-1941,TRYEAR-2-1941,
               TRYEAR-2-1941,TRYEAR-2-1941,
               TRYEAR-2-1941,TRYEAR-2-1941};

       int endYear[] =     {TRYEAR-2-1941,
               NUM_YEARS-1, NUM_YEARS-1, NUM_YEARS-1,
               NUM_YEARS-1, NUM_YEARS-1,
               NUM_YEARS-1, NUM_YEARS-1,
               NUM_YEARS-1, NUM_YEARS-1,
               NUM_YEARS-1, NUM_YEARS-1,
               NUM_YEARS-1, NUM_YEARS-1,
               TRYEAR-3-1941,
               NUM_YEARS-2, NUM_YEARS-2, NUM_YEARS-2,
               NUM_YEARS-2, NUM_YEARS-2,
               NUM_YEARS-2, NUM_YEARS-2,
               NUM_YEARS-2, NUM_YEARS-2,
               NUM_YEARS-2, NUM_YEARS-2,
               NUM_YEARS-2, NUM_YEARS-2};

       for (int alt = 0; alt < NUM_ALTS; alt++)
       {
           PopulationPath pp = new PopulationPath();
           String path = pp.getPath();
           path = path.replace("%20", " ");
           if (alt < NUM_ALTS/2)
           {
           try {
              BufferedReader compopfile = new BufferedReader(new FileReader(path + filename[alt]));

              String header = compopfile.readLine();

              for (int year = startYear[alt]; year <= endYear[alt]; year++)
              {
                 for (int age = 0; age < NUM_AGES; age++)
                 {
                    String line = compopfile.readLine();
                    StringTokenizer strtok = new StringTokenizer(line);
                    strtok.nextToken();   // year
                    strtok.nextToken();   // age
                    strtok.nextToken();   // total
                    for (int sex = 0; sex < NUM_CLASSES; sex++)
                    {
                       strtok.nextToken();   // male or female total
                       for (int marstat = 0; marstat < NUM_MAR_STATS; marstat++)
                       {
                          COMPOP[age][sex][marstat][alt][year] = Integer.parseInt(strtok.nextToken());
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
               out.println( "EOF reached: " + filename[alt]);
               rv = false;
           }
           catch (FileNotFoundException fnf){
               out.println( "File not found: " + filename[alt]);
               rv = false;
           }
           catch (IOException ioe){
               out.println( "IO error: " + filename[alt]);
               rv = false;
           }

       }   else
       {
           try {
              BufferedReader popjulfile = new BufferedReader(new FileReader(path + filename[alt]));

              String header = popjulfile.readLine();

              for (int year = startYear[alt]; year <= endYear[alt]; year++)
              {
                 for (int age = 0; age < NUM_AGES; age++)
                 {
                    String line = popjulfile.readLine();

                    StringTokenizer strtok = new StringTokenizer(line);
                    strtok.nextToken();   // year
                    strtok.nextToken();   // age
                    strtok.nextToken();   // total

                    for (int sex = 0; sex < NUM_CLASSES; sex++)
                    {
                       strtok.nextToken();   // male or female total
                       for (int marstat = 0; marstat < NUM_MAR_STATS; marstat++)
                       {
                          POPJUL[age][sex][marstat][alt][year] = Integer.parseInt(strtok.nextToken());
                          if (alt == 14)
                          {
                             for (int sens = 15; sens <= 27; sens++)
                             {
                                POPJUL[age][sex][marstat][sens][year] =
                                    POPJUL[age][sex][marstat][14][year];
                             }
                          }
                       }
                    }
                 }
              }
              popjulfile.close();
           }

           catch (EOFException eof){
               out.println( "EOF reached: " + filename[alt]);
               rv = false;
           }
           catch (FileNotFoundException fnf){
               out.println( "File not found: " + filename[alt]);
               rv = false;
           }
           catch (IOException ioe){
               out.println( "IO error: " + filename[alt]);
               rv = false;
           }
       }
           }
          return rv;
       }


   private int[][][] pop = new int[101][2][4];
   private int[][][] npop = new int[101][2][4];
   private String month;
   private boolean alt1, alt2, alt3, fer1, fer3,
        imm1, imm3, mor1, mor3, mar1, mar3, div1, div3;
   private int step, begin_year, end_year;
   private int ageStep, begin_age, end_age;
   private int year, age, sex, marStat;
   private int outputFormat;
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
         DATA_FILES_READ = this.readDataFiles();

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
         if (request.getParameter("FORMAT") == null) outputFormat = 0;
         else outputFormat = 1;
         step = Integer.parseInt(request.getParameter("STEP"));
         begin_year = Integer.parseInt(request.getParameter("STACEN") +
              request.getParameter("STADEC")+ request.getParameter("STAYER"));
         end_year = Integer.parseInt(request.getParameter("ENDCEN") +
              request.getParameter("ENDDEC")+ request.getParameter("ENDYER"));
         begin_age = Integer.parseInt(request.getParameter("STARTAGE"));
         end_age = Integer.parseInt(request.getParameter("ENDAGE"));
         if (end_age > 100) {
            end_age = 100;
            String title = "Maximum age is 100.  Please go back and try again.";
                out.println("<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
                 "<H1>" + title + "</H1>");
         }
         ageStep = Integer.parseInt(request.getParameter("AGESTEP"));
         if (month.equals("DEC")) {
             if (begin_year < 1940) {
                 begin_year = 1940;
                 String title = "Minimum year is 1940.  Please go back and try again.";
                 out.println("<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
                         "<H1>" + title + "</H1>");
             }
         }
         if (month.equals("JAN") || month.equals("JUL")) {
             if (begin_year < 1941) {
                 begin_year = 1941;
                 String title = "Minimum year is 1941.  Please go back and try again.";
                 out.println("<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
                         "<H1>" + title + "</H1>");
                 }
         }
         if (month.equals("DEC") || month.equals("JUL")) {
             if (end_year >= 2106) {
                 end_year = 2105;
                 String title = "Maximum year is 2105.  Please go back and try again.";
                 out.println("<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
                         "<H1>" + title + "</H1>");
                 }
         }
         if (month.equals("JAN")) {
             if (end_year > 2106) {
                 end_year = 2106;
                 String title = "Maximum year is 2106.  Please go back and try again.";
                 out.println("<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
                         "<H1>" + title + "</H1>");
             }
         }
   }

   public void run() {

       if (DATA_FILES_READ)
       {
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
   }

   public void mainLoop(int alt) {

      for (year=begin_year; year<=end_year; year=year+step) {
          loadPop(alt);
          if (outputFormat == 1) writeHeader(alt);
         for (age=begin_age; age<=end_age; age=age+ageStep) {
            writeChart(alt);
         }
         if (outputFormat == 1) out.println(sb1.toString());
      }
    }

   public void loadPop(int alt) {
      for (int age=0; age < NUM_AGES; age++) {
          for (int sex=0; sex < NUM_CLASSES; sex++) {
              for (int marstat=0; marstat < NUM_MAR_STATS; marstat++) {
                  if ((month.equals("DEC")) && (year == 1940)){
                      pop[age][sex][marstat] = COMPOP[age][sex][marstat][alt][0];
                      npop[age][sex][marstat] = COMPOP[age][sex][marstat][alt][0];
                  } else {
                      pop[age][sex][marstat] = COMPOP[age][sex][marstat][alt][year-1941];
                        if (year == 2106)
                            npop[age][sex][marstat] = COMPOP[age][sex][marstat][alt][year-1941];
                        else
                            npop[age][sex][marstat] = COMPOP[age][sex][marstat][alt][year-1941+1];
                  }
              }
          }
      }

      if (month.equals("JUL")) {
      for (int age=0; age < NUM_AGES; age++) {
          for (int sex=0; sex < NUM_CLASSES; sex++) {
              for (int marstat=0; marstat < NUM_MAR_STATS; marstat++) {
                      pop[age][sex][marstat] = POPJUL[age][sex][marstat][alt+14][year-1941];
              }
          }
      }
   }

      if (month.equals("DEC")) {
      for (int age=0; age < NUM_AGES; age++) {
          for (int sex=0; sex < NUM_CLASSES; sex++) {
              for (int marstat=0; marstat < NUM_MAR_STATS; marstat++) {
                  pop[age][sex][marstat] = npop[age][sex][marstat];
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

      String line1 = new String("\f" + "Social Security Area Population as of " +
              month.substring(0,1).toUpperCase() + month.substring(1,3).toLowerCase()
              + ". 1, " + year + " under " + TRYEAR + " Trustees Report " + ALTLABEL[alt]);

      if (month.equals("DEC"))
          line1 = new String("\f" + "Social Security Area Population as of " +
                  month.substring(0,1).toUpperCase() + month.substring(1,3).toLowerCase()
                  + ". 31, " + year + " under " + TRYEAR + " Trustees Report " + ALTLABEL[alt]);

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
      if (age+ageStep-1<100 && maxAge<=10) stemp = (age + "- " + (maxAge-1));
      else if (age+ageStep-1<100) stemp = (age + "-" + (maxAge-1));
      else stemp = (age + "-" + "**");
      //else stemp = (age + "-" + "100+");
      //if (age==100) stemp = (age + "-" + "**");
      sbout.append(SPACES.substring(0,10-stemp.length()).concat(stemp));
      for (int i=1; i<12; i++) {
         stemp = String.valueOf(prt[i]);
         sbout.append(SPACES.substring(0,10-stemp.length()).concat(stemp));
      }
      out.println(sbout.toString());
   }
}
