package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.services.DeleteService;

@WebServlet("/delete-event")
public class CancelEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CancelEvent() {
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
				String id = request.getParameter("id");
				Long regId = Long.parseLong(id);
				if (DeleteService.deleteEvent(regId)) {
					out.write("success");
				} else {
					out.write("error");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
