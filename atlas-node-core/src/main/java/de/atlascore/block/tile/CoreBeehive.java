package de.atlascore.block.tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Beehive;
import de.atlasmc.entity.Bee;
import de.atlasmc.entity.EntityType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreBeehive extends CoreTileEntity implements Beehive {

	protected static final NBTFieldSet<CoreBeehive> NBT_FIELDS;
	
	protected static final CharKey
	NBT_FLOWER_POS = CharKey.literal("FlowerPos"),
	NBT_X = CharKey.literal("X"),
	NBT_Y = CharKey.literal("Y"),
	NBT_Z = CharKey.literal("Z"),
	NBT_BEES = CharKey.literal("Bees"),
	NBT_MIN_OCCUPATION_TICKS = CharKey.literal("MinOccupationTicks"),
	NBT_TICKS_IN_HIVE = CharKey.literal("TicksInHive"),
	NBT_ENTITY_DATA = CharKey.literal("EntityData");
	
	static {
		NBT_FIELDS = CoreTileEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setSet(NBT_FLOWER_POS)
			.setField(NBT_X, (holder, reader)-> {
				holder.setFlowerPosX(reader.readIntTag());
			}).setField(NBT_Y, (holder, reader)-> {
				holder.setFlowerPosY(reader.readIntTag());
			}).setField(NBT_Y, (holder, reader)-> {
				holder.setFlowerPosZ(reader.readIntTag());
			});
		NBT_FIELDS.setField(NBT_BEES, (holder, reader) -> {
			if (reader.getType() != TagType.LIST) 
				throw new NBTException("Unexpected TagType(" + reader.getType() + ") expected TagType(LIST)!");
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				int minoccupation = 0;
				int ticksinhive = 0;
				Bee bee = null;
				while (reader.getType() != TagType.TAG_END) {
					final CharSequence value = reader.getFieldName();
					if (NBT_MIN_OCCUPATION_TICKS.equals(value))
						minoccupation = reader.readIntTag();
					else if (NBT_TICKS_IN_HIVE.equals(value))
						ticksinhive = reader.readIntTag();
					else if (NBT_ENTITY_DATA.equals(value)) {
						reader.readNextEntry();
						bee = (Bee) EntityType.get(EntityType.BEE).create(holder.getWorld());
						if (bee != null)
							bee.fromNBT(reader);
					} else
						reader.skipTag();
				}
				reader.skipTag();
				if (bee == null) continue;
				bee.setTicksInHive(ticksinhive);
				bee.setHiveMinOccupationTicks(minoccupation);
				holder.addBee(bee);
			}
		});
	}
	
	private Location flower;
	private List<Bee> bees;
	
	public CoreBeehive(BlockType type) {
		super(type);
		flower = new Location(null, Double.NaN, Double.NaN, Double.NaN);
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
		flower.set(loc);
	}
	
	@Override
	public List<Bee> getBees() {
		if (bees == null) 
			bees = new ArrayList<>();
		return bees;
	}

	@Override
	public void removeBee(Bee bee) {
		if (bees != null) 
			bees.add(bee);
	}

	@Override
	public void addBee(Bee bee) {
		if (bees == null) 
			bees = new ArrayList<>();
		bees.add(bee);
	}
	
	@Override
	public int getBeeCount() {
		if (bees == null) 
			return 0;
		return bees.size();
	}
	
	@Override
	public void setLocation(Chunk chunk, int x, int y, int z) {
		super.setLocation(chunk, x, y, z);
		if (chunk != null) {
			if (flower.isInvalid()) {
				flower.set(getWorld(), getX(), getY(), getZ());
			} else {
				flower.set(getWorld(), flower.x, flower.y, flower.z);
			}
		}
			
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData) 
			return;
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
	public Beehive clone() {
		CoreBeehive hive = (CoreBeehive) super.clone();
		hive.flower = flower.clone();
		if (bees != null) {
			hive.bees = new ArrayList<>(bees.size());
			// TODO clone bees
		}
		return hive;
	}

	@Override
	public void setFlowerPosX(int x) {
		flower.x = x;
	}

	@Override
	public void setFlowerPosY(int y) {
		flower.y = y;
	}

	@Override
	public void setFlowerPosZ(int z) {
		flower.z = z;
	}
	
	@Override
	protected NBTFieldSet<? extends CoreBeehive> getFieldSetRoot() {
		return NBT_FIELDS;
	}

}
