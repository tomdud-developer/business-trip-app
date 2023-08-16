
# Business Trip Reimbursement Calculation Application

Java Application to manage the reimbursement of expenses of a user after a business trip.




## Deployment

Download Apache Tomcat 10.1.12 and Java 11, properly set JAVA_HOME envarionment variable.
To deploy this project run

#### Clone app
```bash
git clone https://github.com/tomdud-developer/BusinessTripReimbursementCalculationApplication.git
cd BusinessTripReimbursementCalculationApplication
```

#### Build war and copy archive to YOUR_TOMCAT_HOME_FOLDER/webapps
```bash
.\mvnw clean
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
http://localhost:8080/businesstripapp
```

## Running Tests

To run tests, run the following command after cloning app

```bash
  .\mvnw test
```


## Authors

- [@tomadud-developer](https://www.github.com/tomadud-developer)

