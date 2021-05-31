Feature: Validating Store Order API's


Scenario Outline:
Given Get pet inventories by calling "GetInventory" with requestType as "GET"
Then Verify inventory details with placed as "100" and delivered "50"
When Create Add Store Payload with "<id>", "<petId>", "<quantity>", "<shipDate>", "<status>" and "<complete>"
When Calling "AddStoreOrderAPI" with requestType "POST"
Then Verify order is created in store with id as "<id>"
Then Verify "GetStoreOrderById" response when filter with orderId and requestType as "GET" then expected "<quantity>"
When Call "DeleteStoreOrderAPI" with requestType "DELETE" to delete with storeId
Then Verify store is deleted by calling "GetStoreOrderById" with requestType "GET" where response should be "404"
Examples:
|id|petId|quantity|shipDate                         |status   |complete|
|1 | 15  |7       |2021-05-30T21:07:33.143Z         |Approved |true    |


