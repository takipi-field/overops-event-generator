#!/usr/bin/env bash
APP_OPT=""

if [[ -n ${MAX_NUM_EVENTS} ]] ; then
    APP_OPT+="--oo.maxNumEvents=${MAX_NUM_EVENTS} "
fi

if [[ -n ${RANDOM_SEED} ]] ; then
	APP_OPT+="--oo.randomSeed=${RANDOM_SEED} "
fi

if [[ -n ${EXIT_ON_MAX_NUM_EVENTS} ]] ; then
	APP_OPT+="--oo.exitOnMaxNumEvents=${EXIT_ON_MAX_NUM_EVENTS} "
fi

if [[ -n ${EVENT_GEN_ID} ]] ; then
	APP_OPT+="--oo.eventGenId=${EVENT_GEN_ID} "
fi


java -jar /opt/takipi/overops-event-generator-*.jar --server.port=${JETTY_PORT} ${APP_OPT}
