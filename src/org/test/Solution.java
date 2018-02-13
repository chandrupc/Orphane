package org.test;

import org.hibernate.SessionFactory;
import org.orphane.util.HBUtil;

public class Solution {

	public static void main(String[] args) {

		@SuppressWarnings("unused")
		SessionFactory sf = HBUtil.getSessionFactory();
		System.out.println("success");

		/*
		 * String email = "1234456"; String url =
		 * "<a href='https://localhost:8080/orphane/activate?authKey=" + email +
		 * "'></a>"; System.out.println(url);
		 */

	}
}
