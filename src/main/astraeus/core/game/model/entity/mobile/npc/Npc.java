package main.astraeus.core.game.model.entity.mobile.npc;

import main.astraeus.core.game.model.entity.EntityEventListener;
import main.astraeus.core.game.model.entity.Position;
import main.astraeus.core.game.model.entity.mobile.MobileEntity;

public class Npc extends MobileEntity {
	
	/**
	 * The id of this npc.
	 */
	private int id;
	
	private Position initialPosition;
	
	private int spawnIndex;
	
	private final NpcEventListener listener = new NpcEventListener();

	public Npc(int id, int spawnIndex) {
		this.id = id;
		this.spawnIndex = spawnIndex;
	}

	public void prepare() {
		getMovement().handleEntityMovement();
	}
	
	/**
	 * Gets the id of this npc.
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	public void setInitialPosition(Position initialPosition) {
		this.initialPosition = initialPosition;
	}
	
	/**
	 * @return the position
	 */
	public Position getPosition() {
		return initialPosition;
	}
	
	/**
	 * @return the spawnIndex
	 */
	public int getSpawnIndex() {
		return spawnIndex;
	}

	@Override
	public EntityEventListener<Npc> getEventListener() {
		return listener;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
