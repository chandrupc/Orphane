package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.model.Address;
import org.orphane.model.RegularUsers;
import org.orphane.services.FindDuplicates;
import org.orphane.services.UserProfileService;

/**
 * Servlet implementation class UpdateUserProfile
 */
@WebServlet("/updateuserprofile")
public class UpdateUserProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateUserProfile() {
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
			Long altPhoneNumber = null;
			response.setContentType("text/html");
			String email = request.getParameter("email");
			Long phoneNumber = Long.parseLong(request.getParameter("phoneNumber"));
			if(!(request.getParameter("altNum").equals(""))) {
				altPhoneNumber = Long.parseLong(request.getParameter("altNum"));
			}
			String address = request.getParameter("address");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			int zip = Integer.parseInt(request.getParameter("zip"));
			RegularUsers user = UserProfileService.getUserProfile(email);
			System.out.println(user.getAddress().getAddress() + user.getName());
			user.setPhoneNumber(phoneNumber);
			user.setAltPhoneNumber(altPhoneNumber);
			Address addrss = new Address();
			addrss.setAddress(address);
			addrss.setCity(city);
			addrss.setState(state);
			addrss.setPincode(zip);
			user.setAddress(addrss);
			System.out.println(user.getAddress().getAddress());
			RegularUsers checkUser = UserProfileService.getUserProfile(email);
			if (user.getAddress().getAddress().equals(checkUser.getAddress().getAddress())
					&& user.getPhoneNumber() == checkUser.getPhoneNumber()
					&& user.getAltPhoneNumber() == checkUser.getAltPhoneNumber()
					&& user.getAddress().getCity().equals(checkUser.getAddress().getCity())
					&& user.getAddress().getState().equals(checkUser.getAddress().getState())
					&& user.getAddress().getPincode() == checkUser.getAddress().getPincode()) {
				out.write("NO CHANGE");
			} else {
				String message = FindDuplicates.inRegularUsers(user,email);
				System.out.println(message);
				if (message.equals("numberTaken")) {
					out.write("NUMBER TAKEN");
				} else if (message.equals("altNumberTaken")) {
					out.write("ALT NUMBER TAKEN");
				} else if (message.equals("AddressTaken")) {
					out.write("ADDRESS TAKEN");
				} else if (message.equals("success")) {
					UserProfileService.updateUserProfile(user);
					out.write("SUCCESS");
				} else {
					out.write("NULL EXCEPTION");
				}
			}

		}
	}

}
