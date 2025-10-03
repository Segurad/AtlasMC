package de.atlasmc.node.recipe.condition;

import de.atlasmc.node.entity.Player;
import de.atlasmc.node.world.Region;

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
	public void pay(Player player) {
		// not required
	}

	@Override
	public boolean isPayCondition() {
		return false;
	}

}
