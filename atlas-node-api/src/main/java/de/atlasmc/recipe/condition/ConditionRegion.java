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
		return region.contains(player.getX(), player.getY(), player.getZ());
	}

	@Override
	public void pay(Player player) {}

	@Override
	public boolean isPayCondition() {
		return false;
	}

}
