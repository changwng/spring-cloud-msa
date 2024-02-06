FROM openjdk:21
COPY build/libs/*.jar DiscoveryService.jar
CMD ["echo", "discovery service start"]
ENTRYPOINT ["java", "-jar", "DiscoveryService.jar"]
