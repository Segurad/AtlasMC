package de.atlasmc.node.attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.inventory.EquipmentSlot;
import de.atlasmc.util.AtlasUtil;

public class AttributeInstance implements NBTSerializable {
	
	public static final NBTCodec<AttributeInstance>
	NBT_HANDLER = NBTCodec
					.builder(AttributeInstance.class)
					.doubleField("base", AttributeInstance::getBaseValue, AttributeInstance::setBaseValue, 0)
					.codecList("modifiers", AttributeInstance::hasModifiers, AttributeInstance::getModifiers, AttributeModifier.NBT_CODEC)
					.build();
	
	private final Consumer<AttributeInstance> updateListener;
	private final Attribute attribute;
	private final double defaultValue;
	private double baseValue;
	private double value;
	private boolean changed;
	private List<AttributeModifier> modifiers;
	
	public AttributeInstance(Attribute attribute, double defaultValue, Consumer<AttributeInstance> updateListener) {
		if (attribute == null)
			throw new IllegalArgumentException("Attribute can not be null!");
		this.attribute = attribute;
		this.defaultValue = defaultValue;
		baseValue = defaultValue;
		this.updateListener = updateListener;
	}
	
	public Attribute getAttribute() {
		return attribute;
	}

	public double getDefaultValue() {
		return defaultValue;
	}

	public int getModifierCount() {
		return modifiers != null ? modifiers.size() : 0;
	}

	public List<AttributeModifier> getModifiers() {
		if (modifiers == null) 
			modifiers = new ArrayList<>();
		return modifiers;
	}
	
	public double getBaseValue() {
		return baseValue;
	}
	
	public void setBaseValue(double value) {
		this.baseValue = value;
		changed = true;
		if (updateListener != null)
			updateListener.accept(this);
	}
	
	public void addAttributeModififer(AttributeModifier modifier) {
		getModifiers().add(modifier);
		changed = true;
		if (updateListener != null)
			updateListener.accept(this);
	}

	public boolean removeModifiers() {
		if (modifiers == null || modifiers.isEmpty())
			return false;
		modifiers.clear();
		changed = true;
		if (updateListener != null)
			updateListener.accept(this);
		return true;
	}

	public boolean hasModifiers() {
		return modifiers != null && !modifiers.isEmpty();
	}

	public void setModifiers(Collection<AttributeModifier> modifiers) {
		if (modifiers == null)
			throw new IllegalArgumentException("Modifiers can not be null!");
		if (!hasModifiers() && modifiers.isEmpty())
			return;
		if (this.modifiers == null) {
			this.modifiers = new ArrayList<>(modifiers);
			if (updateListener != null)
				updateListener.accept(this);
			return;
		}
		List<AttributeModifier> mods = getModifiers();
		mods.clear();
		mods.addAll(modifiers);
		changed = true;
		if (updateListener != null)
			updateListener.accept(this);
	}
	
	public boolean removeModifier(AttributeModifier modifier) {
		if (modifier == null)
			throw new IllegalArgumentException("Modifier can not be null!");
		if (!hasModifiers())
			return false;
		boolean removed = false;
		for (int i = 0; i < modifiers.size(); i++) {
			if (!modifier.equals(modifiers.get(i)))
				continue;
			AtlasUtil.fastRemove(modifiers, i--);
			changed = true;
			removed = true;
		}
		if (removed && updateListener != null)
			updateListener.accept(this);
		return removed;
	}

	public boolean removeModifier(NamespacedKey id) {
		if (id == null)
			throw new IllegalArgumentException("ID can not be null!");
		if (!hasModifiers())
			return false;
		boolean removed = false;
		for (int i = 0; i < modifiers.size(); i++) {
			if (!id.equals(modifiers.get(i).getID()))
				continue;
			AtlasUtil.fastRemove(modifiers, i--);
			changed = true;
			removed = true;
		}
		if (removed && updateListener != null)
			updateListener.accept(this);
		return removed;
	}
	
	/**
	 * Returns the value with all modifiers applied
	 * @return value
	 */
	public double getValue() {
		if (changed)
			recalcValue();
		return value;
	}
	
	private void recalcValue() {
		changed = false;
		value = baseValue;
		if (!hasModifiers())
			return;
		double multiplyBase = 1.0;
		double multiplyTotal = 1.0;
		for (AttributeModifier mod : modifiers) {
			switch (mod.getOperation()) {
			case ADD_VALUE:
				value += mod.getAmount();
				break;
			case ADD_MULTIPLIED_BASE:
				multiplyBase += mod.getAmount();
				break;
			case ADD_MULTIPLIED_TOTAL:
				multiplyTotal += 1 + mod.getAmount();
				break;
			default:
				throw new IllegalArgumentException("Unknwon operation: " + mod.getOperation());
			}
		}
		value += baseValue * multiplyBase;
		value += value * multiplyTotal;
	}
	
	/**
	 * Force updates this instance
	 */
	public void update() {
		recalcValue();
		if (updateListener != null)
			updateListener.accept(this);
	}

	public boolean removeModifier(EquipmentSlot slot) {
		if (slot == null)
			throw new IllegalArgumentException("Slot can not be null!");
		if (!hasModifiers())
			return false;
		boolean removed = false;
		for (int i = 0; i < modifiers.size(); i++) {
			if (slot != modifiers.get(i).getSlot())
				continue;
			AtlasUtil.fastRemove(modifiers, i--);
			changed = true;
			removed = true;
		}
		if (removed && updateListener != null)
			updateListener.accept(this);
		return removed;
	}
	
	@Override
	public NBTCodec<? extends AttributeInstance> getNBTCodec() {
		return NBT_HANDLER;
	}

}
