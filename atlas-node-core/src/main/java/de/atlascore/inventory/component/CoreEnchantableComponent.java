package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.EnchantableComponent;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import io.netty.buffer.ByteBuf;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreEnchantableComponent extends AbstractItemComponent implements EnchantableComponent {

	protected static final NBTFieldSet<CoreEnchantableComponent> NBT_FIELDS;
	
	protected static final CharKey
	NBT_VALUE = CharKey.literal("value");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_VALUE, (holder, reader) -> {
			holder.value = reader.readIntTag();
		});
	}
	
	private int value;
	
	public CoreEnchantableComponent(NamespacedKey key) {
		super(key);
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
	public ComponentType getType() {
		return ComponentType.ENCHANTABLE;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
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
