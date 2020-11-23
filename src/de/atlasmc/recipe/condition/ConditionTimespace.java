package de.atlasmc.recipe.condition;

import de.atlasmc.entity.Player;

public class ConditionTimespace implements Condition {
	
	private final long start, end;
	
	public ConditionTimespace(long start, long end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	public boolean isValid(Player player) {
		long t = player.getLocation().getWorld().getTime();
		return t >= start && t <= end;
	}

	@Override
	public void charge(Player player) {}

	@Override
	public boolean isPayCondition() {
		return false;
	}

}
