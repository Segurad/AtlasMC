package de.atlascore.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.entity.DamageType;
import de.atlasmc.event.entity.EntityDamageEvent.DamageReduction;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.BlocksAttacksComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.sound.Sound;
import de.atlasmc.tag.TagKey;

public class CoreBlocksAttacksComponent extends AbstractItemComponent implements BlocksAttacksComponent {

	private float blockDelay;
	private float disableCooldownScale;
	private List<DamageReduction> reduction;
	private ItemDamage damage;
	private Sound blockSound;
	private Sound disableSound;
	private TagKey<DamageType> bypassedBy;
	
	public CoreBlocksAttacksComponent(ComponentType type) {
		super(type);
	}

	@Override
	public float getBlockDelay() {
		return blockDelay;
	}

	@Override
	public void setBlockDelay(float delay) {
		this.blockDelay = delay;
	}

	@Override
	public float getDisableCooldownScale() {
		return disableCooldownScale;
	}

	@Override
	public void setDisableCooldownScale(float scale) {
		this.disableCooldownScale = scale;
	}

	@Override
	public boolean hasReduction() {
		return reduction != null && !reduction.isEmpty();
	}

	@Override
	public List<DamageReduction> getReduction() {
		if (reduction == null)
			reduction = new ArrayList<>();
		return reduction;
	}

	@Override
	public ItemDamage getItemDamage() {
		return damage;
	}

	@Override
	public void setItemDamage(ItemDamage damage) {
		this.damage = damage;
	}

	@Override
	public Sound getBlockSound() {
		return blockSound;
	}

	@Override
	public void setBlockSound(Sound sound) {
		this.blockSound = sound;
	}

	@Override
	public Sound getDisabledSound() {
		return disableSound;
	}

	@Override
	public void setDisabledSound(Sound sound) {
		this.disableSound = sound;
	}

	@Override
	public TagKey<DamageType> getBypassedBy() {
		return bypassedBy;
	}

	@Override
	public void setBypassedBy(TagKey<DamageType> types) {
		this.bypassedBy = types;
	}

}
