package de.atlascore.inventory.meta;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.SimpleLocation;
import de.atlasmc.inventory.meta.CompassMeta;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreCompassMeta extends CoreItemMeta implements CompassMeta {

	protected static final CharKey 
			LODESTONE_TRACKED = CharKey.of("LodestoneTracked"),
			LODESTONE_DIMENSION = CharKey.of("LodestoneDimension"),
			LODESTONE_POS = CharKey.of("LodestonePos"),
			POS_X = CharKey.of("X"),
			POS_Y = CharKey.of("Y"),
			POS_Z = CharKey.of("Z");
	
	static {
		NBT_FIELDS.setField(LODESTONE_TRACKED, (holder, reader) -> {
			if (holder instanceof CompassMeta) {
				((CompassMeta) holder).setLodestoneTracked(reader.readByteTag() == 1);
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(LODESTONE_DIMENSION, (holder, reader) -> {
			if (holder instanceof CompassMeta) {
				reader.readStringTag();
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(LODESTONE_POS, (holder, reader) -> {
			if (holder instanceof CompassMeta) {
				if (reader.getType() == TagType.COMPOUND) {
					reader.readNextEntry();
					int x = 0, y = 0, z = 0;
					for (int i = 0; i < 3;) {
						i++;
						final CharSequence field = reader.getFieldName();
						if (POS_X.equals(field)) {
							x = reader.readIntTag();
						} else if (POS_Y.equals(field)) {
							y = reader.readIntTag();
						} else if (POS_Z.equals(field)) {
							z = reader.readIntTag();
						} throw new NBTException("Unknown LodestonePos Field: " + reader.getFieldName());
					}
					if (reader.getType() != TagType.TAG_END)
						throw new NBTException("Error while reading Attriubte Field! Expected TAG_END but read: " + reader.getType().name());
					reader.readNextEntry();
					((CompassMeta) holder).setLodestone(new SimpleLocation(x, y, z));
				} else throw new NBTException("Error while reading LodestonePos Field! Expected COMPOUND but read: " + reader.getType().name());
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	private SimpleLocation loc;
	private boolean tracked;
	
	public CoreCompassMeta(Material material) {
		super(material);
	}

	@Override
	public SimpleLocation getLodestone() {
		return loc;
	}

	@Override
	public boolean hasLodestone() {
		return loc != null;
	}

	@Override
	public boolean isLodestoneTracked() {
		return tracked;
	}

	@Override
	public void setLodestone(SimpleLocation lodestone) {
		if (loc == null)
			loc = new SimpleLocation();
		this.loc.setLocation(lodestone);
	}

	@Override
	public void setLodestoneTracked(boolean tracked) {
		this.tracked = tracked;
	}
	
	@Override
	public CoreCompassMeta clone() {
		CoreCompassMeta clone = (CoreCompassMeta) super.clone();
		if (hasLodestone()) clone.setLodestone(getLodestone().clone());
		return clone;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isLodestoneTracked()) writer.writeByteTag(LODESTONE_TRACKED, true);
		if (hasLodestone()) {
			writer.writeCompoundTag(LODESTONE_POS);
			writer.writeIntTag(POS_X, loc.getBlockX());
			writer.writeIntTag(POS_Y, loc.getBlockY());
			writer.writeIntTag(POS_Z, loc.getBlockZ());
			writer.writeEndTag();
		}
	}
	
	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage) {
		if (!isSimilar(meta, ignoreDamage))
			return false;
		CompassMeta compassMeta = (CompassMeta) meta;
		if (isLodestoneTracked() != compassMeta.isLodestoneTracked())
			return false;
		if (hasLodestone() != compassMeta.hasLodestone())
			return false;
		if (hasLodestone() && !getLodestone().equals(compassMeta.getLodestone()))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((loc == null) ? 0 : loc.hashCode());
		result = prime * result + (tracked ? 1231 : 1237);
		return result;
	}

}
