# PetStore

PetStore Cucumber Framework
The tech stack used in this project are:

JAVA as the programming language for writing test code
Cucumber as the framework
RestAssured for the API 
Eclipse as the preferred IDE for writing java code.
Getting Started
Setup your machine.

Install JDK 1.8
Install Eclipse Neon (Community edition should suffice)
Install Cucumber Eclipse as plugin
Install Natural 0.9
Running tests
Run tests from command line: gradle clean build runTests -Dbrowser=chrome -Ptags=@Test

Run tests from Eclipse:

Right Click on each of the Runner file inside this /PetStoreFrameWork/src/test/java/runner to run the feature
Run as Junit Test

To run all the feature at once update the runner with  features = "src/test/java/features"

Report will be found here: 
Text file report - /PetStoreFrameWork/Logging.txt
HTML report - /PetStoreFrameWork/target/cucumber-html-reports/overview-feature.html
Tests
[Assignment 2]
End to End Test Case is created for this each scenario specifies one test case

Pet Feature File
TestCase-1: Add Pet to store, then validate GET, UPDATE and DELETE feature end to end

Store Order Feature File
TestCase-2: Create Store, then validate GET and DELETE feature end to end

User Feature
TestCase-3: Add User to the system, then validate GET, UPDATE, LOGIN, LOGOUT and DELETE feature end to end
