package de.atlasmc.node.block.tile;

import java.util.List;

import org.joml.Vector3i;

import de.atlasmc.node.entity.Bee;
import de.atlasmc.util.annotation.UnsafeAPI;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Beehive extends TileEntity {
	
	public static final NBTSerializationHandler<Beehive>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Beehive.class)
					.include(TileEntity.NBT_HANDLER)
					.typeList("bee", Beehive::hasBees, Beehive::getBees, Occupant.NBT_HANDLER)
					.vector3i("flower_pos", Beehive::getFlowerPosUnsafe, Beehive::setFlowerPos)
					.build();
	
	@UnsafeAPI
	Vector3i getFlowerPosUnsafe();
	
	default Vector3i getFlowerPos() {
		return getFlowerPos(new Vector3i());
	}
	
	Vector3i getFlowerPos(Vector3i loc);
	
	void setFlowerPos(Vector3i loc);
	
	boolean hasBees();
	
	/**
	 * Returns a List containing all {@link Bee}s currently in this hive
	 * @return list of Bees
	 */
	List<Occupant> getBees();
	
	void removeBee(Bee bee);
	
	void addBee(Bee bee);
	
	int getBeeCount();
	
	Beehive clone();
	
	@Override
	default NBTSerializationHandler<? extends Beehive> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static class Occupant implements NBTSerializable {
		
		public static final NBTSerializationHandler<Occupant>
		NBT_HANDLER = NBTSerializationHandler
						.builder(Occupant.class)
						.defaultConstructor(Occupant::new)
						.typeCompoundField("entity_data", Occupant::getBee, Occupant::setBee, Bee.NBT_HANDLER)
						.intField("min_ticks_in_hive", Occupant::getMinTicksInHive, Occupant::setMinTicksInHive, 0)
						.intField("ticks_in_hive", Occupant::getTicksInHive, Occupant::setTicksInHive, 0)
						.build();
		
		private Bee bee;
		private int minTicksInHive;
		private int ticksInHive;
		
		private Occupant() {
			// internal
		}
		
		public Occupant(Bee bee) {
			this(bee, 0, 0);
		}
		
		public Occupant(Bee bee, int minTicksInHive, int ticksInHive) {
			this.bee = bee;
			this.minTicksInHive = minTicksInHive;
			this.ticksInHive = ticksInHive;
		}

		public Bee getBee() {
			return bee;
		}
		
		public void setBee(Bee bee) {
			this.bee = bee;
		}
		
		public int getMinTicksInHive() {
			return minTicksInHive;
		}
		
		public void setMinTicksInHive(int minTicksInHive) {
			this.minTicksInHive = minTicksInHive;
		}
		
		public int getTicksInHive() {
			return ticksInHive;
		}
		
		public void setTicksInHive(int ticksInHive) {
			this.ticksInHive = ticksInHive;
		}
		
		@Override
		public NBTSerializationHandler<? extends NBTSerializable> getNBTHandler() {
			return NBT_HANDLER;
		}
		
	}

}
