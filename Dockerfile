FROM openjdk:11

EXPOSE 8080

WORKDIR /app/keeper

RUN mkdir ./images

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} keeper.jar

ENTRYPOINT ["java", "-jar", "keeper.jar"]


