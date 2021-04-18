package de.atlascore.inventory.meta;

import java.io.IOException;

import de.atlasmc.Color;
import de.atlasmc.Material;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.LeatherArmorMeta;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreLeatherArmorMeta extends CoreDamageableMeta implements LeatherArmorMeta {

	private Color color;
	
	protected static final String COLOR = "color";
	
	static {
		NBT_FIELDS.getContainer(DISPLAY).setField(COLOR, (holder, reader) -> {
			if (LeatherArmorMeta.class.isInstance(holder)) {
				((LeatherArmorMeta) holder).setColor(new Color(reader.readIntTag()));
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
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
	protected void writeDisplayCompound(NBTWriter writer, String local, boolean systemData) throws IOException {
		super.writeDisplayCompound(writer, local, systemData);
		if (color != null) writer.writeIntTag(COLOR, color.asRGB());
	}

}