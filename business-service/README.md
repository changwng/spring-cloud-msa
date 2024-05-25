kubernetes-service
============

## 개요
#### msa에서 business 로직 구현을 위한 서비스이다.

## 주요 스펙
1) springboot 3.2.2 
2) jdk 21
3) API문서 자동화(restDocs + swagger)

## API 문서 자동화 설치 과정

1. 프로젝트 구성하기(Reload All Gradle Projects)
    ```sh
   ./gradlew clean build
    ```

3. API문서생성
    ```sh
   ./gradlew clean restDocsTest
    ```

4. 생성문서 확인
    1. Spring REST Docs: `build/docs/asciidoc/index.html`
    2. Swagger UI: `api-spec/openapi3.yaml`
 
####

5. application.jar 파일 생성
    ```sh
    ./gradlew apiBuild
    ```

6. application.jar 파일 위치로 이동
    ```sh
    cd build/libs
    ```
   
7. application.jar 파일 실행( * 터미널 관리자 권한 실행 )
    ```sh
    java -jar application.jar
    ```



## 설치 확인

### Spring REST Docs
[http://localhost:8030/docs/index.html]()

### Spring REST Docs - OpenAPI Specification Integration
[http://localhost:8030/swagger/swagger-ui.html]()

### Springdoc Swagger-UI
[http://localhost:8030/swagger-ui/index.html]()
