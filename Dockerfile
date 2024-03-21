FROM openjdk:17-alpine

ADD target/diner.jar /tmp/diner.jar

EXPOSE 8080

CMD java -jar /tmp/diner.jar
