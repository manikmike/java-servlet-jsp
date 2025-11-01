package gov.ssa.ocact.lr.annuity._2018;

import javax.servlet.http.*;

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;


public class Annuity {

   private static final int TRyear = 2018;
   private static final int nage = 149;
   private static final int nyea = 352;
   private static final int ndiff = 11;
   private static final int  nsex = 2;
   private static final int  nalt = 4;
   private static final boolean jarFile = false;
   
   private static final double sepFactor[][][] = new double[nsex][nyea][nalt];
   private static final double qximor[][][][] = new double[nage][nsex][nyea][nalt];
   
   static {

	   String filename[] = {"qxExactext.hist", "qxExactext.alt1",
			   "qxExactext.alt2", "qxExactext.alt3"};
	   int startYear[] = {0, TRyear-2-1900, TRyear-2-1900, TRyear-2-1900};
	   int endYear[] =   {TRyear-3-1900, nyea-1, nyea-1, nyea-1};

	   for (int alt = 0; alt < nalt; alt++)
	   {
		   try {
			   AnnuityPath ap = new AnnuityPath();
			   String path = ap.getPath();
			   BufferedReader qxifile;
			   if (jarFile)
			   {
			      InputStream is = ap.getClass().getResourceAsStream(filename[alt]);
			      qxifile = new BufferedReader(new InputStreamReader(is));
			   }
			   else
			   {
			      qxifile = new BufferedReader(new FileReader(path + filename[alt]));
			   }
		         String header = qxifile.readLine();

		         for (int year = startYear[alt]; year <= endYear[alt]; year++)
			   {
				   for (int sex = 0; sex < nsex; sex++)
				   {
				      String line = null;
				      StringTokenizer strtok = null;
                  if (1900+year <= 2251)
                  {
				         line = qxifile.readLine();
					      strtok = new StringTokenizer(line);
					      strtok.nextToken();   // year
					      strtok.nextToken();   // sex
                  }
					   for (int age = -1; age < nage; age++)
					   {
						   if (1900+year <= 2251)
						   {
      					   if (age == -1) sepFactor[sex][year][alt] = Double.parseDouble(strtok.nextToken());
      					   else qximor[age][sex][year][alt] = Double.parseDouble(strtok.nextToken());
						   }
						   else
						   {
						      if (age == -1) sepFactor[sex][year][alt] = sepFactor[sex][year-1][alt];
						      else qximor[age][sex][year][alt] = qximor[age][sex][year-1][alt];
						   }
					   }
				   }
			   }
			   qxifile.close();
		   }
           catch (EOFException eof){
		      System.out.println( "EOF reached: " + filename[alt]);
		   }
		   catch (IOException ioe){
		      System.out.println( "IO error: " + filename[alt]);
		   }
	   }

	   for (int year = startYear[0]; year <= endYear[0]; year++)
	   {
		   for (int age = -1; age < nage; age++)
		   {
			   for (int sex = 0; sex < nsex; sex++)
			   {
				   if (age == -1)
				   {
					   sepFactor[sex][year][1] = sepFactor[sex][year][0];
					   sepFactor[sex][year][2] = sepFactor[sex][year][0];
					   sepFactor[sex][year][3] = sepFactor[sex][year][0];
					   continue;
				   }
				   qximor[age][sex][year][1] = qximor[age][sex][year][0];
				   qximor[age][sex][year][2] = qximor[age][sex][year][0];
				   qximor[age][sex][year][3] = qximor[age][sex][year][0];
			   }
		   }
	   }
   }

