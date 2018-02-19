package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.services.SaveModels;

/**
 * Servlet implementation class OrphanagePost
 */
@WebServlet("/orphanagepost")
public class OrphanagePost extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrphanagePost() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
			String email = request.getParameter("email");
			String text = request.getParameter("postContent");
			if ((SaveModels.addPost(email, text)).equals("success")) {
				out.write("SUCCESS");
			} else {
				out.write("ERROR");
			}
		}
	}
}
