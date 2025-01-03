package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.ContainerLootComponent;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

public class CoreContainerLootComponent extends AbstractItemComponent implements ContainerLootComponent {

	protected static final NBTFieldSet<CoreContainerLootComponent> NBT_FIELDS;
	
	protected static final CharKey
	NBT_LOOT_TABLE = CharKey.literal("loot_table"),
	NBT_SEED = CharKey.literal("seed");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_LOOT_TABLE, (holder, reader) -> {
			holder.lootTable = reader.readNamespacedKey();
		});
		NBT_FIELDS.setField(NBT_SEED, (holder, reader) -> {
			holder.seed = reader.readLongTag();
		});
	}
	
	private NamespacedKey lootTable;
	private long seed;
	
	public CoreContainerLootComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreContainerLootComponent clone() {
		return (CoreContainerLootComponent) super.clone();
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(key.toString());
		if (systemData) {
			if (lootTable != null)
				writer.writeNamespacedKey(NBT_LOOT_TABLE, lootTable);
			if (seed != 0)
				writer.writeLongTag(NBT_SEED, seed);
		}
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}

	@Override
	public NamespacedKey getLootTableKey() {
		return lootTable;
	}

	@Override
	public void setLootTableKey(NamespacedKey key) {
		this.lootTable = key;
	}

	@Override
	public long getSeed() {
		return seed;
	}

	@Override
	public void setSeed(long seed) {
		this.seed = seed;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.CONTAINER_LOOT;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		NBTNIOWriter writer = new NBTNIOWriter(buf, true);
		toNBT(writer, false);
		writer.close();
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		NBTNIOReader reader = new NBTNIOReader(buf);
		fromNBT(reader);
		reader.close();
	}

}
