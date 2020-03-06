package com.wow.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerate {

	public static void main(String[] args) {

		BCryptPasswordEncoder ecode=new BCryptPasswordEncoder();
		
		System.out.println(ecode.encode("password"));
	}

}
