FROM openjdk:21
COPY build/libs/*.jar SpringScheduler.jar
CMD ["echo", "Spring Scheduler Start"]
ENTRYPOINT ["java", "-jar", "SpringScheduler.jar"]
