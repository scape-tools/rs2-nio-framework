package main.astraeus.core.game.processor.impl;

import java.util.concurrent.ConcurrentHashMap;

import main.astraeus.core.game.World;
import main.astraeus.core.game.model.entity.mobile.npc.Npc;
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
		players.put(player.getIndex(), player);
	}

	/**
	 * Removes an existing player from the concurrent collection.
	 * 
	 * @param index
	 *            The instance of the existing player.
	 */
	public final void removePlayer(Player player) {
		players.remove(player.getIndex());
	}

	@Override
	public void execute() {
		synchronized (this) {
			
			// player movement
			for (final Player player : getPlayers().values()) {
				player.prepare();
			}
			
			// npc movement
			for (final Npc npc : World.getNpcs()) {
				if (npc == null || !npc.isRegistered()) {
					continue;
				}
				npc.prepare();
			}
			
			// update player and npc both in parallel
			for (final Player player : players.values()) {
				player.getEventListener().update(player);
			}			
			
			// clear player update flags
			for (final Player player : players.values()) {
				player.getUpdateFlags().clear();
				player.setRegionChange(false);				
			}			
			
			// clear npc update flags
			for (final Npc npc : World.getNpcs()) {
				if (npc == null || !npc.isRegistered()) {
					continue;
				}
				npc.getUpdateFlags().clear();
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