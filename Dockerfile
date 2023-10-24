FROM maven:3.9.5-sapmachine-21 as builder

ARG SECRET_FILE
RUN mkdir -p /home/app/src/main/resources
RUN echo "$SECRET_FILE" > /home/app/src/main/resources/application-secret.properties

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -B -f /home/app/pom.xml clean package

FROM amazoncorretto:21-alpine as corretto-jdk

# требуется, чтобы работал strip-debug
RUN apk add --no-cache binutils

# собираем маленький JRE-образ
RUN $JAVA_HOME/bin/jlink \
    --verbose \
    --add-modules ALL-MODULE-PATH \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /customjre

FROM alpine:latest
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# копируем JRE из базового образа
COPY --from=corretto-jdk /customjre $JAVA_HOME

# Добавляем пользователя приложения
ARG APPLICATION_USER=appuser
RUN adduser --no-create-home -u 1000 -D $APPLICATION_USER

# Конфигурируем рабочий каталог
RUN mkdir /app && \
    chown -R $APPLICATION_USER /app

USER 1000

COPY --chown=1000:1000 --from=builder ./home/app/target/application.jar /app/app.jar
WORKDIR /app

ENTRYPOINT [ "/jre/bin/java", "-jar", "/app/app.jar" ]
