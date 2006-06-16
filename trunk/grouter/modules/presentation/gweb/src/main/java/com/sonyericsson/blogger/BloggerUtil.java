package com.sonyericsson.blogger;

import org.apache.commons.codec.binary.Base64;

public class BloggerUtil {
	/**
	 * Converts a bytearray into a hexadecimal string representation
	 * 
	 * @param bytes to convert
	 * @return string hexadecimal representation
	 */
	public static String toHex(byte[] bytes) {
		StringBuffer hex = new StringBuffer();

		for (int i = 0; i < bytes.length; i++) {
			byte data = bytes[i];
			hex.append(toHexChar((data >>> 4) & 0x0F));
			hex.append(toHexChar(data & 0x0F));
		}

		return hex.toString();
	}

	/**
	 * Converting value to hex num
	 * @param i the number to convert to a hex character
	 * @return number in hexrepesentation
	 */
	private static char toHexChar(int i) {
		if ((0 <= i) && (i <= 9))
			return (char) ('0' + i);
		else
			return (char) ('a' + (i - 10));
	}
	
	public static String encodeBase64(byte[] bytes) {
		return new String(Base64.encodeBase64(bytes));
	}
}
