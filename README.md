# OverOps Event Generator
This project is a simple Spring Boot application that generates a variety of OverOps event types useful for testing OverOps installations and highlighting features.  This application is similar in purpose the `JavaJam` error generating `jar` which is often referenced in OverOps docs and samples, but expands upon its usefulness.  This application includes the following features:

* easy to understand events which correlate to all supported OverOps event types including:
    * Caught and Uncaught Exceptions
    * Logged `ERROR` and `WARN` statements
    * Swallowed Exceptions
    * Custom Events via OverOps [SDK](https://github.com/takipi/takipi-sdk)
    * HTTP Errors
    * Timers
* logging statements at various levels to highlight log view capabilities 
* application name and deployment name specified in `MANIFEST.MF`


## Building & Running
Execute the following commands clean and package the jar:

```bash
./mvnw clean package -DskipTests
```

To start and monitor this application with OverOps you can execute the following commands.  Full documentation on installing and configuring OverOps can be found [here](http://support.overops.com).

```bash
java -agentlib:TakipiAgent -jar target/overops-event-generator-*.jar
```
or 

```bash
nohup java -agentlib:TakipiAgent -jar target/overops-event-generator-*.jar &
```

### Additional Command Line Arguments
| Argument | Description | Value Type | Default Value |  Example |
|---|---|---|---|---|
| `oo.eventGenId` | Identify the event list to be generated | String | RANDOM_EVENTS | `--oo.eventGenId=MOMMY_PACKS`
| `oo.maxNumEvents` | Executes the number events specified and then terminates the application. Ignored if oo.eventGenId not equal to RANDOM_EVENTS | Integer | -1 (run forever) | `--oo.maxNumEvents=100` |
| `oo.exitOnMaxNumEvents` | Determines if the application will exit once max number of events have been reached. Ignored if oo.eventGenId not equal to RANDOM_EVENTS | Boolean | false | `--oo.exitOnMaxNumEvents=false` |
| `oo.randomSeed` | Seed value that will be used when randomizing the events to be fired. Ignored if oo.eventGenId not equal to RANDOM_EVENTS | Long | No Seed | `--oo.randomSeed=123` |
| `server.port` | Specify http port to use | Integer | 8080 | `--server.port=1234`


## DB Console
You can view the H2 web console at `/console` for example `http://localhost:8080/console`.  Use the following connection properties:

|  Field | Value  |
| ------------- | ------------- |
| Driver Class | `org.h2.Driver` |
| JDBC URL | `jdbc:h2:mem:overops` |
| User Name | `sa` |
| Password |  |

## Timer Configuration
This application includes a class called `SlowService` which contains a method called `longRunningMethod`.  This method will always take 5 seconds to execute.  You can easily add this method to the OverOps Timer's dialog with a threshold below 5 seconds to test the Timer capability.

## Running with Docker
Included is a `Dockerfile` which can be used to build and run the event generator with overops agent.

### Building the Image

```console
$ docker build . -t overops-event-generator:latest

# Optional: Use a specific agent version
$ docker build . -t overops-event-generator:latest --build-arg AGENT_VERSION=X.Y.Z
```

### Running the Image
The Overops Agent can be configured with Environment variables as described in the Agent Docs: https://doc.overops.com/docs/agent-properties

Required is the TAKIPI_COLLECTOR_HOST variable which should be the hostname or IP of the collector

```console
$ docker run -e TAKIPI_COLLECTOR_HOST=<HOSTNAME> -ti overops-event-generator:latest
```

By default, the event generator will run indefinitely. To set the "--oo.maxNumEvents" and "--oo.exitOnMaxNumEvents" options in the docker container set the following environment variables:

```console
$ docker run -e TAKIPI_COLLECTOR_HOST=<HOSTNAME> -e NUM_OF_EVENTS=10 -e EXIT_ON_MAX_NUM_EVENTS=true -ti overops-event-generator:latest
```

### Additional Run Options
| Argument | Description | Value Type | Default Value |  Example |
|---|---|---|---|---|
| `MAX_NUM_EVENTS` | Executes the number events specified and then terminates the application (-1 no max events) | Integer | -1 | `-e MAX_NUM_EVENTS=100` |
| `EXIT_ON_MAX_NUM_EVENTS` | Determines if the application will exit once max number of events have been reached | Boolean | false | `-e EXIT_ON_MAX_NUM_EVENTS=true` |
| `RANDOM_SEED` | Seed value that will be used when randomizing the events to be fired | Long | No Seed | `-e RANDOM_SEED=123` |
| `JETTY_PORT` | Specify http port to use | Integer | 8080 | `-e JETTY_PORT=1234`


## Pivotal Cloud Foundry (PCF) Example
Deploying to PCF is a trivial exercise with OverOps.  The OverOps agent is available as part of the PCF Java Buildpack under our original name Takipi.  More details can be found [here](https://github.com/cloudfoundry/java-buildpack/blob/master/docs/framework-takipi_agent.md).

To deploy this error generator on PCF you can execute the following commands from the root of this project.  It is assumed you have the PCF CLI [installed](https://docs.cloudfoundry.org/cf-cli/install-go-cli.html) and an active OverOps [SaaS](http://app.overops.com) account.

```bash
# clean and package the error generator
mvn clean package

# register "takipi" service using CUPS
cf cups takipi -p '{ "secret_key": "<YOUR_KEY>" }'

# push the generator to PCF
cf push overops-event-generator -p target/overops-event-generator-2.1.3.jar

# bind the "takipi" service to the generator
cf bind-service overops-event-generator takipi

# re-stage the generator and enjoy
cf restage overops-event-generator

```
