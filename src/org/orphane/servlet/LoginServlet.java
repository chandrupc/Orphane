package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.model.Credential;
import org.orphane.modelenum.UserStatus;
import org.orphane.services.CredentialService;
import org.orphane.util.AUTHIDGen;
import org.orphane.util.CKUtil;

@WebServlet("/login-user")
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
		// System.out.println("callled");
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		if (CKUtil.fetchCookie(request)) {
			response.sendRedirect("orphanage.jsp");
		} else if (email == null && pass == null) {
			request.getRequestDispatcher("login.html").forward(request, response);
		} else {
			try (PrintWriter out = response.getWriter()) {
				response.setContentType("text/html");
				//System.out.println(email + " " + pass);
				if (email.isEmpty() || (email.isEmpty() && pass.isEmpty() == false)) {
					out.write("email can't be empty");
				} else if (pass.isEmpty() || (email.isEmpty() == false && pass.isEmpty())) {
					out.write("password empty");
				} else if (pass.isEmpty() == false && email.isEmpty() == false) {
					// System.out.println(email+" "+pass);
					Credential user = CredentialService.getUser(email);
					if (user != null) {
						// System.out.println("entered");
						if (pass.equals(user.getPassword())) {
							// System.out.println("entered");
							if (user.getStatus().equals(UserStatus.NOT_ACTIVATED)) {
								out.write("account not activated");
							} else if (user.getStatus().equals(UserStatus.ACTIVATED)) {
								// request.getRequestDispatcher("/index.html").forward(request,
								// response);
								Credential updateAuthKey = new Credential();
								updateAuthKey.setEmail(user.getEmail());
								updateAuthKey.setPassword(user.getPassword());
								updateAuthKey.setAuthKey(AUTHIDGen.generateKey(30));
								updateAuthKey.setUserType(user.getUserType());
								updateAuthKey.setStatus(user.getStatus());
								if (CredentialService.updateUser(updateAuthKey)) {
									user = CredentialService.getUser(email);
									CKUtil.createCookie(response, user.getEmail(), user.getAuthKey());
									out.write("success");
								}
							}
						} else {
							//System.out.println("error");
							out.write("Incorrect Password");
						}
					} else if (user == null) {
						// System.out.println("failed");
						out.write("invalid user");
					}
				}
			}
		}
	}
}
