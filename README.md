# oauth2-with-jira
OAuth2 authentication with Jira

## Getting Started

### Prerequisites

* [Java 11](https://openjdk.java.net/projects/jdk/11/)
* [Maven](https://maven.apache.org/)

### Configuration

Create an ```application-jira.properties``` file with these properties:

```
spring.security.oauth2.client.registration.jira.client-id= ...
spring.security.oauth2.client.registration.jira.client-secret= ...
```

### Running the app in dev mode

Run ```mvn spring-boot:run``` from the command line.

## Running the tests

Run ```mvn clean install``` from the command line.

## Deployment

## Configuration

## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/)
* [Maven](https://maven.apache.org/)

## License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Authors

* **[Björn Wilmsmann](https://bjoernkw.com)**
