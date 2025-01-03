package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Beacon;
import de.atlasmc.inventory.BeaconInventory;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBeacon extends CoreTileEntity implements Beacon {
	
	protected static final NBTFieldSet<CoreBeacon> NBT_FIELDS;
	
	protected static final CharKey
	LEVELS = CharKey.literal("Levels"),
	PRIMARY = CharKey.literal("Primary"),
	SECONDARY = CharKey.literal("Secondary");
	
	static {
		NBT_FIELDS = CoreTileEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(LEVELS, (holder, reader) -> {
			holder.setLevel(reader.readIntTag());
		});
		NBT_FIELDS.setField(PRIMARY, (holder, reader) -> {
			holder.setPrimaryID(reader.readIntTag());
		});
		NBT_FIELDS.setField(SECONDARY, (holder, reader) -> {
			holder.setSecondaryID(reader.readIntTag());
		});
	}
	
	private BeaconInventory inv;

	public CoreBeacon(Material type) {
		super(type);
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
	protected NBTFieldSet<? extends CoreBeacon> getFieldSetRoot() {
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
