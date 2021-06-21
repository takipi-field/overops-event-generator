package com.overops.examples;

import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

@Service
public class EventCallable2 extends EventCallable implements Callable<Boolean>
{
	@Override
	public Boolean call() throws Exception
	{
		return createEvent();
	}
}
