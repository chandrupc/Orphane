package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.model.Credential;
import org.orphane.model.Orphanage;
import org.orphane.model.RegularUserOrphanages;
import org.orphane.model.RegularUsers;
import org.orphane.services.FetchContent;
import org.orphane.services.SaveModels;
import org.orphane.util.CKUtil;

/**
 * Servlet implementation class AddOrphangeWithUser
 */
@WebServlet("/add-orphanage")
public class AddOrphangeWithUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddOrphangeWithUser() {
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
		String orpName = request.getParameter("orpName");
		String orpState = request.getParameter("orpState");
		try (PrintWriter out = response.getWriter()) {
			// System.out.println(orpName + " " + orpState);
			Orphanage orphanage = FetchContent.getOrphanageId(orpName, orpState);
			if (orphanage != null) {
				Credential credential = CKUtil.fetchDetails(request);
				RegularUsers regUser = FetchContent.getRegUserDetails(credential.getEmail());
				if (regUser != null) {
					// System.out.println(orphanage.getId());
					// System.out.println(regUser.getId());
					RegularUserOrphanages obj = new RegularUserOrphanages();
					obj.setOrpId(orphanage.getId());
					obj.setRegId(regUser.getId());
					SaveModels.addRegularUserOrp(obj);
					out.write("success");

				} else {
					out.write("error");
				}
			} else {
				out.write("error");
			}
			System.out.println(orphanage);
		}
	}

}
