#!/bin/bash

# 스크립트 실행 실패 시 즉시 중단
set -e

# 도커 로그인 상태 확인
if ! docker info 2>/dev/null | grep -q "Username:" && ! cat ~/.docker/config.json 2>/dev/null | grep -q "auth"; then
    echo "Error: Docker is not logged in. Please run 'docker login' first."
    exit 1
fi

# 이미지 태그 설정
VERSION="latest"
# 환경 변수에서 레지스트리 이름을 가져오거나 기본값 사용
REGISTRY=${DOCKER_REGISTRY:-"changwng"}

if [ -z "$REGISTRY" ]; then
    echo "Error: Docker registry is not set. Using default registry: changwng"
fi

echo "Using Docker registry: $REGISTRY"

# 작업 디렉토리로 이동
cd ..

# 공통 모듈 빌드
echo "Building common module..."
#cd module-common
#./gradlew clean build publishToMavenLocal
#cd ..

echo "=== Processing $service ==="
# 각 서비스 빌드 및 도커 이미지 생성
#services=("config" "discovery" "apigateway" "user-service" "board-service" "cms-service" "attach-service")
services=("discovery-service" "gateway-service" "user-service" "batch-service" "business-service")
for service in "${services[@]}"
do
    echo "=== Processing $service ==="
    
    # 서비스 디렉토리로 이동
    cd $service
    pwd

    echo "Building $service with Gradle..."
    ./gradlew clean build -x test
    
    echo "Checking Dockerfile existence..."
    if [ ! -f "Dockerfile" ]; then
        echo "Error: Dockerfile not found for $service"
        exit 1
    fi
    
    echo "Building Docker image for $service..."
    docker build -t $REGISTRY/spring-cloud-msa-$service:$VERSION .
    
    echo "Pushing Docker image for $service..."
    if ! docker push $REGISTRY/spring-cloud-msa-$service:$VERSION; then
        echo "Error: Failed to push image $REGISTRY/spring-cloud-msa-$service:$VERSION"
        echo "Please check your Docker Hub credentials and permissions"
        exit 1
    fi
    
    # 상위 디렉토리로 이동
    cd ..
    
    echo "=== Completed processing $service ==="
done

echo "모든 서비스 빌드 및 이미지 푸시 완료"

# 이미지 목록 확인
echo "생성된 이미지 목록:"
docker images | grep $REGISTRY 