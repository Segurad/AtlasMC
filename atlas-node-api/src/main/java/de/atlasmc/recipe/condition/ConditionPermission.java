package de.atlasmc.recipe.condition;

import de.atlasmc.entity.Player;

public final class ConditionPermission implements Condition {

	private final String perm;
	
	public ConditionPermission(String perm) {
		this.perm = perm;
	}
	
	@Override
	public boolean isValid(Player player) {
		return player.hasPermission(perm);
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
