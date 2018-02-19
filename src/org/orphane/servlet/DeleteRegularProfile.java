package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.services.CredentialService;
import org.orphane.util.CKUtil;

@WebServlet("/delete-reg-profile")
public class DeleteRegularProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteRegularProfile() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
			try {
				String email = request.getParameter("email");
				// System.out.println(email); &&

				if (CredentialService.deleteAccount(email)) {
					CKUtil.deleteCookie(request, response);
					out.write("success");
				} else {
					out.write("error");
				}
			} catch (Exception e) {
				out.write("error");
				e.printStackTrace();
			}
		}
	}

}
