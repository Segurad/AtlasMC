package de.atlasmc.node.block.data;

import java.io.IOException;
import java.util.List;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.util.AtlasUtil;
import de.atlasmc.util.codec.CodecContext;

public interface BlockData extends Cloneable, NBTSerializable {

	public static final NBTCodec<BlockData> PROPERTIES_NBT_CODEC = new NBTCodec<BlockData>() {
		
		@Override
		public Class<?> getType() {
			return BlockData.class;
		}
		
		@Override
		public BlockData deserialize(BlockData value, NBTReader input, CodecContext context) throws IOException {
			input.readNextEntry();
			while (input.getType() != TagType.TAG_END) {
				var key = input.getFieldName();
				@SuppressWarnings("unchecked")
				PropertyType<Object> property = (PropertyType<Object>) value.getProperty(key);
				property.set(value, property.fromString(input.readStringTag()));
			}
			input.readNextEntry();
			return value;
		}
		
		@Override
		public boolean serialize(CharSequence key, BlockData value, NBTWriter output, CodecContext context) throws IOException {
			final List<PropertyType<?>> properties = value.getProperties();
			if (properties.isEmpty()) {
				return true;
			}
			output.writeCompoundTag(key);
			final int size = properties.size();
			for (int i = 0; i < size; i++) {
				PropertyType<?> property = properties.get(i);
				property.dataToNBT(value, output, !context.clientSide);
			}
			output.writeEndTag();
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.COMPOUND;
		}
	};
	
	public static final NBTCodec<BlockData>
	NBT_HANDLER = NBTCodec
					.builder(BlockData.class)
					.searchKeyConstructor("Name", BlockType.REGISTRY_KEY, BlockType::createBlockData, BlockData::getType)
					.codec("Properties", AtlasUtil.getSelf(), AtlasUtil.getSetVoid(), PROPERTIES_NBT_CODEC)
					.build();
	
	BlockData clone();
	
	BlockType getType();
	
	int getStateID();

	List<PropertyType<?>> getProperties();
	
	PropertyType<?> getProperty(CharSequence key);
	
	@Override
	default NBTCodec<? extends BlockData> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
