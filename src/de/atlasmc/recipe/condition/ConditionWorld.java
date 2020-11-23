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
		return player.getLocation().getWorld().equals(world);
	}

	@Override
	public void charge(Player player) {}

	@Override
	public boolean isPayCondition() {
		return false;
	}

}
