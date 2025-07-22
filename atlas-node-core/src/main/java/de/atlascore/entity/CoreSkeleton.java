package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Skeleton;

public class CoreSkeleton extends CoreMob implements Skeleton {

	private int strayConversion;
	
	public CoreSkeleton(EntityType type) {
		super(type);
	}

	@Override
	public int getStrayConversionTime() {
		return strayConversion;
	}

	@Override
	public void setStrayConversionTime(int time) {
		this.strayConversion = time;
	}

}