   private int alt;
   private String alttag = new String();
   private String mort = new String();
   private String life = new String();
   private String sexname = new String();
   private String mafxs;
   private double [] mafx = new double [nage];
   private boolean male_class;
   private boolean female_class;
   private boolean unisex_class;
   private int OutputFormat;
   private int begin_age;
   private int end_age;
   private double rat;
   private int sex;
   private int age;
   private int begin_year;
   private int end_year;
   private int step;
   private double[] f0 =  new double[3];
   private int year, diff;
   private double[][] qx = new double[nage+19][3];
   private double[][] qprimex = new double[nage+19][3];
   private double[] lx = new double[nage];
   private double[] dx = new double[nage];
   private double[] lxm = new double[nage];
   private double[] lxf = new double[nage];
   private double[] lxu = new double[nage];
   private double[] ax = new double[nage];
   private double[] ex = new double[nage];
   private double[] Lx = new double[nage];
   private double[] Ax = new double[nage];
   private double[] Tx = new double[nage];
   private double[][] jntax = new double[nage][ndiff+1];
   private double[] Dx = new double[nage];
   private double[] Mx = new double[nage];
   private double[]  Nx = new double[nage];
   private String dashes = new String("----------------------------------------------------------------------" +
                              "-------------------------------------------");
   private String Spaces = new String("          ");
   private PrintWriter out;
   private boolean html = true;

   public Annuity (HttpServletRequest request,
                   PrintWriter out) {

         this.out = out;
         alttag = request.getParameter("ALT");
         alt = Integer.parseInt(alttag);
         mort = request.getParameter("TYPE");
         life = request.getParameter("ANT");
         step = Integer.parseInt(request.getParameter("STEP"));
         begin_year = Integer.parseInt(request.getParameter("STACEN") +
              request.getParameter("STADEC")+ request.getParameter("STAYER"));
         end_year = Integer.parseInt(request.getParameter("ENDCEN") +
              request.getParameter("ENDDEC")+ request.getParameter("ENDYER"));
         rat = Double.parseDouble(request.getParameter("INT"))/100;
         if (request.getParameter("MALE") == null) male_class = false;
         else male_class = true;
         if (request.getParameter("FEMALE") == null) female_class = false;
         else female_class = true;
         if (request.getParameter("UNISEX") == null) unisex_class = false;
         else unisex_class = true;
         begin_age = Integer.parseInt(request.getParameter("BEGINAGE"));
         end_age = Integer.parseInt(request.getParameter("ENDAGE"));
         if (request.getParameter("FORMAT") == null) OutputFormat =0;
         else OutputFormat = 1;
   }
   
	public Annuity(CommandLineRequest request, PrintWriter out) {

        this.out = out;
        alttag = request.getParameter("ALT");
        alt = Integer.parseInt(alttag);
        mort = request.getParameter("TYPE");
        life = request.getParameter("ANT");
        step = Integer.parseInt(request.getParameter("STEP"));
        begin_year = Integer.parseInt(request.getParameter("STACEN") +
             request.getParameter("STADEC")+ request.getParameter("STAYER"));
        end_year = Integer.parseInt(request.getParameter("ENDCEN") +
             request.getParameter("ENDDEC")+ request.getParameter("ENDYER"));
        rat = Double.parseDouble(request.getParameter("INT"))/100;
        if (request.getParameter("MALE") == null) male_class = false;
        else male_class = true;
        if (request.getParameter("FEMALE") == null) female_class = false;
        else female_class = true;
        if (request.getParameter("UNISEX") == null) unisex_class = false;
        else unisex_class = true;
        begin_age = Integer.parseInt(request.getParameter("BEGINAGE"));
        end_age = Integer.parseInt(request.getParameter("ENDAGE"));
        if (request.getParameter("FORMAT") == null) OutputFormat =0;
        else OutputFormat = 1;
        html = false;
	}
   
   public void run() {

	   this.annuity();
   }

   public void annuity () {
      if (html) out.println("<HTML><HEAD><TITLE>INTERACTIVE LIFE TABLE AND ANNUITY PROGRAM</TITLE></HEAD><BODY><PRE>");
      for (year=begin_year; year<=end_year; year=year+step){
         if (year-1900 > nyea-1) break;
         this.lifeTable();
         if (life.equals("JOI") | life.equals("BOT")) 
            for (sex=0; sex<=2; sex++){
               this.jointAnnuity();
            }
      }
      if (html) out.println("</PRE></BODY></HTML>");      
   }

