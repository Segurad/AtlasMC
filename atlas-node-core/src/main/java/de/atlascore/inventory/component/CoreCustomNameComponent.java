package de.atlascore.inventory.component;

import static de.atlasmc.io.PacketUtil.readTextComponent;
import static de.atlasmc.io.PacketUtil.writeTextComponent;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.CustomNameComponent;
import io.netty.buffer.ByteBuf;

public class CoreCustomNameComponent extends AbstractItemComponent implements CustomNameComponent {

	protected Chat customName;
	
	public CoreCustomNameComponent(NamespacedKey key) {
		super(key);
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
	public ComponentType getType() {
		return ComponentType.CUSTOM_NAME;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
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
