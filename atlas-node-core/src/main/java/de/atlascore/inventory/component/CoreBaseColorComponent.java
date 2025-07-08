package de.atlascore.inventory.component;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import de.atlasmc.DyeColor;
import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.BaseColorComponent;
import de.atlasmc.inventory.component.ComponentType;
import io.netty.buffer.ByteBuf;

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
