package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.enums.EnumUtil;

public interface EnderDragon extends Mob {
	
	public static final NBTCodec<EnderDragon>
	NBT_HANDLER = NBTCodec
					.builder(EnderDragon.class)
					.include(Mob.NBT_HANDLER)
					.codec("DragonPhase", EnderDragon::getPhase, EnderDragon::setPhase, EnumUtil.enumIntNBTCodec(DragonPhase.class), DragonPhase.HOVERING)
					.build();
	
	DragonPhase getPhase();
	
	void setPhase(DragonPhase phase);
	
	@Override
	default NBTCodec<? extends EnderDragon> getNBTCodec() {
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
