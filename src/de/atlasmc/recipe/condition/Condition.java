package de.atlasmc.recipe.condition;

import de.atlasmc.entity.Player;

public interface Condition {

	public boolean isValid(Player player);
	public void charge(Player player);
	public boolean isPayCondition();
}
