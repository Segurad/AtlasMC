package de.atlasmc.potion;

import java.util.UUID;

import de.atlasmc.entity.LivingEntity;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface PotionEffect extends Cloneable {
	
	public static final NBTSerializationHandler<PotionEffect>
	NBT_HANDLER = NBTSerializationHandler
					.builder(PotionEffect.class)
					.searchKeyConstructor("id", PotionEffectType.REGISTRY_KEY, PotionEffectType::createEffect, PotionEffect::getType)
					.build();
	
	PotionEffect clone();
	
	/**
	 * Called when this PotionEffect is set active for its {@link LivingEntity}
	 */
	@InternalAPI
	void addEffect(LivingEntity entity);
	
	/**
	 * Called when this PotionEffect is set inactive for its {@link LivingEntity}
	 */
	@InternalAPI
	void removeEffect(LivingEntity entity);
	
	/**
	 * Returns whether or not this effect will only do something when applied to a entity
	 * @return false if it does not tick or need {@link #removeEffect(LivingEntity)}
	 */
	boolean isOnlyOnApply();
	
	@NotNull
	PotionEffectType getType();
	
	/**
	 * Ticks this {@link PotionEffect}
	 * @param entity the entity it ticks for
	 * @param active if false only time will be reduced
	 * @return the remaining duration
	 */
	@InternalAPI
	int tick(LivingEntity entity, boolean active);

	boolean hasReducedAmbient();

	int getAmplifier();

	int getDuration();

	boolean hasParticels();
	
	boolean isShowingIcon();
	
	/**
	 * Returns the UUID associated with this PotionEffect or null if no UUID is present.
	 * @return UUID or null
	 */
	@Nullable
	UUID getUUID();

}
