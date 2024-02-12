package de.atlasmc.recipe.condition;

import de.atlasmc.entity.Player;

public final class ConditionLevel implements Condition {

	private final int lvl;
	private final boolean pay;
	
	public ConditionLevel(int value, boolean pay) {
		this.lvl = value;
		this.pay = pay;
	}
	
	@Override
	public boolean isValid(Player player) {
		return player.getLevel() >= lvl;
	}

	@Override
	public void pay(Player player) {
		if (pay)
			player.setLevel(player.getLevel() - lvl);
	}

	@Override
	public boolean isPayCondition() {
		return pay;
	}

}
