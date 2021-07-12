package com.overops.examples;

import com.overops.examples.controller.EventGenerator;

public class EventCallable1 extends EventCallable
{
	EventCallable1(EventGenerator eventGenerator) {
		super(eventGenerator);
	}

	@Override
	public Boolean call() throws Exception
	{
		return createEvent();
	}

}
