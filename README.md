# CShop

Open source cloth web application that written in java and spring framework (still under development)

## How to Run
This application is packaged as a jar which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run it using the java -jar command.

* Clone this repository
* Make sure you are using JDK 1.8 and Maven 3.x
* You can build the project and run the tests by running mvn clean package
* Once successfully built, you can run the service by one of these two methods:
```
java -jar core/target/core-1.0-SNAPSHOT-spring-boot.jar
```
```
cd core
mvn spring-boot:run
```
Once the application runs you should see something like this

```
2018-02-03 18:46:46.185  INFO 1816 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2018-02-03 18:46:48.992  INFO 1816 --- [           main] io.ermdev.cshop.core.CShopApplication    : Started CShopApplication in 34.43 seconds (JVM running for 43.366)
```

Here are some endpoints you can call:
### Get information about the user
```
http://localhost:8080/api/users
http://localhost:8080/api/users/{id}
http://localhost:8080/api/users/{id}/roles
```

### Create a user resource

```
POST /api/users
Accept: application/json
Content-Type: application/json

{
  "name" : "Kelvin Datu",
  "email" : "sample@mail.com",
  "username" : "kelvz",
  "password" : "123"
}

RESPONSE: HTTP 201 (Created)
Location header: http://localhost:8080/api/users
```

### Update a user resource

```
PUT /api/users/{id}
Accept: application/json
Content-Type: application/json

{
  "name" : "Kelvin Datu",
  "email" : "sample@mail.com",
  "username" : "kelvz",
  "password" : "123"
}

RESPONSE: HTTP 204 (No Content)
```
### To view Swagger 2 API docs

Run the server and browse to localhost:8080/swagger-ui.html

## Questions and Comments: ermdev.io@gmail.com
