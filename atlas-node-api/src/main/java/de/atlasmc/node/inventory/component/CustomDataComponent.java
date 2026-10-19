package de.atlasmc.node.inventory.component;

import java.util.List;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.tag.NBT;
import de.atlasmc.util.AtlasUtil;
import de.atlasmc.util.annotation.NotNull;

public interface CustomDataComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<CustomDataComponent>
	NBT_CODEC = CodecFieldCodec
				.builder(CustomDataComponent.class, NBT.rawFieldNBTCodec(List.of(TagType.COMPOUND, TagType.STRING)))
				.include(ItemComponent.NBT_CODEC)
				.getter(CustomDataComponent::getData)
				.setter(CustomDataComponent::setData)
				.build();
	
	@NotNull
	public static final StreamCodec<CustomDataComponent>
	STREAM_CODEC = StreamCodec
					.builder(CustomDataComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(AtlasUtil.getSelf(), AtlasUtil.getSetVoid(), NBT_CODEC)
					.build();
	
	@Override
	CustomDataComponent clone();
	
	NBT getData();
	
	void setData(NBT data);

	@Override
	default NBTCodec<? extends CustomDataComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends CustomDataComponent> getStreamCodec() {
		return STREAM_CODEC;
	}
	
}
