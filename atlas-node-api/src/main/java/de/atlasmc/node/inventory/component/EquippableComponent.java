package de.atlasmc.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.inventory.EquipmentSlot;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface EquippableComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<EquippableComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(EquippableComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.EQUIPPABLE.getNamespacedKey())
					.enumStringField("slot", EquippableComponent::getSlot, EquippableComponent::setSlot, EquipmentSlot::getByName, null)
					.addField(Sound.getNBTSoundField("equip_sound", EquippableComponent::getEquipSound, EquippableComponent::setEquipSound, EnumSound.ITEM_ARMOR_EQUIP_GENERIC))
					.namespacedKey("asset_id", EquippableComponent::getAssetID, EquippableComponent::setAssetID)
					.dataSetField("allowed_entities", EquippableComponent::getAllowedEntities, EquippableComponent::setAllowedEntities, Registries.getRegistry(EntityType.class))
					.boolField("dispensable", EquippableComponent::isDispensable, EquippableComponent::setDispensable, true)
					.boolField("swappable", EquippableComponent::isSwappable, EquippableComponent::setSwappable, true)
					.boolField("damage_on_hurt", EquippableComponent::isDamageOnHurt, EquippableComponent::setDamageOnHurt, true)
					.boolField("equip_on_interact", EquippableComponent::isEquipOnInteract, EquippableComponent::setEquipOnInteract, true)
					.namespacedKey("camera_overlay", EquippableComponent::getCameraOverlay, EquippableComponent::setCameraOverlay)
					.endComponent()
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
	
	NamespacedKey getAssetID();
	
	void setAssetID(NamespacedKey assetID);
	
	EquippableComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends EquippableComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
