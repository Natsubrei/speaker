FROM hub.atomgit.com/amd64/openjdk:21
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY speaker-backend-1.0.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]