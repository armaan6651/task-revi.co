## Requirements

For building and running the application you need:

- [JDK 11.8](https://www.oracle.com/in/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

```shell
mvn spring-boot:run
```

## Local database setup
1. Create a foldr in your machine and set the path in the property `spring.datasource.url` by replacing the placeholder `<path>`
2. For first time startup, set `spring.jpa.hibernate.ddl-auto` to `create`. Subsequently you can change it to `create` to maintain the data


