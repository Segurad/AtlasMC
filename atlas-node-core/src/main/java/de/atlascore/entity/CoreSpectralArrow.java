package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.SpectralArrow;

public class CoreSpectralArrow extends CoreAbstractArrow implements SpectralArrow {
	
	private int duration;
	
	public CoreSpectralArrow(EntityType type) {
		super(type);
	}

	@Override
	public void setDuration(int ticks) {
		this.duration = ticks;
	}

	@Override
	public int getDuration() {
		return duration;
	}

}
