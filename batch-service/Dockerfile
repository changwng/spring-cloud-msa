FROM openjdk:21
COPY build/libs/*.jar BatchService.jar
CMD ["echo", "Batch Service Start"]
ENTRYPOINT ["java", "-jar", "BatchService.jar"]
