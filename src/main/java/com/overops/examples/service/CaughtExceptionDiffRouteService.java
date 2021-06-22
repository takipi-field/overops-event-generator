package com.overops.examples.service;

import org.springframework.stereotype.Service;

/**
 * Class used to create a different entry point for the caught exception which
 * will in turn simulate a Mommy Pack situation for the frontend
 */
@Service
public class CaughtExceptionDiffRouteService extends AbstractEventService
{
	private final CatchAndProcessService catchAndProcessService;

	public CaughtExceptionDiffRouteService(CatchAndProcessService catchAndProcessService)
	{
		this.catchAndProcessService = catchAndProcessService;
	}

	@Override
	void fireEvent()
	{
		catchAndProcessService.fireEvent();
	}
}
