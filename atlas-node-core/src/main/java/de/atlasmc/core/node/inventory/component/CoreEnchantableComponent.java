package de.atlasmc.core.node.inventory.component;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.EnchantableComponent;
import io.netty.buffer.ByteBuf;

public class CoreEnchantableComponent extends AbstractItemComponent implements EnchantableComponent {
	
	private int value;
	
	public CoreEnchantableComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreEnchantableComponent clone() {
		return (CoreEnchantableComponent) super.clone();
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		value = readVarInt(buf);
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		writeVarInt(value, buf);
	}

}
