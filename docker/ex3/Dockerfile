FROM eclipse-temurin

WORKDIR /usr/src/myapp

COPY . .

RUN mkdir data

RUN javac ExecutionCounterV2.java

CMD ["java", "ExecutionCounterV2"]