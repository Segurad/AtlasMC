package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Beacon;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.BeaconInventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreBeacon extends CoreTileEntity implements Beacon {
	
	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	LEVELS = CharKey.of("Levels"),
	PRIMARY = CharKey.of("Primary"),
	SECONDARY = CharKey.of("Secondary");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreTileEntity.NBT_FIELDS);
		NBT_FIELDS.setField(LEVELS, (holder, reader) -> {
			((Beacon) holder).setLevel(reader.readIntTag());
		});
		NBT_FIELDS.setField(PRIMARY, (holder, reader) -> {
			((Beacon) holder).setPrimaryID(reader.readIntTag());
		});
		NBT_FIELDS.setField(SECONDARY, (holder, reader) -> {
			((Beacon) holder).setSecondaryID(reader.readIntTag());
		});
	}
	
	private BeaconInventory inv;

	public CoreBeacon(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
	}

	@Override
	public BeaconInventory getInventory() {
		if (inv == null) inv = ContainerFactory.BEACON_INV_FACTORY.create(InventoryType.BEACON, this);
		return inv;
	}

	@Override
	public void setLevel(int level) {
		getInventory().setPowerLevel(level);
	}

	@Override
	public int getLevel() {
		if (inv == null) return 0;
		return getInventory().getPowerLevel();
	}

	@Override
	public int getPrimaryID() {
		if (inv == null) return 0;
		return getInventory().getPrimaryID();
	}

	@Override
	public void setPrimaryID(int primary) {
		getInventory().setPrimaryID(primary);
	}

	@Override
	public int getSecondaryID() {
		if (inv == null) return 0;
		return getInventory().getSecondaryID();
	}

	@Override
	public void setSecondaryID(int secondary) {
		getInventory().setSecondaryID(secondary);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(LEVELS, getLevel());
		writer.writeIntTag(PRIMARY, getPrimaryID());
		writer.writeIntTag(SECONDARY, getSecondaryID());
	}
	
}
