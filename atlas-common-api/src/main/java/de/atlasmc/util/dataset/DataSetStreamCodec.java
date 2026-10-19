package de.atlasmc.util.dataset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.PacketUtil;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.registry.ProtocolRegistryValue;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.tag.Tag;
import de.atlasmc.tag.Tags;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

final class DataSetStreamCodec<T extends ProtocolRegistryValue> implements StreamCodec<DataSet<T>> {

	private final RegistryKey<T> registry;
	
	public DataSetStreamCodec(RegistryKey<T> registry) {
		this.registry = Objects.requireNonNull(registry, "registry");
	}
	
	@Override
	public Class<?> getType() {
		return DataSet.class;
	}

	@Override
	public boolean serialize(DataSet<T> value, ByteBuf output, CodecContext context) throws IOException {
		if (value instanceof TagDataSet<T> tagSet) {
			PacketUtil.writeVarInt(0, output);
			Tag<T> tag = tagSet.getTag();
			NamespacedKey key = context.clientSide ? tag.getClientKey() : tag.getNamespacedKey();
			NamespacedKey.STREAM_CODEC.serialize(key, output, context);
			return true;
		}
		int size = value.size();
		PacketUtil.writeVarInt(size + 1, output);
		if (size == 0)
			return true;
		if (value instanceof CollectionDataSet<T> set) {
			for (var v : set) {
				PacketUtil.writeVarInt(v.getID(), output);
			}
		} else if (value instanceof SingleValueDataSet<T> set) {
			PacketUtil.writeVarInt(set.getValue().getID(), output);
		}
		return true;
	}

	@Override
	public DataSet<T> deserialize(DataSet<T> value, ByteBuf input, CodecContext context) throws IOException {
		int size = PacketUtil.readVarInt(input);
		if (size == 0) {
			NamespacedKey key = NamespacedKey.STREAM_CODEC.deserialize(null, input, context);
			Tag<T> tag = Tags.getTag(key);
			if (tag == null)
				throw new ProtocolException("Unknown tag: " + key);
			return DataSet.of(tag);
		}
		size--;
		if (size < 0 || size > input.readableBytes()) // each id needs at least one byte
			throw new ProtocolException("Invalid data set size: " + size);
		if (size == 0)
			return DataSet.of();
		if (size == 1)
			return DataSet.of(registry.getValue(PacketUtil.readVarInt(input)));
		final ArrayList<T> values = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			T v = registry.getValue(PacketUtil.readVarInt(input));
			if (v != null)
				values.add(v);
		}
		return DataSet.of(values);
	}

}
