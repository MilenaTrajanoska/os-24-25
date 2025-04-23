FROM eclipse-temurin

WORKDIR /usr/src/myapp

COPY . .

RUN mkdir data

RUN javac ExecutionCounter.java

CMD ["java", "ExecutionCounter"]