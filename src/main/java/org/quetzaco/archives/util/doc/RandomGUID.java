/******************************************************************************
*      					Quetzaco, Inc.
*******************************************************************************
*  File Name	: 	RandomGUID.java
*  Package Name	: 	org.quetzaco.commoncomponents.global.model
*  Product Name	: 	Quetzaco
*  Version 		: 	2.5
*  Copyright	: 	Copyright 2003 - 2004, Quetzaco, Inc., All rights reserved.
*      					This software is the confidential and proprietary
*  information of Quetzaco, Inc. This software is provided under a license
*  agreement containing restrictions on use and disclosure and is protected
*  by copyright laws. Reverse engineering of the software is strictly
*  prohibited.
*     It is the customer's responsibility to ensure the proper use and
*  application of this  software and take all necessary and appropriate
*  measures for the safe use of such applications if the software is used
*  for such purposes.
*
******************************************************************************/
package org.quetzaco.archives.util.doc;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RandomGUID extends Object {
	 final  Logger LOGGER = LoggerFactory.getLogger(RandomGUID.class);
	/*
	 * Static block to take care of one time secureRandom seed.
	 * It takes a few seconds to initialize SecureRandom.  You might
	 * want to consider removing this static block or replacing
	 * it with a "time since first loaded" seed to reduce this time.
	 * This block will run only once per JVM instance.
	 */
	private static Object mutex = null;
	private static Random myRand;
	private static SecureRandom mySecureRand;

	private static String s_id;
	public String valueAfterMD5 = "";

	public String valueBeforeMD5 = "";
	
	static {
		mySecureRand = new SecureRandom();
		long secureInitializer = mySecureRand.nextLong();
		myRand = new Random(secureInitializer);
		try {
			s_id = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Default constructor.  With no specification of security option,
	 * this constructor defaults to lower security, high performance.
	 */
	public RandomGUID() {
		mutex = new Object();
		synchronized (mutex) {
			getRandomGUID(false);
		}

	}

	/*
	 * Constructor with security option.  Setting secure true
	 * enables each random number generated to be cryptographically
	 * strong.  Secure false defaults to the standard Random function seeded
	 * with a single cryptographically strong random number.
	 */
	public RandomGUID(boolean secure) {
		mutex = new Object();
		synchronized (mutex) {
			getRandomGUID(secure);
		}

	}

	/*added by vinay halyal 22-11-2002
	//class added for future use
	*/
	/*
	public Vector getGUIDvector(int k)
	{
		Vector containGUID = new Vector();
		for (int i=0; i< k; i++)
		{
	
			RandomGUID myGUID = new RandomGUID();
			containGUID.add(myGUID.toString());
	    }
	    return containGUID;
	
	}
	*/

	//added by nandish
	//this class will return a guid in the string format
	public String getGUID() {
		RandomGUID myGUID = new RandomGUID();
		return myGUID.toString();
		//return myGUID.toStringWithOutHyphens();
	}

	/*
	 * Method to generate the random GUID
	 */
	private void getRandomGUID(boolean secure) {
		MessageDigest md5 = null;
		StringBuffer sbValueBeforeMD5 = new StringBuffer();
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error( e.getMessage());
		}

		try {
			long time = System.currentTimeMillis();
			long rand = 0;

			if (secure) {
				rand = mySecureRand.nextLong();
			} else {
				rand = myRand.nextLong();
			}

			// This StringBuffer can be a long as you need; the MD5
			// hash will always return 128 bits.  You can change
			// the seed to include anything you want here.
			// You could even stream a file through the MD5 making
			// the odds of guessing it at least as great as that
			// of guessing the contents of the file!
			sbValueBeforeMD5.append(s_id);
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(time));
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(rand));

			valueBeforeMD5 = sbValueBeforeMD5.toString();
			md5.update(valueBeforeMD5.getBytes());

			byte[] array = md5.digest();
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < array.length; ++j) {
				int b = array[j] & 0xFF;
				if (b < 0x10)
					sb.append('0');
				sb.append(Integer.toHexString(b));
			}

			valueAfterMD5 = sb.toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	/*
	public static void main(String args[])
	{
			RandomGUID myGUID = new RandomGUID();
			log(myGUID.getGUID());
	
	}
	*/

	/*
	 * Convert to the standard format for GUID
	 * (Useful for SQL Server UniqueIdentifiers, etc.)
	 * Example: C2FEEEAC-CFCD-11D1-8B05-00600806D9B6
	 */
	public String toString() {
		String raw = valueAfterMD5.toUpperCase();
		StringBuffer sb = new StringBuffer();
		sb.append(raw.substring(0, 8));
		sb.append("-");
		sb.append(raw.substring(8, 12));
		sb.append("-");
		sb.append(raw.substring(12, 16));
		sb.append("-");
		sb.append(raw.substring(16, 20));
		sb.append("-");
		sb.append(raw.substring(20));

		return sb.toString();
	}
	public String toStringWithOutHyphens() {
		return valueAfterMD5.toUpperCase();
	}
	
	public static void main(String[] args) {
		System.out.println(new RandomGUID().getGUID());
	}
}

