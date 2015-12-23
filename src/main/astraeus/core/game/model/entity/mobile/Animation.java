package main.astraeus.core.game.model.entity.mobile;

/**
 * The class that represents a single animation that can be performed by
 * a {@link MobileEntity}.
 * 
 * @author SeVen
 */
public class Animation {

	/**
	 * The id of this animation.
	 */
	private final int id;

	/**
	 * The delay of this animation.
	 */
	private int delay;
	
	/**
	 * Constructs a new {@link Animation} with a default delay
	 * of {@code zero}.
	 * 
	 * @param id
	 * 		The id of this animation.
	 */
	public Animation(int id) {
		this(id, 0);
	}

	/**
	 * Constructs a new {@link Animation} with a
	 * specified id and delay.
	 * 
	 * @param id
	 * 		The id of this animation.
	 * @param delay
	 * 		The delay of this animation in seconds.
	 */
	public Animation(int id, int delay) {
		this.id = id;
		this.delay = delay;
	}

	/**
	 * Gets the id of this animation.
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the delay of this animation.
	 * 
	 * @return The delay.
	 */
	public int getDelay() {
		return delay;
	}
	
	/**
	 * Sets the delay of this animation in {@code seconds}.
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
}
