FROM openjdk:21
VOLUME /tmp
COPY build/libs/*.jar DiscoveryService.jar
CMD ["echo", "discovery service start"]
ENTRYPOINT ["java", "-jar", "DiscoveryService.jar"]
