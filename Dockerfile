FROM openjdk:8-jdk-alpine
ADD target/Sally2-0.0.1-SNAPSHOT.jar .
EXPOSE 5002
CMD java -jar Sally2-0.0.1-SNAPSHOT.jar
