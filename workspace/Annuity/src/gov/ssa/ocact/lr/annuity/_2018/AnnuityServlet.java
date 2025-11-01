package gov.ssa.ocact.lr.annuity._2018;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AnnuityServlet extends HttpServlet {
	
   public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {

	  response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      Annuity annuity = new Annuity(request, out);
      annuity.run();
	   
   }
   
   public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
      throws ServletException, IOException {
	   
	   response.sendRedirect("/Annuity/index2018.jsp");
   }

}