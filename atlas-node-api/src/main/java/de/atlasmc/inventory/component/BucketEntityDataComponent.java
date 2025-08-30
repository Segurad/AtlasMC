package de.atlasmc.inventory.component;

import de.atlasmc.DyeColor;
import de.atlasmc.entity.Axolotl.Variant;
import de.atlasmc.entity.Salmon.Type;
import de.atlasmc.entity.TropicalFish.Pattern;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface BucketEntityDataComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<BucketEntityDataComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BucketEntityDataComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.BUCKET_ENTITY_DATA.getNamespacedKey())
					.boolField("NoAI", BucketEntityDataComponent::hasNoAI, BucketEntityDataComponent::setNoAI, false)
					.boolField("Silent", BucketEntityDataComponent::isSilent, BucketEntityDataComponent::setSilent, false)
					.boolField("NoGravity", BucketEntityDataComponent::hasNoGravity, BucketEntityDataComponent::setNoGravity, false)
					.boolField("Glowing", BucketEntityDataComponent::isGlowing, BucketEntityDataComponent::setGlowing, false)
					.boolField("Invulnerable", BucketEntityDataComponent::isInvulnerable, BucketEntityDataComponent::setInvulnerable, false)
					.floatField("Health", BucketEntityDataComponent::getHealth, BucketEntityDataComponent::setHealth, 0)
					.intField("Age", BucketEntityDataComponent::getAge, BucketEntityDataComponent::setAge, 0)
					.enumIntField("Variant", BucketEntityDataComponent::getVariant, BucketEntityDataComponent::setVariant, Variant::getByID, null)
					.longField("HuntingCooldown", BucketEntityDataComponent::getHuntingCooldown, BucketEntityDataComponent::setHuntingCooldown, 0)
					.enumStringField("type", BucketEntityDataComponent::getSalmonType, BucketEntityDataComponent::setSalmonType, Type::getByName, null)
					.endComponent()
					.build();
	
	BucketEntityDataComponent clone();
	
	boolean hasNoAI();
	
	void setNoAI(boolean noai);
	
	boolean isSilent();
	
	void setSilent(boolean silent);
	
	boolean hasNoGravity();
	
	void setNoGravity(boolean gravity);
	
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
	
	int getTropicalFishVariant();
	
	void setTropicalFishVariant(int tropicalFishVariant);
	
	boolean isGlowing();
	
	void setGlowing(boolean glowing);

	Type getSalmonType();
	
	void setSalmonType(Type type);
	
}