   void lifeTable () {
	   this.qx = this.readQx(this.qx);
	   this.qx = this.unisex(this.qx);
      for (sex=0; sex <=2; sex++){
         this.constructLifeTable();
      }
   }

   double[][] readQx (double q[][]) {

      int yr = year - 1900;
      int firstYear  = yr;
      if (yr < 0) yr = 0;
      if (yr > nyea-1) yr = nyea-1;      
      f0[0] = sepFactor[0][yr][alt];
	  f0[1] = sepFactor[1][yr][alt];    
      
	  q[0][0] = qximor[0][0][yr][alt];
      q[0][1] = qximor[0][1][yr][alt];
      
      int cohortYr = firstYear;
      for (age=1; age<=nage-1; age++) {
         if (mort.equals("COH")){
        	cohortYr = firstYear + age;
            if (cohortYr < 0) cohortYr = 0;
            if (cohortYr > nyea-1) cohortYr = nyea-1;      
            q[age][0] = qximor[age][0][cohortYr][alt];
            q[age][1] = qximor[age][1][cohortYr][alt];
         }
         else {
            q[age][0] = qximor[age][0][yr][alt];
            q[age][1] = qximor[age][1][yr][alt];
         }

      }
      return q;
   }

   void constructLifeTable(){
      lx[0] = 100000;

      for (age=0; age<=nage-1; age++){
         dx[age] = lx[age] * qx[age][sex];
         if (age+1 <= nage-1) lx[age+1] = lx[age] * (1.0 - qx[age][sex]);
         if (age == 0) Lx[age] = lx[age] - dx[age] * f0[sex];
         if (age > 0) Lx[age] = lx[age] - dx[age] * 0.5;
      }

      Tx[nage-1] = Lx[nage-1];

      for (age=nage-2; age>=0; age=age-1){
         Tx[age] = Tx[age+1] + Lx[age];
         if (lx[age] > 0)
            ex[age] = Tx[age]/lx[age];
         else
            ex[age] = 0;
      }

      ex[nage-1] = 0;

      Dx[0] = (double) lx[0];

      for (age=1; age<=nage-1; age++){
         Dx[age] = (double) (lx[age]*Math.pow((1.0/(1.0+rat)),age));
      }

      Nx[nage-1] = Dx[nage-1];
      Mx[nage-1] = Nx[nage-1]/(double) (1.0 + rat);
      
      for (age=nage-2; age>=0; age=age-1){
         Nx[age] = Nx[age+1] + Dx[age];
         Mx[age] = Nx[age]/(double)(1.0+rat) - Nx[age+1];
         if (Dx[age] > 0){
            ax[age] = (Nx[age] / Dx[age]);
            Ax[age] = (Mx[age] / Dx[age]);
            mafx[age] = (12.0*(ax[age]-11.0/24.0));
         }
         else{
            ax[age] = 0;
            Ax[age] = 0;
            mafx[age] = 0;            
         }
      }

      ax[nage-1] = 0;
      Ax[nage-1] = 0;
      mafx[nage-1] = 0;
      
      if ((life.equals("IND")) | (life.equals("BOT"))) this.printLifeTable();
   }

   double[][] unisex(double q[][]){
      f0[2] = ( 1.05 * f0[0] + 1.00 * f0[1] ) / 2.05;
      lxm[0] = 105000;
      lxf[0] = 100000;
      lxu[0] = lxm[0] + lxf[0];

      for (age=1; age<=nage-1; age++){
         if (lxm[age-1] > 0){
            lxm[age] = lxm[age-1] - lxm[age-1] * q[age-1][0];
         }
         else
            lxm[age] = 0;
         if (lxf[age-1] > 0)
            lxf[age] = lxf[age-1] - lxf[age-1] * q[age-1][1];
         else
            lxf[age] = 0;
         lxu[age] = lxm[age] + lxf[age];
         if (lxu[age] > 0)
            q[age-1][2] = 1 - lxu[age]/lxu[age-1];
         else
            q[age-1][2] = 1.0;
      }
      return q;
   }

