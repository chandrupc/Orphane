package org.orphane.util;

import java.security.SecureRandom;

public class AUTHIDGen {

	static final String name = "0123456789ABCDEFGHIJKLMNOPQRUSTUVWXYZ";
	static SecureRandom random = new SecureRandom();
	
	public static String generateKey(int length){
		StringBuilder genString = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			genString.append(name.charAt(random.nextInt(name.length())));
		}
		return genString.toString();
	}
}
