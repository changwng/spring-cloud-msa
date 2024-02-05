FROM openjdk:21
COPY build/libs/*.jar GatewayService.jar
CMD ["echo", "api-gateway start"]
ENTRYPOINT ["java", "-jar", "GatewayService.jar"]
