FROM openjdk:21
VOLUME /tmp
COPY build/libs/*.jar GatewayService.jar
CMD ["echo", "api-gateway start"]
ENTRYPOINT ["java", "-jar", "GatewayService.jar"]
