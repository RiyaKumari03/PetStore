package requestPayload;

import java.util.ArrayList;
import java.util.List;

import requestPojo.Category;
import requestPojo.PetStore;
import requestPojo.Tags;
import resources.APIResources.Status;

public class AddPetPayload {
	
	PetStore petStore;
	
	public PetStore payload(int petId, String petName, int categoryId, String categoryName, String photoUrls,
			int tagsId, String tagsName,Status petStatus){
		
		petStore = new PetStore();
    	petStore.setId(petId);
    	petStore.setName(petName);
    	List<String> lst = new ArrayList<String>();
    	lst.add(photoUrls);
    	petStore.setPhotoUrls(lst);
    	petStore.setStatus(petStatus.getStatus());
    	Category category = new Category();
    	category.setId(categoryId);
    	category.setName(categoryName);
    	petStore.setCategory(category);
    	Tags tag = new Tags();
    	tag.setId(tagsId);
    	tag.setName(tagsName);
    	List<Tags> tags = new ArrayList<Tags>();
    	tags.add(tag);
    	petStore.setTags(tags);
    	
    	return petStore;
	}

}
