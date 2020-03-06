package com.wow.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/public")
	public String homePage(){
		return "home";
	}
	
	@GetMapping("/secure/user")
	public String userPage(){
		return "user";
	}
	
	@GetMapping("/secure/admin")
	public String adminPage(){
		return "admin";
	}
	
	@GetMapping("/secure/user/welcome")
	public String welcomePage(Authentication user){
		return "welcome user: "+user.getName();
	}
	
	@GetMapping("/secure/admin/welcome")
	public String welcomePageAdmin(Authentication user){
		return "welcome admin: "+user.getName();
	}
}
