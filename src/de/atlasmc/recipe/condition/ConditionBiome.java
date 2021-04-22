package de.atlasmc.recipe.condition;

import de.atlasmc.entity.Player;
import de.atlasmc.world.EnumBiome;

public final class ConditionBiome implements Condition {

	private final EnumBiome bio;
	
	public ConditionBiome(EnumBiome bio) {
		this.bio = bio;
	}
	
	@Override
	public boolean isValid(Player player) {
		return player.getLocation().getBlock().getBiome() == bio;
	}

	@Override
	public void charge(Player player) {}

	@Override
	public boolean isPayCondition() {
		return false;
	}

}
