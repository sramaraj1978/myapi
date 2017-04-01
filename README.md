# myapi

Read Me - Instructions

Install JDK 1.7 or above
Install Maven and .m2/settings.xml with repository settings
Install Tomcat 8 versionBuild & Deploy the application in to TomcatRun the application in ROOT – make necessary
setting in Tomcat server.xml as below
  <Host name="localhost"  appBase="webapps"
      unpackWARs="true" autoDeploy="false" deployOnStartup="false">                                    
  <Context path="" docBase="myapi-0.0.1-SNAPSHOT"></Context>
Access the application by http://localhost:8080/nbiDocs - this would open the SWAGGER UI where the below APIs are exposed –
To display the Transactions with monthly breakdowns in JSON formatAdditional API - 
To display the Transactions with filtering the Donut SPENT Transactions
Scroll down to verify the above API Responses
Pushed the myapi project to Github and the URL is - https://github.com/sramaraj1978/myapi.git


Technical Design

Used JDK 1.7 Spring
Used CXF based REST API to expose Used Maven project 
Used Swagger Tool API for REST documentation and for Testing Purpose
Use the below request payload to access/request
API{"args": {"uid":  1110590645, "token":  "219F172C33C374EB52B09869684705D2",
"api-token":  "AppTokenForInterview", "json-strict-mode": false, "json-verbose-response": false}}
Find the screenshots below from Swagger UI.

Below Non Functional requirements are not covered -

Overall exception, Junit, Logging etcUI screen display etc
