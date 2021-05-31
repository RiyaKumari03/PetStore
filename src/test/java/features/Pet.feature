Feature: Validating Pet API's


Scenario Outline:
Given Create Add Pet Payload with "<petId>", "<petName>", "<categoryId>", "<categoryName>", "<photoUrls>", "<tagsId>", "<tagsName>" and "<status>"
When Call "AddPetAPI" with requestType "POST"
Then Verify API Response Status Code should be "200"
Then Verify "GetPetAPIById" response when filter with petId and requestType as "GET" then expected "<petName>" 
Then Verify "GetPetAPIByStatus" response when filter with status and requestType as "GET" then expected "<categoryName>"
Then Verify "GetPetAPIByTags" response when filter with tags and requestType as "GET" then expected "<tagsId>"
When Call "UpdatePetAPIById" to update pet name with "Mouse" and status with "Pending" where request type is "POST"
Then Verify pet name and status got updated with "Mouse" and "Pending"
When Call "UpdatePetAPI" to update category id with "5" and category name with "Carbs" where request type is "PUT"
Then Verify category id and name is updated with "5" and "Carbs" 
When Call "DeletePetAPI" with requestType "DELETE" to delete with petId
Then Verify pet is deleted by calling "GetPetAPIById" with requestType "GET" where response should be "404"

Examples:
|petId|petName|categoryId|categoryName|photoUrls|tagsId|tagsName|status   |
| 15  |Rabbit |3         |Herbivorous |string   |15    |Animal  |Available|