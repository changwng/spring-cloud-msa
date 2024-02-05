FROM openjdk:21
COPY build/libs/*.jar Eureka.jar
CMD ["echo", "discovery service start"]
ENTRYPOINT ["java", "-jar", "Eureka.jar"]
