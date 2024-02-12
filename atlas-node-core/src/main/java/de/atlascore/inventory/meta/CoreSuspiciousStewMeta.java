package de.atlascore.inventory.meta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.SuspiciousStewMeta;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSuspiciousStewMeta extends CoreItemMeta implements SuspiciousStewMeta {
	
	protected static final CharKey 
	SUSPICIOUS_STEW_EFFECTS = CharKey.literal("SuspiciousStewEffects"),
	NBT_AMBIENT = CharKey.literal("Ambient"),
	NBT_AMPLIFIER = CharKey.literal("Amplifier"),
	NBT_DURATION = CharKey.literal("Duration"),
	NBT_SHOW_PARTICLES = CharKey.literal("ShowParticles"),
	NBT_SHOW_ICON = CharKey.literal("ShowIcon");
	
	static {
		NBT_FIELDS.getContainer(NBT_ATLASMC).setField(SUSPICIOUS_STEW_EFFECTS, (holder, reader) -> {
			if (holder instanceof SuspiciousStewMeta) {
				SuspiciousStewMeta meta = ((SuspiciousStewMeta) holder);
				reader.readNextEntry();
				while (reader.getRestPayload() > 0) {
					boolean reduceAmbient = false;
					int amplifier = 0;
					int duration = 0;
					int id = -1;
					boolean showParticles = true;
					boolean showIcon = true;
					while (reader.getType() != TagType.TAG_END) {
						final CharSequence value = reader.getFieldName();
						if (NBT_AMBIENT.equals(value))
							reduceAmbient = reader.readByteTag() == 1;
						else if (NBT_AMPLIFIER.equals(value))
							amplifier = reader.readByteTag();
						else if (NBT_DURATION.equals(value))
							duration = reader.readIntTag();
						else if (NBT_ID.equals(value))
							id = reader.readByteTag();
						else if (NBT_SHOW_PARTICLES.equals(value))
							showParticles = reader.readByteTag() == 1;
						else if (NBT_SHOW_ICON.equals(value))
							showIcon = reader.readByteTag() == 1;
						else
							reader.skipTag();
					}
					PotionEffectType type = PotionEffectType.getByID(id);
					if (duration <= 0 || type == null) {
						reader.readNextEntry();
						continue;
					}
					reader.readNextEntry();
					meta.addCustomEffect(type.createEffect(amplifier, duration, reduceAmbient, showParticles, showIcon), false);
				}
			} else ((ItemMeta) holder).getCustomTagContainer().addSystemTag(reader.readNBT());
		});
	}
	
	private List<PotionEffect> customEffects;
	
	public CoreSuspiciousStewMeta(Material material) {
		super(material);
	}

	@Override
	public void addCustomEffect(PotionEffect effect, boolean overwrite) {
		if (overwrite) 
			removeAllCustomEffects(effect.getType()); 
		getCustomEffects().add(effect);
	}

	@Override
	public boolean clearCustomEffects() {
		if (hasCustomEffects()) {
			customEffects.clear();
			return true;
		}
		return false;
	}

	@Override
	public CoreSuspiciousStewMeta clone() {
		CoreSuspiciousStewMeta clone = (CoreSuspiciousStewMeta) super.clone();
		if (clone == null)
			return null;
		if (hasCustomEffects()) {
			clone.customEffects = new ArrayList<>(getCustomEffectCount());
			for (PotionEffect effect : getCustomEffects()) {
				clone.customEffects.add(effect.clone());
			}
		}
		return clone;
	}

	@Override
	public List<PotionEffect> getCustomEffects() {
		if (customEffects == null) customEffects = new ArrayList<>();
		return customEffects;
	}

	@Override
	public boolean hasCustomEffect(PotionEffectType type) {
		if (hasCustomEffects()) 
		for (PotionEffect effect : customEffects) {
			if (effect.getType() == type) return true;
		}
		return false;
	}

	@Override
	public boolean hasCustomEffects() {
		return customEffects != null && !customEffects.isEmpty();
	}

	@Override
	public boolean removeCustomEffect(PotionEffectType type) {
		if (hasCustomEffects()) {
			Iterator<PotionEffect> it = customEffects.iterator();
			while (it.hasNext()) {
				if (it.next().getType() == type) {
					it.remove();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean removeAllCustomEffects(PotionEffectType type) {
		if (hasCustomEffects()) {
			Iterator<PotionEffect> it = customEffects.iterator();
			boolean changes = false;
			while (it.hasNext()) {
				if (it.next().getType() == type) {
					it.remove();
					changes = true;
				}
			}
			return changes;
		}
		return false;
	}
	
	@Override
	protected boolean hasSystemData() {
		return hasCustomEffects() || super.hasSystemData();
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasCustomEffects()) {
			writer.writeListTag(SUSPICIOUS_STEW_EFFECTS, TagType.COMPOUND, customEffects.size());
			for (PotionEffect effect : customEffects) {
				writer.writeByteTag(NBT_AMBIENT, effect.hasReducedAmbient());
				writer.writeByteTag(NBT_AMPLIFIER, effect.getAmplifier());
				writer.writeIntTag(NBT_DURATION, effect.getDuration());
				writer.writeIntTag(NBT_ID, effect.getType().getID());
				writer.writeByteTag(NBT_SHOW_PARTICLES, effect.hasParticels());
				writer.writeByteTag(NBT_SHOW_ICON, effect.isShowingIcon());
				writer.writeEndTag();
			}
		}
	}

	@Override
	public int getCustomEffectCount() {
		return customEffects == null ? 0 : customEffects.size();
	}
	
	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage) {
		if (!super.isSimilar(meta, ignoreDamage))
			return false;
		SuspiciousStewMeta stewMeta = (SuspiciousStewMeta) meta;
		if (hasCustomEffects() != stewMeta.hasCustomEffects())
			return false;
		if (hasCustomEffects() && !getCustomEffects().equals(stewMeta.getCustomEffects()))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((customEffects == null) ? 0 : customEffects.hashCode());
		return result;
	}
	
}
