package org.orphane.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.orphane.model.Address;
import org.orphane.model.Credential;
import org.orphane.model.RegularUsers;
import org.orphane.services.CredentialService;
import org.orphane.services.FindDuplicates;
import org.orphane.services.SaveModels;

/**
 * Servlet Filter implementation class RegularSignUpFilter
 */
@WebFilter(description = "Authentication Filter", urlPatterns = { "/rsignup" })
public class RegularSignUpFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public RegularSignUpFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try (PrintWriter out = response.getWriter()) {
			try {
				response.setContentType("type/plain");
				String fname = request.getParameter("firstName");
				String lname = request.getParameter("lastName");
				String phoneNumber = request.getParameter("phoneNumber");
				String altNum = request.getParameter("altNumber");
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
					CredentialService.addNewCredential(email, pass, "REGULAR");
					Credential credential = CredentialService.getUser(email);
					reg.setCredential(credential);
					SaveModels.addRegularUsers(reg);
					chain.doFilter(request, response);
				} else if (message == "Phone Number Found") {
					out.write("Phone Number Taken");
				} else if (message == "Address Found") {
					out.write("Address Already Taken");
				}

				// System.out.println(date);
			} catch (Exception e) {
				out.write("error");
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
