FROM openjdk:21
COPY build/libs/*.jar DockerControlService.jar
CMD ["echo", "Docker Control Start"]
ENTRYPOINT ["java", "-jar", "DockerControlService.jar"]
