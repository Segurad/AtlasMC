package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.Color;
import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.DyedColorComponent;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

public class CoreDyedColorComponent extends AbstractItemComponent implements DyedColorComponent {

	protected static final NBTFieldSet<CoreDyedColorComponent> NBT_FIELDS;
	
	protected static final CharKey
	NBT_RGB = CharKey.literal("rgb"),
	NBT_SHOW_IN_TOOLTIP = CharKey.literal("show_in_tooltip");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_RGB, (holder, reader) -> {
			holder.color = Color.fromRGB(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_SHOW_IN_TOOLTIP, (holder, reader) -> {
			holder.showInTooltip = reader.readBoolean();
		});
	}
	
	private boolean showInTooltip;
	private Color color;
	
	public CoreDyedColorComponent(NamespacedKey key) {
		super(key);
		showInTooltip = true;
	}
	
	@Override
	public CoreDyedColorComponent clone() {
		return (CoreDyedColorComponent) super.clone();
	}

	@Override
	public boolean isShowTooltip() {
		return showInTooltip;
	}

	@Override
	public void setShowTooltip(boolean show) {
		this.showInTooltip = show;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (!showInTooltip) {
			writer.writeCompoundTag(key.toString());
			if (color != null)
				writer.writeIntTag(NBT_RGB, color.asRGB());
			writer.writeByteTag(NBT_SHOW_IN_TOOLTIP, false);
			writer.writeEndTag();
		} else if (color != null) {
			writer.writeIntTag(key.toString(), color.asRGB());
		}
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		TagType type = reader.getType();
		switch (type) {
		case COMPOUND: {
			reader.readNextEntry();
			NBTUtil.readNBT(NBT_FIELDS, this, reader);
			break;
		}
		case INT:
			color = Color.fromRGB(reader.readIntTag());
		default:
			throw new IllegalArgumentException("Unexpected tag type: " + type);
		}
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.DYED_COLOR;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		color = Color.fromRGB(buf.readInt());
		showInTooltip = buf.readBoolean();
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		buf.writeInt(color != null ? color.asRGB() : 0);
		buf.writeBoolean(showInTooltip);
	}

}
