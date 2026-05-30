package de.atlasmc.node.entity;

import org.joml.Vector3i;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Dolphin extends WaterAnimal {
	
	@NotNull
	public static final NBTCodec<Dolphin>
	NBT_CODEC = NBTCodec
					.builder(Dolphin.class)
					.include(WaterAnimal.NBT_CODEC)
					.intField("Moistness", Dolphin::getMoistureLevel, Dolphin::setMoistureLevel, 2400)
					.boolField("GotFish", Dolphin::hasFish, Dolphin::setFish, false)
					.build();
	
	default Vector3i getTreasurePosition() {
		return getTreasurePosition(new Vector3i());
	}
	
	Vector3i getTreasurePosition(Vector3i loc);
	
	default void setTreasurePosition(Vector3i loc) {
		setTreasurePosition(loc.x, loc.y, loc.z);
	}
	
	void setTreasurePosition(int x, int y, int z);
	
	boolean hasFish();
	
	void setFish(boolean fish);
	
	int getMoistureLevel();
	
	void setMoistureLevel(int level);
	
	int getMaxMoistureLevel();
	
	@Override
	default NBTCodec<? extends LivingEntity> getNBTCodec() {
		return NBT_CODEC;
	}

}
