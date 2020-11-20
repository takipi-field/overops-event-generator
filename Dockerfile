FROM openjdk:8-jdk-slim as BUILDER

LABEL maintainer="corey.severino@overops.com"

ARG AGENT_VERSION=latest

WORKDIR /overops-event-generator

# install curl
RUN apt-get update && apt-get -y install curl

# copy source code to container	
COPY .mvn ./.mvn
COPY src ./src
COPY mvnw mvnw.cmd pom.xml ./

RUN ./mvnw clean package -DskipTests

RUN curl -sL https://s3.amazonaws.com/app-takipi-com/deploy/linux/takipi-agent-${AGENT_VERSION}.tar.gz | tar -xvzf -


FROM openjdk:8-jre-slim
LABEL maintainer="corey.severino@overops.com"
WORKDIR /overops/

COPY --from=BUILDER /overops-event-generator/target/overops-event-generator-*.jar .
COPY --from=BUILDER /overops-event-generator/takipi ./takipi/

# set default environmental variables
ENV TAKIPI_COLLECTOR_HOST=collector
ENV TAKIPI_COLLECTOR_PORT=6060
ENV JAVA_TOOL_OPTIONS=-agentpath:/overops/takipi/lib/libTakipiAgent.so=takipi.debug.logconsole

# port for embedded Jetty
EXPOSE 8080

ENTRYPOINT java -jar ./overops-event-generator-*.jar
