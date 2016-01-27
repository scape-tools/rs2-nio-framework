package main.astraeus.game.pulse;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author Dylan Vicchiarelli
 *
 * A utility class for the scheduling and execution of pulses.
 */
public final class PulseScheduler {

	/**
	 * The collection of active pulses in the system.
	 */
	private final HashMap<String, Pulse> pulses = new HashMap<String, Pulse>();

	/**
	 * The class instance to avoid direct static access upon class
	 * reference.
	 */
	private static final PulseScheduler instance = new PulseScheduler();

	/**
	 * The service for the fixed execution of pulses in a closed environment.
	 */
	private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

	/**
	 * The listener to execute all destined pulses.
	 */
	private final PulseListener pulseListener = new PulseObserver();

	/**
	 * Destroys all the pulses of a specified owner or attachment.
	 * 
	 * @param owner The owner or set attachment of the pulses.
	 */
	public final void destoryPulsesForOwner(String owner) {

		int accumulator = 0;

		for (final Pulse pulse : getPulses().values()) {

			if (pulse.getOwner().equalsIgnoreCase(owner)) {

				pulse.setActive(false);

				accumulator ++;
			}
		}

		Logger.getLogger(PulseScheduler.class.getSimpleName()).info("[" + accumulator + "] pulse(s) have been destroyed for the owner : [" + owner + "].");

		Logger.getLogger(PulseScheduler.class.getSimpleName()).info("There are now [" + pulses.size() + "] pulse(s) currently active in the system.");
	}

	/**
	 * Registers a new pulse for fixed execution.
	 * 
	 * @param pulse The pulse to be registered.
	 * 
	 * @param executePromptly If the pulse skips the first cycle or executes right away.
	 */
	public final void register(final Pulse pulse, final boolean executePromptly) {

		getPulses().put(pulse.getOwner(), pulse);

		Runnable runnable = new Runnable() {

			/*
			 * If the first cycle is to be skipped. This is used when
			 * the action is to be timed instead of cycled constantly.
			 */
			boolean skipInitialPulse = false;

			@Override
			public void run() {
				synchronized (this) {

					if (pulse.isActive()) {
						
						if (executePromptly == false && skipInitialPulse == false) {
							skipInitialPulse = true;

						} else {
							pulseListener.execute(pulse);
						}

					} else {
						pulseListener.stop(pulse);
						getPulses().remove(pulse.getOwner());
						pulse.getFuture().cancel(false);
					}
				}
			}
		};

		final ScheduledFuture<?> scheduledFuture = executor.scheduleAtFixedRate(runnable, 0, pulse.getExecutionRate(), TimeUnit.MILLISECONDS);
		pulse.setFuture(scheduledFuture);
	}

	/**
	 * Returns the encapsulated class instance.
	 * 
	 * @return The returned instance.
	 */
	public static final PulseScheduler getInstance() {
		return instance;
	}

	/**
	 * Returns the collection of active pulses.
	 * 
	 * @return The returned collection.
	 */
	public final HashMap<String, Pulse> getPulses() {
		return pulses;
	}

	/**
	 * Returns the execution service for pulses.
	 * 
	 * @return The returned service.
	 */
	public final ScheduledExecutorService getExecutor() {
		return executor;
	}
}
