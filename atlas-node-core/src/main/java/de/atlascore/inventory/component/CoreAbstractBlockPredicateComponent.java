package de.atlascore.inventory.component;

import static de.atlasmc.io.PacketUtil.readDataSet;
import static de.atlasmc.io.PacketUtil.readString;
import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeDataSet;
import static de.atlasmc.io.PacketUtil.writeString;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.blockpredicate.BlockDataPredicate;
import de.atlasmc.block.blockpredicate.BlockPredicate;
import de.atlasmc.block.blockpredicate.BlockTypePredicate;
import de.atlasmc.block.blockpredicate.TileBlockPredicate;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.inventory.component.AbstractBlockPredicateComponent;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

public class CoreAbstractBlockPredicateComponent extends AbstractItemComponent implements AbstractBlockPredicateComponent {
	
	protected static final NBTFieldSet<CoreAbstractBlockPredicateComponent> NBT_FIELDS;
	
	protected static final CharKey
	NBT_PREDICATES = CharKey.literal("predicates"),
	NBT_BLOCKS = CharKey.literal("blocks"),
	NBT_NBT = CharKey.literal("nbt"),
	NBT_STATE = CharKey.literal("state"),
	NBT_SHOW_IN_TOOLTIP = CharKey.literal("show_in_tooltip");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		
		final NBTField<CoreAbstractBlockPredicateComponent>
		blocksField = (holder, reader) -> {
			DataSet<BlockType> types = DataSet.getFromNBT(BlockType.getRegistry(), reader);
			if (types.isEmpty())
				return; // avoid creating empty predicate
			holder.addPredicate(new BlockTypePredicate(types));
		},
		nbtField = (holder, reader) -> {
			reader.readNextEntry();
			TileEntity tile = TileEntity.getFromNBT(reader);
			if (tile == null)
				return; // avoid creating empty predicates
			holder.addPredicate(new TileBlockPredicate(tile));
		},
		stateField = (holder, reader) -> {
			reader.readNextEntry();
			Map<BlockDataProperty<?>, Object> properties = BlockDataProperty.readProperties(reader);
			if (properties.isEmpty())
				return; // avoid creating empty predicates
			holder.addPredicate(new BlockDataPredicate(properties));
		};
		NBTFieldSet<CoreAbstractBlockPredicateComponent> predicateFields = NBTFieldSet.newSet();
		predicateFields.setField(NBT_BLOCKS, blocksField);
		predicateFields.setField(NBT_NBT, nbtField);
		predicateFields.setField(NBT_STATE, stateField);
		
		NBT_FIELDS.setField(NBT_PREDICATES, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				reader.readNextEntry();
				NBTUtil.readNBT(predicateFields, holder, reader);
			}
			reader.readNextEntry();
		});
		NBT_FIELDS.setField(NBT_BLOCKS, blocksField);
		NBT_FIELDS.setField(NBT_NBT, nbtField);
		NBT_FIELDS.setField(NBT_STATE, stateField);
		NBT_FIELDS.setField(NBT_SHOW_IN_TOOLTIP, (holder, reader) -> {
			holder.showInToolTip = reader.readBoolean();
		});
	}
	
	private List<BlockPredicate> predicates;
	private boolean showInToolTip = true;

	public CoreAbstractBlockPredicateComponent(NamespacedKey key) {
		super(key);
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
	public boolean isShowTooltip() {
		return showInToolTip;
	}

	@Override
	public void setShowTooltip(boolean show) {
		this.showInToolTip = show;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(key.toString());
		if (hasPredicates()) {
			final int size = predicates.size();
			writer.writeListTag(NBT_PREDICATES, TagType.COMPOUND, size);
			for (int i = 0; i < size; i++) {
				BlockPredicate predicate = predicates.get(i);
				if (predicate instanceof BlockTypePredicate typePredicate) {
					DataSet<BlockType> types = typePredicate.getMaterials();
					DataSet.toNBT(NBT_BLOCKS, types, writer, systemData);
				} else if (predicate instanceof TileBlockPredicate tilePredicate) {
					writer.writeCompoundTag(NBT_NBT);
					tilePredicate.getTile().toNBT(writer, systemData);
					writer.writeEndTag();
				} else if (predicate instanceof BlockDataPredicate block) {
					writer.writeCompoundTag(NBT_STATE);
					BlockDataProperty.writeProperties(block.getProperties(), writer, systemData);
					writer.writeEndTag();
				} else {
					throw new NBTException("Unsupported block predicate: " + predicate.getClass().getName());
				}
				writer.writeEndTag();
			}
		}
		if (!showInToolTip)
			writer.writeByteTag(NBT_SHOW_IN_TOOLTIP, false);
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		if (hasPredicates()) {
			final int size = predicates.size();
			writeVarInt(size, buf);
			NBTWriter writer = null;
			for (int i = 0; i < size; i++) {
				BlockPredicate predicate = predicates.get(i);
				if (predicate instanceof BlockTypePredicate typePredicate) {
					DataSet<BlockType> types = typePredicate.getMaterials();
					writeDataSet(types, BlockType.getRegistry(), buf);
				} else if (predicate instanceof BlockDataPredicate block) {
					Map<BlockDataProperty<?>, Object> properties = block.getProperties();
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
				} else if (predicate instanceof TileBlockPredicate tilePredicate) {
					if (writer == null)
						writer = new NBTNIOWriter(buf, true);
					writer.writeCompoundTag();
					tilePredicate.getTile().toNBT(writer, false);
					writer.writeEndTag();
				} else {
					throw new ProtocolException("Unsupported block predicate: " + predicate.getClass().getName());
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
				if (buf.readBoolean()) { // has blocks
					DataSet<BlockType> types = readDataSet(BlockType.getRegistry(), buf);
					if (types != null)
						addPredicate(new BlockTypePredicate(types));
				}
				if (buf.readBoolean()) { // has properties
					final int count = readVarInt(buf);
					if (count > 0) {
						Map<BlockDataProperty<?>, Object> props = new HashMap<>();
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
									props.put(property, value);
									break;
								}
							} else {
								// TODO min max property
								readString(buf); // min
								readString(buf); // max
							}
						}
						if (!props.isEmpty())
							addPredicate(new BlockDataPredicate(props));
					}
				}
				if (buf.readBoolean()) { // has nbt
					if (reader == null)
						reader = new NBTNIOReader(buf, true);
					reader.readNextEntry();
					TileEntity tile = TileEntity.getFromNBT(reader);
					if (tile != null)
						addPredicate(new TileBlockPredicate(tile));
				}
			}
			if (reader != null)
				reader.close();
		}
		showInToolTip = buf.readBoolean();
	}

}
