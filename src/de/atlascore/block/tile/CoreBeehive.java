package de.atlascore.block.tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Location;
import de.atlasmc.Material;
import de.atlasmc.SimpleLocation;
import de.atlasmc.block.tile.Beehive;
import de.atlasmc.entity.Bee;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreBeehive extends CoreTileEntity implements Beehive {

	protected static final String
	NBT_FLOWER_POS = "FlowerPos",
	NBT_X = "X",
	NBT_Y = "Y",
	NBT_Z = "Z",
	NBT_BEES = "Bees",
	NBT_MIN_OCCUPATION_TICKS = "MinOccupationTicks",
	NBT_TICKS_IN_HIVE = "TicksInHive",
	NBT_ENTITY_DATA = "EntityData";
	
	static {
		NBT_FIELDS.setField(NBT_FLOWER_POS, NBTField.SKIP);
		NBT_FIELDS.setField(NBT_BEES, NBTField.SKIP);
	}
	
	private Location flower;
	private List<Bee> bees;
	
	public CoreBeehive(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
		flower = new Location(chunk.getWorld(), x, y, z);
	}

	@Override
	public Location getFlowerPos() {
		return flower.clone();
	}

	@Override
	public Location getFlowerPos(Location loc) {
		return flower.copyTo(loc);
	}

	@Override
	public void setFlowerPos(SimpleLocation loc) {
		flower.setLocation(loc);
	}
	
	@Override
	public List<Bee> getBees() {
		if (bees == null) bees = new ArrayList<Bee>();
		return bees;
	}

	@Override
	public void removeBee(Bee bee) {
		if (bees != null) bees.add(bee);
	}

	@Override
	public void addBee(Bee bee) {
		if (bees == null) bees = new ArrayList<Bee>();
		bees.add(bee);
	}
	
	@Override
	public int getBeeCount() {
		if (bees == null) return 0;
		return bees.size();
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData) return;
		writer.writeCompoundTag(NBT_FLOWER_POS);
		writer.writeIntTag(NBT_X, flower.getBlockX());
		writer.writeIntTag(NBT_Y, flower.getBlockY());
		writer.writeIntTag(NBT_Z, flower.getBlockZ());
		writer.writeEndTag();
		if (getBeeCount() > 0) {
			writer.writeListTag(NBT_BEES, TagType.COMPOUND, getBeeCount());
			for (Bee b : bees) {
				writer.writeIntTag(NBT_MIN_OCCUPATION_TICKS, b.getHiveMinOccupationTicks());
				writer.writeIntTag(NBT_TICKS_IN_HIVE, b.getTicksInHive());
				writer.writeCompoundTag(NBT_ENTITY_DATA);
				b.toNBT(writer, systemData);
				writer.writeEndTag();
				writer.writeEndTag();
			}
		} else writer.writeListTag(NBT_BEES, TagType.TAG_END, 0);
	}

}
