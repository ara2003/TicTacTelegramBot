FROM openjdk:20-jdk
COPY target/*.jar /home/app/application.jar
ENTRYPOINT ["java","-jar","/home/app/application.jar"]