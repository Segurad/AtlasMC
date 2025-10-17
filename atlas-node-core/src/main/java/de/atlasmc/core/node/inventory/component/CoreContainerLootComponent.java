package de.atlasmc.core.node.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.ContainerLootComponent;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class CoreContainerLootComponent extends AbstractItemComponent implements ContainerLootComponent {
	
	private NamespacedKey lootTable;
	private long seed;
	
	public CoreContainerLootComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreContainerLootComponent clone() {
		return (CoreContainerLootComponent) super.clone();
	}

	@Override
	public NamespacedKey getLootTable() {
		return lootTable;
	}

	@Override
	public void setLootTable(NamespacedKey key) {
		this.lootTable = key;
	}

	@Override
	public long getLootTableSeed() {
		return seed;
	}

	@Override
	public void setLootTableSeed(long seed) {
		this.seed = seed;
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		NBTNIOWriter writer = new NBTNIOWriter(buf, true);
		getNBTCodec().serialize(this, writer, CodecContext.DEFAULT_CLIENT);
		writer.close();
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		NBTNIOReader reader = new NBTNIOReader(buf);
		getNBTCodec().deserialize(this, reader, CodecContext.DEFAULT_CLIENT);
		reader.close();
	}

}
