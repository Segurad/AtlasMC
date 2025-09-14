package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Vindicator;

public class CoreVindicator extends CoreRaider implements Vindicator {
	
	private boolean johnny;
	
	public CoreVindicator(EntityType type) {
		super(type);
	}

	@Override
	public void setJohnny(boolean johnny) {
		this.johnny = johnny;
	}

	@Override
	public boolean isJohnny() {
		return johnny;
	}

}
