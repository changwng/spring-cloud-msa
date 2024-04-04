spring-cloud-msa
============

## 개요
#### spring-cloud 라이브러리를 활용해서 만든 msa framework
#### docker와 k8s 환경에서 구축하여 사용

## 구성
  - batch-service
  - business-service
  - discovery service
  - gateway-service
  - postgres
  - user-service

## 설명
### 1) 배치 서비스
- 스프링 배치, 스케쥴링을 사용할 수 있다.

### 2) 비즈니스 서비스
- 목적에 따라 비즈니스 로직을 구현하는 API 서버이다.

### 3) 유레카(발견 서비스)
- msa의 단점인 서버간 관계의 복잡성을 eureka에 등록을 하여 관리한다.
- API Gateway는 여기서 등록된 Service Name으로 route한다.

```yml
spring:
  application:
    name: discovery-service

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```
client가 아닌 eureka 본서버이기 때문에 모든 설정은 false여야 한다.


### 4) API 게이트웨이
- api gateway는 jwt(인증, 인가), 라우팅, 기능을 제공한다.
- 라운드 로빈 방식의 로드밸런싱 기능을 제공한다.
- defaultZone의 hostname은 docker와 k8s의 클러스터 내부 IP를 이용해야하기 때문에 prod에 설정한다. 
```yml
eureka:
  instance:
    prefer-ip-address: true
    hostname: gateway-service
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://discovery-service:8761/eureka/
```

### 5) postgres
- DB서버를 별도로 구축하지 않고도 docker나 k8s에서 바로 실행되어 연결된다.

### 6) 유저 서비스
- 기본 회원가입 및 로그인 기능을 제공하며 이때 jwt 토큰이 발급된다.

## Github Actions CI&CD 구축

![image](https://github.com/Parkjinman/spring-cloud-msa/assets/48800354/c7278c0f-8ccc-4546-87db-babd495a45ea)
