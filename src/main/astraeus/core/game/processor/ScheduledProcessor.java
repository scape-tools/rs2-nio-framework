package main.astraeus.core.game.processor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * A simple processor for scheduling major logical procedures. This class
 * should not be used for execution of minor periodical processes.
 */
public abstract class ScheduledProcessor implements Runnable {

	/**
	 * Handles the creation, destruction, and periodical execution of threads 
	 * within a fixed concurrent environment.
	 */
	private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

	/**
	 * Executes the implementation's logical commands.
	 */
	public abstract void execute();

	/**
	 * A delayed result-bearing action that can be cancelled.
	 */
	private ScheduledFuture<?> future;

	/**
	 * The rate in milliseconds at which the thread will be executed.
	 */
	private final int rate;

	/**
	 * The overloaded class constructor used for instantiation of this
	 * class file.
	 * 
	 * @param rate The rate in milliseconds at which the thread will be executed.
	 */
	public ScheduledProcessor(int rate) {
		this.rate = rate;
	}

	/**
	 * Cancels the execution of this processor.
	 * 
	 * @param interrupt If that task should be interrupted in the attempt
	 * to cancel it.
	 */
	public void cancel(boolean interrupt) {
		future.cancel(interrupt);
	}

	/**
	 * Starts the periodical execution of this processor.
	 * 
	 * @param delay The delay in milliseconds the processor will wait
	 * before it starts.
	 */
	public void start(int delay) {
		setFuture(service.scheduleAtFixedRate(this, delay, rate, TimeUnit.MILLISECONDS));
	}

	/**
	 * Modifies the delayed result-bearing action.
	 * 
	 * @param future The new modification.
	 */
	public void setFuture(ScheduledFuture<?> future) {
		this.future = future;
	}

	@Override
	public void run() {
		execute();
	}
}