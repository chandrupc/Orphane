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

@WebServlet("/signup")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SignUp() {
		super();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()){
			String email = request.getParameter("emailId");
			String pass = request.getParameter("pass");
			boolean status = CredentialService.checkUserAvailability(email);
			if(status){
				CredentialService.addNewCredential(email, pass, "ORPHANAGE");
				Credential user  = CredentialService.getUser(email);
				EmailService.send(user.getEmail(), "verification",user.getAuthKey());
			}
			else{
				out.println("<h1>EMAIL ALREADY EXISTS IN DATABASE TRY WITH NEW MAIL ID</h1>");
				response.sendRedirect("index.html");
			}
		}
	}

}
