package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.GlowSquid;

public class CoreGlowSquid extends CoreSquid implements GlowSquid {
	
	private int darkTicksRemaining;
	
	public CoreGlowSquid(EntityType type) {
		super(type);
	}

	@Override
	public int getDarkTicksRemaining() {
		return darkTicksRemaining;
	}

	@Override
	public void setDarkTicksRemaining(int ticks) {
		this.darkTicksRemaining = Math.max(ticks, 0);
	}

}
