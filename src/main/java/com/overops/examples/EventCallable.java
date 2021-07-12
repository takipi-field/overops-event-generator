package com.overops.examples;

import com.overops.examples.controller.EventGenerator;
import com.overops.examples.domain.User;
import com.overops.examples.utils.EventType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class EventCallable implements Callable<Boolean>
{
	private static final Logger log = LoggerFactory.getLogger(EventCallable.class);

	User user;
	EventType eventType;

	final private EventGenerator eventGenerator;

	EventCallable(EventGenerator eventGenerator) {
		this.eventGenerator = eventGenerator;
	}

	public Boolean createEvent()
	{
		AtomicBoolean eventGenerated = new AtomicBoolean(false);
		try
		{
			eventGenerator.generateEvent(user, eventType);
			eventGenerated.set(true);
		}
		catch (Exception e)
		{
			log.error("THIS IS A BUG IN THE GENERATOR: " + e.getMessage(), e);
		}
		return eventGenerated.get();
	}

	public void setTarget(User user, EventType eventType) {
		this.user = user;
		this.eventType = eventType;
	}

	public EventType getEventType() {
		return eventType;
	}

	@Override
	public Boolean call() throws Exception
	{
		return createEvent();
	}
}
