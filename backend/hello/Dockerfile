FROM openjdk:11-jdk
RUN mkdir /srv/docker-server
WORKDIR /srv/docker-server
COPY ./ ./
CMD java -jar ./build/libs/*.jar