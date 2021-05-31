package requestPayload;

import java.util.ArrayList;
import java.util.List;

import requestPojo.Category;
import requestPojo.PetStore;
import requestPojo.StoreOrder;
import requestPojo.Tags;
import resources.APIResources.Status;

public class AddStoreOrderPayload {

	StoreOrder orderPayload;
	
	public StoreOrder payload(int id, int petId, int quantity, String shipDate, Status orderStatus, boolean complete){
		
		orderPayload = new StoreOrder();
		orderPayload.setId(id);
		orderPayload.setPetId(petId);
		orderPayload.setQuantity(quantity);
		orderPayload.setShipDate(shipDate);
		orderPayload.setStatus(orderStatus.getStatus());
		orderPayload.setComplete(complete);
		
    	
    	return orderPayload;
	}

}