   void printLifeTable(){
      for (age=begin_age; age<=end_age; age++){
         switch (sex){
            case 0:
               sexname = "males";
               break;
            case 1:
               sexname = "females";
               break;
            case 2:
               sexname = "unisex";
          }



      if ( (OutputFormat == 1) && (((age-begin_age+1)%60 ==1) || (age == begin_age)) ){
         switch (mort.charAt(0)){
            case 'P':
               if ((sex == 0 & male_class == true) |
                  (sex == 1 & female_class == true) |
                  (sex == 2 & unisex_class == true)) this.printPeriodHeader('S');
                  break; 
           case 'C':
               if ((sex == 0 & male_class == true) |
                  (sex == 1 & female_class == true) |
                  (sex == 2 & unisex_class == true)) this.printCohortHeader('S');
         }
      }
      if ((sex == 0 & male_class == true) |
          (sex == 1 & female_class == true) |
          (sex == 2 & unisex_class == true)) this.printTableData('S');
      }

   }

   void jointAnnuity(){

         for (int idiff=1; idiff<=ndiff; idiff++){
            diff = (idiff-1)*2;
            lx[19] = 100000;
            int temp = year;
            if (mort.equals("COH")) year = year - diff;
            this.qprimex = this.readQx(this.qprimex);
            this.qprimex = this.unisex(this.qprimex);
            year = temp;
            for (age=20; age<=nage-1; age++){
               if (sex == 0) lx[age] = lx[age-1] * (1-qx[age-1][0]) * (1-qprimex[age-1+diff][1]);
               if (sex == 1) lx[age] = lx[age-1] * (1-qprimex[age-1+diff][0]) * (1-qx[age-1][1]);
               if (sex == 2) lx[age] = lx[age-1] * (1-qx[age-1][2]) *(1-qprimex[age-1+diff][2]);
               Dx[age] = (double) lx[age] * (1+rat)/Math.pow((1+rat),age);
            }
            Nx[nage-1] = Dx[nage-1];
            for (age=nage-2; age>=20; age=age-1){
               Nx[age] = Nx[age+1] + Dx[age];
               if (Dx[age] > 0) {
                  jntax[age][idiff] = Nx[age]/Dx[age];
               }
               else jntax[age][idiff] = 0;
            }
         }
      if ((begin_age >= 20)&&(end_age <=99))
         for (age=begin_age; age<=end_age; age++) this.printJointAnnuity();
      else for (age=20; age<=99; age++) this.printJointAnnuity();
      
   }

   void printJointAnnuity(){
      switch (sex){
         case 0:
            sexname="male:female";
            break;
         case 1:
            sexname="female:male";
            break;
         case 2:
            sexname="unisex:unisex";

      }
      if ( (OutputFormat == 1) && ((age == begin_age) || (age == 20)) ){
         switch (mort.charAt(0)){
            case 'P':
               if ((sex == 0 & male_class == true) |
                  (sex == 1 & female_class == true)|
                  (sex == 2 & unisex_class == true)) this.printPeriodHeader('J');
                  break; 
            case 'C':
               if ((sex == 0 & male_class == true) |
                  (sex == 1 & female_class == true)|
                  (sex == 2 & unisex_class == true)) this.printCohortHeader('J');
         }
       }
       if ((sex == 0 & male_class == true) |
          (sex == 1 & female_class == true)|
          (sex == 2 & unisex_class == true)) this.printTableData('J');
   }

