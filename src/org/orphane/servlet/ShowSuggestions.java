package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.model.Orphanage;
import org.orphane.services.FetchContent;

import com.google.gson.Gson;

/**
 * Servlet implementation class ShowSuggestions
 */
@WebServlet("/show-suggestions")
public class ShowSuggestions extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ShowSuggestions() {
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
				List<Orphanage> orp = FetchContent.getSubscribedOrphanage(Long.parseLong(request.getParameter("id")));
				// System.out.println("Successfully fetched");
				List<String> orpNames = new LinkedList<String>();
				for (Orphanage each : orp) {
					orpNames.add(each.getName() + "-" + each.getId() + '-' + each.getAddress().state);
				}
				out.write(new Gson().toJson(orpNames));
			} catch (Exception e) {
				out.write("error");
				e.printStackTrace();
			}
		}
	}
}
