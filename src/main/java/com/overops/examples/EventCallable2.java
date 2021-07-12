package com.overops.examples;

import com.overops.examples.controller.EventGenerator;

public class EventCallable2 extends EventCallable
{
	EventCallable2(EventGenerator eventGenerator) {
		super(eventGenerator);
	}

	@Override
	public Boolean call() throws Exception
	{
		return createEvent();
	}
}
