
# Business Trip Reimbursement Calculation Application

Java Application to manage the reimbursementDetails of expenses of a user after a business trip.

## Link to application deployed on AWS EC2
I deployed application on AWS EC2
http://34.240.9.153:8080/login


## Deployment

Download Apache Tomcat 10.1.12 https://tomcat.apache.org/download-10.cgi and Java 11, properly set JAVA_HOME envarionment variable.
To deploy this project run

#### Clone app
```bash
git clone https://github.com/tomdud-developer/business-trip-app.git
cd business-trip-app
```

#### Build war and copy archive to YOUR_TOMCAT_HOME_FOLDER/webapps
```bash
.\mvnw package
copy target/businesstripapp.war YOUR_TOMCAT_HOME_FOLDER/webapps
//Example: copy target/businesstripapp.war C:/Users/tomas/IdeaProjects/apache-tomcat-10.1.12/webapps
```

#### Go to YOUR_TOMCAT_HOME_FOLDER/bin and run a tomcat server
```bash
cd YOUR_TOMCAT_HOME_FOLDER/bin 
//Example: cd C:/Users/tomas/IdeaProjects/apache-tomcat-10.1.12/bin
.\catalina.bat run
```

#### Open browser and type
```bash
http://localhost:8080/businesstripapp/login
```

## Running Tests

To run tests, run the following command after cloning app

```bash
  .\mvnw test
```

## Screenshots
![tests.png](assets%2Ftests.png)
![admin-panel.png](assets%2Fadmin-panel.png)
![calculator.png](assets%2Fcalculator.png)
![dashboard.png](assets%2Fdashboard.png)


## Authors

- [@tomadud-developer](https://www.github.com/tomadud-developer)

