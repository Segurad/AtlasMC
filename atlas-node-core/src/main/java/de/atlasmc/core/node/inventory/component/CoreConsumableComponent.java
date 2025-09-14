package de.atlasmc.core.node.inventory.component;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;
import static de.atlasmc.node.io.protocol.ProtocolUtil.readSound;
import static de.atlasmc.node.io.protocol.ProtocolUtil.writeSound;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.ConsumableComponent;
import de.atlasmc.node.inventory.component.effect.ComponentEffect;
import de.atlasmc.node.inventory.component.effect.ComponentEffectType;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.Sound;
import io.netty.buffer.ByteBuf;

public class CoreConsumableComponent extends AbstractItemComponent implements ConsumableComponent {
	
	private float consumeSeconds;
	private Animation animation;
	private Sound sound;
	private List<ComponentEffect> effects;
	private boolean particles;
	
	public CoreConsumableComponent(ComponentType type) {
		super(type);
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
			ComponentEffect effect = type.createEffect();
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
