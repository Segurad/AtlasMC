package de.atlasmc.inventory.component.effect;

import java.io.IOException;

import de.atlasmc.entity.Entity;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.IOReadable;
import de.atlasmc.io.IOWriteable;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;

public interface ComponentEffect extends NBTHolder, IOReadable, IOWriteable, Cloneable {
	
	public static final CharKey NBT_TYPE = CharKey.literal("type");
	
	ComponentEffectType getType();
	
	void apply(Entity target, ItemStack item);
	
	static ComponentEffect getFromNBT(NBTReader reader) throws IOException {
		if (reader.getType() == TagType.TAG_END) { // Empty Tag 
			reader.readNextEntry();
			return null;
		}
		String rawType = null;
		if (!NBT_TYPE.equals(reader.getFieldName())) {
			reader.mark();
			reader.search(NBT_TYPE);
			rawType = reader.readStringTag();
			reader.reset();
		} else {
			rawType = reader.readStringTag();
		}
		if (rawType == null) {
			throw new NBTException("NBT did not container type field!");
		}
		ComponentEffectType type = ComponentEffectType.getByName(rawType);
		if (type == null) {
			throw new NBTException("Not type found with name: " + rawType);
		}
		ComponentEffectFactory factory = ComponentEffectFactory.REGISTRY.get(type.getNamespacedKey());
		if (factory == null)
			throw new IllegalStateException("No component effect factory found with key: " + type.getNamespacedKey());
		ComponentEffect effect = factory.createEffect();
		effect.fromNBT(reader);
		return effect;
	}
	
	ComponentEffect clone();

}
