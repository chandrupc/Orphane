package org.orphane.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.model.Address;
import org.orphane.model.Credential;
import org.orphane.model.Orphanage;
import org.orphane.services.CredentialService;
import org.orphane.services.FindDuplicates;
import org.orphane.services.SaveModels;

@WebServlet("/osignup")
public class OrphanageSignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public OrphanageSignUp() {
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
				response.setContentType("type/plain");
				String orphanageName = request.getParameter("orpName");
				String orphanagePhone = request.getParameter("orpPhone");
				String orphanageAltNum = request.getParameter("orpAltNum");
				String orphanageAddress = request.getParameter("orpAddress");
				String orphanageCity = request.getParameter("orpCity");
				String orphanageState = request.getParameter("orpState");
				String orphanageZip = request.getParameter("orpZip");
				String orphanageWebsite = request.getParameter("orpWebsite");
				String orphanageMail = request.getParameter("orpEmail");
				String orphanagePass = request.getParameter("orpPassword");
				/*
				 * System.out.println(orphanageAddress + " " + orphanageAltNum +
				 * " " + orphanageCity + " " + orphanageMail + " " +
				 * orphanageName + " " + orphanagePass + " " + orphanagePhone +
				 * " " + orphanageState + " " + orphanageWebsite + " " +
				 * orphanageZip);
				 */
				long e = 0;
				Address address = new Address();
				address.setAddress(orphanageAddress);
				address.setCity(orphanageCity);
				address.setPincode(Integer.parseInt(orphanageZip));
				address.setState(orphanageState);
				Orphanage orp = new Orphanage();
				orp.setAddress(address);
				if (orphanageAltNum.isEmpty() == false && orphanageAltNum != null) {
					orp.setAltPhoneNumber(Long.parseLong(orphanageAltNum));
					e = Long.parseLong(orphanageAltNum);
				}
				orp.setName(orphanageName);
				orp.setPhoneNumber(Long.parseLong(orphanagePhone));
				String res = FindDuplicates.orphanage(Long.parseLong(orphanagePhone), orphanageWebsite, address, e);
				System.out.println(res);
				if (res == null) {
					orp.setWebsite(orphanageWebsite);
					CredentialService.addNewCredential(orphanageMail, orphanagePass, "ORPHANAGE");
					Credential credential = CredentialService.getUser(orphanageMail);
					orp.setCredential(credential);
					SaveModels.addOrphanage(orp);
					out.write("success");
				}

				else {
					out.write(res);
				}
			} catch (Exception e) {
				out.write("{message : 'Error'}");
				e.printStackTrace();
			}
		}
	}

}
