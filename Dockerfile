FROM maven:3.8.3-openjdk-21
COPY src /home/app/src
COPY pom.xml /home/app

ARG SECRET_FILE
RUN echo "$SECRET_FILE" > /home/app/src/main/resources/application-secret.properties

RUN mvn -B -f /home/app/pom.xml clean package
EXPOSE 3030
ENTRYPOINT ["java","-jar","/home/app/target/application.jar"]