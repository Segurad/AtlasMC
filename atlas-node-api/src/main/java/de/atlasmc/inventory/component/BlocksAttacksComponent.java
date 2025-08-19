package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.entity.DamageType;
import de.atlasmc.event.entity.EntityDamageEvent.DamageReduction;
import de.atlasmc.sound.Sound;
import de.atlasmc.tag.TagKey;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface BlocksAttacksComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<BlocksAttacksComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BlocksAttacksComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.BLOCKS_ATTACKS)
					.floatField("block_delay_seconds", BlocksAttacksComponent::getBlockDelay, BlocksAttacksComponent::setBlockDelay, 0)
					.floatField("disable_cooldown_scale", BlocksAttacksComponent::getDisableCooldownScale, BlocksAttacksComponent::setDisableCooldownScale, 1)
					.typeList("damage_reductions", BlocksAttacksComponent::hasReduction, BlocksAttacksComponent::getReduction, DamageReduction.NBT_HANDLER)
					.typeCompoundField("item_damage", BlocksAttacksComponent::getItemDamage, BlocksAttacksComponent::setItemDamage, ItemDamage.NBT_HANDLER)
					.addField(Sound.getNBTSoundField("block_sound", BlocksAttacksComponent::getBlockSound, BlocksAttacksComponent::setBlockSound, null))
					.addField(Sound.getNBTSoundField("disabled_sound", BlocksAttacksComponent::getDisabledSound, BlocksAttacksComponent::setDisabledSound, null))
					.tagField("bypassed_by", BlocksAttacksComponent::getBypassedBy, BlocksAttacksComponent::setBypassedBy)
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
	default NBTSerializationHandler<? extends ItemComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static class ItemDamage implements NBTSerializable {
		
		public static final NBTSerializationHandler<ItemDamage>
		NBT_HANDLER = NBTSerializationHandler
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
		public NBTSerializationHandler<? extends NBTSerializable> getNBTHandler() {
			return NBT_HANDLER;
		}
		
	}
	
}
