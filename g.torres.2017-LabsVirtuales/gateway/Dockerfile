ARG MSVC_NAME=gateway

FROM openjdk:17-jdk-alpine as builder

ARG MSVC_NAME

WORKDIR /app/$MSVC_NAME

COPY pom.xml /app
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./$MSVC_NAME/src ./src

RUN ./mvnw clean package -DskipTests

#Optimización de Dockerfile tamaño
FROM openjdk:17-jdk-alpine

ARG MSVC_NAME

WORKDIR /app

COPY --from=builder /app/$MSVC_NAME/target/gateway-0.0.1-SNAPSHOT.jar .

ENV PORT 8090

EXPOSE $PORT

CMD ["java", "-jar", "gateway-0.0.1-SNAPSHOT.jar"]
