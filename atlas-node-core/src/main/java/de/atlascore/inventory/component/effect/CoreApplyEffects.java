package de.atlascore.inventory.component.effect;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;
import static de.atlasmc.io.protocol.ProtocolUtil.readPotionEffect;
import static de.atlasmc.io.protocol.ProtocolUtil.writePotionEffect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.component.effect.ApplyEffects;
import de.atlasmc.inventory.component.effect.ComponentEffectType;
import de.atlasmc.potion.PotionEffect;
import io.netty.buffer.ByteBuf;

public class CoreApplyEffects extends CoreAbstractEffect implements ApplyEffects {

	private List<PotionEffect> effects;
	private float probability;

	public CoreApplyEffects(ComponentEffectType type) {
		super(type);
	}
	
	@Override
	public void apply(Entity target, ItemStack item) {
		if (!hasEffects())
			return;
		if (!(target instanceof LivingEntity entity))
			return;
		// TODO probability
		final int size = effects.size();
		for (int i = 0; i < size; i++) {
			PotionEffect effect = effects.get(i);
			entity.addPotionEffect(effect);
		}
	}

	@Override
	public void read(ByteBuf buf) throws IOException {
		if (!hasEffects()) {
			writeVarInt(0, buf);
			buf.writeFloat(probability);
			return;
		}
		final int size = effects.size();
		for (int i = 0; i < size; i++) {
			PotionEffect effect = effects.get(i);
			writePotionEffect(effect, buf);
		}
		buf.writeFloat(probability);
	}

	@Override
	public void write(ByteBuf buf) throws IOException {
		final int count = readVarInt(buf);
		if (effects != null)
			effects.clear();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				PotionEffect effect = readPotionEffect(buf);
				if (effect == null)
					continue;
				addEffect(effect);
			}
		}
		probability = buf.readFloat();
	}

	@Override
	public List<PotionEffect> getEffects() {
		if (effects == null)
			effects = new ArrayList<>();
		return effects;
	}

	@Override
	public boolean hasEffects() {
		return effects != null && !effects.isEmpty();
	}

	@Override
	public void addEffect(PotionEffect effect) {
		getEffects().add(effect);
	}

	@Override
	public void removeEffect(PotionEffect effect) {
		if (effects != null)
			effects.remove(effect);
	}

	@Override
	public float getProbability() {
		return probability;
	}

	@Override
	public void setProbability(float probability) {
		this.probability = probability;
	}
	
	@Override
	public CoreApplyEffects clone() {
		CoreApplyEffects clone = (CoreApplyEffects) super.clone();
		if (hasEffects()) {
			clone.effects = new ArrayList<>();
			final int size = effects.size();
			for (int i = 0; i < size; i++) {
				clone.effects.add(effects.get(i).clone());
			}
		}
		return clone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(effects, probability);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoreApplyEffects other = (CoreApplyEffects) obj;
		return Objects.equals(effects, other.effects)
				&& Float.floatToIntBits(probability) == Float.floatToIntBits(other.probability);
	}

}
