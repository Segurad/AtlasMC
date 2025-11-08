package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.DyeColor;
import de.atlasmc.node.entity.Axolotl.Variant;
import de.atlasmc.node.entity.Salmon.Type;
import de.atlasmc.node.entity.TropicalFish.Pattern;
import de.atlasmc.util.AtlasUtil;
import de.atlasmc.util.enums.EnumUtil;

public interface BucketEntityDataComponent extends ItemComponent {
	
	public static final NBTCodec<BucketEntityDataComponent>
	NBT_CODEC = NBTCodec
					.builder(BucketEntityDataComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.BUCKET_ENTITY_DATA.getNamespacedKey())
					.boolField("NoAI", BucketEntityDataComponent::hasNoAI, BucketEntityDataComponent::setNoAI, false)
					.boolField("Silent", BucketEntityDataComponent::isSilent, BucketEntityDataComponent::setSilent, false)
					.boolField("NoGravity", BucketEntityDataComponent::hasNoGravity, BucketEntityDataComponent::setNoGravity, false)
					.boolField("Glowing", BucketEntityDataComponent::isGlowing, BucketEntityDataComponent::setGlowing, false)
					.boolField("Invulnerable", BucketEntityDataComponent::isInvulnerable, BucketEntityDataComponent::setInvulnerable, false)
					.floatField("Health", BucketEntityDataComponent::getHealth, BucketEntityDataComponent::setHealth, 0)
					.intField("Age", BucketEntityDataComponent::getAge, BucketEntityDataComponent::setAge, 0)
					.codec("Variant", BucketEntityDataComponent::getVariant, BucketEntityDataComponent::setVariant, EnumUtil.enumIntNBTCodec(Variant.class))
					.longField("HuntingCooldown", BucketEntityDataComponent::getHuntingCooldown, BucketEntityDataComponent::setHuntingCooldown, 0)
					.codec("type", BucketEntityDataComponent::getSalmonType, BucketEntityDataComponent::setSalmonType, EnumUtil.enumStringNBTCodec(Type.class))
					.endComponent()
					.build();

	public static final StreamCodec<BucketEntityDataComponent>
	STREAM_CODEC = StreamCodec
					.builder(BucketEntityDataComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(AtlasUtil.getSelf(), AtlasUtil.getSetVoid(), NBT_CODEC)
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
	
	@Override
	default NBTCodec<? extends BucketEntityDataComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends BucketEntityDataComponent> getStreamCodec() {
		return STREAM_CODEC;
	}
	
}
