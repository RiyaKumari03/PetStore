package apiCalls;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requestPojo.PetStore;
import resources.Utils;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APICalls {

	private static Logger logger = LoggerFactory.getLogger(APICalls.class);
	public static Response postAPICall(String URL, RequestSpecification requestSpec) throws IOException{
		
		Response response;
		
			logger.info("URL {}, RequestSpec {}",URL,requestSpec);
			
			response =  given().log().all().spec(requestSpec).when().post(URL).then().log().all().extract().response();
			
			logger.info("Response {}", response.getBody());
			
		
		return response;
		
	}
	
    public static Response getAPICall(String URL, RequestSpecification requestSpec){
		
	    
		logger.info("URL {}, RequestSpec {}",URL,requestSpec);
		
		Response response =  given().log().all().spec(requestSpec).when().get(URL).then().log().all().extract().response();
		
		logger.info("Response {}", response.getBody());
		
			
		return response;
		
	}
    
    public static Response putAPICall(String URL,  RequestSpecification requestSpec){
		
		logger.info("URL {}, RequestSpec {}",URL,requestSpec);
		
		Response response =  given().log().all().spec(requestSpec).when().put(URL).then().log().all().extract().response();
		
		logger.info("Response {}", response.getBody());
		
			
		return response;
		
	}
    
    public static Response deleteAPICall(String URL, RequestSpecification requestSpec){
		
	    
		logger.info("URL {}, RequestSpec {}",URL,requestSpec);
		
		Response response =  given().log().all().spec(requestSpec).when().delete(URL).then().log().all().extract().response();
		
		logger.info("Response {}", response.getBody());
		
			
		return response;
		
	}
	

}
