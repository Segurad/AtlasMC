package de.atlasmc.node.entity;

import java.util.UUID;

import de.atlasmc.node.event.entity.EntityGrowEvent;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface AgeableMob extends Mob {
	
	public static final NBTSerializationHandler<AgeableMob>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AgeableMob.class)
					.include(LivingEntity.NBT_HANDLER)
					.intField("Age", AgeableMob::getAge, AgeableMob::setAge, 0)
					.boolField("IsBaby", AgeableMob::isBaby, AgeableMob::setBaby, false) // non standard
					.intField("ForcedAge", AgeableMob::getForcedAge, AgeableMob::setForcedAge, 0)
					.intField("InLove", AgeableMob::getInLove, AgeableMob::setInLove, 0)
					.uuid("LoveCause", AgeableMob::getLoveCause, AgeableMob::setLoveCause)
					.build();
	
	boolean isBaby();
	
	void setBaby(boolean baby);
	
	/**
	 * Sets the age value of this Entity.<br>
	 * Meanings of Age:
	 * <ul>
	 * <li>age < 0 it ticks up until 0 and will turn try to turn it into an adult by calling {@link EntityGrowEvent}</li>
	 * <li>setting the age to 0 will not call any Events and will keep the is as adult or baby</li>
	 * <li>age > 0 is ticking down until 0 and indicates the breeding cool down</li>
	 * </ul>
	 * @param age
	 */
	void setAge(int age);
	
	/**
	 * Returns the age value of this Entity.<br>
	 * @return age
	 * @see #setAge(int)
	 */
	int getAge();
	
	int getForcedAge();
	
	void setForcedAge(int age);

	/**
	 * Sets the time in ticks this entity is in love mode.
	 * @param time
	 */
	void setInLove(int time);

	int getInLove();

	/**
	 * Sets the UUID of the Entity that causes the love state.
	 * @param uuid
	 */
	void setLoveCause(UUID uuid);
	
	UUID getLoveCause();
	
	@Override
	default NBTSerializationHandler<? extends AgeableMob> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
