package com.overops.examples.service;

import com.overops.examples.error.ExampleSwallowedException;
import org.springframework.stereotype.Service;

@Service
public class SwallowInDifferentMethodService extends AbstractEventService
{
	@Override
	void fireEvent()
	{
		try
		{
			throwException();
		}
		catch (ExampleSwallowedException e)
		{
			// Go ahead and swallow this one
		}
	}
	
	private void throwException() throws ExampleSwallowedException
	{
		throw new ExampleSwallowedException("Throwing an exception to be swallowed by a different method");
	}
}