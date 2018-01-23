package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.services.CredentialService;

@WebServlet("/availability")
public class UserAvailability extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserAvailability() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
			response.setContentType("text/plain");
			String email = request.getParameter("email");
			boolean status = CredentialService.checkUserAvailability(email);
			if (status) {
				out.write("available");
			} else {
				out.write("already exists");
			}
		}
	}

}
