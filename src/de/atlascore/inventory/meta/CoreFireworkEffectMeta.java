package de.atlascore.inventory.meta;

import java.io.IOException;

import de.atlasmc.FireworkEffect;
import de.atlasmc.Material;
import de.atlasmc.inventory.meta.FireworkEffectMeta;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreFireworkEffectMeta extends CoreItemMeta implements FireworkEffectMeta {

	private FireworkEffect effect;
	
	protected static final String NBT_EXPLOSION = "Explosion";
	
	static {
		NBT_FIELDS.setField(NBT_EXPLOSION, (holder, reader) -> {
			if (holder instanceof FireworkEffectMeta) {
				reader.readNextEntry();
				FireworkEffect effect = new FireworkEffect();
				effect.fromNBT(reader);
				((FireworkEffectMeta) holder).setEffect(effect);
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	public CoreFireworkEffectMeta(Material material) {
		super(material);
	}
	
	@Override
	public CoreFireworkEffectMeta clone() {
		CoreFireworkEffectMeta clone = (CoreFireworkEffectMeta) super.clone();
		if (clone == null)
			return null;
		if (hasEffect())
			clone.setEffect(effect.clone());
		return clone;
	}

	@Override
	public FireworkEffect getEffect() {
		return effect;
	}

	@Override
	public boolean hasEffect() {
		return effect != null;
	}

	@Override
	public void setEffect(FireworkEffect effect) {
		this.effect = effect;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasEffect()) {
			writer.writeCompoundTag(NBT_EXPLOSION);
			effect.toNBT(writer, systemData);
			writer.writeEndTag();
		}
	}
	
	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage, boolean checkClass) {
		if (!super.isSimilar(meta, ignoreDamage, checkClass))
			return false;
		FireworkEffectMeta effectMeta = (FireworkEffectMeta) meta;
		if (hasEffect() != effectMeta.hasEffect())
			return false;
		if (hasEffect() && !getEffect().equals(effectMeta.getEffect()))
			return false;
		return true;
	}

}
