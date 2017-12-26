package com.shrralis.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * An utility for performing some {@link java.lang.String} objects.
 *
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0 Created 12/24/17 at 1:44 AM
 */
public class TextUtils {
	private static final Logger logger = LoggerFactory.getLogger(TextUtils.class);

	/**
	 * Checks that some substring contains in full text or not.
	 *
	 * @param str
	 * 		- substring.
	 * @param searchStr
	 * 		- full text.
	 *
	 * @return true OR false
	 */
	public static boolean containsIgnoreCase(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}

		final int length = searchStr.length();

		if (length == 0) {
			return true;
		}

		for (int i = str.length() - length; i >= 0; i--) {
			if (str.regionMatches(true, i, searchStr, 0, length)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks that text is empty or null.
	 *
	 * @param s
	 * 		- text.
	 *
	 * @return true OR false
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s);
	}

	/**
	 * Checks that E-Mail has valid format or not.
	 *
	 * @param s
	 * 		- E-Mail string.
	 *
	 * @return true OR false
	 */
	public static boolean isEmailValid(String s) {
		if (!isEmptyTrimmed(s) && s.matches("^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*\n" +
				"@[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$")) {
			try {
				new InternetAddress(s).validate();
				return true;
			} catch (AddressException ignored) {
				logger.info("{} isn't real E-Mail", s);
			}
		}
		return false;
	}

	/**
	 * Checks that trimmed text is empty or null.
	 *
	 * @param s
	 * 		- text.
	 *
	 * @return true OR false
	 */
	public static boolean isEmptyTrimmed(String s) {
		return s == null || "".equals(s.trim());
	}

	/**
	 * Checks that passed string has valid human name format or not.
	 *
	 * @param s
	 * 		- string with possible name.
	 *
	 * @return true OR false
	 */
	public static boolean isNameValid(String s) {
		return !isEmptyTrimmed(s) && s.matches("^[A-ZА-Я]['a-zа-я]+$");
	}
}
