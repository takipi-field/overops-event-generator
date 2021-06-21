package com.overops.examples;

import com.overops.examples.controller.Controller;
import com.overops.examples.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicBoolean;

public class EventCallable
{
	private static final Logger log = LoggerFactory.getLogger(EventCallable.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Controller controller;

	public Boolean createEvent()
	{
		int userCount = Math.toIntExact(userRepository.count());
		int randomUserId = controller.getRandom().nextInt(userCount) + 1;

		AtomicBoolean eventGenerated = new AtomicBoolean(false);

		userRepository.findById((long) randomUserId).ifPresent(user -> {
			try
			{
				controller.route(user);
				eventGenerated.set(true);
			}
			catch (Exception e)
			{
				log.error("THIS IS A BUG IN THE GENERATOR: " + e.getMessage(), e);
			}
		});

		return eventGenerated.get();
	}
}
