package de.atlasmc.tag;

import java.io.IOException;
import java.util.List;

import de.atlasmc.NamespacedAccessKey;
import de.atlasmc.NamespacedKey;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class TagKey<T> extends NamespacedAccessKey<Tag<T>> {
	
	@SuppressWarnings("rawtypes")
	public static final NBTCodec<TagKey>
	NBT_CODEC = new NBTCodec<TagKey>() {
		
		@Override
		public Class<? extends TagKey> getType() {
			return TagKey.class;
		}
		
		@Override
		public TagKey deserialize(TagKey value, NBTReader input, CodecContext context) throws IOException {
			String v = input.readStringTag();
			if (v.charAt(0) != '#')
				throw new NBTException("Tag identifier has to start with #: " + v);
			return new TagKey<>(NamespacedKey.of(v.substring(1)));
		}
		
		@Override
		public boolean serialize(CharSequence key, TagKey value, NBTWriter output, CodecContext context) throws IOException {
			output.writeStringTag(key, "#" + value.getNamespacedKey());
			return true;
		}

		@Override
		public List<TagType> getTags() {
			return CodecTags.STRING;
		}
		
	};
	
	@SuppressWarnings("rawtypes")
	public static final StreamCodec<TagKey> STREAM_CODEC = new StreamCodec<TagKey>() {
		
		@Override
		public boolean serialize(TagKey value, ByteBuf output, CodecContext context) throws IOException {
			NamespacedKey.STREAM_CODEC.serialize(value.key, output, context);
			return true;
		}
		
		@Override
		public Class<?> getType() {
			return TagKey.class;
		}
		
		@Override
		public TagKey deserialize(TagKey value, ByteBuf input, CodecContext context) throws IOException {
			return new TagKey<>(NamespacedKey.STREAM_CODEC.deserialize(null, input, null));
		}
	};
	
	public TagKey(NamespacedKey key) {
		super(key);
	}

	@Override
	public Tag<T> get() {
		return Tags.getTag(key);
	}

}
