package de.atlasmc.recipe.condition;

import de.atlasmc.entity.Player;
import de.atlasmc.world.Region;

public final class ConditionRegion implements Condition {

	private final Region region;
	
	public ConditionRegion(Region region) {
		this.region = region;
	}
	
	@Override
	public boolean isValid(Player player) {
		return region.contains(player.getLocation());
	}

	@Override
	public void charge(Player player) {}

	@Override
	public boolean isPayCondition() {
		return false;
	}

}
