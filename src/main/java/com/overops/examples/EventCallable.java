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

	// This method is intended to be called by java.util.concurrent.ExecutorService.submit()
	// to run in a new thread. The createEvent() method, implemented in the abstract base class and called below, 
	// uses the com.overops.examples.controller.EventGenerator.generateEvent() 
	// method to actually fire the event (exception, log event, etc.). In the overops dashboard event list, 
	// this call() method will appear as the entry point for the event. If you wish to have a 
	// different entry point, extend this class and override this call method. The
	// override can simply call the same createEvent method. The call() method that overrides
	// this method will become the new entry point. Note that all EventCallables that extend this class, but do
	// not override this call method will share this call method as the entry point for the event.
	@Override
	public Boolean call() throws Exception
	{
		return createEvent();
	}
}
