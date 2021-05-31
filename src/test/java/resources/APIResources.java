package resources;

public class APIResources {
		
public enum Status {
	
	Available("available"),
    Pending("pending"),
    Sold("sold"),
    Approved("approved");
	
	private String status;
	Status(String status){
		this.status = status;
	}
	
	public String getStatus(){
		return status;
	}
	public static Status getEnumByString(String value){
        for(Status e : Status.values()){
            if(e.status.equals(value)) {
            	return e;
            	
            	
            }
        }
        return null;
    }
}

public enum URIResources {

	AddPetAPI("/pet"),
	GetPetAPIById("/pet/{petId}"),
	GetPetAPIByStatus("/pet/findByStatus"),
	GetPetAPIByTags("/pet/findByTags"),
	UpdatePetAPIById("/pet/{petId}"),
	UpdatePetAPI("/pet"),
	DeletePetAPI("/pet/{petId}"),
	UploadImageAPIById("/pet/{petId}/uploadImage"),
	AddStoreOrderAPI("/store/order"),
	GetStoreOrderById("/store/order/{orderId}"),
	GetInventory("/store/inventory"),
	DeleteStoreOrderAPI("/store/order/{orderId}"),
	UserLoginAPI("/user/login"),
	UserLogoutAPI("/user/logout"),
	GetUserAPIByUsername("/user/{username}"),
	CreateUerAsList("/user/createWithList"),
	CreateUser("/user"),
	UpdateUser("/user/{username}"),
	DeleteUser("/user/{username}");
	
	private String resource;
	URIResources(String resource){	
		this.resource = resource;
	}
	
	public String getURIResources(){
		return resource;
	}
  }


  
	
}
