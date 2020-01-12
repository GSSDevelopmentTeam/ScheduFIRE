
package control;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.CapoTurnoBean;
import model.bean.CredenzialiBean;
import model.dao.CapoTurnoDao;
import model.dao.CredenzialiDao;
import util.Notifiche;
import util.PasswordSha256;
import util.Validazione;

/**
 * Classe che si occupa dell'accesso dell'utente 
 * @author Ciro Cipolletta
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		//Controllo se l utente � gi� loggato e lo rimando alla pagina corretta
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
			
			if(username!=null) {
				if(username.length()<3)
					throw new ScheduFIREException("Il campo \"Username\" deve essere formato da almeno 3 caratteri");
				else if(username.length()>20)
					throw new ScheduFIREException("Il campo \"Username\" deve essere formato da massimo 20 caratteri");
				else if(!Validazione.username(username))
					throw new ScheduFIREException("Il campo \"Username\" accetta solo caratteri alfanumerici");
			}
			if(password!=null) {
				if(password.length()<6)
					throw new ScheduFIREException("Il campo \"Password\" deve essere formato da almeno 6 caratteri");
				else if(password.length()>16)
					throw new ScheduFIREException("Il campo \"Password\" deve essere formato da massimo 16 caratteri");
				else if(!Validazione.password(password))
					throw new ScheduFIREException("Il campo \"Password\" accetta solo caratteri alfanumerici");
			}
			
			
			if (username == null || username.equals(""))
				request.getRequestDispatcher("/JSP/LoginJSP.jsp").forward(request, response);
			else {
				String passwordBase256format;
				try {
					
					passwordBase256format = PasswordSha256.getEncodedpassword(password);
				
				
				CredenzialiDao credenziali = new CredenzialiDao();
				CredenzialiBean utente = credenziali.login(username);

				if (utente == null) {
					request.setAttribute("usernameErrato", true);
					request.getRequestDispatcher("/JSP/LoginJSP.jsp").forward(request, response);
				} else {

					if (passwordBase256format.equals(utente.getPassword())) {
						session.setAttribute("ruolo", utente.getRuolo());
						if (utente.getRuolo().equalsIgnoreCase("capoturno")) {
							CapoTurnoBean capoturno=CapoTurnoDao.ottieni(username);
							session.setMaxInactiveInterval(2700); //45 min
							session.setAttribute("capoturno", capoturno);
							session.setAttribute("notifiche", new Notifiche());
							response.sendRedirect("HomeCTServlet");
							return;
							//RequestDispatcher dispatcher = request.getRequestDispatcher("WebContent\\JSP\\LoginJSP.jsp");
							//dispatcher.forward(request, response);
						} else {
							session.setMaxInactiveInterval(2700); // 45 min
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