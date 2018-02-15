package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.model.Events;
import org.orphane.model.Orphanage;
import org.orphane.modelenum.EventName;
import org.orphane.modelenum.EventStatus;
import org.orphane.services.FetchContent;
import org.orphane.services.FindDuplicates;
import org.orphane.services.SaveModels;

@WebServlet("/add-event")
public class AddEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddEvent() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
			response.setContentType("text/html");
			try {
				String eventName = request.getParameter("eventName");
				String eventDate = request.getParameter("eventDate");
				String orpId = request.getParameter("orpId");
				String description = request.getParameter("description");
				String reg = request.getParameter("regEmail");
				Date eDate = new SimpleDateFormat("yyyy-MM-dd").parse(eventDate);
				System.out.println(eventName + " " + " " + eventDate + " " + orpId + " " + description + " " + eDate
						+ " " + reg + "\n\n\n");
				Events event = new Events();
				event.setDescription(description);
				event.setEventDate(eDate);
				if (eventName.equals("BREAKFAST")) {
					event.setEventName(EventName.BREAKFAST);
				} else if (eventName.equals("LUNCH")) {
					event.setEventName(EventName.LUNCH);
				} else if (eventName.equals("DINNER")) {
					event.setEventName(EventName.DINNER);
				}
				Orphanage orp = FetchContent.getOrphanageDetailsById(Long.parseLong(orpId));
				event.setRegularUsers(FetchContent.getRegUserDetails(reg));
				event.setOrphanage(orp);
				// System.out.println(event + "\n\n\n\n\n");
				if (FindDuplicates.checkEventFound(event)) {
					// System.out.println(orp + "\n\n\n\n\n");
					event.setEventStatus(EventStatus.BOOKED);
					SaveModels.addEvents(event);
					out.write("success");
				} else {
					out.write("Event has been already Booked\nTry some other");
				}

			} catch (Exception e) {
				out.write("error");
				e.printStackTrace();
			}
		}
	}

}
