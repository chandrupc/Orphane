package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.services.CredentialService;

@WebServlet(description = "Activating the user account", urlPatterns = { "/activate" })
public class ActivateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActivateAccount() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
			response.setContentType("text/html");
			String disp = "";
			try {
				String authkey = request.getParameter("authkey");
				String email = request.getParameter("mail");
				System.out.println(authkey + " " + email);
				if (CredentialService.activateUser(email, authkey)) {
					request.getRequestDispatcher("orphane/login.html").forward(request, response);
				}
			} catch (Exception e) {
				disp = "<h1>ERROR OCCURED PLEASE TRY AFTER SOMETIMES</h1>";
				e.printStackTrace();
			} finally {
				out.println(disp);
			}
		}
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
