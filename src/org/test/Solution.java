package org.test;

import org.hibernate.SessionFactory;
import org.orphane.util.HBUtil;

public class Solution {

	public static void main(String[] args) {
		SessionFactory sf = HBUtil.getSessionFactory();
		System.out.println("success");
		sf.close();
	}
}
