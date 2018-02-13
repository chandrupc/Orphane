package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.model.Orphanage;
import org.orphane.services.GetAllDetails;

import com.google.gson.Gson;

/**
 * Servlet implementation class FetchAddedDetails
 */
@WebServlet("/fetch-added-details")
public class FetchAddedDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FetchAddedDetails() {
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
		try (PrintWriter out = response.getWriter()) {
			try {
				response.setContentType("application/json");
				String stateName = request.getParameter("state");
				Long regId = Long.parseLong(request.getParameter("regId"));
				List<Orphanage> orphanage = GetAllDetails.fetchAddedOrphanageState(stateName, regId);
				out.write(new Gson().toJson(orphanage));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
