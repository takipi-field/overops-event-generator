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
java -agentlib:TakipiAgent -jar overops-event-generator-1.1.0.jar
```
or 

```bash
nohup java -agentlib:TakipiAgent -jar overops-event-generator-1.1.0.jar &
```

## Timer Configuration
This application includes a class called `SlowService` which contains a method called `longRunningMethod`.  This method will always take 5 seconds to execute.  You can easily add this method to the OverOps Timer's dialog with a threshold below 5 seconds to test the Timer capability.

## Pivotal Cloud Foundry (PCF) Example
Deploying to PCF is a trivial exercise with OverOps.  The OverOps agent is available as part of the PCF Java Buildpack under our original name Takipi.  More details can be found [here](https://github.com/cloudfoundry/java-buildpack/blob/master/docs/framework-takipi_agent.md).

To deploy this error generator on PCF you can execute the following commands from the root of this project.  It is assumed you have the PCF CLI [installed](https://docs.cloudfoundry.org/cf-cli/install-go-cli.html) and an active OverOps [SaaS](http://app.overops.com) account.

```bash
# clean and package the error generator
mvn clean package

# register "takipi" service using CUPS
cf cups takipi -p '{ "secret_key": "<YOUR_KEY>" }'

# push the generator to PCF
cf push overops-event-generator -p target/overops-event-generator-1.1.0.jar

# bind the "takipi" service to the generator
cf bind-service overops-event-generator takipi

# restage the generator and enjoy
cf restage overops-event-generator

```