package main.astraeus.game.pulse.impl;

import main.astraeus.game.model.Position;
import main.astraeus.game.model.entity.mobile.player.Player;
import main.astraeus.game.model.entity.mobile.player.Player.Attributes;
import main.astraeus.game.model.entity.mobile.player.action.PlayerWalkToActions;
import main.astraeus.game.pulse.Pulse;

/**
 * @author Dylan Vicchiarelli
 *
 * Delays all entity interaction until the player is within proper distance
 * of the entity.
 */
public final class InteractionDistancePulse extends Pulse {

	/**
	 * The type of interaction being performed.
	 */
	private final InteractionType interactionType;

	/**
	 * The destination of the entity.
	 */
	private final Position destination;

	/**
	 * The player performing the interaction.
	 */
	private final Player player;

	/**
	 * Defines the possible entity interaction types.
	 */
	public enum InteractionType {

		RETREIVE_ITEM,

		OBJECT_INTERACTION_FIRST_CLICK,

		OBJECT_INTERACTION_SECOND_CLICK,

		MOB_INTERACTION_FIRST_CLICK
	}

	/**
	 * The default class constructor for this pulse implementation.
	 * 
	 * @param player The player performing the interaction.
	 * 
	 * @param interaction The type of interaction being performed.
	 */
	public InteractionDistancePulse(Player player, InteractionType interaction) {

		super(600, player.getDetails().getUsername());

		this.interactionType = interaction;
		this.player = player;
		this.destination = new Position((Integer) player.getAttributes().get(Attributes.CLICK_X), (Integer) player.getAttributes().get(Attributes.CLICK_Y), player.getPosition().getHeight());
	}

	@Override
	public void execute() {

		player.getAttributes().put(Attributes.WALK_TO_ACTION, true);

		switch (interactionType) {

		case OBJECT_INTERACTION_FIRST_CLICK:

			if (player.getPosition().isWithinDistance(destination, 1)) {
				player.faceDirection(destination);
				setActive(false);
			}
			break;

		case OBJECT_INTERACTION_SECOND_CLICK:

			if (player.getPosition().isWithinDistance(destination, 1)) {
				player.faceDirection(destination);
				setActive(false);
			}
			break;

		case RETREIVE_ITEM:

			if (player.getPosition().coordinatesEqual(destination)) {
				setActive(false);
			}
			break;

		case MOB_INTERACTION_FIRST_CLICK:

			if (player.getPosition().isWithinDistance(destination, 1)) {
				player.faceDirection(destination);
				setActive(false);
			}
			break;

		}
	}

	@Override
	public void stop() {

		if (!(Boolean) player.getAttributes().get(Attributes.WALK_TO_ACTION)) {
			return;
		}

		switch (interactionType) {

		case OBJECT_INTERACTION_FIRST_CLICK:
			if (player.getPosition().isWithinDistance(destination, 1)) {
				PlayerWalkToActions.objectFirstClick(player, destination, (Integer) player.getAttributes().get(Attributes.CLICK_INDEX));
			}
			break;
			
		case OBJECT_INTERACTION_SECOND_CLICK:
			if (player.getPosition().isWithinDistance(destination, 1)) {
				PlayerWalkToActions.objectSecondClick(player, destination, (Integer) player.getAttributes().get(Attributes.CLICK_INDEX));
			}
			break;
			
		case MOB_INTERACTION_FIRST_CLICK:
			break;

		case RETREIVE_ITEM:
			break;
			
		}
	}
}
