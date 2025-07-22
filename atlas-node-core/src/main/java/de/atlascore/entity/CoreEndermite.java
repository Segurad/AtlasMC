package de.atlascore.entity;

import de.atlasmc.entity.Endermite;
import de.atlasmc.entity.EntityType;

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
