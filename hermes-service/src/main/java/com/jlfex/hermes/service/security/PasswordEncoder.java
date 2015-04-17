package com.jlfex.hermes.service.security;

import com.jlfex.hermes.common.utils.Strings;

/**
 * 密码加密
 */
public class PasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {

	/* (non-Javadoc)
	 * @see org.springframework.security.crypto.password.PasswordEncoder#encode(java.lang.CharSequence)
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		return Strings.md5(String.valueOf(rawPassword));
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.crypto.password.PasswordEncoder#matches(java.lang.CharSequence, java.lang.String)
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return Strings.equals(encodedPassword, encode(rawPassword));
	}
	
	/**
	 * @param args
	 */
	public static void main(String... args) {
		PasswordEncoder encoder = new PasswordEncoder();
		System.out.println(encoder.encode("admin"));
	}
}
