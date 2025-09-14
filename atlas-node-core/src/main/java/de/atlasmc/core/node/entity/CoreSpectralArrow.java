package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.SpectralArrow;

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
