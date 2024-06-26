package de.atlasmc.recipe.condition;

import de.atlasmc.entity.Player;

public class ConditionTimespace implements Condition {
	
	private final long start;
	private final long end;
	
	public ConditionTimespace(long start, long end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	public boolean isValid(Player player) {
		long t = player.getWorld().getTime();
		return t >= start && t <= end;
	}

	@Override
	public void pay(Player player) {}

	@Override
	public boolean isPayCondition() {
		return false;
	}

}
