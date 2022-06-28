package de.atlascore.inventory.meta;

import java.io.IOException;

import de.atlasmc.Color;
import de.atlasmc.Material;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.LeatherArmorMeta;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreLeatherArmorMeta extends CoreDamageableMeta implements LeatherArmorMeta {

	protected static final CharKey COLOR = CharKey.of("color");
	
	static {
		NBT_FIELDS.getContainer(NBT_DISPLAY).setField(COLOR, (holder, reader) -> {
			if (holder instanceof LeatherArmorMeta) {
				((LeatherArmorMeta) holder).setColor(new Color(reader.readIntTag()));
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	private Color color;
	
	public CoreLeatherArmorMeta(Material material) {
		super(material);
	}
	
	@Override
	public CoreLeatherArmorMeta clone() {
		return (CoreLeatherArmorMeta) super.clone();
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	protected boolean hasDisplayCompound() {
		return color != null || super.hasDisplayCompound();
	}
	
	@Override
	protected void writeDisplayCompound(NBTWriter writer, boolean systemData) throws IOException {
		super.writeDisplayCompound(writer, systemData);
		if (color != null) writer.writeIntTag(COLOR, color.asRGB());
	}
	
	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage) {
		if (!super.isSimilar(meta, ignoreDamage))
			return false;
		LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
		if (getColor() == armorMeta.getColor())
			return true;
		if (getColor() != null && !getColor().equals(armorMeta.getColor()))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}

}