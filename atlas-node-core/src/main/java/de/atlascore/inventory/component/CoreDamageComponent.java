package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.DamageComponent;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreDamageComponent extends AbstractItemComponent implements DamageComponent {

	private int damage;
	
	public CoreDamageComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreDamageComponent clone() {
		return (CoreDamageComponent) super.clone();
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (damage != 0)
			writer.writeIntTag(key.toString(), damage);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		damage = reader.readIntTag();
	}

	@Override
	public int getDamage() {
		return damage;
	}

	@Override
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.DAMAGE;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		damage = readVarInt(buf);
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		writeVarInt(damage, buf);
	}

}
