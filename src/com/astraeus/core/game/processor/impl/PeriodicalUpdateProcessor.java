package com.astraeus.core.game.processor.impl;

import java.util.concurrent.ConcurrentHashMap;

import com.astraeus.core.game.model.entity.UpdateFlags;
import com.astraeus.core.game.model.entity.mobile.player.Player;
import com.astraeus.core.game.processor.PeriodicalLogicProcessor;
import com.astraeus.core.game.processor.PeriodicalLogicProcessorConstants;
import com.astraeus.core.utility.IndexContainer;

public final class PeriodicalUpdateProcessor extends PeriodicalLogicProcessor {

	/**
	 * A collection supporting full concurrency of retrievals and high expected
	 * concurrency for updates. This collection is used to store active players
	 * in a index to instance relationship.
	 */
	private final ConcurrentHashMap<Integer, Player> players = new ConcurrentHashMap<Integer, Player>();

	/**
	 * A container for the assignment and organization of the player
	 * identification numbers.
	 */
	private final IndexContainer playerIndexes = new IndexContainer(2000);

	/**
	 * The overloaded class constructor used for instantiation of this class
	 * file.
	 */
	public PeriodicalUpdateProcessor() {
		super(PeriodicalLogicProcessorConstants.UPDATE_PROCESSOR_RATE);
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
		playerIndexes.discardIndex(player.getIndex());
	}

	/**
	 * Returns an instance of the concurrent player collection.
	 * 
	 * @return The returned instance.
	 */
	public ConcurrentHashMap<Integer, Player> getPlayers() {
		return players;
	}

	@Override
	public void execute() {

		for (final Player player : getPlayers().values()) {

			if (player == null) {

				continue;
			}

			player.prepare();
		}

		for (final Player player : players.values()) {
			if (player == null) {
				continue;
			}

			/*
			 * Determines if the update list contains a flag that denotes a
			 * regional update.
			 */
			if (player.getUpdateFlags().contains(UpdateFlags.UPDATE_MAP_REGION)) {
				player.getPacketSender().sendRegionalUpdate();
			}
			player.getEventListener().update(player);
		}

		for (final Player player : players.values()) {
			if (player == null) {
				continue;
			}

			/*
			 * Clears any stray flags that didn't receive attention.
			 */
			player.getUpdateFlags().clear();
		}
	}
}