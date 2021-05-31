package requestPayload;

import requestPojo.StoreOrder;
import requestPojo.User;
import resources.APIResources.Status;

public class AddUserPayload {

	User user;
		
public User payload(int id, String username, String firstName, String lastName, String email, String password, String phone,int userStatus){
		
	    user = new User();
	    user.setId(id);
	    user.setUsername(username);
	    user.setFirstName(firstName);
	    user.setLastName(lastName);
	    user.setEmail(email);
	    user.setPassword(password);
	    user.setPhone(phone);
	    user.setUserStatus(userStatus);
	   
    	
    	return user;
	}
	
	
	
}
