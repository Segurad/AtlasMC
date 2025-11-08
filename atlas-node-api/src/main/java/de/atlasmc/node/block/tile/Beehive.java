package de.atlasmc.node.block.tile;

import java.util.List;

import org.joml.Vector3i;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.entity.Bee;
import de.atlasmc.util.annotation.UnsafeAPI;

public interface Beehive extends TileEntity {
	
	public static final NBTCodec<Beehive>
	NBT_HANDLER = NBTCodec
					.builder(Beehive.class)
					.include(TileEntity.NBT_HANDLER)
					.codecList("bee", Beehive::hasBees, Beehive::getBees, Occupant.NBT_CODEC)
					.codec("flower_pos", Beehive::getFlowerPosUnsafe, Beehive::setFlowerPos, NBTCodecs.VECTOR_3I)
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
	default NBTCodec<? extends Beehive> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	public static class Occupant implements NBTSerializable, StreamSerializable {
		
		public static final NBTCodec<Occupant>
		NBT_CODEC = NBTCodec
						.builder(Occupant.class)
						.defaultConstructor(Occupant::new)
						.codec("entity_data", Occupant::getBee, Occupant::setBee, Bee.NBT_CODEC)
						.intField("min_ticks_in_hive", Occupant::getMinTicksInHive, Occupant::setMinTicksInHive, 0)
						.intField("ticks_in_hive", Occupant::getTicksInHive, Occupant::setTicksInHive, 0)
						.build();
		
		public static final StreamCodec<Occupant>
		STREAM_CODEC = StreamCodec
						.builder(Occupant.class)
						.defaultConstructor(Occupant::new)
						.codec(Occupant::getBee, Occupant::setBee, Bee.NBT_CODEC)
						.varInt(Occupant::getTicksInHive, Occupant::setTicksInHive)
						.varInt(Occupant::getMinTicksInHive, Occupant::setMinTicksInHive)
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
		public NBTCodec<? extends Occupant> getNBTCodec() {
			return NBT_CODEC;
		}
		
		@Override
		public StreamCodec<? extends Occupant> getStreamCodec() {
			return STREAM_CODEC;
		}
		
	}

}
