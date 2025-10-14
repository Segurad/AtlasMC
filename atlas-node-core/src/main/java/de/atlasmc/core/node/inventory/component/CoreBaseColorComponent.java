package de.atlasmc.core.node.inventory.component;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import de.atlasmc.node.DyeColor;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.BaseColorComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.util.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CoreBaseColorComponent extends AbstractItemComponent implements BaseColorComponent {

	private DyeColor color;
	
	public CoreBaseColorComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreBaseColorComponent clone() {
		return (CoreBaseColorComponent) super.clone();
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
	public void read(ByteBuf buf) {
		color = EnumUtil.getByID(DyeColor.class, readVarInt(buf));
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
