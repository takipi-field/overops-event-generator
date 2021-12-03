FROM openjdk:8-jdk-slim as BUILDER

LABEL maintainer="support@overops.com"

WORKDIR /overops-event-generator

# install curl
RUN apt-get update && apt-get -y install curl

# copy source code to container	
COPY .mvn ./.mvn
COPY src ./src
COPY mvnw mvnw.cmd pom.xml ./
COPY takipi-agent-native.tar.gz ./

RUN ./mvnw clean package -DskipTests

RUN tar -xvzf ./takipi-agent-native.tar.gz


FROM openjdk:8-jre-slim
LABEL maintainer="support@overops.com"

RUN groupadd --gid 1000 overops
RUN adduser --home /opt/takipi --uid 1000 --gid 1000 overops

WORKDIR /opt/takipi

COPY --from=BUILDER --chown=1000:1000 /overops-event-generator/target/overops-event-generator-*.jar .
COPY --from=BUILDER --chown=1000:1000 /overops-event-generator/takipi .
COPY --chown=1000:1000 scripts/run.sh .
RUN chmod +x run.sh

USER 1000:1000 

# set default environmental variables
ENV TAKIPI_COLLECTOR_HOST=collector
ENV TAKIPI_COLLECTOR_PORT=6060
ENV JETTY_PORT=8888
ENV JAVA_TOOL_OPTIONS=-agentpath:/opt/takipi/lib/libTakipiAgent.so=takipi.debug.logconsole
ENV MAX_NUM_EVENTS=-1
ENV EXIT_ON_MAX_NUM_EVENTS=false

ENTRYPOINT ["./run.sh" ]
