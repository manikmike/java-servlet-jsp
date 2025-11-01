package gov.ssa.ocact.lr.population._2016;

public class PopulationPath {
	
   private String path;
	
   public PopulationPath() {
		
	   String classPath = this.getClass().getResource("PopulationPath.class").getPath();
	   this.path = classPath.replaceAll("PopulationPath.class","");
      this.path = this.path.replace("%20", " ");
   }
	
   public String getPath() {
	   
	   return this.path;
   }

}