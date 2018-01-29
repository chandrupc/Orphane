package org.orphane.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.model.Credential;
import org.orphane.services.CredentialService;
import org.orphane.services.EmailService;

/**
 * Servlet implementation class RegularSignUp
 */
@WebServlet(description = "Redirecting Page", urlPatterns = { "/rsignup" })
public class RegularSignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegularSignUp() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String email = request.getParameter("email");
			response.setContentType("type/html");
			Credential credential = CredentialService.getUser(email);
			EmailService.send(email, "Verify Your Account",
					"<a href='https://localhost:8080/orphane/activate?authKey=" + credential.getAuthKey() + "'></a>");
			response.sendRedirect("index.html");
			// System.out.println(response);
		} catch (Exception e) {

		}

	}

}
