package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.EnchantableComponent;
import io.netty.buffer.ByteBuf;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

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
