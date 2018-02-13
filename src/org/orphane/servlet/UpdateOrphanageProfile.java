package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.model.Address;
import org.orphane.model.Orphanage;
import org.orphane.services.FetchContent;
import org.orphane.services.FindDuplicates;
import org.orphane.services.OrphanageProfileSerive;

/**
 * Servlet implementation class UpdateOrphanageProfile
 */
@WebServlet("/updateorphanageprofile")
public class UpdateOrphanageProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateOrphanageProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
			Long altPhoneNumber = null;
			response.setContentType("text/html");
			String email = request.getParameter("email");
			Long phoneNumber = Long.parseLong(request.getParameter("phoneNumber"));
			if (!(request.getParameter("altNum").equals(""))) {
				altPhoneNumber = Long.parseLong(request.getParameter("altNum"));
			}
			String address = request.getParameter("address");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String website = request.getParameter("website");
			int zip = Integer.parseInt(request.getParameter("zip"));
			Orphanage orp = FetchContent.getOrphanageDetails(email);
			System.out.println(orp.getAddress().getAddress() + orp.getName());
			orp.setPhoneNumber(phoneNumber);
			orp.setAltPhoneNumber(altPhoneNumber);
			orp.setWebsite(website);
			Address addrss = new Address();
			addrss.setAddress(address);
			addrss.setCity(city);
			addrss.setState(state);
			addrss.setPincode(zip);
			orp.setAddress(addrss);
			System.out.println(orp.getAddress().getAddress());
			Orphanage checkOrp = FetchContent.getOrphanageDetails(email);
			if (orp.getAddress().getAddress().equals(checkOrp.getAddress().getAddress())
					&& orp.getPhoneNumber() == checkOrp.getPhoneNumber()
					&& orp.getAltPhoneNumber() == checkOrp.getAltPhoneNumber()
					&& orp.getAddress().getCity().equals(checkOrp.getAddress().getCity())
					&& orp.getAddress().getState().equals(checkOrp.getAddress().getState())
					&& orp.getAddress().getPincode() == checkOrp.getAddress().getPincode()
					&& orp.getWebsite().equals(checkOrp.getWebsite())) {
				out.write("NO CHANGE");
			} else {
				String message = FindDuplicates.inOrphanage(orp, email);
				System.out.println(message);
				if (message.equals("numberTaken")) {
					out.write("NUMBER TAKEN");
				} else if (message.equals("altNumberTaken")) {
					out.write("ALT NUMBER TAKEN");
				} else if (message.equals("AddressTaken")) {
					out.write("ADDRESS TAKEN");
				} else if (message.equals("success")) {
					OrphanageProfileSerive.updateOrphanageProfile(orp);
					out.write("SUCCESS");
				}  else if (message.equals("websiteTaken")) {
					out.write("WEBSITE TAKEN");
				}
				else {
					out.write("NULL EXCEPTION");
				}
			}

		}
	}

}
