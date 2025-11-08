package de.atlasmc.core.node.inventory.component;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import de.atlasmc.node.attribute.Attribute;
import de.atlasmc.node.attribute.AttributeModifier;
import de.atlasmc.node.inventory.EquipmentSlot;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.AttributeModifiersComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.util.map.ArrayListMultimap;
import de.atlasmc.util.map.ListMultimap;
import de.atlasmc.util.map.Multimap;

public class CoreAttributeModifiersComponent extends AbstractItemComponent implements AttributeModifiersComponent {
	
	private ListMultimap<Attribute, AttributeModifier> attributes;
	
	public CoreAttributeModifiersComponent(ComponentType key) {
		super(key);
	}
	
	@Override
	public boolean hasAttributeModifiers() {
		return attributes != null && !attributes.isEmpty();
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
		if (attributes == null) 
			attributes = new ArrayListMultimap<>();
		return attributes;
	}

	@Override
	public List<AttributeModifier> getAttributeModifiers(Attribute attribute) {
		if (attribute == null)
			throw new IllegalArgumentException("Attribute can not be null!");
		if (attributes == null || attributes.isEmpty()) 
			return List.of();
		List<AttributeModifier> list = attributes.get(attribute);
		return list == null ? List.of() : list;
	}

	@Override
	public boolean addAttributeModifier(Attribute attribute, AttributeModifier modifier) {
		if (attribute == null) 
			throw new IllegalArgumentException("Attribute can not be null!");
		if (modifier == null) 
			throw new IllegalArgumentException("AttributeModifier can not be null!");
		if (attributes == null)
			attributes = new ArrayListMultimap<>();
		return this.attributes.put(attribute, modifier);
	}
	
	@Override
	public void setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers) {
		if (attributeModifiers == null) 
			throw new IllegalArgumentException("AttributeModifier can not be null!");
		this.attributes.clear();
		this.attributes.putAll(attributeModifiers);
	}

	@Override
	public boolean removeAttributeModifier(Attribute attribute) {
		if (attribute == null) 
			throw new IllegalArgumentException("Attribute can not be null!");
		if (attributes == null) 
			return false;
		return attributes.remove(attribute) != null;
	}

	@Override
	public boolean removeAttributeModifier(Attribute attribute, AttributeModifier modifier) {
		if (attribute == null) 
			throw new IllegalArgumentException("Attribute can not be null!");
		if (modifier == null) 
			throw new IllegalArgumentException("AttributeModifier can not be null!");
		if (attributes == null) 
			return false;
		List<AttributeModifier> mods = getAttributeModifiers(attribute);
		if (mods == null) 
			return false;
		return mods.remove(modifier);
	}

	@Override
	public boolean removeAttributeModifier(EquipmentSlot slot) {
		if (slot == null) 
			throw new IllegalArgumentException("EquipmentSlot can not be null!");
		if (!hasAttributeModifiers()) 
			return false;
		boolean changes = false;
		for (Attribute a : attributes.keySet()) {
			List<AttributeModifier> mods = attributes.get(a);
			if (mods.isEmpty())
				continue;
			Iterator<AttributeModifier> modIt = mods.iterator();
			while (modIt.hasNext()) {
				AttributeModifier mod = modIt.next();
				if (mod.getSlot() == slot && mods.remove(mod)) {
					changes = true;
				}
			}
		}
		return changes;
	}
	
	@Override
	public CoreAttributeModifiersComponent clone() {
		CoreAttributeModifiersComponent clone =  (CoreAttributeModifiersComponent) super.clone();
		if (clone == null)
			return null;
		if (hasAttributeModifiers()) {
			clone.attributes = null;
			Multimap<Attribute, AttributeModifier> attributes = clone.getAttributeModifiers();
			for (Entry<Attribute, Collection<AttributeModifier>> entry : this.attributes.entrySet()) {
				Attribute attribute = entry.getKey();
				List<AttributeModifier> modifiers = (List<AttributeModifier>) entry.getValue();
				final int size = modifiers.size();
				for (int i = 0; i < size; i++) {
					attributes.put(attribute, modifiers.get(i).clone());
				}
			}
		}
		return clone;
	}

}
