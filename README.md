"# Library-CRUD-App" 
"# Library-CRUD-App" 

## Introduction:

This document provides comprehensive information on setting up the project, configuring the database, understanding the API endpoints, testing procedures, and accessing the project on GitHub.


## Technologies Used:

* Spring Framework: Used to build the API, providing an architecture.
* H2 Database: A database used for development and testing.
* Postman: Used to test API endpoints during development.
* JUnit: Employed for writing unit tests to ensure the reliability of the code.
* JMeter: Utilized for performance testing to evaluate the API's scalability and responsiveness.
* GitHub: Version control system for managing and sharing the source code.

## Setting Up the Project:

To set up the project locally, follow these steps:
* Navigate to the project directory.
* Build the project using Maven.
* Run the application.
> API port: 'http://localhost:9090'

## Database Configuration:

API uses an H2 database for development and testing. The database configuration details can be found in the application.properties file.


## API Endpoints:

##### The API provides the following CRUD endpoints:
* Create: POST /api/
* Read: GET /api/
* Update: PUT /api/
* Delete: DELETE /api/
>> sample from an endpoint that gets the books that are published before the entered year :
>>![image (1)](https://github.com/ymirioglu/Library-CRUD-App/assets/130938062/199a4a91-e71d-4092-a197-84b2083351f0)

>
>
> For detailed information on request and response formats, please refer to the API Documentation.



## Unit Testing:

Unit tests have been implemented to validate the functionality of each endpoint. 


## Performance Testing:

Performance tests have been conducted using JMeter to assess the API's scalability and responsiveness.


## GitHub Repository:

The source code for this project is available on GitHub.


