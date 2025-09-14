package de.atlasmc.core.node.inventory.component;

import static de.atlasmc.io.PacketUtil.readString;
import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeString;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.CustomModelDataComponent;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

public class CoreCustomModelDataComponent extends AbstractItemComponent implements CustomModelDataComponent {
	
	private FloatList floats;
	private BooleanList flags;
	private List<String> strings;
	private IntList colors;
	
	public CoreCustomModelDataComponent(ComponentType type) {
		super(type);
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
