package com.overops.examples.service;

import com.overops.examples.error.ExampleCaughtException;
import org.springframework.stereotype.Service;

@Service
public class CatchInDifferentMethodService extends AbstractEventService
{
	@Override
	void fireEvent()
	{
		try
		{
			throwException();
		}
		catch (ExampleCaughtException e)
		{
			log.debug("here we catch: " + e.getMessage(), e);
		}
	}
	
	private void throwException() throws ExampleCaughtException
	{
		throw new ExampleCaughtException("Throwing an exception to be caught by a different method");
	}
}
