package de.atlasmc.recipe.condition;

import de.atlasmc.entity.Player;
import de.atlasmc.world.World;

public final class ConditionWorld implements Condition {

	private final World world;
	
	public ConditionWorld(World world) {
		this.world = world;
	}
	
	@Override
	public boolean isValid(Player player) {
		return player.getWorld().equals(world);
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
