package com.resreqapitest.qa.com;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Quota.Resource;

import org.apache.logging.log4j.core.util.IOUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.restapi.qa.test.payLoad;
import com.restapi.qa.test.Resources;
import com.restapi.qa.test.TestValues;

public class TestResreqApi {
	
	Properties prop = new Properties();
	
	@BeforeTest
	public void getPropertiesData() throws IOException{
		FileInputStream fis = new FileInputStream("src\\main\\java\\TestFramework\\env.properties");
		prop.load(fis);
	}
	

	@Test(priority=1)
	public void getAllUser() {
		RestAssured.baseURI = prop.getProperty("ENDPOINT1");
		RequestSpecification request = RestAssured.given();
		Response response = request.contentType(prop.getProperty("JSON")).get();
		 int statusCode = response.getStatusCode();
		 String bodyS = response.getBody().asString();
		 JsonPath js= new JsonPath(bodyS);
		   int count=js.get("data.size()");
		   
		   for(int i=0;i<count;i++)
		   {
			  System.out.println(js.get("data["+i+"].id"));
			  System.out.println(js.get("data["+i+"].email"));
			  System.out.println(js.get("data["+i+"].first_name"));
		   }
		 
		 Assert.assertEquals(statusCode, 200);
		
	}
	
	@Test(priority=2)
	public void getSingleUser() {
		RestAssured.baseURI = prop.getProperty("ENDPOINT2");
		RequestSpecification request = RestAssured.given();
		Response response = request.contentType(prop.getProperty("JSON")).get();
		 int statusCode = response.getStatusCode();
		 String bodyS = response.getBody().asString();
		 Assert.assertEquals(statusCode, 200);
		 Assert.assertNotNull(bodyS);
		
	}
	

	@Test(priority=3)
	public void postData() {
		 RestAssured.baseURI = prop.getProperty("ENDPOINT3");
		 RequestSpecification request = RestAssured.given();
		 JSONObject requestParams = new JSONObject();
		 requestParams.put("name", "morpheus");
		 requestParams.put("job", "leader");
		 Response response = request.contentType(prop.getProperty("JSON")).body(requestParams.toString()).post();
		 int statusCode = response.getStatusCode();
		 Assert.assertEquals(statusCode, 201);
		String id =  response.jsonPath().get("id");
		String name = response.jsonPath().get("name");
		Assert.assertEquals(name, "morpheus");
		Assert.assertNotNull(id);
		 
	}
	
	@Test(priority=4)
	public void updateData() {
		 RestAssured.baseURI = prop.getProperty("ENDPOINT2");
		 RequestSpecification request = RestAssured.given();
		 JSONObject requestParams = new JSONObject();
		 requestParams.put("name", "morpheus");
		 requestParams.put("job", "zion resident");
		 Response response = request.contentType("application/json").body(requestParams.toString()).put();
		 int statusCode = response.getStatusCode();
		 String responseJob = response.jsonPath().get("job");
		 Assert.assertEquals(statusCode, 200);
		 Assert.assertEquals(responseJob, "zion resident");
	}
	
	
	
	@Test(priority=5)
	public void checkLoginSuccessful() throws FileNotFoundException {
		 RestAssured.baseURI = prop.getProperty("ENDPOINT4");
		 RequestSpecification request = RestAssured.given();
		 JSONObject requestParams = new JSONObject();
		 requestParams.put("email", "eve.holt@reqres.in");
		 requestParams.put("password", "cityslicka");
		 Response response = request.contentType(prop.getProperty("JSON")).body(requestParams.toString()).post();
		 int statusCode = response.getStatusCode();
		 String token = response.jsonPath().get("token");
		 Assert.assertEquals(statusCode, 200);
		 Assert.assertEquals(token, "QpwL5tke4Pnpja7X4");
		
	}
	
	@Test(priority=6)
	public void checkLoginUnsuccessful() throws FileNotFoundException {
		 RestAssured.baseURI = prop.getProperty("ENDPOINT4");
		 RequestSpecification request = RestAssured.given();
		 JSONObject requestParams = new JSONObject();
		 requestParams.put("email", "peter@klaven");
		 Response response = request.contentType(prop.getProperty("JSON")).body(requestParams.toString()).post();
		 int statusCode = response.getStatusCode();
		 String bodyS = response.getBody().asString();
		 Assert.assertEquals(statusCode, 400);
		 Assert.assertTrue(bodyS.contains("Missing password"), "Missing password");
		
	}
	
	@Test(priority=7)
	public void registerSuccessful() throws FileNotFoundException {
		RestAssured.baseURI = prop.getProperty("ENDPOINT5");
		 RequestSpecification request = RestAssured.given();
		 JSONObject requestParams = new JSONObject();
		 requestParams.put("email", "eve.holt@reqres.in");
		 requestParams.put("password", "pistol");
		 Response response = request.contentType(prop.getProperty("JSON")).body(requestParams.toString()).post();
		 int statusCode = response.getStatusCode();
		String bodyS = response.getBody().asString();
		 Assert.assertEquals(statusCode, 200);
		 Assert.assertTrue(bodyS.contains("QpwL5tke4Pnpja7X4"));
		
	}
	
	@Test(priority=8)
	public void registerUnsuccessful() throws FileNotFoundException {
		RestAssured.baseURI = prop.getProperty("ENDPOINT5");
		 RequestSpecification request = RestAssured.given();
		 JSONObject requestParams = new JSONObject();
		 requestParams.put("email", "sydney@fife");
		 Response response = request.contentType(prop.getProperty("JSON")).body(requestParams.toString()).post();
		 int statusCode = response.getStatusCode();
		 String bodyS = response.getBody().asString();
		 Assert.assertEquals(statusCode, 400);
		 Assert.assertTrue(bodyS.contains("Missing password"), "Missing password");
		
	}
	
	@Test(priority=9)
	public void getListOfResources() throws FileNotFoundException {
		RestAssured.baseURI = prop.getProperty("ENDPOINT6");
		 RequestSpecification request = RestAssured.given();
		 Response response = request.contentType(prop.getProperty("JSON")).get();
		 int statusCode = response.getStatusCode();
		 String bodyS = response.getBody().asString();
		 JsonPath js= new JsonPath(bodyS);
		   
		   int count=js.get("data.size()");
		   for(int i=0;i<count;i++)
		   {
			  System.out.println(js.get("data["+i+"].id"));
			  System.out.println(js.get("data["+i+"].name"));
			  System.out.println(js.get("data["+i+"].year"));
			  System.out.println(js.get("data["+i+"].color"));
			  System.out.println(js.get("data["+i+"].pantone_value"));
		   }
		 
		 Assert.assertEquals(statusCode, 200);
		 Assert.assertEquals(count, 6);
	}
	
	@Test(priority=10)
	public void singleResourseNotFound() throws FileNotFoundException {
		RestAssured.baseURI = prop.getProperty("ENDPOINT7");
		RequestSpecification request = RestAssured.given();
		Response response = request.contentType(prop.getProperty("JSON")).get();
		 int statusCode = response.getStatusCode();
		 String bodyS = response.getBody().asString();
		 Assert.assertEquals(statusCode, 404);
		 Assert.assertEquals("{}",bodyS);
	}
} 
