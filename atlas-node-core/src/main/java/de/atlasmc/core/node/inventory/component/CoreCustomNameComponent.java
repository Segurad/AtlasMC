package de.atlasmc.core.node.inventory.component;

import static de.atlasmc.io.PacketUtil.readTextComponent;
import static de.atlasmc.io.PacketUtil.writeTextComponent;

import java.io.IOException;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.CustomNameComponent;
import io.netty.buffer.ByteBuf;

public class CoreCustomNameComponent extends AbstractItemComponent implements CustomNameComponent {

	protected Chat customName;
	
	public CoreCustomNameComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreCustomNameComponent clone() {
		return (CoreCustomNameComponent) super.clone();
	}

	@Override
	public Chat getCustomName() {
		return customName;
	}

	@Override
	public void setCustomName(Chat name) {
		this.customName = name;
	}

	@Override
	public boolean hasCustomName() {
		return customName != null;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		customName = readTextComponent(buf);
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		writeTextComponent(customName, buf);
	}

}
