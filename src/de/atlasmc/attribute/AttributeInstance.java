package de.atlasmc.attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class AttributeInstance {
	
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
			modifiers = new ArrayList<AttributeModifier>();
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

	public boolean removeModifier(UUID uuid) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		if (!hasModifiers())
			return false;
		boolean removed = false;
		for (int i = 0; i < modifiers.size(); i++)
			if (modifiers.get(i).getUUID().equals(uuid)) {
				modifiers.remove(i--);
				changed = true;
				removed = true;
			}
		if (removed)
			if (updateListener != null)
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
		double scale = 0.0;
		double scale_1 = 1.0;
		for (AttributeModifier mod : modifiers) {
			switch (mod.getOperation()) {
			case ADD_NUMBER:
				value += mod.getAmount();
				break;
			case ADD_SCALAR:
				scale += mod.getAmount();
				break;
			case MULTIPLY_SCALAR_1:
				scale_1 += mod.getAmount();
				break;
			}
		}
		value *= scale;
		value *= scale_1;
	}
	
	/**
	 * Force updates this instance
	 */
	public void update() {
		recalcValue();
		if (updateListener != null)
			updateListener.accept(this);
	}

}
