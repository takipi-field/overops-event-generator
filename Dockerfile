FROM centos:7

LABEL maintainer="tjveil@gmail.com"

RUN yum install -y java-1.8.0-openjdk-devel.x86_64 net-tools \
    && yum -y update \
    && yum clean all \
    && mkdir /code

ENV JAVA_HOME /usr/lib/jvm/java-1.8.0-openjdk

ENV PATH $JAVA_HOME/bin:$PATH

ADD .mvn /code/.mvn
ADD mvnw /code
ADD mvnw.cmd /code
ADD src /code/src
ADD pom.xml /code

WORKDIR /code

RUN ./mvnw clean package -DskipTests

ENTRYPOINT java -jar target/*.jar