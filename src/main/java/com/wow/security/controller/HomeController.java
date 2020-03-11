package com.wow.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wow.security.constants.ErrorResponseCode;
import com.wow.security.constants.ResponseCode;
import com.wow.security.request.UserData;
import com.wow.security.response.BaseResponse;
import com.wow.security.response.ErrorResponse;
import com.wow.security.validator.CustomValidator;

@RestController
public class HomeController {
	
	@Autowired
	private CustomValidator customValidator;

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
	
	@PostMapping("/secure/admin/add-user")
	public ResponseEntity<BaseResponse> addUser(@RequestBody UserData userData, BindingResult result){
		
		BaseResponse response = new BaseResponse();
		response.setResponseCode(ResponseCode.SUCCESS);
		
		customValidator.validate(userData, result, false);
		
		if (result.hasErrors()) {
			response.setResponseCode(ResponseCode.FAILURE);;
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setAdditionalMsg(result.getFieldErrors().get(0).getField() + "-"
					+ result.getFieldErrors().get(0).getDefaultMessage());
			errorResponse.setResponseCode(ErrorResponseCode.VALIDATION_FAILURE);
			response.setError(errorResponse);
		} else {
			response.setResponseBody(userData);
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
