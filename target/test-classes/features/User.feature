Feature: Validating User API's


Scenario Outline:
Given Create Add User payload with "<id>", "<username>", "<firstName>", "<lastName>", "<email>", "<password>", "<phone>" and "<userStatus>"
When Calling "CreateUser" with requestType "POST" to create user
Then Verify user is created  with username as "<username>" and "<password>"
When Login to the system with user credentials using "UserLoginAPI" with requestType "GET"
Then User should be able to successfully logged into the system with statusCode "200"
Then Verify "GetUserAPIByUsername" response when filter with username and requestType as "GET" then expected "<firstName>"
When Call "UpdateUser" to update user lastName with "Miller" and phoneNumber with "23456224" where request type is "PUT"
Then Verify user lastName and phoneNumber got updated with "Miller" and "23456224"
When Calling "CreateUerAsList" with requestType "POST" and payload "<id>", "<userListUserName>", "<userListFirstName>", "<userListLastName>", "<email>", "<password>", "<phone>" and "<userStatus>"
Then Verify created user details "<userListUserName>", "<userListFirstName>" and "<userListLastName>"
When Call "DeleteUser" with requestType "DELETE" to delete with user
Then Verify user is deleted by calling "GetUserAPIByUsername" with requestType "GET" where response should be "404"
Then Verify user logout of the system by calling "UserLogoutAPI" with requestType "GET" where response should be "200"
Examples:
|id|username|firstName|lastName  |email           |password|phone|userStatus|userListUserName|userListFirstName|userListLastName|
|3 | Anne  |Anne     |Fayan    |fayan@email.com |12345   |223756|1          |XYZ             |   John           | Miller         |


