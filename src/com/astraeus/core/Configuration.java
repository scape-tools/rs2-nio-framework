package com.astraeus.core;

/**
 * The main configuration for the server.
 * @author SeVen
 */
public class Configuration {
	
	/**
	 * The name of the server.
	 */
	public static final String SERVER_NAME = "Astraeus";
	
	/**
	 * The address to create the {@link InetSocketAddress} and to bind
	 * the {@link ServerSocketChannel}.
	 */
	public static final String ADDRESS = "localhost";
	
	/**
	 * The port to which the {@link ServerSocketChannel} will bind to.
	 */
	public static final int PORT = 43594;
	
	/**
	 * Displays server debug messages.
	 */
	public static boolean SERVER_DEBUG = true;
	
	/**
	 * The path to the data folder.
	 */
	public static final String DATA = "./data/";
}
