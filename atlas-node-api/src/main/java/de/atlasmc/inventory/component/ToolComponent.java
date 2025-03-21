package de.atlasmc.inventory.component;

import java.util.List;
import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemType;
import de.atlasmc.inventory.itempredicate.ItemTypePredicate;
import de.atlasmc.inventory.itempredicate.TagItemPredicate;

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
		
		ItemTypePredicate getBlockPredicate();
		
		TagItemPredicate getBlockTagPredicate();
		
		float getSpeed();
		
		void setSpeed(float speed);
		
		boolean isCorredForDrops();
		
		void setCorrectForDrop(boolean correct);
		
		boolean matches(ItemType type);
		
	}

}
