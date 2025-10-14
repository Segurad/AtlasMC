package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface EnderDragon extends Mob {
	
	public static final NBTSerializationHandler<EnderDragon>
	NBT_HANDLER = NBTSerializationHandler
					.builder(EnderDragon.class)
					.include(Mob.NBT_HANDLER)
					.enumIntField("DragonPhase", EnderDragon::getPhase, EnderDragon::setPhase, DragonPhase.class, DragonPhase.HOVERING)
					.build();
	
	DragonPhase getPhase();
	
	void setPhase(DragonPhase phase);
	
	@Override
	default NBTSerializationHandler<? extends EnderDragon> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum DragonPhase implements IDHolder {
		
		CIRCLING,
		STRAFING,
		FLYING_TO_PORTAL,
		LANDING,
		TAKING_OFF,
		LANDED_BREATH_ATTACK,
		LANDED_BREATH_ATTACK_LOOKING,
		LANDED_BREATH_ATTACK_BEGINNING,
		CHARGING,
		FLYING_TO_DIE,
		HOVERING;

		@Override
		public int getID() {
			return ordinal();
		}
		
	}

}
