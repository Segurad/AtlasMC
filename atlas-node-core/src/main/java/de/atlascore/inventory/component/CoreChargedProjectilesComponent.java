package de.atlascore.inventory.component;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;
import static de.atlasmc.io.protocol.ProtocolUtil.readSlot;
import static de.atlasmc.io.protocol.ProtocolUtil.writeSlot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ChargedProjectilesComponent;
import de.atlasmc.inventory.component.ComponentType;
import io.netty.buffer.ByteBuf;

public class CoreChargedProjectilesComponent extends AbstractItemComponent implements ChargedProjectilesComponent {
	
	private List<ItemStack> projectiles;
	
	public CoreChargedProjectilesComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreChargedProjectilesComponent clone() {
		return (CoreChargedProjectilesComponent) super.clone();
	}

	@Override
	public List<ItemStack> getProjectiles() {
		if (projectiles == null)
			projectiles = new ArrayList<>();
		return projectiles;
	}

	@Override
	public boolean hasProjectiles() {
		return projectiles != null && !projectiles.isEmpty();
	}

	@Override
	public void addProjectile(ItemStack item) {
		if (item == null)
			throw new IllegalArgumentException("Item can not be null!");
		getProjectiles().add(item);
	}

	@Override
	public void removeProjectile(ItemStack item) {
		if (projectiles == null)
			return;
		projectiles.remove(item);
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		if (projectiles != null)
			projectiles.clear();
		final int count = readVarInt(buf);
		if (count == 0)
			return;
		for (int i = 0; i < count; i++) {
			ItemStack item = readSlot(buf);
			if (item == null)
				continue;
			addProjectile(item);
		}
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		if (!hasProjectiles()) {
			writeVarInt(0, buf);
			return;
		}
		final int count = projectiles.size();
		writeVarInt(count, buf);
		for (int i = 0; i < count; i++) {
			writeSlot(projectiles.get(i), buf);
		}
	}

}
