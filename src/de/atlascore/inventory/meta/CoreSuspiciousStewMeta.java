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
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSuspiciousStewMeta extends CoreItemMeta implements SuspiciousStewMeta {

	private List<PotionEffect> customEffects;
	
	protected static final String SUSPICIOUS_STEW_EFFECTS = "SuspiciousStewEffects";
	
	public CoreSuspiciousStewMeta(Material material) {
		super(material);
	}

	@Override
	public void addCustomEffect(PotionEffect effect, boolean overwrite) {
		if (overwrite) removeAllCustomEffects(effect.getType()); 
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
	
	static {
		NBT_FIELDS.getContainer(ATLASMC).setField(SUSPICIOUS_STEW_EFFECTS, (holder, reader) -> {
			if (SuspiciousStewMeta.class.isInstance(holder)) {
				SuspiciousStewMeta meta = ((SuspiciousStewMeta) holder);
				while (reader.getRestPayload() > 0) {
					PotionEffect effect = new PotionEffect();
					effect.fromNBT(reader);
					meta.addCustomEffect(effect, false);
				}
			} else ((ItemMeta) holder).getCustomTagContainer().addSystemTag(reader.readNBT());
		});
	}

	@Override
	public CoreSuspiciousStewMeta clone() {
		CoreSuspiciousStewMeta clone = (CoreSuspiciousStewMeta) super.clone();
		if (hasCustomEffects()) {
			for (PotionEffect effect : getCustomEffects()) {
				clone.getCustomEffects().add(effect.clone());
			}
		}
		return clone;
	}

	@Override
	public List<PotionEffect> getCustomEffects() {
		if (customEffects == null) customEffects = new ArrayList<PotionEffect>();
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
	protected boolean hasSystemDataCompound() {
		return hasCustomEffects() || super.hasSystemDataCompound();
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasCustomEffects()) {
			writer.writeListTag(SUSPICIOUS_STEW_EFFECTS, TagType.COMPOUND, customEffects.size());
			for (PotionEffect effect : customEffects) {
				effect.toNBT(writer, systemData);
				writer.writeEndTag();
			}
		}
	}

}