   void printPeriodHeader (char tableType){
      switch (tableType){
         case 'S':
            out.println("\f" + "United States life table functions and actuarial functions at "+
                               (double) Math.round(rat*100000000)/1000000 + " percent interest for " + sexname + " in calendar year " + year +
                               "\nbased on the Alternative " + alttag + " mortality probabilities used in the " +
                                TRyear + " Trustees Report");
            
            out.println(dashes);
            out.println("                                                 o                                          ..        .. (12)");
            out.println("  x      q(x)    l(x)   d(x)   L(x)       T(x)   e(x)   D(x)   M(x)    A(x)       N(x)      a(x)    12a(x)");
            out.println(dashes);
            break;

         case 'J':
            out.println("\f" + "Present Values of United States " + sexname + " joint-life annuities at " +
                               (double) Math.round(rat*100000000)/1000000 + " percent interest in calendar year " + year +
                               "\nbased on the Alternative " + alttag + " mortality probabilities used in the " +
                                TRyear + " Trustees Report");
            
            out.println(dashes);
            out.println("       ..      ..        ..        ..        ..       ..        ..        ..        ..        ..        ..       ");
            out.println("  x    a(x:x)  a(x:x+2)  a(x:x+4)  a(x:x+6)  a(x:x+8) a(x:x+10) a(x:x+12) a(x:x+14) a(x:x+16) a(x:x+18) a(x:x+20)");
            out.println(dashes);
      }

   }

   void printCohortHeader (char tableType){
      switch (tableType){
         case 'S':
            out.println("\f" + "United States life table functions and actuarial functions at "+
                               (double) Math.round(rat*100000000)/1000000 + " percent interest for " + sexname + " born in " + year +
                               "\nbased on the Alternative " + alttag + " mortality probabilities used in the " +
                                TRyear + " Trustees Report\n" + "(assuming constant mortality before 1900 and after 2251)");
            
            out.println(dashes);
            out.println("                                                 o                                          ..        .. (12)");
            out.println("  x      q(x)    l(x)   d(x)   L(x)       T(x)   e(x)   D(x)   M(x)    A(x)       N(x)      a(x)    12a(x)");
            out.println(dashes);
            break;

         case 'J':
            out.println("\f" + "Present Values of United States " + sexname + " joint-life annuities at " +
                               (double)Math.round(rat*100000000)/1000000 + " percent interest - (x) born in year " + year +
                               "\nbased on the Alternative " + alttag + " mortality probabilities used in the " +
                                TRyear + " Trustees Report");
            
            out.println(dashes);
            out.println("       ..      ..        ..        ..        ..       ..        ..        ..        ..        ..        ..       ");
            out.println("  x    a(x:x)  a(x:x+2)  a(x:x+4)  a(x:x+6)  a(x:x+8) a(x:x+10) a(x:x+12) a(x:x+14) a(x:x+16) a(x:x+18) a(x:x+20)");
            out.println(dashes);
      }
   }

