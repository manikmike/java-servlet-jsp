package gov.ssa.ocact.lr.annuity._2009;

public class AnnuityPath {
	
   private String path;
	
   public AnnuityPath() {
		
	   String classPath = this.getClass().getResource("AnnuityPath.class").getPath();
	   this.path = classPath.replaceAll("AnnuityPath.class","");
      this.path = path.replace("%20", " ");
   }
	
   public String getPath() {
	   
	   return this.path;
   }

}
