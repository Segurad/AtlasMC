package de.atlasmc.potion;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.LivingEntity;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public interface PotionEffect extends Cloneable {
	
	public static final CharKey
	NBT_ID = CharKey.literal("id"),
	NBT_AMPLIFIER = CharKey.literal("amplififer"),
	NBT_DURATION = CharKey.literal("duration"),
	NBT_AMBIENT = CharKey.literal("ambient"),
	NBT_SHOW_ICON = CharKey.literal("show_icon"),
	NBT_SHOW_PARTICLES = CharKey.literal("show_particles"),
	NBT_UUID = CharKey.literal("uuid");
	
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
	
	static PotionEffect getFromNBT(NBTReader reader) throws IOException {
		boolean reduceAmbient = false;
		int amplifier = 0;
		int duration = 0;
		String id = null;
		boolean showParticles = true;
		boolean showIcon = true;
		UUID uuid = null;
		while (reader.getType() != TagType.TAG_END) {
			final CharSequence key = reader.getFieldName();
			if (NBT_AMPLIFIER.equals(key))
				amplifier = reader.readByteTag();
			else if (NBT_DURATION.equals(key))
				duration = reader.readIntTag();
			else if (NBT_ID.equals(key))
				id = reader.readStringTag();
			else if (NBT_SHOW_PARTICLES.equals(key))
				showParticles = reader.readBoolean();
			else if (NBT_SHOW_ICON.equals(key))
				showIcon = reader.readBoolean();
			else if (NBT_AMBIENT.equals(key)) {
				reduceAmbient = reader.readBoolean();
			} else if (NBT_UUID.equals(key)) {
				uuid = reader.readUUID();
			} else {
				reader.skipTag();
			}
		}
		reader.readNextEntry();
		PotionEffectType type = PotionEffectType.get(id);
		if (type == null)
			throw new NBTException("No type with found with id: " + id);
		return type.createEffect(amplifier, duration, reduceAmbient, showParticles, showIcon, uuid);
	}
	
	static void toNBT(PotionEffect effect, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(NBT_ID, effect.getType().getNamespacedKeyRaw());
		if (effect.getAmplifier() != 0)
			writer.writeByteTag(NBT_AMPLIFIER, effect.getAmplifier());
		if (effect.getAmplifier() != 1)
			writer.writeIntTag(NBT_DURATION, effect.getDuration());
		if (effect.hasReducedAmbient())
			writer.writeByteTag(NBT_AMBIENT, true);
		if (!effect.hasParticels())
			writer.writeByteTag(NBT_SHOW_PARTICLES, false);
		if (!effect.isShowingIcon())
			writer.writeByteTag(NBT_SHOW_ICON, false);
		UUID uuid = effect.getUUID();
		if (uuid != null)
			writer.writeUUID(NBT_UUID, uuid);
	}

}
