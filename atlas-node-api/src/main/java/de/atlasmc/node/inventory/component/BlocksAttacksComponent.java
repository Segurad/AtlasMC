package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.entity.DamageType;
import de.atlasmc.node.event.entity.EntityDamageEvent.DamageReduction;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.tag.TagKey;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface BlocksAttacksComponent extends ItemComponent {
	
	public static final NBTCodec<BlocksAttacksComponent>
	NBT_HANDLER = NBTCodec
					.builder(BlocksAttacksComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.BLOCKS_ATTACKS.getNamespacedKey())
					.floatField("block_delay_seconds", BlocksAttacksComponent::getBlockDelay, BlocksAttacksComponent::setBlockDelay, 0)
					.floatField("disable_cooldown_scale", BlocksAttacksComponent::getDisableCooldownScale, BlocksAttacksComponent::setDisableCooldownScale, 1)
					.codecList("damage_reductions", BlocksAttacksComponent::hasReduction, BlocksAttacksComponent::getReduction, DamageReduction.NBT_HANDLER)
					.codec("item_damage", BlocksAttacksComponent::getItemDamage, BlocksAttacksComponent::setItemDamage, ItemDamage.NBT_HANDLER)
					.codec("block_sound", BlocksAttacksComponent::getBlockSound, BlocksAttacksComponent::setBlockSound, Sound.NBT_CODEC)
					.codec("disabled_sound", BlocksAttacksComponent::getDisabledSound, BlocksAttacksComponent::setDisabledSound, Sound.NBT_CODEC)
					.codec("bypassed_by", BlocksAttacksComponent::getBypassedBy, BlocksAttacksComponent::setBypassedBy, TagKey.NBT_CODEC)
					.endComponent()
					.build();
	
	float getBlockDelay();
	
	void setBlockDelay(float delay);
	
	float getDisableCooldownScale();
	
	void setDisableCooldownScale(float scale);
	
	boolean hasReduction();
	
	@NotNull
	List<DamageReduction> getReduction();
	
	@Nullable
	ItemDamage getItemDamage();
	
	void setItemDamage(@Nullable ItemDamage damage);
	
	@Nullable
	Sound getBlockSound();
	
	void setBlockSound(@Nullable Sound sound);
	
	@Nullable
	Sound getDisabledSound();
	
	void setDisabledSound(@Nullable Sound sound);
	
	@Nullable
	TagKey<DamageType> getBypassedBy();
	
	void setBypassedBy(@Nullable TagKey<DamageType> types);
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	public static class ItemDamage implements NBTSerializable {
		
		public static final NBTCodec<ItemDamage>
		NBT_HANDLER = NBTCodec
						.builder(ItemDamage.class)
						.setRedirectAfterConstruction(false)
						.floatField("threshold", ItemDamage::getThreashold, ItemDamage::setThreashold, 0)
						.floatField("base", ItemDamage::getBase, ItemDamage::setBase, 0)
						.floatField("factor", ItemDamage::getFactor, ItemDamage::setFactor, 0)
						.build();
		
		private float threashold;
		private float base;
		private float factor;
		
		public float getThreashold() {
			return threashold;
		}
		
		public void setThreashold(float threashold) {
			this.threashold = threashold;
		}
		
		public float getBase() {
			return base;
		}
		
		public void setBase(float base) {
			this.base = base;
		}
		
		public float getFactor() {
			return factor;
		}
		
		public void setFactor(float factor) {
			this.factor = factor;
		}

		@Override
		public NBTCodec<? extends NBTSerializable> getNBTCodec() {
			return NBT_HANDLER;
		}
		
	}
	
}
