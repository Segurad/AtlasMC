package de.atlascore.inventory.component;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.DamageComponent;
import io.netty.buffer.ByteBuf;

public class CoreDamageComponent extends AbstractItemComponent implements DamageComponent {
	
	private int damage;
	
	public CoreDamageComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreDamageComponent clone() {
		return (CoreDamageComponent) super.clone();
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
	public void read(ByteBuf buf) throws IOException {
		damage = readVarInt(buf);
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		writeVarInt(damage, buf);
	}

}
