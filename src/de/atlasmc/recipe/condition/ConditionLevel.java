package de.atlasmc.recipe.condition;

import de.atlasmc.entity.Player;

public final class ConditionLevel implements Condition {

	private final int lvl;
	
	public ConditionLevel(int value) {
		this.lvl = value;
	}
	
	@Override
	public boolean isValid(Player player) {
		return player.getLevel() >= lvl;
	}

	@Override
	public void charge(Player player) {
		player.setLevel(player.getLevel() - lvl);
	}

	@Override
	public boolean isPayCondition() {
		return false;
	}

}
