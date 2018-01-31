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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
			response.setContentType("text/html");
			String email = request.getParameter("email");
			String pass = request.getParameter("pass");
			// System.out.println(email+" "+pass);
			Credential user = CredentialService.getUser(email);
			if (user != null) {
				// System.out.println("entered");
				if (pass.equals(user.getPassword())) {
					// System.out.println("entered");
					boolean status = CredentialService.validateUser(email, pass);
					// System.out.println(status);
					if (status == false) {
						out.write("account not activated");
					} else if (status) {
						request.getRequestDispatcher("/index.html").forward(request, response);
						// out.write("success");
					}
				} else {
					System.out.println("error");
					out.write("Incorrect Password");
				}
			} else if (user == null) {
				// System.out.println("failed");
				out.write("invalid user");
			}
		}
	}

}
