package de.atlasmc.node.entity;

import org.joml.Vector3i;

import de.atlasmc.util.annotation.UnsafeAPI;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Bee extends Animal, AngerableMob {

	public static final NBTSerializationHandler<Bee>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Bee.class)
					.include(Animal.NBT_HANDLER)
					.include(AngerableMob.NBT_HANDLER)
					.intField("CannotEnterHiveTicks", Bee::getTicksCannotEnterHive, Bee::setTicksCannotEnterHive, 0)
					.intField("CropsGrownSincePollination", Bee::getCropsGrownSincePollination, Bee::setCropsGrownSincePollination, 0)
					.vector3i("flower_pos", Bee::getFlowerLocationUnsafe, Bee::setFlowerLocation)
					.boolField("HasNectar", Bee::hasNectar, Bee::setNectar, false)
					.boolField("HasStung", Bee::hasStung, Bee::setStung, false)
					.vector3i("hive_pos", Bee::getHiveLocationUnsafe, Bee::setHiveLocation)
					.intField("TicksSincePollination", Bee::getTicksSincePollination, Bee::setTicksSincePollination, 0)
					.build();
	
	boolean hasStung();
	
	void setStung(boolean stung);
	
	boolean hasNectar();
	
	void setNectar(boolean nectar);

	void setHiveLocation(Vector3i location);
	
	default Vector3i getHiveLocation() {
		return getHiveLocation(new Vector3i());
	}
	
	Vector3i getHiveLocation(Vector3i vec);
	
	@UnsafeAPI
	Vector3i getHiveLocationUnsafe();
	
	void setFlowerLocation(Vector3i location);
	
	@UnsafeAPI
	Vector3i getFlowerLocationUnsafe();
	
	default Vector3i getFlowerLocation() {
		return getFlowerLocation(new Vector3i());
	}
	
	Vector3i getFlowerLocation(Vector3i vec);
	
	void setTicksSincePollination(int ticks);
	
	int getTicksSincePollination();
	
	void setTicksCannotEnterHive(int ticks);
	
	int getTicksCannotEnterHive();
	
	void setCropsGrownSincePollination(int crops);
	
	int getCropsGrownSincePollination();
	
	@Override
	default NBTSerializationHandler<? extends Bee> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
