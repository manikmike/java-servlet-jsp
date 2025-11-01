package gov.ssa.ocact.lr.population._2017;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class PopulationServlet extends HttpServlet {
	
   public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {

	  response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      Population population = new Population(request, out);
      population.run();
	   
   }
   
   public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
      throws ServletException, IOException {
	   
	   response.sendRedirect("/Population/index2017.jsp");
   }

}
