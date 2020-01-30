package com.restapi.qa.test;

public class payLoad {

	public static String getPostData() {
		String postdata = "{" + 
				"\"name\": \"priyaa\",\r\n" + 
				"\"job\": \"qa\"\r\n" + 
				"}";
		return postdata;
	}
	
	
	
	public static String getUpdateData() {
		String updatedata = "{" + 
				    "\"name\": \"morpheus\",\r\n" + 
				    "\"job\": \"zion resident\"\r\n" + 
				"}";
		return updatedata;
	}
	
	
}
