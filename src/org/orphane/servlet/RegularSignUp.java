package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.model.Address;
import org.orphane.model.Credential;
import org.orphane.model.RegularUsers;
import org.orphane.services.CredentialService;
import org.orphane.services.EmailService;
import org.orphane.services.FindDuplicates;
import org.orphane.services.SaveModels;
import org.orphane.util.AUTHIDGen;

/**
 * Servlet implementation class RegularSignUp
 */
@WebServlet("/rsignup")
public class RegularSignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegularSignUp() {
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
				String fname = request.getParameter("firstName");
				String lname = request.getParameter("lastName");
				String phoneNumber = request.getParameter("phoneNumber");
				String altNum = request.getParameter("altNum");
				String regAddress = request.getParameter("address");
				String city = request.getParameter("city");
				String state = request.getParameter("state");
				String zip = request.getParameter("zip");
				String email = request.getParameter("email");
				String pass = request.getParameter("password");
				String dob = request.getParameter("dateOfBirth");
				String date[] = dob.split("-");
				System.out.println(fname + " " + lname + " " + phoneNumber + " " + altNum + " " + regAddress + " "
						+ city + " " + state + " " + zip + " " + email + " " + pass + " " + dob);
				@SuppressWarnings("deprecation")
				Date dateOfBirth = new Date(Integer.parseInt(date[0].substring(2)), Integer.parseInt(date[1]) - 1,
						Integer.parseInt(date[2]));
				System.out.println(dateOfBirth);
				Address address = new Address();
				address.setAddress(regAddress);
				address.setCity(city);
				address.setPincode(Integer.parseInt(zip));
				address.setState(state);
				RegularUsers reg = new RegularUsers();
				reg.setName(fname);
				reg.setLastName(lname);
				reg.setAddress(address);
				reg.setPhoneNumber(Long.parseLong(phoneNumber));
				if (altNum != null && altNum.isEmpty() == false) {
					reg.setAltPhoneNumber(Long.parseLong(altNum));
				}
				reg.setDate(dateOfBirth);
				String message = FindDuplicates.RegularUsers(reg);
				System.out.println(message);
				if (message == null) {
					String authkey = AUTHIDGen.generateKey(30);
					System.out.println(authkey);
					if (EmailService.send(email, "Confirm Your Account",
							"<a href='http://localhost:8080/orphane/activate?mail=" + email + "&authkey=" + authkey
									+ "'>Follow the link to activate your account</a>")) {
						CredentialService.addNewCredential(email, pass, "REGULAR", authkey);
						Credential credential = CredentialService.getUser(email);
						reg.setCredential(credential);
						SaveModels.addRegularUsers(reg);
					} else {
						out.write("Network Error");
					}

					/*
					 * String res = "{ \"message\" : \"success\"," +
					 * "\"firstName\" :" + '"' + fname + '"' + ",\"lastName\" :"
					 * + '"' + lname + '"' + ",\"phoneNumber\" :" +
					 * '"' + phoneNumber + '"' + ",\"altNum\" :" +
					 * '"' + altNum + '"' + ",\"address\" :" +
					 * '"' + regAddress + '"' + ",\"city\" :" + '"' + city + '"'
					 * + ",\"state\" :" + '"' + state + '"' + ",\"zip\" :" + '"'
					 * + zip + '"' + ",\"dateOfBirth\" :" + '"' + dob + '"' +
					 * ",\"email\" :" + '"' + email + '"' + ",\"pass\" :" +
					 * '"' + pass + '"' + "}";
					 */
					// request.getRequestDispatcher("/index.html").forward(request,
					// response);
					out.write("success");
				} else {
					// out.write("{ \"message\" :" + '"' + message + '"' + "}");
					out.write(message);
				}
				// System.out.println(date);
			} catch (Exception e) {
				out.write("error");
				e.printStackTrace();
			}
		}

	}
}
