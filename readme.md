# Survey Application

The goal of this project is to build a backend solution to perform surveys. This solution is able to run in the cloud.

 The following features are available by REST services:
 
 * Creation of new questions.
 * Opening the vote on a question, with a set duration or one minute by default.
 * Members voting on a question. The allowed answers are YES or NO, and the same member can vote only once per question.
 * Check the current amount of votes for each answer for a question and if it is already finished or not.
 
 Security of access are not being treated in this project.
 
## Running the application
 
Before running the below commands, you must build the project by executing the following command:
 
```
gradlew build
```

To execute the application you need to have a MySQL instance running.
If you have, you can launch the application passing the database URL by JVM parameter:

Example:
 
```
gradlew bootRun -Dspring.datasource.url=jdbc:mysql://192.168.99.100:3306/surveydb
```
Then access using the following link:

```
http://localhost:8080
```
If you don't have an instance of mysql running, you can launch the application using docker-compose:
 
```
docker-compose build
docker-compose up
```

Then access using the following link:

```
http://<YOUR-DOCKER-MACHINE-IP>:8080
``` 

## Business decisions

 The decision of allowing the creation of several sessions for the same question is based on the idea that a question could be reused to vote several times, like, for example, a team deciding every morning in which restaurant they will go to have a lunch.
 
 It was defined that the user can configure the start date and time for a session. This approach simulates what happen in a real government election, for example.
 
 
## Technical decisions
 
 Internally the domain objects are moved between layers through DTO objects.
 To expose these objects to the front layer and receive them from it, it is being used Input and Output objects. This approach guarantees that if an update is needed in the output value, for example, it may not be necessary to change the DTO object. So, the impact in the code is lower.
 
 In order to guarantee that an associate can vote only once per session, it was created in the session table a composed primary key, containing the session id and the associate id.
 So, when an associate tries to vote twice in the same session, internaly a DataIntegrityViolationException exception is thrown which is mapped to our exception called DuplicatedVoteException.
 This approach was used due to the behavoring of a survey. Oftenly people vote only once, so performing a query  before all insertions to check if a person have already voted is a processing waste.
 Besides that, if we need to scale the application launching several PODs in a kubernetes container, it could occurs of a POD executes a query to check if an associate have already voted and right after that, another POD executes the insertion, so the first POD will understand that it is the first time that the associate is voting. In our approach we leave this validation responsability to the DBMS.
 That is also the reason why during a vote insertion is used the method persist from EntityManager, which must be used only for new entities, otherwise the DataIntegrityViolationException exception is thrown.
 
 To compile the survey results, it was created queries to be executed directly in the database, getting and groupping the desired information. It was implemented in this way aiming improving performance.
 It is more performatic getting data processed from database than getting all data and processing it after, using, for example, streams, what could be an approach too.
 
## Applied technologies
 
 **Java 8**
 
 It was used this language mainly because of my knowledge and experience with Java over the years. The version 8 was chosen due to the improvement of performance when related to the past versions and due to the possibility to use lambda expressions, Streams, Optional and the new API for date and time.
 
 **Gradle 5.2.1**
 
 Gradle is a build automation tool used also to control the project dependencies. Unlike other tools like Ant and Maven, Gradle uses a powerful and expressive domain-specific language (DSL) implemented in Groovy instead of XML, what seems more friendly for developers.   
 
 **Spring Boot 2.1.3**
 
 This project was developed using microservices architecture, exposing REST APIs, an appropriate scenario to apply this framework. Besides this, Spring Boot is a Java-based framework and makes faster and easier the project creation, hidden from the developer a lot of configurations that he would have to do without using Spring Boot. 
 Having this in mind, it accelerates the development process. 
 
 Another advantage of using Spring Boot is that it contains an embedded servlet container, so we can start our application by simply running the created jar file. 
  
 **MySQL 5.7**
 
 Due to our technical decision of using data integrity constraint to guarantee only one vote per session per associate, and due to the structure of relation among votes, sessions, and questions, we decided to use a relational database. 
 
 MySQL was chosen due to my experience using this DBMS, and even not being designed for scale, unlike noSQL databases, MySQL has the option to use master-slave architecture for scalability and high availability.
  
 **Swagger 2.9.2**
 
 Swagger is an easy usage framework to document APIs generating a interactive documentation that allows users to immediately test the APIs operations without a need of a developed frontend neither the usage of complex commands from command line or third part tools.
 
 **Docker**
 
 Docker was used in order to configure the database creating a container having all configurations needed to run the application. It was used also to make easier the publish in a cloud machine. 