package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.modelenum.SubscriptionStatus;
import org.orphane.services.FindDuplicates;
import org.orphane.services.SaveModels;

@WebServlet("/notify-users")
public class NotifyUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NotifyUsers() {
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
				response.setContentType("text/html");
				String name = request.getParameter("username");
				String mail = request.getParameter("mail");
				// System.out.println(name + " " + mail);
				org.orphane.model.NotifyUsers user = new org.orphane.model.NotifyUsers();
				user.setEmail(mail);
				user.setName(name);
				user.setStatus(SubscriptionStatus.SUBSCRIBED);
				if (FindDuplicates.notifyUsers(mail)) {
					SaveModels.addNotifyUsers(user);
					out.write("success");
				} else {
					out.write("Mail id already taken try with a different one");
				}
			} catch (Exception e) {
				e.printStackTrace();
				out.write("error");
			}
		}
	}

}
