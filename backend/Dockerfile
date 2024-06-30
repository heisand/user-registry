FROM gradle:8.8.0-jdk17-focal
RUN apt update && apt install findutils -y
COPY . /app
WORKDIR /app
RUN gradle wrapper
EXPOSE 8080
ENTRYPOINT ["/bin/sh", "/app/gradlew"]