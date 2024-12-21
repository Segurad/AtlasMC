package de.atlasmc.inventory.component;

import de.atlasmc.DyeColor;
import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.Axolotl.Variant;
import de.atlasmc.entity.Salmon.Type;
import de.atlasmc.entity.TropicalFish.Pattern;

public interface BucketEntityDataComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:block_entity_data");
	
	BucketEntityDataComponent clone();
	
	boolean hasAI();
	
	void setAI(boolean ai);
	
	boolean isSilent();
	
	void setSilent(boolean silent);
	
	boolean hasGravity();
	
	void setGravity(boolean gravity);
	
	boolean isInvulnerable();
	
	void setInvulnerable(boolean invulnerable);
	
	float getHealth();
	
	void setHealth(float health);
	
	int getAge();
	
	void setAge(int age);
	
	Variant getVariant();
	
	void setVariant(Variant variant);
	
	long getHuntingCooldown();
	
	void setHuntingCooldown(long cooldown);
	
	DyeColor getBodyColor();
	
	Pattern getPattern();
	
	DyeColor getPatternColor();
	
	void setBodyColor(DyeColor color);
	
	void setPattern(Pattern pattern);
	
	void setPatternColor(DyeColor color);
	
	boolean isGlowing();
	
	void setGlowing(boolean glowing);

	Type getSalmonType();
	
	void setSalmonType(Type type);
	
}
