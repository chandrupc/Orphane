package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.model.Credential;
import org.orphane.services.CredentialService;

@WebServlet("/validate")
public class Validate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Validate() {
		super();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
			String email = request.getParameter("emailId");
			String pass = request.getParameter("pass");
			String authKey = request.getParameter("authKey");
			Credential user = CredentialService.getUser(email);
			if (user != null) {
				if (pass.equals(user.getPassword()) && authKey.equals(user.getAuthKey())) {
					CredentialService.activateUser(email, pass, authKey);
				}
			} else {
				out.println("<h1>Email Id doesnot exists</h1>");
			}
		}
	}

}
