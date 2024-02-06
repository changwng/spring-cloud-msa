FROM openjdk:21
COPY build/libs/*.jar UserService.jar
CMD ["echo", "Docker Controll start"]
ENTRYPOINT ["java", "-jar", "UserService.jar"]
