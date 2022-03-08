package de.atlascore.inventory.meta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.FireworkEffect;
import de.atlasmc.Material;
import de.atlasmc.inventory.meta.FireworkMeta;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreFireworkMeta extends CoreItemMeta implements FireworkMeta {
	
	protected static final String
	NBT_EXPLOSIONS = "Explosions",
	NBT_FLIGHT = "Flight";
	
	static {
		NBT_FIELDS.setField(NBT_EXPLOSIONS, (holder, reader) -> {
			if (holder instanceof FireworkMeta) {
				List<FireworkEffect> effects = ((FireworkMeta) holder).getEffects();
				while (reader.getRestPayload() > 0) {
					FireworkEffect effect = new FireworkEffect();
					effect.fromNBT(reader);
					effects.add(effect);
					if (reader.getType() != TagType.TAG_END)
						throw new NBTException("Error while reading Explosions Field! Expected TAG_END but read: " + reader.getType().name());
					reader.readNextEntry();
				}
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(NBT_FLIGHT, (holder, reader) -> {
			if (holder instanceof FireworkMeta) {
				((FireworkMeta) holder).setPower(reader.readByteTag());
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	private List<FireworkEffect> effects;
	private int power;
	
	public CoreFireworkMeta(Material material) {
		super(material);
		power = 1;
	}

	@Override
	public void addEffect(FireworkEffect effect) {
		getEffects().add(effect);
	}

	@Override
	public void addEffects(FireworkEffect... effects) {
		List<FireworkEffect> effectsList = getEffects();
		for (FireworkEffect effect : effects) {
			effectsList.add(effect);
		}
	}

	@Override
	public void clearEffects() {
		if (effects != null) effects.clear();
	}

	@Override
	public CoreFireworkMeta clone() {
		CoreFireworkMeta clone = (CoreFireworkMeta) super.clone();
		if (clone == null)
			return null;
		if (hasEffects()) {
			clone.effects = new ArrayList<>(effects.size());
			for (FireworkEffect effect : effects) {
				clone.effects.add(effect.clone());
			}
		}
		return clone;
	}

	@Override
	public List<FireworkEffect> getEffects() {
		if (effects == null) effects = new ArrayList<FireworkEffect>();
		return effects;
	}

	@Override
	public int getEffectSize() {
		return hasEffects() ? effects.size() : 0;
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public boolean hasEffects() {
		return effects != null && !effects.isEmpty();
	}

	@Override
	public void removeEffect(int index) {
		if (hasEffects()) effects.remove(index);
	}

	@Override
	public void setPower(int power) {
		if (power < 0 || power > 4) throw new IllegalArgumentException("Power is not between 1 and 3:" + power);
		this.power = power;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasEffects()) {
			writer.writeListTag(NBT_EXPLOSIONS, TagType.COMPOUND, effects.size());
			for (FireworkEffect effect : effects) {
				effect.toNBT(writer, systemData);
				writer.writeEndTag();
			}
		}
		writer.writeByteTag(NBT_FLIGHT, power);
	}
	
	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage, boolean checkClass) {
		if (!super.isSimilar(meta, ignoreDamage, checkClass))
			return false;
		FireworkMeta fireMeta = (FireworkMeta) meta;
		if (getPower() != fireMeta.getPower())
			return false;
		if (hasEffects() != fireMeta.hasEffects())
			return false;
		if (hasEffects() && !fireMeta.getEffects().equals(fireMeta.getEffects()))
			return false;
		return false;
	}

}
