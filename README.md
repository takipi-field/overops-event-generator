# Demo

clean and package the jar.

```bash
./mvnw clean package -DskipTests
```

start the application and enjoy...

```
java -agentlib:TakipiAgent -jar spring-aspect-test-0.0.1-SNAPSHOT.jar
```


deploy app using PCF

```
mvn clean package
cf cups takipi -p '{ "secret_key": "S34749#PtqgO3AQi2NNOEZD#2hpk07nOVmYBTSuIDkjy9MBY4PGCboVkttTZWx/wHJ8=#773c" }'
cf push spring-oo-test -p target/spring-aspect-test-0.0.1-SNAPSHOT.jar
cf bind-service spring-oo-test takipi
cf restage my-app
```