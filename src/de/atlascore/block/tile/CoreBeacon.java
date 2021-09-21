package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Beacon;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.BeaconInventory;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreBeacon extends CoreTileEntity implements Beacon {
	
	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	LEVELS = "Levels",
	PRIMARY = "Primary",
	SECONDARY = "Secondary";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreTileEntity.NBT_FIELDS);
		NBT_FIELDS.setField(LEVELS, (holder, reader) -> {
			if (holder instanceof Beacon)
			((Beacon) holder).setLevel(reader.readIntTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(PRIMARY, (holder, reader) -> {
			if (holder instanceof Beacon)
			((Beacon) holder).setPrimaryID(reader.readIntTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(SECONDARY, (holder, reader) -> {
			if (holder instanceof Beacon)
			((Beacon) holder).setSecondaryID(reader.readIntTag());
			else reader.skipTag();
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
