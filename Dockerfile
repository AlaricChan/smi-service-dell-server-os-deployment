FROM openjdk:8-jre
VOLUME /tmp
ADD build/libs/service-server-osdeployment*.jar app.jar
RUN apt-get update && apt-get install -y genisoimage
RUN bash -c 'touch /app.jar'
COPY /application.yml /application.yml
EXPOSE 46014
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

