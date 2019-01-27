# oauth2-with-jira
OAuth2 authentication with Jira

## Getting Started

### Prerequisites

* [Java 1.8 or higher](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven](https://maven.apache.org/)

### Configuration

Before running the application you need to create a properties file named ```application-jira.properties``` and add
these properties with the appropriate values for your Jira app:

```
spring.security.oauth2.client.registration.jira.client-id=...
spring.security.oauth2.client.registration.jira.client-secret=...
```

### Running the app in dev mode

Run ```mvn spring-boot:run``` from the command line.

## Running the tests

Run ```mvn clean install``` from the command line.

## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/)
* [Maven](https://maven.apache.org/)

## License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Authors

* **[Björn Wilmsmann](https://bjoernkw.com)**
