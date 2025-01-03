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
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

public class CoreApplyEffects implements ApplyEffects {
	
	protected static final NBTFieldContainer<CoreApplyEffects> NBT_FIELDS;
	
	protected static final CharKey
	NBT_PROPABILITY = CharKey.literal("probability"),
	NBT_EFFECTS = CharKey.literal("effects");
	
	static {
		NBT_FIELDS = NBTFieldContainer.newContainer();
		NBT_FIELDS.setField(NBT_EFFECTS, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				PotionEffect effect = PotionEffect.getFromNBT(reader);
				if (effect == null)
					continue;
				holder.addEffect(effect);
			}
			reader.readNextEntry();
		});
		NBT_FIELDS.setField(NBT_PROPABILITY, (holder, reader) -> {
			holder.probability = reader.readFloatTag();
		});
		NBT_FIELDS.setField(NBT_TYPE, NBTField.skip());
	}

	private List<PotionEffect> effects;
	private float probability;
	
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
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(NBT_TYPE, getType().getNamespacedKeyRaw());
		if (hasEffects()) {
			final int size = effects.size();
			writer.writeListTag(NBT_EFFECTS, TagType.COMPOUND, size);
			for (int i = 0; i < size; i++) {
				PotionEffect effect = effects.get(i);
				writer.writeCompoundTag();
				PotionEffect.toNBT(effect, writer, systemData);
				writer.writeEndTag();
			}
		}
		if (probability != 1.0f)
			writer.writeFloatTag(NBT_PROPABILITY, probability);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
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
		try {
			CoreApplyEffects clone = (CoreApplyEffects) super.clone();
			if (hasEffects()) {
				clone.effects = new ArrayList<>();
				final int size = effects.size();
				for (int i = 0; i < size; i++) {
					clone.effects.add(effects.get(i).clone());
				}
			}
			return clone;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(effects, probability);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoreApplyEffects other = (CoreApplyEffects) obj;
		return Objects.equals(effects, other.effects)
				&& Float.floatToIntBits(probability) == Float.floatToIntBits(other.probability);
	}

}
