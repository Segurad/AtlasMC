package de.atlasmc.node.inventory.component;

import java.util.List;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.tag.NBT;
import de.atlasmc.util.AtlasUtil;

public interface CustomDataComponent extends ItemComponent {
	
	public static final NBTCodec<CustomDataComponent>
	NBT_CODEC = NBTCodec
					.builder(CustomDataComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.CUSTOM_DATA.getNamespacedKey(), CustomDataComponent::getData, CustomDataComponent::setData, NBT.rawFieldNBTCodec(List.of(TagType.COMPOUND, TagType.STRING)))
					.build();
	
	public static final StreamCodec<CustomDataComponent>
	STREAM_CODEC = StreamCodec
					.builder(CustomDataComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codec(AtlasUtil.getSelf(), AtlasUtil.getSetVoid(), NBT_CODEC)
					.build();
	
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
