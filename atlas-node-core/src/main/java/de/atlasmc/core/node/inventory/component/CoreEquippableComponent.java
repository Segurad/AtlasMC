package de.atlasmc.core.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.inventory.EquipmentSlot;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.EquippableComponent;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.util.dataset.DataSet;

public class CoreEquippableComponent extends AbstractItemComponent implements EquippableComponent {
	
	private EquipmentSlot slot;
	private Sound equipSound;
	private NamespacedKey assetID;
	private DataSet<EntityType> allowedEntities;
	private boolean dispensable;
	private boolean swappable;
	private boolean damageOnHurt;
	private boolean equipOnInteract;
	private NamespacedKey cameraOverlay;
	
	public CoreEquippableComponent(ComponentType type) {
		super(type);
		dispensable = true;
		swappable = true;
		damageOnHurt = true;
		equipOnInteract = true;
		equipSound = EnumSound.ITEM_ARMOR_EQUIP_GENERIC;
	}
	
	@Override
	public CoreEquippableComponent clone() {
		CoreEquippableComponent clone = (CoreEquippableComponent) super.clone();
		return clone;
	}
	
	@Override
	public boolean isEquipOnInteract() {
		return equipOnInteract;
	}
	
	@Override
	public void setEquipOnInteract(boolean equipOnInteract) {
		this.equipOnInteract = equipOnInteract;
	}

	@Override
	public EquipmentSlot getSlot() {
		return slot;
	}

	@Override
	public void setSlot(EquipmentSlot slot) {
		this.slot = slot;
	}

	@Override
	public Sound getEquipSound() {
		return equipSound;
	}

	@Override
	public void setEquipSound(Sound sound) {
		if (sound == null) {
			this.equipSound = EnumSound.ITEM_ARMOR_EQUIP_GENERIC;
		} else {
			this.equipSound = sound;
		}
	}

	@Override
	public boolean hasAllowedEntities() {
		return allowedEntities != null;
	}

	@Override
	public boolean isDispensable() {
		return dispensable;
	}

	@Override
	public void setDispensable(boolean dispensable) {
		this.dispensable = dispensable;
	}

	@Override
	public boolean isSwappable() {
		return swappable;
	}

	@Override
	public void setSwappable(boolean swappable) {
		this.swappable = swappable;
	}

	@Override
	public boolean isDamageOnHurt() {
		return damageOnHurt;
	}

	@Override
	public void setDamageOnHurt(boolean damage) {
		this.damageOnHurt = damage;
	}

	@Override
	public DataSet<EntityType> getAllowedEntities() {
		return allowedEntities;
	}

	@Override
	public void setAllowedEntities(DataSet<EntityType> entities) {
		this.allowedEntities = entities;
	}

	@Override
	public NamespacedKey getCameraOverlay() {
		return cameraOverlay;
	}

	@Override
	public void setCameraOverlay(NamespacedKey overlay) {
		this.cameraOverlay = overlay;
	}

	@Override
	public NamespacedKey getAssetID() {
		return assetID;
	}

	@Override
	public void setAssetID(NamespacedKey assetID) {
		this.assetID = assetID;
	}

}
