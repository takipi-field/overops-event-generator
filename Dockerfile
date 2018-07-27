FROM centos:7

LABEL maintainer="tjveil@gmail.com"

ARG PROJECT_DIR=/overops-event-generator

RUN yum install -y java-1.8.0-openjdk-devel.x86_64 net-tools \
    && yum -y update \
    && yum clean all \
    && mkdir $PROJECT_DIR

ENV JAVA_HOME /usr/lib/jvm/java-1.8.0-openjdk

ENV PATH $JAVA_HOME/bin:$PATH

ADD .mvn $PROJECT_DIR/.mvn
ADD mvnw $PROJECT_DIR
ADD mvnw.cmd $PROJECT_DIR
ADD src $PROJECT_DIR/src
ADD pom.xml $PROJECT_DIR

WORKDIR $PROJECT_DIR

RUN ./mvnw clean package -DskipTests

ARG SOURCES_DIR=/sources

#VOLUME $SOURCES_DIR

RUN ./mvnw dependency:copy-dependencies -Dsources.dir=$SOURCES_DIR \
    && cp target/*.jar $SOURCES_DIR

ENTRYPOINT java -jar -Dtakipi.sources.path=$SOURCES_DIR target/*.jar