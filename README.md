# SpringBootTrivia[![Build Status](https://travis-ci.org/EmielMuit/SpringBootTrivia.png?branch=master)](https://travis-ci.org/EmielMuit/SpringBootTrivia)
A small demo project using SpringBoot framework

Project is built with maven:
```mvn clean package```
This generates:
```SpringBootTrivia/target/triviawebservice-0.0.1-SNAPSHOT.jar```
To run this application, from the project root:
```java -jar target/triviawebservice-0.0.1-SNAPSHOT.jar```

At localhost:8080 a 5 question trivia quiz is running.

At localhost:8080/questions the questions and possible answers for the quiz can be retrieved.

At localhost:8080/checkanswers you can manually post an int array containing indices of correct answers in order.

After finishing the quiz a new one appears.
