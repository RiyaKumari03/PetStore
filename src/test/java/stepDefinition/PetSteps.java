package stepDefinition;

import java.io.File;
import java.io.IOException;
import org.junit.runner.RunWith;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import junit.framework.Assert;
import requestPayload.AddPetPayload;
import requestPojo.PetStore;
import resources.APIResources.Status;
import resources.Utils;

@RunWith(Cucumber.class)
public class PetSteps extends Utils {

	RequestSpecification request;
	ResponseSpecification responseSpec;
	PetStore addNewPetStore;
	Response response;
	AddPetPayload addPetPayload = new AddPetPayload();
	private static int petId;
	private static String status;
	private static String tagName;
	
	@Given("^Create Add Pet Payload with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
    public void create_add_pet_payload(int petid, String petname, int categoryid, String categoryname, String photourls, 
    		int tagsid, String tagsname, Status status) throws Throwable {
         
    	addNewPetStore = addPetPayload.payload(petid, petname, categoryid, categoryname, photourls, tagsid, tagsname, status);
    	request = intializeRequest(request);
    	request = request.body(addNewPetStore);
    	
    }
    
	 @When("^Call \"([^\"]*)\" with requestType \"([^\"]*)\"$")
    public void apiCall(String resource, String method) throws IOException{
    	 
		 response = apiCallBasedOnResourceAndMethod(resource,method,request);
		 
    }
    
    @Then("^Verify API Response Status Code should be \"([^\"]*)\"$")
    public void verify_api_response_status_code_should_be_something(int responseCode) throws Throwable {
        
    	Assert.assertEquals(response.getStatusCode(), responseCode);
    }
    
    @Then("^Verify \"([^\"]*)\" response when filter with petId and requestType as \"([^\"]*)\" then expected \"([^\"]*)\"$")
    public void verifyGetAPIByPetId(String resource, String method, String expectedPetName) throws IOException{
    	
    	request = intializeRequest(request);
    	petId = Integer.parseInt(getJsonPath(response,"id"));
    	tagName = getJsonPath(response, "tags[0].name");
    	status = getJsonPath(response, "status");
    	request= request.pathParam("petId", petId);
    	response = apiCallBasedOnResourceAndMethod(resource,method,request);
    	
    	Assert.assertEquals(expectedPetName, getJsonPath(response,"name"));
        
    	
    }
    
    @Then("^Verify \"([^\"]*)\" response when filter with status and requestType as \"([^\"]*)\" then expected \"([^\"]*)\"$")
    public void verifyGetAPIByPetStatus(String resource, String method, String expectedCategoryName) throws IOException{
    	
    	request = intializeRequest(request);
      	request= request.queryParam("status", status);
      	response = apiCallBasedOnResourceAndMethod(resource,method,request);
    	Assert.assertTrue(getJsonPath(response,"category.name").contains(expectedCategoryName));
    }
    
    @Then("^Verify \"([^\"]*)\" response when filter with tags and requestType as \"([^\"]*)\" then expected \"([^\"]*)\"$")
    public void verifyGetAPIByPetTag(String resource, String method, int expectedTagId) throws IOException{
    	
    	request = intializeRequest(request);
      	request= request.param("tags",tagName);
      	response = apiCallBasedOnResourceAndMethod(resource,method,request);
     	Assert.assertEquals(expectedTagId, Integer.parseInt(replaceSpecialCharacter(getJsonPath(response,"tags[0].id"))));
    }
    
    @When("^Call \"([^\"]*)\" to update pet name with \"([^\"]*)\" and status with \"([^\"]*)\" where request type is \"([^\"]*)\"$")
    public void updatePetById(String resource, String petName, Status status, String method) throws IOException{
    	request = intializeRequest(request);
    	petId = Integer.parseInt(replaceSpecialCharacter(getJsonPath(response,"id")));
    	request= request.pathParam("petId", petId);
    	request= request.queryParam("status", status.getStatus());
    	request= request.queryParam("name", petName);
    	response = apiCallBasedOnResourceAndMethod(resource,method,request);
    	
    }
    @Then("^Verify pet name and status got updated with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void verifyNameAndStatusUpdate(String expectedPetName, Status expectedStatus){
    	
    	Assert.assertEquals(expectedPetName, getJsonPath(response, "name"));
    	
    	Assert.assertEquals(expectedStatus.getStatus(), getJsonPath(response, "status"));
    }
    
    @When("^Call \"([^\"]*)\" to update category id with \"([^\"]*)\" and category name with \"([^\"]*)\" where request type is \"([^\"]*)\"$")
    public void updatePet(String resource, int categoryId, String categoryName, String method) throws IOException {
    	
    	addNewPetStore = addPetPayload.payload(Integer.parseInt(getJsonPath(response, "id")), 
    			getJsonPath(response, "name"), categoryId, categoryName, getJsonPath(response, "photoUrls[0]"),
    			Integer.parseInt(getJsonPath(response, "tags[0].id")), getJsonPath(response, "tags[0].name"),
    			Status.getEnumByString(getJsonPath(response, "status")));
    	request = intializeRequest(request);
    	request = request.body(addNewPetStore);
    	response = apiCallBasedOnResourceAndMethod(resource,method,request);
    }
    
    @Then("^Verify category id and name is updated with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void verifyCategoryIdAndName(String expectedId, String expectedName){
        
    	Assert.assertEquals(expectedId, getJsonPath(response, "category.id"));
      	Assert.assertEquals(expectedName, getJsonPath(response, "category.name"));
    	
    }
    
    @When("^Call \"([^\"]*)\" with requestType \"([^\"]*)\" to delete with petId$")
    public void deletePet(String resource, String method) throws IOException{
    	
    	request = intializeRequest(request);
    	request= request.pathParam("petId", petId);
    	response = apiCallBasedOnResourceAndMethod(resource,method,request);
    }
    
    @Then("^Verify pet is deleted by calling \"([^\"]*)\" with requestType \"([^\"]*)\" where response should be \"([^\"]*)\"$")
    public void verifyPetDeleted(String resource, String method, int expectedStatusCode) throws IOException{
    	
    	request = intializeRequest(request);
    	request= request.pathParam("petId", petId);
    	response = apiCallBasedOnResourceAndMethod(resource,method,request);
    	Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
    	
    }
    
    @When("^Call \"([^\"]*)\" to update photoUrls where request type is \"([^\"]*)\"$")
    public void uploadImage(String resource, String method) throws Exception{
    	
    	addNewPetStore =null;
        request = intializeRequest(request);
    	request= request.pathParam("petId", petId).
    			header("Content-Type","application/octet-stream").
    			header("Content-Type","multipart/form-data").
    			formParam("file", new File(userDirectoryPath()+"\\src\\test\\java\\resources\\logo.png"));
    			 
      
        response = apiCallBasedOnResourceAndMethod(resource,method,request);
        
        Assert.assertEquals(response.getStatusCode(), 200);
    	
    }

}
