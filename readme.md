# Order Capture Web Service

## Description

An implementation of a very basic standalone web service, that manages the storage, retrieval and update (CRUD functionality) of product orders, by exposing the necessary APIs to other services.

## Implementation Summary
The web service has been implemented by using the Spring framework and specifically Spring Boot. This Spring Boot application has been configured in such way to produce a stand-alone, production-ready Spring Application with an embedded Tomcat application server to host a Spring Rest Controller and Spring JPA Data functionality.

There are two main layers of implementation, the model layer and the controller layer (following the MVC architecture). In the model layer Spring JPA Data library is used in order to provide connectivity to database through Hibernate framework.

The controller layer uses the Spring Web Rest Controllers to provide the mechanisms and the necessary endpoints for exposing the CRUD functionality as  RESTful services.

Additionally, in order to test the functionality of the application there have been implemented a number of unit tests for the model layer and a number of integration tests for testing both the controller and the model layers.

## Installation and use

### Downloading the source code
In order to use the application you need to download or clone the code.

### Configuring the databases
This application is implemented with two environments in mind. One for testing and one for the runtime. That means that there are two databases, Postgresql for the runtime and the embedded H2 for testing.

So the first thing to do prior using the application is to change the configuration for the desired databases. If you don’t want to use Postgresql and/or H2 you are free to change the drivers that are imported as maven dependencies in the pom.xml file.

In case you choose not to change the embedded H2 database, its configuration is ready to use (as it is already configured in the application.properties file in the resources directory of the test environment).

On the other hand, you will be needing to change the properties in the application.properties file either of the test or the runtime environment and set the desired values for accessing the database that you have at your disposal. The same occurs also with the preselected Postgresql database configuration.

The properties you should change is the following:

    spring.datasource.url - the address of the database
    spring.datasource.username - user for the database
    spring.datasource.password - user’s password
    spring.datasource.platform - the name of the platform to use i.e. postgresql, mysql etc
    spring.datasource.driverClassName - the name of the driver

It is advisable not to change the spring.jpa properties (nor the hibernate ones) in case you know what you are doing.

### Packaging the application
Since it is a maven based application, to create the package you need to go to the projects directory and run:

    mvn clean package

This will compile, run all configured tests and finally package the application creating the order-capture-web-service-0.0.1-SNAPSHOT.jar file within the target directory.

When the application runs the embedded Tomcat server will start running at the default port which is the 8080. In case port 8080 is not available (some other service is already using it) then in the application.properties (of the runtime environment) add the following property:

    server.port=9090

To run the application go to the target directory and run:

    $>java -jar order-capture-web-service-0.0.1-SNAPSHOT.jar

## Exposing the CRUD functionality

### Service Endpoints

The CRUD functionality is exposed through the following endpoints:

#### Product

#### GET messages

    server_name:8080://products - lists all available products.
    server_name:8080://products/product?id=productID - retrieves the selected product based its ID.

#### POST messages

    server_name:8080://products/create - creates a product. The JSON object as input payload, has the following structure:

    {   "name": <nameValue>,
       "sku": <SKUValue>,
       "description": <descriptionValue>
    }

#### DELETE messages

    server_name:8080://products/delete?id=productID - deletes the selected product by its ID.

#### Customer
#### GET messages

    server_name:8080://customers - lists all available customers.
    server_name:8080://customers/customer?id=customerID - retrieves the selected customer based its ID.

#### POST messages

    server_name:8080://customers/create - creates a customer. The JSON object as input payload, has the following structure:
    { 	"firstName": <firstNameValue>,
        "lastName": <lastNameValue>,
        "address": <addressValue>
    }

#### DELETE messages

    server_name:8080://customers/delete?id=customerID - deletes the selected customer by its ID.

#### Order
#### GET messages

    server_name:8080://orders - lists all available orders.
    server_name:8080://orders/order?id=orderID - retrieves the selected order based its ID.

#### POST messages

    server_name:8080://orders/create - creates an order. The JSON object as input payload, has the following structure:
    { 
        "customerID": <customerIDValue>, 
        "productIDs": ["productID1","productID2","productID3",...]
    } 

#### DELETE messages

    server_name:8080://orders/delete?id=orderID - deletes the selected order by its ID.

#### PUT messages

    server_name:8080://orders/update?id=orderID&status=orderstatus - updates the selected order by its ID and update its orderStatus to the orderstatus value.

### Notes and assumptions on exposed functionality

#### Deleting resources

When deleting a product, in case there is an order that has a product of such, then the product is also deleted by the order without deleting the order (the other approach would be to delete the order as a whole). In case the deleted product is the only product of an order, then the order is also deleted because an order with no products has no purpose.

When deleting a customer then all the orders that have been assigned to that customer are also deleted.


## Technical Implementation Details

### Model Layer
The Spring JPA Data framework was selected for the model layer along with Hibernate libraries. It is worth mentioning that the application doesn’t heavily   depend on Hibernate libraries, apart from most several properties in the properties files and only a few annotations (GenericGenerator) in the model resources (Product, Customer and Order). This gives the ability to select a different implementor of the JPA specification such as EclipseLink or OpenJPA etc.

So, for each resource (Product, Customer and Order) a Repository has been implemented by extending the CrudRepository class.

The application was implemented having testing in mind right from the beginning. That is the reason for having two different databases like Postgresql for the runtime environment and the H2 as the embedded database for the testing environment. That was succeeded with some extended effort for keeping separate databases for different scopes since this was never asked for me to implement with the JPA Data approach before.

As mentioned above, the drivers are imported as maven dependencies and they are tagged in which scope they belong accordingly.

### Controller layer
In the controller layer, the Spring Web Framework has been used. RestController annotation has decorated the three classes that are responsible for providing connectivity to the RESTful services.

Each controller (ProductController, CustomerController and OrderController) has a main request mapping according to the name of the controller (products, customers, orders) and the functionality is described in the path such as create, delete and update (wherever applicable) or with a single  denoting word such as product, customer or order for retrieving single resources.

In case of erroneous usage of the endpoints (missing IDs or objects for input) exceptions are thrown (ResourceNotFoundException) to inform the user of the error.

Apart the three controllers an additional one (ErrorResponseController) handles the throwable exceptions in order to return a meaningful message to the user  and log the error to the server’s log.

### Testing
As mentioned above, Unit tests with JUnit are testing the model layer’s functionality and database connectivity. Furthermore, integration testing via postman is also featured. A separate file (edet.postman_collection.json) is accompanying the current documentation which can be imported and executed in a postman application.

### Additional thoughts and notes
The application could be well distinguished in separate modules at least one for the model and one for the Spring Boot application that would contain the Controller part. This will be done in next steps.

This will also help with CI in mind i.e. by using separate jobs in Jenkins and keeping the produced archives in a different repository such as Archiva.

Additionally, dockering the Spring Boot application will help distributing it.
