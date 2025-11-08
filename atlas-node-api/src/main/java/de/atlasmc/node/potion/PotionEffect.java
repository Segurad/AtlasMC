package de.atlasmc.node.potion;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.io.ProtocolException;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.entity.LivingEntity;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

import static de.atlasmc.io.PacketUtil.writeVarInt;
import static de.atlasmc.io.PacketUtil.readVarInt;

public interface PotionEffect extends Cloneable, NBTSerializable, StreamSerializable {
	
	public static final NBTCodec<PotionEffect>
	NBT_CODEC = NBTCodec
					.builder(PotionEffect.class)
					.searchKeyConstructor("id", PotionEffectType.REGISTRY_KEY, PotionEffectType::createEffect, PotionEffect::getType)
					.build();
	
	public static final StreamCodec<PotionEffect>
	STREAM_CODEC = new StreamCodec<PotionEffect>() {
		
		@Override
		public boolean serialize(PotionEffect value, ByteBuf output, CodecContext context) throws IOException {
			writeVarInt(value.getType().getID(), output);
			writeVarInt(value.getAmplifier(), output);
			writeVarInt(value.getDuration(), output);
			output.writeBoolean(value.hasReducedAmbient());
			output.writeBoolean(value.hasParticels());
			output.writeBoolean(value.isShowingIcon());
			output.writeBoolean(false); // hidden details
			return true;
		}
		
		@Override
		public Class<? extends PotionEffect> getType() {
			return PotionEffect.class;
		}
		
		@Override
		public PotionEffect deserialize(PotionEffect value, ByteBuf in, CodecContext context) throws IOException {
			final int id = readVarInt(in);
			PotionEffectType type = PotionEffectType.getByID(id);
			if (type == null)
				throw new ProtocolException("Unable to find PotionEffectType with id: " + id);
			int amplifier = readVarInt(in);
			int duration = readVarInt(in);
			boolean ambient = in.readBoolean();
			boolean particles = in.readBoolean();
			boolean icon = in.readBoolean();
			while (in.readBoolean())  { // skip hidden details
				readVarInt(in);
				readVarInt(in);
				in.readBoolean();
				in.readBoolean();
				in.readBoolean();
			}
			return type.createEffect(amplifier, duration, ambient, particles, icon);
		}
	};
	
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
	
	@Override
	default NBTCodec<? extends PotionEffect> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends StreamSerializable> getStreamCodec() {
		return STREAM_CODEC;
	}

}
