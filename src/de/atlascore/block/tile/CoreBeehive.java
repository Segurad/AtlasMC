package de.atlascore.block.tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Location;
import de.atlasmc.Material;
import de.atlasmc.SimpleLocation;
import de.atlasmc.block.tile.Beehive;
import de.atlasmc.entity.Bee;
import de.atlasmc.entity.EntityType;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreBeehive extends CoreTileEntity implements Beehive {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
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
		NBT_FIELDS = new ChildNBTFieldContainer(CoreTileEntity.NBT_FIELDS);
		NBT_FIELDS.setContainer(NBT_FLOWER_POS)
			.setField(NBT_X, (holder, reader)-> {
				if (holder instanceof Beehive)
				((Beehive) holder).setFlowerPosX(reader.readIntTag());
				else reader.skipTag();
			}).setField(NBT_Y, (holder, reader)-> {
				if (holder instanceof Beehive)
				((Beehive) holder).setFlowerPosY(reader.readIntTag());
				else reader.skipTag();
			}).setField(NBT_Y, (holder, reader)-> {
				if (holder instanceof Beehive)
				((Beehive) holder).setFlowerPosZ(reader.readIntTag());
				else reader.skipTag();
			});
		NBT_FIELDS.setField(NBT_BEES, (holder, reader) -> {
			if (!(holder instanceof Beehive)) {
				reader.skipTag();
				return;
			}
			if (reader.getType() != TagType.LIST) 
				throw new NBTException("Unexpected TagType(" + reader.getType() + ") expected TagType(LIST)!");
			reader.readNextEntry();
			Beehive hive = (Beehive) holder;
			while (reader.getRestPayload() > 0) {
				int minoccupation = 0;
				int ticksinhive = 0;
				Bee bee = null;
				switch (reader.getFieldName()) {
				case NBT_MIN_OCCUPATION_TICKS:
					minoccupation = reader.readIntTag();
					break;
				case NBT_TICKS_IN_HIVE:
					ticksinhive = reader.readIntTag();
					break;
				case NBT_ENTITY_DATA:
					reader.readNextEntry();
					bee = (Bee) EntityType.BEE.create(-1, hive.getLocation(), null);
					if (bee != null)
						bee.fromNBT(reader);
					break;
				default:
					reader.skipTag();
					break;
				}
				if (bee == null) continue;
				bee.setTicksInHive(ticksinhive);
				bee.setHiveMinOccupationTicks(minoccupation);
				hive.addBee(bee);
			}
		});
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

	@Override
	public void setFlowerPosX(int x) {
		flower.setX(x);
	}

	@Override
	public void setFlowerPosY(int y) {
		flower.setY(y);
	}

	@Override
	public void setFlowerPosZ(int z) {
		flower.setZ(z);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

}
