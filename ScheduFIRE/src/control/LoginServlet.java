
package control;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.CapoTurnoBean;
import model.bean.CredenzialiBean;
import model.dao.CapoTurnoDao;
import model.dao.UserDao;
import util.Notifiche;
import util.PasswordSha256;

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
		HttpSession session = request.getSession();

		//Controllo se l utente è già loggato e lo rimando alla pagina corretta
		if(session.getAttribute("ruolo")!=null) {
			String ruolo=(String)session.getAttribute("ruolo");
			if (ruolo.equalsIgnoreCase("capoturno")) {
				response.sendRedirect("HomeCTServlet");
				return;
			} else {
				response.sendRedirect("CalendarioServlet");
				return;
			}


		}
		else {
			response.setContentType("text/html");

			String username = request.getParameter("Username");
			String password = request.getParameter("Password");
			if (username == null || username.equals(""))
				request.getRequestDispatcher("/JSP/LoginJSP.jsp").forward(request, response);
			else {
				String passwordBase256format;
				try {
					
					passwordBase256format = PasswordSha256.getEncodedpassword(password);
				
				
				UserDao credenziali = new UserDao();
				CredenzialiBean utente = credenziali.login(username);

				if (utente == null) {
					request.setAttribute("usernameErrato", true);
					request.getRequestDispatcher("/JSP/LoginJSP.jsp").forward(request, response);
				} else {

					if (passwordBase256format.equals(utente.getPassword())) {
						session.setAttribute("ruolo", utente.getRuolo());
						if (utente.getRuolo().equalsIgnoreCase("capoturno")) {
							CapoTurnoBean capoturno=CapoTurnoDao.ottieni(username);
							session.setMaxInactiveInterval(3600); //1 ora
							session.setAttribute("capoturno", capoturno);
							session.setAttribute("notifiche", new Notifiche());
							response.sendRedirect("HomeCTServlet");
							return;
							//RequestDispatcher dispatcher = request.getRequestDispatcher("WebContent\\JSP\\LoginJSP.jsp");
							//dispatcher.forward(request, response);
						} else {
							session.setMaxInactiveInterval(3600); // 1 ora
							response.sendRedirect("CalendarioServlet");
							return;
							//RequestDispatcher dispatcher = request.getRequestDispatcher("/CalendarioServlet");
							//dispatcher.forward(request, response);
						}
					}
					else {
						request.setAttribute("passwordErrata", true);
						request.getRequestDispatcher("/JSP/LoginJSP.jsp").forward(request, response);

					}

				}
				
			} catch (NoSuchAlgorithmException e) {
				
				e.printStackTrace();
			}

		}
	}
	}
}