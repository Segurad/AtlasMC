package de.atlasmc.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.inventory.EquipmentSlot;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.ResourceSound;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.enums.EnumUtil;

public interface EquippableComponent extends ItemComponent {
	
	public static final NBTCodec<EquippableComponent>
	NBT_HANDLER = NBTCodec
					.builder(EquippableComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.EQUIPPABLE.getNamespacedKey())
					.codec("slot", EquippableComponent::getSlot, EquippableComponent::setSlot, EnumUtil.enumStringNBTCodec(EquipmentSlot.class))
					.codec("equip_sound", EquippableComponent::getEquipSound, EquippableComponent::setEquipSound, Sound.NBT_CODEC, EnumSound.ITEM_ARMOR_EQUIP_GENERIC)
					.codec("asset_id", EquippableComponent::getAssetID, EquippableComponent::setAssetID, NamespacedKey.NBT_CODEC)
					.codec("allowed_entities", EquippableComponent::getAllowedEntities, EquippableComponent::setAllowedEntities, DataSet.nbtCodec(EntityType.REGISTRY_KEY))
					.boolField("dispensable", EquippableComponent::isDispensable, EquippableComponent::setDispensable, true)
					.boolField("swappable", EquippableComponent::isSwappable, EquippableComponent::setSwappable, true)
					.boolField("damage_on_hurt", EquippableComponent::isDamageOnHurt, EquippableComponent::setDamageOnHurt, true)
					.boolField("equip_on_interact", EquippableComponent::isEquipOnInteract, EquippableComponent::setEquipOnInteract, true)
					.codec("camera_overlay", EquippableComponent::getCameraOverlay, EquippableComponent::setCameraOverlay, NamespacedKey.NBT_CODEC)
					.endComponent()
					.build();
	
	public static final StreamCodec<EquippableComponent>
	STREAM_CODEC = StreamCodec
					.builder(EquippableComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(EquippableComponent::getSlot, EquippableComponent::setSlot, StreamCodec.varIntToObject(EquipmentSlot.class, EquipmentSlot::getByEquippableID, EquipmentSlot::getEquippableID))
					.varIntEnumOrCodec(EquippableComponent::getEquipSound, EquippableComponent::setEquipSound, EnumSound.class, ResourceSound.STREAM_CODEC)
					.optional(EquippableComponent::hasAssetID)
					.namespacedKey(EquippableComponent::getAssetID, EquippableComponent::setAssetID)
					.optional(EquippableComponent::hasCameraOverlay)
					.namespacedKey(EquippableComponent::getCameraOverlay, EquippableComponent::setCameraOverlay)
					.optional(EquippableComponent::hasAllowedEntities)
					.codec(EquippableComponent::getAllowedEntities, EquippableComponent::setAllowedEntities, DataSet.streamCodec(EntityType.REGISTRY_KEY))
					.booleanValue(EquippableComponent::isDispensable, EquippableComponent::setDispensable)
					.booleanValue(EquippableComponent::isSwappable, EquippableComponent::setSwappable)
					.booleanValue(EquippableComponent::isDamageOnHurt, EquippableComponent::setDamageOnHurt)
					.build();
	
	EquipmentSlot getSlot();
	
	void setSlot(EquipmentSlot slot);
	
	Sound getEquipSound();
	
	void setEquipSound(Sound sound);
	
	DataSet<EntityType> getAllowedEntities();
	
	boolean hasAllowedEntities();
	
	void setAllowedEntities(DataSet<EntityType> entities);
	
	boolean isDispensable();
	
	void setDispensable(boolean dispensable);
	
	boolean isSwappable();
	
	void setSwappable(boolean swappable);
	
	boolean isDamageOnHurt();
	
	void setDamageOnHurt(boolean damage);
	
	boolean isEquipOnInteract();
	
	void setEquipOnInteract(boolean equipOnInteract);
	
	NamespacedKey getCameraOverlay();
	
	void setCameraOverlay(NamespacedKey overlay);
	
	default boolean hasCameraOverlay() {
		return getCameraOverlay() != null;
	}
	
	NamespacedKey getAssetID();
	
	default boolean hasAssetID() {
		return getAssetID() != null;
	}
	
	void setAssetID(NamespacedKey assetID);
	
	EquippableComponent clone();
	
	@Override
	default NBTCodec<? extends EquippableComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	@Override
	default StreamCodec<? extends EquippableComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
