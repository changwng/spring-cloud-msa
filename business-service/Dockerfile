FROM openjdk:21
COPY build/libs/*.jar BusinessService.jar
CMD ["echo", "business service Start"]
ENTRYPOINT ["java", "-jar", "BusinessService.jar"]
