package de.atlasmc.node.recipe.condition;

import de.atlasmc.node.entity.Player;

public interface Condition {

	boolean isValid(Player player);
	
	void pay(Player player);
	
	boolean isPayCondition();
	
}
