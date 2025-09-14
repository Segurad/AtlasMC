package de.atlasmc.core.node.inventory.component;

import static de.atlasmc.io.PacketUtil.readDataSet;
import static de.atlasmc.io.PacketUtil.readString;
import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeDataSet;
import static de.atlasmc.io.PacketUtil.writeString;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.atlasmc.node.block.BlockPredicate;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.tile.TileEntity;
import de.atlasmc.node.inventory.component.AbstractBlockPredicateComponent;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import io.netty.buffer.ByteBuf;

public class CoreAbstractBlockPredicateComponent extends AbstractItemComponent implements AbstractBlockPredicateComponent {
	
	private List<BlockPredicate> predicates;
	private boolean showInToolTip = true;

	public CoreAbstractBlockPredicateComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreAbstractBlockPredicateComponent clone() {
		CoreAbstractBlockPredicateComponent clone = (CoreAbstractBlockPredicateComponent) super.clone();
		if (predicates != null && !predicates.isEmpty()) {
			clone.predicates = new ArrayList<>(predicates);
		}
		return clone;
	}

	@Override
	public List<BlockPredicate> getPredicates() {
		if (predicates == null)
			predicates = new ArrayList<>();
		return null;
	}

	@Override
	public boolean hasPredicates() {
		return predicates != null && !predicates.isEmpty();
	}

	@Override
	public void addPredicate(BlockPredicate predicate) {
		getPredicates().add(predicate);
	}

	@Override
	public void removePredicate(BlockPredicate predicate) {
		if (predicates == null)
			return;
		predicates.remove(predicate);
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		if (hasPredicates()) {
			final int size = predicates.size();
			writeVarInt(size, buf);
			NBTWriter writer = null;
			for (int i = 0; i < size; i++) {
				BlockPredicate predicate = predicates.get(i);
				DataSet<BlockType> types = predicate.getTypes();
				boolean hasTypes = types != null && !types.isEmpty();
				buf.writeBoolean(hasTypes);
				if (hasTypes)
					writeDataSet(types, BlockType.getRegistry(), buf);
				boolean hasStates = predicate.hasStates();
				buf.writeBoolean(hasStates);
				if (hasStates) {
					Map<BlockDataProperty<?>, Object> properties = predicate.getStates();
					final int count = properties.size();
					writeVarInt(count, buf);
					for (Entry<BlockDataProperty<?>, Object> entry : properties.entrySet()) {
						@SuppressWarnings("unchecked")
						BlockDataProperty<Object> property = (BlockDataProperty<Object>) entry.getKey();
						Object value = entry.getValue();
						writeString(property.getKey(), buf);
						buf.writeBoolean(true);
						writeString(property.toString(value), buf);
					}
				}
				TileEntity tile = predicate.getTile();
				buf.writeBoolean(tile != null);
				if (tile != null) {
					if (writer == null)
						writer = new NBTNIOWriter(buf, true);
					writer.writeCompoundTag();
					if (tile != null)
						tile.writeToNBT(writer, NBTSerializationContext.DEFAULT_CLIENT);
					writer.writeEndTag();
				}
			}
			if (writer != null)
				writer.close();
		} else {
			writeVarInt(0, buf);
		}
		buf.writeBoolean(showInToolTip);
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		final int size = readVarInt(buf);
		if (predicates != null)
			predicates.clear();
		if (size > 0) {
			NBTReader reader = null;
			for (int i = 0; i < size; i++) {
				BlockPredicate predicate = null;
				if (buf.readBoolean()) { // has blocks
					DataSet<BlockType> types = readDataSet(BlockType.getRegistry(), buf);
					if (predicate == null)
						predicate = new BlockPredicate();
					predicate.setTypes(types);
				}
				if (buf.readBoolean()) { // has properties
					final int count = readVarInt(buf);
					if (count > 0) {
						Map<BlockDataProperty<?>, Object> props = null;
						for (int j = 0; j < count; j++) {
							String name = readString(buf);
							if (buf.readBoolean()) { // check if exact value
								String rawValue = readString(buf);
								Collection<BlockDataProperty<?>> properties = BlockDataProperty.getProperties(name);
								Object value = null;
								for (BlockDataProperty<?> property : properties) {
									value = property.fromString(rawValue);
									if (value == null)
										continue;
									if (props == null) {
										if (predicate == null)
											predicate = new BlockPredicate();
										props = predicate.getStates();
									}
									props.put(property, value);
									break;
								}
							} else {
								// TODO min max property
								readString(buf); // min
								readString(buf); // max
							}
						}
					}
				}
				if (buf.readBoolean()) { // has nbt
					if (reader == null)
						reader = new NBTNIOReader(buf, true);
					reader.readNextEntry();
					TileEntity tile = TileEntity.NBT_HANDLER.deserialize(reader);
					if (predicate == null)
						predicate = new BlockPredicate();
					predicate.setTile(tile);
				}
			}
			if (reader != null)
				reader.close();
		}
		showInToolTip = buf.readBoolean();
	}

}
