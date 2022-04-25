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
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CorePotionMeta extends CoreItemMeta implements PotionMeta {
	
	protected static final String
	NBT_CUSTOM_POTION_COLOR = "CustomPotionColor",
	NBT_CUSTOM_POTION_EFFECTS = "CustomPotionEffects",
	NBT_POTION = "Potion",
	NBT_AMBIENT = "Ambient",
	NBT_AMPLIFIER = "Amplifier",
	NBT_DURATION = "Duration",
	NBT_SHOW_PARTICLES = "ShowParticles",
	NBT_SHOW_ICON = "ShowIcon";
	
	static {
		NBT_FIELDS.setField(NBT_CUSTOM_POTION_COLOR, (holder, reader) -> {
			if (holder instanceof PotionMeta) {
				((PotionMeta) holder).setColor(new Color(reader.readIntTag()));
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(NBT_CUSTOM_POTION_EFFECTS, (holder, reader) -> {
			if (holder instanceof PotionMeta) {
				PotionMeta meta = ((PotionMeta) holder);
				reader.readNextEntry();
				while (reader.getRestPayload() > 0) {
					boolean reduceAmbient = false;
					int amplifier = 0;
					int duration = 0;
					int id = -1;
					boolean showParticles = true;
					boolean showIcon = true;
					while (reader.getType() != TagType.TAG_END) {
						switch (reader.getFieldName()) {
						case NBT_AMBIENT:
							reduceAmbient = reader.readByteTag() == 1;
							break;
						case NBT_AMPLIFIER:
							amplifier = reader.readByteTag();
							break;
						case NBT_DURATION:
							duration = reader.readIntTag();
							break;
						case NBT_ID:
							id = reader.readByteTag();
							break;
						case NBT_SHOW_PARTICLES:
							showParticles = reader.readByteTag() == 1;
							break;
						case NBT_SHOW_ICON:
							showIcon = reader.readByteTag() == 1;
							break;
						default:
							reader.skipTag();
							break;
						}
					}
					PotionEffectType type = PotionEffectType.getByID(id);
					if (duration <= 0 || type == null) {
						reader.readNextEntry();
						continue;
					}
					reader.readNextEntry();
					meta.addCustomEffect(new PotionEffect(type, duration, amplifier, reduceAmbient, showParticles, showIcon));
				}
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(NBT_POTION, (holder, reader) -> {
			if (holder instanceof PotionMeta) {
				((PotionMeta) holder).setBasePotionData(PotionData.getByName(reader.readStringTag()));
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	private Color color;
	private List<PotionEffect> customEffects;
	private PotionData data;
	
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
		if (hasColor()) writer.writeIntTag(NBT_CUSTOM_POTION_COLOR, color.asRGB());
		if (hasCustomEffects()) {
			writer.writeListTag(NBT_CUSTOM_POTION_EFFECTS, TagType.COMPOUND, customEffects.size());
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
		if (hasBasePotionData()) writer.writeStringTag(NBT_POTION, data.getName());
	}

	@Override
	public int getCustomEffectCount() {
		return customEffects == null ? 0 : customEffects.size();
	}

	@Override
	public boolean hasBasePotionData() {
		return data != null;
	}
	
	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage) {
		if (!super.isSimilar(meta, ignoreDamage))
			return false;
		PotionMeta potionMeta = (PotionMeta) meta;
		if (hasColor() != potionMeta.hasColor())
			return false;
		if (hasColor() && !getColor().equals(potionMeta.getColor()))
			return false;
		if (hasBasePotionData() != potionMeta.hasBasePotionData())
			return false;
		if (hasBasePotionData() && !getBaseData().equals(potionMeta.getBaseData()))
			return false;
		if (hasCustomEffects() != potionMeta.hasCustomEffects())
			return false;
		if (hasCustomEffects() && !getCustomEffects().equals(potionMeta.getCustomEffects()))
			return false;
		return true;
	}

}
