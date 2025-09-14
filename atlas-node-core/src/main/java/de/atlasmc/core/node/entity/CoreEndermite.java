package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.Endermite;
import de.atlasmc.node.entity.EntityType;

public class CoreEndermite extends CoreMob implements Endermite {
	
	private int lifetime;
	
	public CoreEndermite(EntityType type) {
		super(type);
	}

	@Override
	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;		
	}

	@Override
	public int getLifetime() {
		return lifetime;
	}

}
