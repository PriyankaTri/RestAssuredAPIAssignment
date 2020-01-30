package com.restapi.qa.test;

public class Resources {

	public static String getSingleResource() {
		String singleres = "/api/users/2";
		return singleres; 
	}
	
	public static String getAllResources() {
		String allres = "/api/users?page=2";
		return allres;
	}
	
	public static String postResource() {
		String postres = "/users";
		return postres;
	}
	
	
}
