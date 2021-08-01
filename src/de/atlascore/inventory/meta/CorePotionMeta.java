package de.atlascore.inventory.meta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.atlasmc.Color;
import de.atlasmc.Material;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.PotionMeta;
import de.atlasmc.potion.PotionData;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CorePotionMeta extends CoreItemMeta implements PotionMeta {

	private Color color;
	private List<PotionEffect> customEffects;
	private PotionData data;
	
	protected static final String
		CUSTOM_POTION_COLOR = "CustomPotionColor",
		CUSTOM_POTION_EFFECTS = "CustomPotionEffects",
		POTION = "Potion";
	
	static {
		NBT_FIELDS.setField(CUSTOM_POTION_COLOR, (holder, reader) -> {
			if (PotionMeta.class.isInstance(holder)) {
				((PotionMeta) holder).setColor(new Color(reader.readIntTag()));
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(CUSTOM_POTION_EFFECTS, (holder, reader) -> {
			if (PotionMeta.class.isInstance(holder)) {
				PotionMeta meta = ((PotionMeta) holder);
				List<PotionEffect> effects = meta.getCustomEffects();
				while (reader.getRestPayload() > 0) {
					PotionEffect effect = new PotionEffect();
					effect.fromNBT(reader);
					effects.add(effect);
				}
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(POTION, (holder, reader) -> {
			if (PotionMeta.class.isInstance(holder)) {
				((PotionMeta) holder).setBasePotionData(PotionData.getByName(reader.readStringTag()));
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	public CorePotionMeta(Material material) {
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

	@Override
	public CorePotionMeta clone() {
		CorePotionMeta clone = (CorePotionMeta) super.clone();
		if (hasCustomEffects()) {
			for (PotionEffect effect : getCustomEffects()) {
				clone.getCustomEffects().add(effect.clone());
			}
		}
		return clone;
	}

	@Override
	public PotionData getBaseData() {
		return data;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public List<PotionEffect> getCustomEffects() {
		if (customEffects == null) customEffects = new ArrayList<PotionEffect>();
		return customEffects;
	}

	@Override
	public boolean hasColor() {
		return color != null;
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
	public void setBasePotionData(PotionData data) {
		Validate.notNull(data, "PotionData can not be null!");
		this.data = data;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
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
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasColor()) writer.writeIntTag(CUSTOM_POTION_COLOR, color.asRGB());
		if (hasCustomEffects()) {
			writer.writeListTag(CUSTOM_POTION_EFFECTS, TagType.COMPOUND, customEffects.size());
			for (PotionEffect effect : customEffects) {
				effect.toNBT(writer, systemData);
				writer.writeEndTag();
			}
		}
		writer.writeStringTag(POTION, data.getName());
	}

}
