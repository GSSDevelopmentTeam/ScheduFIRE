package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * Servlet implementation class HomeCTServlet
 */
@WebServlet("/HomeCTServlet")
public class HomeCTServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
       
    /*
     * @see HttpServlet#HttpServlet()
     */
    public HomeCTServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

  /*
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  /*
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	if(!request.getSession().getAttribute("ruolo").equals("capoturno")) {
		response.sendRedirect("Login");
	}
	else {
	
    response.setContentType("text/html");
    request.getRequestDispatcher("JSP/HomeCT_JSP.jsp").forward(request, response);
	}
  }

}
