package de.atlasmc.node.inventory.component.effect;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.OpenCloneable;
import de.atlasmc.util.annotation.NotNull;

public interface ComponentEffect extends NBTSerializable, StreamSerializable, OpenCloneable {
	
	@NotNull
	public static final NBTCodec<ComponentEffect>
	NBT_CODEC = NBTCodec
					.builder(ComponentEffect.class)
					.searchKeyConstructor("type", ComponentEffectType.REGISTRY_KEY, ComponentEffectType::createEffect, ComponentEffect::getType)
					.build();
	
	@NotNull
	public static final StreamCodec<ComponentEffect>
	STREAM_CODEC = StreamCodec
					.builder(ComponentEffect.class)
					.registryVarIntConstructor(ComponentEffectType.REGISTRY_KEY, ComponentEffectType::createEffect, ComponentEffect::getType)
					.build();
	
	@NotNull
	ComponentEffectType getType();
	
	void apply(Entity target, ItemStack item);
	
	@Override
	ComponentEffect clone();
	
	@Override
	default NBTCodec<? extends ComponentEffect> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends ComponentEffect> getStreamCodec() {
		return STREAM_CODEC;
	}

}
