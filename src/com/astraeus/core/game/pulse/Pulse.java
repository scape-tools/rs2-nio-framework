package com.astraeus.core.game.pulse;

import java.util.concurrent.ScheduledFuture;

/**
 * @author Dylan Vicchiarelli
 *
 * Represents a game pulse which has a fixed execution rate.
 */
public abstract class Pulse {

	/**
	 * The execution rate of the pulse.
	 */
	private final int executionRate;

	/**
	 * If the pulse is currently active.
	 */
	private boolean isActive;

	/**
	 * The future delayed action of the pulse once it is finished
	 * executing.
	 */
	private ScheduledFuture<?> concurrentFuture;

	/**
	 * The name of the pulse's owner.
	 */
	private final String owner;

	/**
	 * Executes the pulse on demand.
	 */
	public abstract void execute();

	/**
	 * Executes the last wishes of the pulse before it is
	 * removed from the system.
	 */
	public abstract void stop();

	/**
	 * The default class constructor.
	 * 
	 * @param executionRate The execution rate of the pulse.
	 * 
	 * @param owner The name of the pulse's owner.
	 */
	public Pulse(int executionRate, String owner) {
		this.executionRate = executionRate;
		this.owner = owner;
		this.isActive = true;
	}

	/**
	 * Returns the execution rate of the pulse.
	 * 
	 * @return The returned rate.
	 */
	public final int getExecutionRate() {
		return executionRate;
	}

	/**
	 * Returns if the pulse is currently active.
	 * 
	 * @return The returned state.
	 */
	public final boolean isActive() {
		return isActive;
	}

	/**
	 * Modifies if the pulse is currently active.
	 * 
	 * @param isActive The new modification.
	 */
	public final void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * Returns the owner of the pulse.
	 * 
	 * @return The returned pulse owner.
	 */
	public final String getOwner() {
		return owner;
	}

	/**
	 * Returns the future delayed action of the pulse.
	 * 
	 * @return The returned action.
	 */
	public ScheduledFuture<?> getFuture() {
		return concurrentFuture;
	}

	/**
	 * Modifies the future delayed action of the pulse.
	 * 
	 * @param future The new modification.
	 */
	public void setFuture(ScheduledFuture<?> future) {
		this.concurrentFuture = future;
	}
}