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
import org.orphane.util.AUTHIDGen;

@WebServlet("/change-password")
public class NewPasswordUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NewPasswordUpdate() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
			response.setContentType("text/html");
			String mail = request.getParameter("mail");
			String pass1 = request.getParameter("pass1");
			String pass2 = request.getParameter("pass2");
			System.out.println(mail + " " + pass1 + " " + pass2);
			if (pass1.equals(pass2)) {
				Credential user = CredentialService.getUser(mail);
				if (user != null) {
					Credential newUser = new Credential();
					newUser.setEmail(mail);
					newUser.setStatus(user.getStatus());
					newUser.setUserType(user.getUserType());
					newUser.setPassword(pass1);
					newUser.setAuthKey(AUTHIDGen.generateKey(30));
					System.out.println("success");
					System.out.println(newUser);
					if (CredentialService.updateUser(newUser)) {
						System.out.println("success");
						out.write("success");
					}
				} else {
					out.write("No user");
				}
			}

		}
	}

}
