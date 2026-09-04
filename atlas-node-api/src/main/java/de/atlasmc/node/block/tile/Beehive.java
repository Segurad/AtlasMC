package de.atlasmc.node.block.tile;

import java.util.List;

import org.joml.Vector3i;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.entity.LivingEntity;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.UnsafeAPI;

public interface Beehive extends TileEntity {
	
	@NotNull
	public static final NBTCodec<Beehive>
	NBT_CODEC = NBTCodec
					.builder(Beehive.class)
					.include(TileEntity.NBT_CODEC)
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
	
	void removeBee(LivingEntity bee);
	
	void addBee(LivingEntity bee);
	
	int getBeeCount();
	
	@Override
	Beehive clone();
	
	@Override
	default NBTCodec<? extends Beehive> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static class Occupant implements NBTSerializable, StreamSerializable {
		
		@NotNull
		public static final NBTCodec<Occupant>
		NBT_CODEC = NBTCodec
						.builder(Occupant.class)
						.defaultConstructor(Occupant::new)
						.codec("entity_data", Occupant::getBee, Occupant::setBee, LivingEntity.NBT_CODEC)
						.intField("min_ticks_in_hive", Occupant::getMinTicksInHive, Occupant::setMinTicksInHive, 0)
						.intField("ticks_in_hive", Occupant::getTicksInHive, Occupant::setTicksInHive, 0)
						.build();
		
		@NotNull
		public static final StreamCodec<Occupant>
		STREAM_CODEC = StreamCodec
						.builder(Occupant.class)
						.defaultConstructor(Occupant::new)
						.codec(Occupant::getBee, Occupant::setBee, LivingEntity.NBT_CODEC)
						.varInt(Occupant::getTicksInHive, Occupant::setTicksInHive)
						.varInt(Occupant::getMinTicksInHive, Occupant::setMinTicksInHive)
						.build();
		
		private LivingEntity bee;
		private int minTicksInHive;
		private int ticksInHive;
		
		private Occupant() {
			// internal
		}
		
		public Occupant(LivingEntity bee) {
			this(bee, 0, 0);
		}
		
		public Occupant(LivingEntity bee, int minTicksInHive, int ticksInHive) {
			this.bee = bee;
			this.minTicksInHive = minTicksInHive;
			this.ticksInHive = ticksInHive;
		}

		public LivingEntity getBee() {
			return bee;
		}
		
		public void setBee(LivingEntity bee) {
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
