package main.astraeus.core.game.processor.impl;

import java.util.concurrent.ConcurrentHashMap;

import main.astraeus.core.game.model.entity.mobile.EntityList;
import main.astraeus.core.game.model.entity.mobile.player.Player;
import main.astraeus.core.game.processor.ProcessorConstants;
import main.astraeus.core.game.processor.ScheduledProcessor;

public final class ScheduledUpdateProcessor extends ScheduledProcessor {

	/**
	 * A collection supporting full concurrency of retrievals and high expected
	 * concurrency for updates. This collection is used to store active players
	 * in a index to instance relationship.
	 */
	private final ConcurrentHashMap<Integer, Player> players = new ConcurrentHashMap<Integer, Player>();
	
	private final EntityList<Player> playerList = new EntityList<>(2000);

	/**
	 * The overloaded class constructor used for instantiation of this class
	 * file.
	 */
	public ScheduledUpdateProcessor() {
		super(ProcessorConstants.UPDATE_PROCESSOR_RATE);
	}

	/**
	 * Places a new player into the concurrent collection.
	 * 
	 * @param index
	 *            The index of the new player.
	 * 
	 * @param entity
	 *            The instance of the new player.
	 */
	public final void addPlayer(Player player) {
		playerList.add(player);
	}

	/**
	 * Removes an existing player from the concurrent collection.
	 * 
	 * @param index
	 *            The instance of the existing player.
	 */
	public final void removePlayer(Player player) {
		players.remove(player.getIndex());
		playerList.remove(player);
	}

	@Override
	public void execute() {
		synchronized (this) {
			
			for (final Player player : getPlayers().values()) {
				player.prepare(); //movement
			}
			
			for (final Player player : players.values()) {
				player.getEventListener().update(player);
			}
			
			
			for (final Player player : players.values()) {
				player.getUpdateFlags().clear(); // clears the flags
			}
			
		}
	}
	
	/**
	 * Returns an instance of the concurrent player collection.
	 * 
	 * @return The returned instance.
	 */
	public ConcurrentHashMap<Integer, Player> getPlayers() {
		return players;
	}
	
}