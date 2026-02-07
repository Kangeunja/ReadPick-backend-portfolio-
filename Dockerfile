# 1. 빌드 이미지
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# 의존성만 먼저 설치 (캐싱 목적)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 소스 복사 후 빌드
COPY src ./src
RUN mvn clean package -DskipTests

# 2. 실행 이미지
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# 빌드된 JAR 복사
COPY --from=build /app/target/*.jar app.jar

# 서버 포트
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
