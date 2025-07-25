package de.atlasmc.recipe.condition;

import de.atlasmc.entity.Player;
import de.atlasmc.world.Biome;

public final class ConditionBiome implements Condition {

	private final Biome bio;
	
	public ConditionBiome(Biome bio) {
		this.bio = bio;
	}
	
	@Override
	public boolean isValid(Player player) {
		return player.getLocation().getBlock().getBiome() == bio;
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
