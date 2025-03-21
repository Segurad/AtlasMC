package de.atlascore.inventory.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.CustomModelDataComponent;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreCustomModelDataComponent extends AbstractItemComponent implements CustomModelDataComponent {
	
	protected static final NBTFieldSet<CoreCustomModelDataComponent> NBT_FIELDS;
	
	protected static final CharKey
	NBT_FLOATS = CharKey.literal("floats"),
	NBT_FLAGS = CharKey.literal("flags"),
	NBT_STRINGS = CharKey.literal("strings"),
	NBT_COLORS = CharKey.literal("colors");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_FLOATS, (holder, reader) -> {
			FloatArrayList floats = new FloatArrayList();
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				floats.add(reader.readFloatTag());
			}
			reader.readNextEntry();
			floats.trim();
			holder.floats = floats;
		});
		NBT_FIELDS.setField(NBT_FLAGS, (holder, reader) -> {
			BooleanArrayList flags = new BooleanArrayList();
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				flags.add(reader.readBoolean());
			}
			reader.readNextEntry();
			flags.trim();
			holder.flags = flags;
		});
		NBT_FIELDS.setField(NBT_STRINGS, (holder, reader) -> {
			ArrayList<String> strings = new ArrayList<>();
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				strings.add(reader.readStringTag());
			}
			reader.readNextEntry();
			strings.trimToSize();
			holder.strings = strings;
		});
		NBT_FIELDS.setField(NBT_COLORS, (holder, reader) -> {
			TagType listType = reader.getListType();
			if (listType == TagType.LIST) {
				reader.skipTag();
				// i don't care about this mess
			} else if (listType == TagType.INT) {
				IntArrayList colors = new IntArrayList();
				reader.readNextEntry();
				while (reader.getRestPayload() > 0) {
					colors.add(reader.readIntTag());
				}
				reader.readNextEntry();
				colors.trim();
				holder.colors = colors;
			} else {
				throw new NBTException("Unexpected list type: " + listType);
			}
		});
	}
	
	private FloatList floats;
	private BooleanList flags;
	private List<String> strings;
	private IntList colors;
	
	public CoreCustomModelDataComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreCustomModelDataComponent clone() {
		CoreCustomModelDataComponent clone = (CoreCustomModelDataComponent) super.clone();
		if (floats != null)
			clone.floats = new FloatArrayList(floats);
		if (flags != null)
			clone.flags = new BooleanArrayList(flags);
		if (strings != null)
			clone.strings = new ArrayList<>(strings);
		if (colors != null)
			clone.colors = new IntArrayList(colors);
		return clone;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(key.toString());
		if (floats != null) {
			final int size = floats.size();
			writer.writeListTag(NBT_FLOATS, TagType.FLOAT, size);
			for (int i = 0; i < size; i++) {
				writer.writeFloatTag(null, floats.getFloat(i));
			}
		}
		if (flags != null) {
			final int size = flags.size();
			writer.writeListTag(NBT_FLAGS, TagType.BYTE, size);
			for (int i = 0; i < size; i++) {
				writer.writeByteTag(null, flags.getBoolean(i));
			}
		}
		if (strings != null) {
			final int size = strings.size();
			writer.writeListTag(NBT_STRINGS, TagType.STRING, size);
			for (int i = 0; i < size; i++) {
				writer.writeStringTag(null, strings.get(i));
			}
		}
		if (colors != null) {
			final int size = colors.size();
			writer.writeListTag(NBT_COLORS, TagType.INT, size);
			for (int i = 0; i < size; i++) {
				writer.writeIntTag(null, colors.getInt(i));
			}
		}
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}

	@Override
	public FloatList getFloats() {
		if (floats == null)
			floats = new FloatArrayList();
		return floats;
	}

	@Override
	public void setFloats(FloatList floats) {
		FloatList current = getFloats();
		current.clear();
		current.addAll(floats);
	}

	@Override
	public BooleanList getFlags() {
		if (flags == null)
			flags = new BooleanArrayList();
		return flags;
	}

	@Override
	public void setFlags(BooleanList flags) {
		BooleanList current = getFlags();
		current.clear();
		current.addAll(flags);
	}

	@Override
	public List<String> getStrings() {
		if (strings == null)
			strings = new ArrayList<>();
		return strings;
	}

	@Override
	public void setStrings(List<String> strings) {
		List<String> current = getStrings();
		current.clear();
		current.addAll(strings);
	}

	@Override
	public IntList getColors() {
		if (colors == null)
			colors = new IntArrayList();
		return colors;
	}

	@Override
	public void setColors(IntList colors) {
		IntList current = getColors();
		current.clear();
		current.addAll(colors);
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.CUSTOM_MODEL_DATA;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		final int floatSize = readVarInt(buf);
		if (floatSize > 0) {
			FloatArrayList floats = new FloatArrayList(floatSize);
			for (int i = 0; i < floatSize; i++) {
				floats.add(buf.readFloat());
			}
			this.floats = floats;
		} else {
			floats = null;
		}
		final int flagSize = readVarInt(buf);
		if (flagSize > 0) {
			BooleanArrayList flags = new BooleanArrayList(flagSize);
			for (int i = 0; i < flagSize; i++) {
				flags.add(buf.readBoolean());
			}
			this.flags = flags;
		} else {
			flags = null;
		}
		final int stringsSize = readVarInt(buf);
		if (stringsSize > 0) {
			List<String> strings = new ArrayList<>(stringsSize);
			for (int i = 0; i < stringsSize; i++) {
				strings.add(readString(buf));
			}
			this.strings = strings;
		} else {
			strings = null;
		}
		final int colorsSize = readVarInt(buf);
		if (colorsSize > 0) {
			IntArrayList colors = new IntArrayList(colorsSize);
			for (int i = 0; i < colorsSize; i++) {
				colors.add(buf.readInt());
			}
			this.colors = colors;
		} else {
			colors = null;
		}
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		if (hasFloats()) {
			final int size = floats.size();
			writeVarInt(size, buf);
			for (int i = 0; i < size; i++) {
				buf.writeFloat(floats.getFloat(i));
			}
		} else {
			writeVarInt(0, buf);
		}
		if (hasFlags()) {
			final int size = flags.size();
			writeVarInt(size, buf);
			for (int i = 0; i < size; i++) {
				buf.writeBoolean(flags.getBoolean(i));
			}
		} else {
			writeVarInt(0, buf);
		}
		if (hasStrings()) {
			final int size = strings.size();
			writeVarInt(size, buf);
			for (int i = 0; i < size; i++) {
				writeString(strings.get(i), buf);
			}
		} else {
			writeVarInt(0, buf);
		}
		if (hasColors()) {
			final int size = colors.size();
			writeVarInt(size, buf);
			for (int i = 0; i < size; i++) {
				buf.writeInt(colors.getInt(i));
			}
		} else {
			writeVarInt(0, buf);
		}
	}

	@Override
	public boolean hasFloats() {
		return floats != null && !floats.isEmpty();
	}

	@Override
	public boolean hasFlags() {
		return flags != null && !flags.isEmpty();
	}

	@Override
	public boolean hasStrings() {
		return strings != null && !strings.isEmpty();
	}

	@Override
	public boolean hasColors() {
		return colors != null && !colors.isEmpty();
	}

}
