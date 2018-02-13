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

@WebServlet("/fetch-details")
public class FetchByState extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FetchByState() {
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
				response.setContentType("application/json");
				String stateName = request.getParameter("name");
				List<Orphanage> orphanage = GetAllDetails.fetchSelectedOrphanageState(stateName);
				out.write(new Gson().toJson(orphanage));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
