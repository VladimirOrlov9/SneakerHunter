FROM adoptopenjdk/openjdk11:alpine-jre
COPY target/SneakerHunter-0.0.1-SNAPSHOT.jar /spring-boot-app.jar
ENTRYPOINT ["java","-jar","spring-boot-app.jar"]