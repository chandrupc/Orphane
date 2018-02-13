package org.orphane.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orphane.model.Credential;
import org.orphane.services.CredentialService;

public class CKUtil {

	public static void createCookie(HttpServletResponse response, String name, String authKey) {
		Cookie cookie = new Cookie("mail", authKey + name);
		cookie.setMaxAge(24 * 60 * 60);
		// cookie.setPath("/orphane");
		response.addCookie(cookie);
	}

	public static boolean fetchCookie(HttpServletRequest request) {
		boolean status = false;
		try {
			// System.out.println("entered");
			Cookie[] cookie = request.getCookies();
			if (cookie != null) {
				for (Cookie each : cookie) {
					if (each.getName().equals("mail")) {
						String fetch = each.getValue().toString();
						String key = fetch.substring(0, 30);
						String mail = fetch.substring(30);
						System.out.println(key + " " + mail);
						Credential user = CredentialService.getUser(mail);
						// System.out.println(user);
						if (user != null) {
							// System.out.println(user + " entered");
							if (user.getAuthKey().equals(key)) {
								System.out.println("status true");
								status = true;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		}
		return status;
	}

	public static boolean deleteCookie(HttpServletRequest request, HttpServletResponse response) {
		boolean status = false;
		try {
			Cookie[] cookie = request.getCookies();
			if (cookie != null) {
				for (Cookie each : cookie) {
					if (each.getName().equals("mail")) {
						each.setValue("");
						each.setMaxAge(0);
						response.addCookie(each);
						status = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}

	public static Credential fetchDetails(HttpServletRequest request) {
		try {
			// System.out.println("entered");
			Cookie[] cookie = request.getCookies();
			if (cookie != null) {
				for (Cookie each : cookie) {
					if (each.getName().equals("mail")) {
						String fetch = each.getValue().toString();
						String key = fetch.substring(0, 30);
						String mail = fetch.substring(30);
						System.out.println(key + " " + mail);
						Credential user = CredentialService.getUser(mail);
						// System.out.println(user);
						if (user != null) {
							// System.out.println(user + " entered");
							if (user.getAuthKey().equals(key)) {
								System.out.println("status true");
								return user;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
