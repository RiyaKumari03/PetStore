package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Properties;

import org.apache.commons.codec.binary.Base64;

import apiCalls.APICalls;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources.URIResources;

public class Utils {

	static RequestSpecification request;
	static ResponseSpecification response;
	static RequestSpecBuilder spec;
	public static RequestSpecification requestSpecification() throws IOException{
		
		if(request==null){
		PrintStream log = new PrintStream(new FileOutputStream("Logging.txt"));
		
		 request = new RequestSpecBuilder().
					setBaseUri(getGlobalProperties("baseUrl")).
					setContentType(ContentType.JSON).
					setAccept(ContentType.XML).
					addFilter(RequestLoggingFilter.logRequestTo(log)).
					addFilter(ResponseLoggingFilter.logResponseTo(log)).
					build();
		}
		else{
			
			request = new RequestSpecBuilder().
					setBaseUri(getGlobalProperties("baseUrl")).
					setContentType(ContentType.JSON).
					setAccept(ContentType.XML).
					build();
			
		}
		return request;
	}

	
	public static String getGlobalProperties(String key) throws IOException{
		
		Properties prop = new Properties();
		FileInputStream stream = new FileInputStream(userDirectoryPath()+"\\src\\test\\java\\resources\\Global.properties");
		prop.load(stream);
		
		return prop.getProperty(key);
		 
		
	}
	
	public static String userDirectoryPath(){
		
		Path currentWorkingDir = Paths.get("").toAbsolutePath();
        String path = currentWorkingDir.normalize().toString();
        return path;
	}
	
	public String getJsonPath(Response resp ,String key){
		
		String response = resp.asString();
		JsonPath json = new JsonPath(response);
		return json.get(key).toString();
	}
	
	
	public static RequestSpecification intializeRequest( RequestSpecification request) throws IOException{
		
		request = null;
		request = requestSpecification();
		return request;		
	}
	
	public String replaceSpecialCharacter(String string){
		
		return string.replaceAll("\\[", "").replaceAll("\\]","");
	}
	
	public String convertFileToBinary() throws Exception{
		
		File f =  new File(userDirectoryPath()+"\\src\\test\\java\\resources\\logo.png");
        String encodstring = encodeFileToBase64Binary(f);
        return encodstring;
	}
	
	private static String encodeFileToBase64Binary(File file) throws Exception{
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.encodeBase64(bytes), "UTF-8");
    }
	
	public Response apiCallBasedOnResourceAndMethod(String resource, String method, RequestSpecification request) throws IOException{
		
		Response response = null;
		
		 
		 if(method.equalsIgnoreCase("POST"))
   	     response = APICalls.postAPICall(URIResources.valueOf(resource).getURIResources(),
   	    		 request);
		 else if(method.equalsIgnoreCase("GET"))
			 response = APICalls.getAPICall(URIResources.valueOf(resource).getURIResources(), 
					 request);	
		 else if(method.equalsIgnoreCase("PUT"))
		    	response = APICalls.putAPICall(URIResources.valueOf(resource).getURIResources(), 
		    		 request);
		 else if(method.equalsIgnoreCase("DELETE"))
		    	response = APICalls.deleteAPICall(URIResources.valueOf(resource).getURIResources(), 
		    		 request);
			 	 
		 return response;
	}
   	 

}
