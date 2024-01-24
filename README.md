spring-cloud-msa
============

## 개요
spring-cloud 라이브러리를 활용해서 만든 msa framework

## 구성
  - api-gateway
  - springboot service 1
  - springboot service 2
  - eureka (discovery service)

## 설명
### 1) API 게이트웨이
- api gateway는 jwt(인증, 인가), 라우팅, 기능 제공

```yml
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defalutZone: http://localhost:8761/eureka

spring:
  application:
    name: apigateway-service
  cloud:
    gateway:
      routes:
        - id: spring-service
          uri: lb://SPRING-SERVICE
          predicates:
            - Path=/spring-service/**

management:
  endpoints:
    web:
      exposure:
        include: health, info, metrics, prometheus, gateway
```
라운드 로빈 방식의 로드밸런싱 기능 제공.
spring.cloud.gateway.routes의 하위 노드에서 spring.application.name과 url경로를 등록하여 라우팅


### 2) 스프링부트 서비스 1
- 비즈니스 로직으로 구축된 api server

### 3) 스프링부트 서비스 2
- springboot service 1과 동일하고 로드밸런싱 테스트를 위해 copy

```yml
spring:
  application:
    name: spring-service

eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://127.0.0.1:8761/eureka
```
api-gateway에 등록할 name정의 및 eureka서버에 등록 설정

### 4) 유레카(발견 서비스)
- msa의 단점인 서버간 관계의 복잡성을 eureka에 등록을 하여 관리  

```yml
spring:
  application:
    name: discovery-service

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```
client가 아닌 eureka 본서버이기 때문에 모든 설정은 false 
