package de.atlasmc.core.node.inventory.component;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ChargedProjectilesComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.util.codec.CodecContext;
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
			ItemStack item = ItemStack.STREAM_CODEC.deserialize(buf, CodecContext.DEFAULT_CLIENT);
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
			ItemStack.STREAM_CODEC.serialize(projectiles.get(i), buf, CodecContext.DEFAULT_CLIENT);
		}
	}

}
