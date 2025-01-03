package de.atlascore.inventory.component;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.ConsumableComponent;
import de.atlasmc.inventory.component.effect.ComponentEffect;
import de.atlasmc.inventory.component.effect.ComponentEffectFactory;
import de.atlasmc.inventory.component.effect.ComponentEffectType;
import de.atlasmc.sound.EnumSound;
import de.atlasmc.sound.Sound;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreConsumableComponent extends AbstractItemComponent implements ConsumableComponent {

	protected static final NBTFieldContainer<CoreConsumableComponent> NBT_FIELDS;
	
	protected static final CharKey
	NBT_CONSUME_SECONDS = CharKey.literal("consume_seconds"),
	NBT_ANIMATION = CharKey.literal("animation"),
	NBT_SOUND = CharKey.literal("sound"),
	NBT_HAS_CONSUME_PARTICLES = CharKey.literal("has_consume_particles"),
	NBT_ON_CONSUME_EFFECTS = CharKey.literal("on_consume_effects");
	
	static {
		NBT_FIELDS = NBTFieldContainer.newContainer();
		NBT_FIELDS.setField(NBT_CONSUME_SECONDS, (holder, reader) -> {
			holder.consumeSeconds = reader.readFloatTag();
		});
		NBT_FIELDS.setField(NBT_ANIMATION, (holder, reader) -> {
			holder.setAnimation(Animation.getByName(reader.readStringTag()));
		});
		NBT_FIELDS.setField(NBT_SOUND, (holder, reader) -> {
			holder.setSound(Sound.fromNBT(reader));
		});
		NBT_FIELDS.setField(NBT_HAS_CONSUME_PARTICLES, (holder, reader) -> {
			holder.setParticles(reader.readBoolean());
		});
		NBT_FIELDS.setField(NBT_ON_CONSUME_EFFECTS, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				ComponentEffect effect = ComponentEffect.getFromNBT(reader);
				if (effect == null)
					continue;
				holder.addEffect(effect);
			}
			reader.readNextEntry();
		});
	}
	
	private float consumeSeconds;
	private Animation animation;
	private Sound sound;
	private List<ComponentEffect> effects;
	private boolean particles;
	
	public CoreConsumableComponent(NamespacedKey key) {
		super(key);
		consumeSeconds = 1.6f;
		particles = true;
		animation = Animation.EAT;
		sound = EnumSound.ENTITY_GENERIC_EAT;
	}
	
	@Override
	public CoreConsumableComponent clone() {
		return (CoreConsumableComponent) super.clone();
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(key.toString());
		if (consumeSeconds != 1.6f)
			writer.writeFloatTag(NBT_CONSUME_SECONDS, consumeSeconds);
		if (animation != Animation.EAT)
			writer.writeStringTag(NBT_ANIMATION, animation.getName());
		if (sound != EnumSound.ENTITY_GENERIC_EAT)
			Sound.toNBT(NBT_SOUND, sound, writer, systemData);
		if (!particles)
			writer.writeByteTag(NBT_HAS_CONSUME_PARTICLES, false);
		if (hasEffects()) {
			final int size = effects.size();
			writer.writeListTag(NBT_ON_CONSUME_EFFECTS, TagType.COMPOUND, size);
			for (int i = 0; i < size; i++) {
				ComponentEffect effect = effects.get(i);
				effect.toNBT(writer, systemData);
				writer.writeEndTag();
			}
		}
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}

	@Override
	public float getConsumeSeconds() {
		return consumeSeconds;
	}

	@Override
	public void setConsumeSeconds(float duration) {
		this.consumeSeconds = duration;
	}

	@Override
	public Animation getAnimation() {
		return animation;
	}

	@Override
	public void setAnimation(Animation animation) {
		if (animation == null)
			throw new IllegalArgumentException("Animation can not be null!");
		this.animation = animation;
	}

	@Override
	public Sound getSound() {
		return sound;
	}

	@Override
	public void setSound(Sound sound) {
		if (sound == null)
			throw new IllegalArgumentException("Sound can not be null!");
		this.sound = sound;
	}

	@Override
	public boolean hasParticles() {
		return particles;
	}

	@Override
	public void setParticles(boolean particles) {
		this.particles = particles;
	}

	@Override
	public List<ComponentEffect> getEffects() {
		if (effects == null)
			effects = new ArrayList<>();
		return effects;
	}

	@Override
	public boolean hasEffects() {
		return effects != null && !effects.isEmpty();
	}

	@Override
	public void addEffect(ComponentEffect effect) {
		if (effect == null)
			throw new IllegalArgumentException("Effect can not be null!");
		getEffects().add(effect);
	}

	@Override
	public void removeEffect(ComponentEffect effect) {
		if (effects == null)
			return;
		effects.remove(effect);
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.CONSUMABLE;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		consumeSeconds = buf.readFloat();
		setAnimation(Animation.getByID(readVarInt(buf)));
		setSound(readSound(buf));
		particles = buf.readBoolean();
		final int count = readVarInt(buf);
		if (effects != null)
			effects.clear();
		if (count == 0)
			return;
		for (int i = 0; i < count; i++) {
			int typeID = readVarInt(buf);
			ComponentEffectType type = ComponentEffectType.getByID(typeID);
			if (type == null)
				throw new ProtocolException("Unknown component type with id: " + typeID);
			ComponentEffectFactory factory = ComponentEffectFactory.REGISTRY.get(type.getNamespacedKey());
			if (factory == null)
				throw new IllegalStateException("No component effect factory found with key: " + type.getNamespacedKey());
			ComponentEffect effect = factory.createEffect();
			effect.read(buf);
		}
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		buf.writeFloat(consumeSeconds);
		writeVarInt(animation.getID(), buf);
		writeSound(sound, buf);
		buf.writeBoolean(particles);
		if (!hasEffects()) {
			writeVarInt(0, buf);
			return;
		}
		final int size = effects.size();
		writeVarInt(size, buf);
		for (int i = 0; i < size; i++) {
			ComponentEffect effect = effects.get(i);
			writeVarInt(effect.getType().getID(), buf);
			effect.write(buf);
		}
	}

}
