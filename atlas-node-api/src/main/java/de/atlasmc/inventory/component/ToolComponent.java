package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.itempredicate.MaterialItemPredicate;
import de.atlasmc.inventory.itempredicate.MaterialTagItemPredicate;

public interface ToolComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:tool");
	
	float getDefaultMinigSpeed();
	
	void setDefaultMinigSpeed(float speed);
	
	int getDamagePerBlock();
	
	void setDamagePerBlock(int damage);
	
	List<Rule> getRules();
	
	boolean hasRules();
	
	Rule createRule();
	
	void addRule(Rule rule);
	
	void removeRule(Rule rule);
	
	public static interface Rule {
		
		MaterialItemPredicate getBlockPredicate();
		
		MaterialTagItemPredicate getBlockTagPredicate();
		
		float getSpeed();
		
		void setSpeed(float speed);
		
		boolean isCorredForDrops();
		
		void setCorrectForDrop(boolean correct);
		
		boolean matches(Material material);
		
	}

}
