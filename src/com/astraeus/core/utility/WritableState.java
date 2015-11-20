package com.astraeus.core.utility;

/**
 * The writable interface, that allows any implementing class the
 * ability to serialize its objects.
 * 
 * @author SeVen
 */
public interface WritableState {
	
	/**
	 * Writes objects into a file.
	 * 
	 * {@code true} If the operation can be completed, {@code false} otherwise.
	 */
	boolean serialize();

}