   void printTableData (char tableType){
      switch (tableType){
         case 'S':
            DecimalFormat qs = new DecimalFormat("0.000000");
            String years;
            years = Spaces.substring(0,(4-String.valueOf(year).length())).concat(String.valueOf(year));
            String sexes;
            // Program stores the 3 sexes as 0, 1, or 2.  But we want to print 1, 2, or 3. Thus the "+1"
            sexes = Spaces.substring(0,(4-String.valueOf(sex).length())).concat(String.valueOf(sex + 1));
            String ages;
            ages = Spaces.substring(0,(4-String.valueOf(age).length())).concat(String.valueOf(age));
            String lxs;
            lxs = Spaces.substring(0,(6-String.valueOf(Math.round(lx[age])).length())).concat(String.valueOf(Math.round(lx[age])));
            String dxs;
            dxs = Spaces.substring(0,(6-String.valueOf(Math.round(dx[age])).length())).concat(String.valueOf(Math.round(dx[age])));
            String Lxs;
            Lxs = Spaces.substring(0,(6-String.valueOf(Math.round(Lx[age])).length())).concat(String.valueOf(Math.round(Lx[age])));
            String Txs;
            Txs = Spaces.substring(0,(10-String.valueOf(Math.round(Tx[age])).length())).concat(String.valueOf(Math.round(Tx[age])));
            String exs;
            DecimalFormat es = new DecimalFormat("##0.00");
            exs = Spaces.substring(0,(6-String.valueOf(es.format(ex[age])).length())).concat(String.valueOf(es.format(ex[age])));
            String Dxs;
            Dxs = Spaces.substring(0,(6-String.valueOf(Math.round(Dx[age])).length())).concat(String.valueOf(Math.round(Dx[age])));
            String Mxs;
            Mxs = Spaces.substring(0,(6-String.valueOf(Math.round(Mx[age])).length())).concat(String.valueOf(Math.round(Mx[age])));
            DecimalFormat As = new DecimalFormat("0.0000");
            String Axs;
            Axs = Spaces.substring(0,(6-String.valueOf(As.format(Ax[age])).length())).concat(String.valueOf(As.format(Ax[age])));
            String Nxs;
            Nxs = Spaces.substring(0,(10-String.valueOf(Math.round(Nx[age])).length())).concat(String.valueOf(Math.round(Nx[age])));
            DecimalFormat as = new DecimalFormat("#0.0000");
            String axs;
            axs = Spaces.substring(0,(9-String.valueOf(as.format(ax[age])).length())).concat(String.valueOf(as.format(ax[age])));
            DecimalFormat mafs = new DecimalFormat("##0.00");
            mafxs = Spaces.substring(0,(9-String.valueOf(mafs.format(mafx[age])).length())).concat(String.valueOf(mafs.format(mafx[age])));
            if (OutputFormat == 1)
               out.println(ages + "  " + qs.format(qx[age][sex]) + "  " + lxs +
                    " " + dxs + " " + Lxs + " " + Txs + " " + exs + " " + Dxs + " " +
                    Mxs + "  " + Axs + " " + Nxs + " " + axs + " " + mafxs);
            else
               out.println(years + sexes + ages + "  " + qs.format(qx[age][sex]) + "  " + lxs +
                    " " + dxs + " " + Lxs + " " + Txs + " " + exs + " " + Dxs + " " +
                    Mxs + "  " + Axs + " " + Nxs + " " + axs + " " + mafxs); 
            if (OutputFormat == 1 && (age-begin_age+1)%5 == 0 && (age-begin_age+1)%60 != 0)
               out.println();
            if (OutputFormat == 1 && ((age-begin_age+1)%60 == 0 || age == end_age)){
               out.println(dashes);
               if (sex == 2){
                     out.println("Each unisex l(x) value is a weighted average of the male and female l(x) values,\n" +
                                  "with weights 1.05 and 1.00, respectively.\n" +
                                  "All other unisex life table values are calculated from the unisex l(x) values.");
                     out.println(dashes);
               }
            }
            break;
         case 'J':
            DecimalFormat jntas = new DecimalFormat("#0.0000");
            years = Spaces.substring(0,(4-String.valueOf(year).length())).concat(String.valueOf(year));
            // Program stores the 3 sexes as 0, 1, or 2 (for M, F, U). For M, we want to print 1:2;
            // for F, we want to print 2:1; and for U, we want to print 3:3. This formula does the trick.
            sexes = " " + String.valueOf(sex + 1) + ":" + String.valueOf(1 + ((7 - sex) % 3));
            ages = Spaces.substring(0,(4-String.valueOf(age).length())).concat(String.valueOf(age));
            String lineOfOutput = new String(String.valueOf(ages));
            // If no headers, then put the year and sex on the line, before the age.
            if (OutputFormat == 0) {
            	lineOfOutput = String.valueOf(years + sexes) + lineOfOutput;
            }
            String jntaxs;
            for (int idiff=1; idiff<=ndiff; idiff++){
               jntaxs = Spaces.substring(0,(7-String.valueOf(jntas.format(jntax[age][idiff])).length())).concat(String.valueOf(jntas.format(jntax[age][idiff])));
               lineOfOutput = lineOfOutput.concat("   " + jntaxs);
            }

            out.println(lineOfOutput);

            if (OutputFormat == 1) {
            	if ((age-begin_age+1)%5 == 0 && age+1 != 100 ) out.println();
            }
            if (OutputFormat == 1) {
            	if ((age) == end_age) out.println(dashes);      
            }
      }
   }

}