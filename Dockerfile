FROM openjdk:8-jdk-slim as BUILDER

LABEL maintainer="support@harness.io"

ARG AGENT_URL=https://get.et.harness.io/releases/latest/nix/harness-et-agent.tar.gz
WORKDIR /et-event-generator

# install curl
RUN apt-get update && apt-get -y install curl

# copy source code to container
COPY .mvn ./.mvn
COPY src ./src
COPY mvnw mvnw.cmd pom.xml ./

RUN ./mvnw clean package -DskipTests

RUN curl -sL ${AGENT_URL} | tar -xvzf -


FROM openjdk:8-jre-slim
LABEL maintainer="support@harness.io"

RUN groupadd --gid 1000 harness
RUN adduser --home /opt/harness --uid 1000 --gid 1000 harness

WORKDIR /opt/harness

COPY --from=BUILDER --chown=1000:1000 /et-event-generator/target/et-event-generator-*.jar ./et-event-generator.jar
COPY --from=BUILDER --chown=1000:1000 /et-event-generator/harness .
COPY --chown=1000:1000 scripts/run.sh .
RUN chmod +x run.sh

USER 1000:1000

# set default environmental variables
ENV ET_COLLECTOR_URL=http://collector:6060
ENV JETTY_PORT=8888
ENV JAVA_TOOL_OPTIONS=-agentpath:/opt/harness/lib/libETAgent.so=debug.logconsole
ENV MAX_NUM_EVENTS=-1
ENV EXIT_ON_MAX_NUM_EVENTS=false

ENTRYPOINT ["./run.sh" ]
