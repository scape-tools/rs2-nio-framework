package main.astraeus.core.game.pulse;

/**
 * @author Dylan Vicchiarelli
 *
 * Listens for a pulse event request and then passes that request
 * on to an observer.
 */
public interface PulseListener {

	/**
	 * Executes the requested pulse on demand.
	 * 
	 * @param pulse The pulse being executed.
	 */
	void execute(Pulse pulse);

	/**
	 * Stops the fixed execution of pulse and executes it's last wishes.
	 * 
	 * @param pulse The pulse being stopped.
	 */
	void stop(Pulse pulse);
}
