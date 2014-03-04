package edu.ubb.ccwp.logic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

	public static byte[] hashString(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        md.update(str.getBytes());
        return md.digest();
	}
}
