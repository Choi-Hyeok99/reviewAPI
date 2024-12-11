# Base image로 OpenJDK 17을 사용
FROM openjdk:17-jdk-slim

# 빌드된 JAR 파일을 컨테이너로 복사
COPY build/libs/sparta_ReviewAPI-0.0.1-SNAPSHOT.jar /app/sparta_ReviewAPI.jar

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "sparta_ReviewAPI.jar"]

# 컨테이너가 사용하는 포트 설정
EXPOSE 8080
