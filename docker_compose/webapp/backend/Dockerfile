FROM eclipse-temurin
WORKDIR /usr/src/app
COPY ./src/SimpleHttpServer.java .
RUN javac SimpleHttpServer.java
RUN mkdir data
CMD ["java", "SimpleHttpServer"]
