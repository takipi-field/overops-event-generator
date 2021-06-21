#!/usr/bin/env bash
APP_OPT=""

# Only set the number of events when Event Generator is not running as a daemon.
if [[ ${IS_DAEMON} != "true" ]] ; then
    APP_OPT+="--oo.maxNumEvents=${MAX_NUM_EVENTS} "
fi

if [[ -n ${RANDOM_SEED} ]] ; then
	APP_OPT+="--oo.randomSeed=${RANDOM_SEED} "
fi

if [[ -n ${EXIT_ON_MAX_NUM_EVENTS} ]] ; then
	APP_OPT+="--oo.exitOnMaxNumEvents=${EXIT_ON_MAX_NUM_EVENTS} "
fi

java -jar /opt/takipi/overops-event-generator-*.jar --server.port=${JETTY_PORT} ${APP_OPT}
