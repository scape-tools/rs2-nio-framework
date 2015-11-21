package com.astraeus.core.game.model.entity.mobile.npc;

import com.astraeus.core.game.model.entity.Entity;
import com.astraeus.core.game.model.entity.EntityEventListener;

public class Npc extends Entity {

	@Override
	public EntityEventListener<? extends Entity> getEventListener() {
		return null;
	}

}
