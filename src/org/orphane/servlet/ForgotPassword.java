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
import org.orphane.services.EmailService;

@WebServlet("/forgot-pass")
public class ForgotPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ForgotPassword() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
			String email = request.getParameter("mail");
			CredentialService.requestReset(email);
			Credential user = CredentialService.getUser(email);
			response.setContentType("text/html");
			try {
				if (EmailService.send(email, "Password Reset Link",
						"<a href='http://localhost:8080/orphane/change-password.jsp?mail=" + email + "&authkey="
								+ user.getAuthKey() + "'>Follow the link to change the account password</a>")) {
					out.write("success");
				}
			} catch (Exception e) {
				e.printStackTrace();
				out.write("error");
			}
		}
	}

}
