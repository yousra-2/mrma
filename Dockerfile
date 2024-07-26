FROM maven:3.6.1-jdk-8-alpine as builder
WORKDIR /usr/app
COPY . ./
RUN mvn clean install

FROM openjdk:8-jre
WORKDIR /usr/src
COPY --from=builder /usr/app/mrm-app-api/target/*.jar ./mrm-app-api.jar
EXPOSE 1090
CMD ["java","-jar","/usr/src/mrm-app-api.jar"]