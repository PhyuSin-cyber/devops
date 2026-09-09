FROM eclipse-temurin:25-jre
COPY ./target/classes /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "com.napier.sem.App"]