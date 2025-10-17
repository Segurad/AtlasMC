package de.atlasmc.node.inventory.component.effect;

import de.atlasmc.io.IOReadable;
import de.atlasmc.io.IOWriteable;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ComponentEffect extends NBTSerializable, IOReadable, IOWriteable, Cloneable {
	
	public static final NBTCodec<ComponentEffect>
	NBT_HANDLER = NBTCodec
					.builder(ComponentEffect.class)
					.searchKeyConstructor("type", ComponentEffectType.REGISTRY_KEY, ComponentEffectType::createEffect, ComponentEffect::getType)
					.build();
	
	ComponentEffectType getType();
	
	void apply(Entity target, ItemStack item);
	
	ComponentEffect clone();
	
	@Override
	default NBTCodec<? extends ComponentEffect> getNBTCodec() {
		return NBT_HANDLER;
	}

}
