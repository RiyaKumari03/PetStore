package stepDefinition;

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
import requestPayload.AddStoreOrderPayload;
import requestPojo.StoreOrder;
import resources.Utils;
import resources.APIResources.Status;

@RunWith(Cucumber.class)
public class StoreOrderSteps extends Utils{


	RequestSpecification request;
	ResponseSpecification responseSpec;
	StoreOrder storeOrder;
	Response response;
	AddStoreOrderPayload addStoreOrderPayload = new AddStoreOrderPayload();
	private static int orderId;
	
    @Given("^Get pet inventories by calling \"([^\"]*)\" with requestType as \"([^\"]*)\"$")
    public void getStoreInventory(String resource, String method) throws IOException{
    	
    	request = intializeRequest(request);
    	response = apiCallBasedOnResourceAndMethod(resource,method,request);
    	
    }
    
    @Then("^Verify inventory details with placed as \"([^\"]*)\" and delivered \"([^\"]*)\"$")
    public void verifyInventoryDetails(int expectedPlaced, int expectedDelivered){
    	
    	Assert.assertEquals(expectedDelivered, Integer.parseInt(getJsonPath(response, "delivered")));
    	
    }
    
    @When("^Create Add Store Payload with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
    public void createStorePayload(int id, int petId, int quantity, String shipDate, Status orderStatus, boolean complete) throws IOException{
    	
    	storeOrder = addStoreOrderPayload.payload(id, petId, quantity, shipDate, orderStatus, complete);
    	request = intializeRequest(request);
    	request = request.body(storeOrder);
    	
    }
    
    @When("^Calling \"([^\"]*)\" with requestType \"([^\"]*)\"$")
    public void callingStoreAPI(String resource, String method) throws IOException{
    	
    	response = apiCallBasedOnResourceAndMethod(resource,method,request);
    	    	
    }
    
    @Then("^Verify order is created in store with id as \"([^\"]*)\"$")
    public void verifyStoreCreated(int expectedId){
    	
    	orderId = Integer.parseInt(getJsonPath(response, "id"));
    	Assert.assertEquals(expectedId, orderId);
    	
    }
    
    @Then("^Verify \"([^\"]*)\" response when filter with orderId and requestType as \"([^\"]*)\" then expected \"([^\"]*)\"$")
    public void verifyGetStoreByOrderId(String resource, String method, int expectedQuantity) throws IOException{
    	
    	request = intializeRequest(request);
      	request= request.pathParam("orderId",orderId);
      	response = apiCallBasedOnResourceAndMethod(resource,method,request);
     	Assert.assertEquals(expectedQuantity, Integer.parseInt(getJsonPath(response, "quantity")));
    
    }
    
    @When("^Call \"([^\"]*)\" with requestType \"([^\"]*)\" to delete with storeId$")
    public void deleteStore(String resource, String method) throws IOException{
    	
    	response = apiCallBasedOnResourceAndMethod(resource,method,request);
    	
    }
    
    @Then("^Verify store is deleted by calling \"([^\"]*)\" with requestType \"([^\"]*)\" where response should be \"([^\"]*)\"$")
    public void verifyGetOrderById(String resource, String method, int expectedStatusCode) throws IOException{
    	
    	request = intializeRequest(request);
    	request= request.pathParam("orderId", orderId);
    	response = apiCallBasedOnResourceAndMethod(resource,method,request);
    	Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
    	
    }
    
    
}
