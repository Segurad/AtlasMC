package de.atlasmc.recipe.condition;

import de.atlasmc.entity.Player;

public interface Condition {

	boolean isValid(Player player);
	
	void pay(Player player);
	
	boolean isPayCondition();
	
}
