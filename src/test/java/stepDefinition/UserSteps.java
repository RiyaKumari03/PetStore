package stepDefinition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import junit.framework.Assert;
import requestPayload.AddPetPayload;
import requestPayload.AddUserPayload;
import requestPojo.PetStore;
import requestPojo.User;
import resources.Utils;

@RunWith(Cucumber.class)
public class UserSteps extends Utils {
	
	RequestSpecification request;
	ResponseSpecification responseSpec;
	User user;
	Response response;
	AddUserPayload addUserPayload = new AddUserPayload();
	private static String username;
	private static String password;
	private static String userListUsername;
	
	@When("^Login to the system with user credentials using \"([^\"]*)\" with requestType \"([^\"]*)\"$")
    public void loginUser(String resource, String method) throws IOException {
		
		request = intializeRequest(request);
		request.queryParam("username", username);
	    request.queryParam("password", password);
	    response = apiCallBasedOnResourceAndMethod(resource,method,request);
		
		
    }
	
	@Then("^User should be able to successfully logged into the system with statusCode \"([^\"]*)\"$")
	public void verifyUserloggedIn(int expectedResponseCode){
		
		Assert.assertEquals(response.getStatusCode(), expectedResponseCode);
	}
	
	@Given("^Create Add User payload with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
    public void createUserPayload(int id, String username, String firstName, String lastName, String email, 
    		String password, String phone,int userStatus) throws IOException{
		
		request = intializeRequest(request);
		user = addUserPayload.payload(id, username, firstName, lastName, email, password, phone, userStatus);
		request = request.body(user);
	}
	
	 @When("^Calling \"([^\"]*)\" with requestType \"([^\"]*)\" to create user$")
	 public void addUser(String resource, String method) throws IOException{
		 
		 response = apiCallBasedOnResourceAndMethod(resource,method,request);
		 
	 }
	 
	 @Then("^Verify user is created  with username as \"([^\"]*)\" and \"([^\"]*)\"$")
	 public void verifyUserCreated(String expectedUsername, String expectedPassword){
		 
		 username = getJsonPath(response, "username");
		 password = getJsonPath(response, "password");
	     Assert.assertEquals(expectedUsername, username);
	     Assert.assertEquals(expectedPassword, password);
	     
	 }
     
	 @Then("^Verify \"([^\"]*)\" response when filter with username and requestType as \"([^\"]*)\" then expected \"([^\"]*)\"$")
	 public void verifyGetUserAPI(String resource, String method, String expectedFirstName) throws IOException{
		 
		 request = intializeRequest(request);
		 request = request.pathParam("username", username);
		 response = apiCallBasedOnResourceAndMethod(resource,method,request);
		 Assert.assertEquals(expectedFirstName, getJsonPath(response, "firstName"));
		 
	 }
	 
	 @When("^Call \"([^\"]*)\" to update user lastName with \"([^\"]*)\" and phoneNumber with \"([^\"]*)\" where request type is \"([^\"]*)\"$")
	 public void updateUserDetails(String resource, String lastName, String phoneNumber, String method) throws IOException {
		 
		
		 user = addUserPayload.payload(Integer.parseInt(getJsonPath(response, "id")), username, 
				 getJsonPath(response, "firstName"), lastName, getJsonPath(response, "email"), getJsonPath(response, "password"),
				 phoneNumber, Integer.parseInt(getJsonPath(response, "userStatus")));
		
		 request = intializeRequest(request);
		 request = request.pathParam("username", username).body(user);
		 response = apiCallBasedOnResourceAndMethod(resource,method,request);
		 
	 }
	 
	 
	 @Then("^Verify user lastName and phoneNumber got updated with \"([^\"]*)\" and \"([^\"]*)\"$")
	 public void verifyUpdatedDetails(String expectedLastName, String expectedPhoneNumber) throws JsonMappingException, JsonProcessingException{
		 
		 Assert.assertEquals(expectedLastName, getJsonPath(response, "lastName"));
		 Assert.assertEquals(expectedPhoneNumber, getJsonPath(response, "phone"));
		 
     }
	 
	 @When("^Calling \"([^\"]*)\" with requestType \"([^\"]*)\" and payload \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
	 public void createUserListPayload(String resource, String method, int id, String username, String firstName, String lastName, String email, 
	    		String password, String phone,int userStatus) throws IOException{
		 
		 request = intializeRequest(request);
		 user = addUserPayload.payload(id, username, firstName, lastName, email, password, phone, userStatus);
		 List<User> list = new ArrayList<User>();	
		 list.add(user);
		 request = request.body(list);		 
		 response = apiCallBasedOnResourceAndMethod(resource,method,request);
		 
	 }
	 
	 @Then("^Verify created user details \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
	 public void verifyCreatedUserList(String expectedUserName, String expectedFirstName, String expectedLastName){
		 
		 userListUsername = replaceSpecialCharacter(getJsonPath(response, "username"));
		 Assert.assertEquals(expectedFirstName, replaceSpecialCharacter(getJsonPath(response, "firstName")));
		 Assert.assertEquals(expectedLastName, replaceSpecialCharacter(getJsonPath(response, "lastName")));
		  
	 }
	 
	 @When("^Call \"([^\"]*)\" with requestType \"([^\"]*)\" to delete with user$")
	 public void deleteUser(String resource, String method) throws IOException{
		 
		 request = intializeRequest(request);
		 request = request.pathParam("username", userListUsername);
		 response = apiCallBasedOnResourceAndMethod(resource,method,request);
	 }
	 
	 @Then("^Verify user is deleted by calling \"([^\"]*)\" with requestType \"([^\"]*)\" where response should be \"([^\"]*)\"$")
	 public void verifyUserDeleted(String resource, String method, int expectedResponseCode) throws IOException{
		 
		    request = intializeRequest(request);
	    	request= request.pathParam("username", userListUsername);
	    	response = apiCallBasedOnResourceAndMethod(resource,method,request);
	    	Assert.assertEquals(response.getStatusCode(), expectedResponseCode);
	 }
	 
	 @Then("^Verify user logout of the system by calling \"([^\"]*)\" with requestType \"([^\"]*)\" where response should be \"([^\"]*)\"$")
	 public void verifyLogout(String resource, String method, int expectedResponseCode) throws IOException{
		 
		    request = intializeRequest(request);
	       	response = apiCallBasedOnResourceAndMethod(resource,method,request);
	    	Assert.assertEquals(response.getStatusCode(), expectedResponseCode);
	 }   
	 
}
