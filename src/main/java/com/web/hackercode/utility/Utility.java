package com.web.hackercode.utility;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

public class Utility {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	 public boolean isUserAuthenticated(HttpServletRequest req) {
 		if (req.getSession() != null) {
 			if(req.getSession().getAttribute("user") != null) {
 		    	if (req.getSession().getAttribute("isLoggedIn").toString().equalsIgnoreCase("true")) {
 		    			return true;
 			}
 		}
     }
 		
     return false;
 }
	 
	 public String getMd5(String input) {
	        try {

	            // Static getInstance method is called with hashing MD5 
	            MessageDigest md = MessageDigest.getInstance("MD5");

	            // digest() method is called to calculate message digest 
	            //  of an input digest() return array of byte 
	            byte[] messageDigest = md.digest(input.getBytes());

	            // Convert byte array into signum representation 
	            BigInteger no = new BigInteger(1, messageDigest);

	            // Convert message digest into hex value 
	            String hashtext = no.toString(16);
	            while (hashtext.length() < 32) {
	                hashtext = "0" + hashtext;
	            }
	            return hashtext;
	        }

	        // For specifying wrong message digest algorithms 
	        catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	  }
	 public String randomAlphaNumeric(int count) {
		    StringBuilder builder = new StringBuilder();
		    
		    while (count-- != 0) {
			    int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			    builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		    }
		    
		    return builder.toString();
	    }

	public String getHTMLCompatContent(String content) {
		
		try {
			String[] bts = StringUtils.substringsBetween(content, "<pre><code>", "</code></pre>");
			
			if (bts.length > 0) {
				System.out.println("Found code");
				for (String bt : bts) {
					String escaped = StringEscapeUtils.escapeHtml4(bt);
					content = StringUtils.replace(content, bt, escaped);
					 
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
		}
		
		
		return content; 
	}


}