package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.CodecCollectionFieldCodec;
import de.atlasmc.nbt.codec.MapFieldNameToCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.util.annotation.NotNull;

public interface DebugStickStateComponent extends AbstractBlockDataComponent {

	@NotNull
	public static final NBTCodec<DebugStickStateComponent>
	NBT_CODEC = CodecCollectionFieldCodec
				.builder(DebugStickStateComponent.class, new MapFieldNameToCodec<>(NBTCodecs.STRING))
				.include(AbstractBlockDataComponent.NBT_CODEC)
				.hasData(DebugStickStateComponent::hasProperties)
				.getter(DebugStickStateComponent::getProperties)
				.build();
	
	@Override
	DebugStickStateComponent clone();
	
	@Override
	default NBTCodec<? extends DebugStickStateComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
