package de.atlasmc.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.inventory.EquipmentSlot;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.ResourceSound;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface EquippableComponent extends ItemComponent {
	
	public static final NBTCodec<EquippableComponent>
	NBT_HANDLER = NBTCodec
					.builder(EquippableComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.EQUIPPABLE.getNamespacedKey())
					.enumStringField("slot", EquippableComponent::getSlot, EquippableComponent::setSlot, EquipmentSlot.class, null)
					.enumStringOrType("equip_sound", EquippableComponent::getEquipSound, EquippableComponent::setEquipSound, EnumSound.class, ResourceSound.NBT_CODEC, EnumSound.ITEM_ARMOR_EQUIP_GENERIC)
					.namespacedKey("asset_id", EquippableComponent::getAssetID, EquippableComponent::setAssetID)
					.dataSetField("allowed_entities", EquippableComponent::getAllowedEntities, EquippableComponent::setAllowedEntities, Registries.getRegistry(EntityType.class))
					.boolField("dispensable", EquippableComponent::isDispensable, EquippableComponent::setDispensable, true)
					.boolField("swappable", EquippableComponent::isSwappable, EquippableComponent::setSwappable, true)
					.boolField("damage_on_hurt", EquippableComponent::isDamageOnHurt, EquippableComponent::setDamageOnHurt, true)
					.boolField("equip_on_interact", EquippableComponent::isEquipOnInteract, EquippableComponent::setEquipOnInteract, true)
					.namespacedKey("camera_overlay", EquippableComponent::getCameraOverlay, EquippableComponent::setCameraOverlay)
					.endComponent()
					.build();
	
	public static final StreamCodec<EquippableComponent>
	STREAM_CODEC = StreamCodec
					.builder(EquippableComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.varIntToObject(EquippableComponent::getSlot, EquippableComponent::setSlot, EquipmentSlot::getByEquippableID, EquipmentSlot::getEquippableID)
					.enumValueOrCodec(EquippableComponent::getEquipSound, EquippableComponent::setEquipSound, EnumSound.class, ResourceSound.STREAM_CODEC)
					.optional(EquippableComponent::hasAssetID)
					.namespacedKey(EquippableComponent::getAssetID, EquippableComponent::setAssetID)
					.optional(EquippableComponent::hasCameraOverlay)
					.namespacedKey(EquippableComponent::getCameraOverlay, EquippableComponent::setCameraOverlay)
					.optional(EquippableComponent::hasAllowedEntities)
					.dataSet(EquippableComponent::getAllowedEntities, EquippableComponent::setAllowedEntities, EntityType.REGISTRY_KEY)
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
