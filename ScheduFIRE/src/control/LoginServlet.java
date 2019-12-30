package control;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.CredenzialiBean;
import model.dao.UserDao;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		if (username == null || username.equals(""))
			request.getRequestDispatcher("/JSP/LoginJSP.jsp").forward(request, response);
		else {
			String passwordBase64format = Base64.getEncoder().encodeToString(password.getBytes());

			UserDao credenziali = new UserDao();
			CredenzialiBean utente = credenziali.login(username);

			if (utente == null) {
				request.setAttribute("usernameErrato", true);
				request.getRequestDispatcher("/JSP/LoginJSP.jsp").forward(request, response);
				} else {

				if (passwordBase64format.equals(utente.getPassword())) {

					HttpSession session = request.getSession();
					session.setAttribute("ruolo", utente.getRuolo());
					if (utente.getRuolo().equalsIgnoreCase("capoturno")) {
						response.sendRedirect("HomeCTServlet");
						//RequestDispatcher dispatcher = request.getRequestDispatcher("WebContent\\JSP\\LoginJSP.jsp");
						//dispatcher.forward(request, response);
					} else {
						response.sendRedirect("CalendarioServlet");
						//RequestDispatcher dispatcher = request.getRequestDispatcher("/CalendarioServlet");
						//dispatcher.forward(request, response);
					}
				}
				else {
					request.setAttribute("passwordErrata", true);
					request.getRequestDispatcher("/JSP/LoginJSP.jsp").forward(request, response);

				}

			}
		}
	}
}
