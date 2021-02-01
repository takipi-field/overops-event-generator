#!/usr/bin/env bash
EVENTS_OPT=""

# Only set the number of events when Event Generator is not running as a daemon.
if [[ ${IS_DAEMON} != "true" ]] ; then
    EVENTS_OPT="--oo.events=${NUM_OF_EVENTS}" 
fi

java -jar /opt/takipi/overops-event-generator-*.jar --server.port=${JETTY_PORT} ${EVENTS_OPT}
