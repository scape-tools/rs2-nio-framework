package main.astraeus.core.net.packet.incoming;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The reflection will be available for reference at run-time.
 */
@Retention(RetentionPolicy.RUNTIME)

/**
 * A class, enumeration, or interface will be annotated.
 */
@Target(ElementType.TYPE)

/**
 * @author Dylan Vicchiarelli
 *
 * An annotation implementation to retain packet opcodes.
 */
public @interface IncomingPacketOpcode {

	/**
	 * The incoming opcodes the packet implementation handles.
	 * 
	 * @return The collection of opcodes.
	 */
	int[] value();
}
