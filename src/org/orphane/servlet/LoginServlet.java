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
			response.setContentType("type/plain");
			String email = request.getParameter("email");
			String pass = request.getParameter("pass");			
			//System.out.println(email+" "+pass);
			Credential user = CredentialService.getUser(email);
			if (user != null) {
				if (pass.equals(user.getPassword())) {
					boolean status = CredentialService.validateUser(email, pass);
					if(status == false){
						out.write("account not activated");
					}
					else if(status){
						out.write("success");
					}
				}
			} else if(user == null) {
				//System.out.println("failed");
				out.write("invalid user");
			}
		}
	}

}
