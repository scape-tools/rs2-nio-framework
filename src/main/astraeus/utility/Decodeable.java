package main.astraeus.utility;

/**
 * The readable interface, that allows any implementing class the
 * ability to deserialize its objects.
 * 
 * @author SeVen
 */
public interface Decodeable {
	
	/**
	 * Reads objects from a file.
	 * 
	 * {@code true} If the operation can be completed, {@code false} otherwise.
	 */
	boolean deserialize();

}
