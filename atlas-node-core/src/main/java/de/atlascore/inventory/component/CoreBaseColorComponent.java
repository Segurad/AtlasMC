package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.DyeColor;
import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.BaseColorComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreBaseColorComponent extends AbstractItemComponent implements BaseColorComponent {

	private DyeColor color;
	
	public CoreBaseColorComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreBaseColorComponent clone() {
		return (CoreBaseColorComponent) super.clone();
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(getNamespacedKeyRaw(), color.getName());
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		color = DyeColor.getByName(reader.readStringTag());
	}

	@Override
	public DyeColor getColor() {
		return color;
	}

	@Override
	public void setColor(DyeColor color) {
		this.color = color;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.BASE_COLOR;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) {
		color = DyeColor.getByID(readVarInt(buf));
	}
	
	@Override
	public void write(ByteBuf buf) {
		if (color == null) {
			writeVarInt(0, buf);
		} else {
			writeVarInt(color.getID(), buf);
		}
	}

}
