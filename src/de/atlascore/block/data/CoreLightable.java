package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Lightable;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreLightable extends CoreBlockData implements Lightable {

	private boolean lit;
	
	protected static final String LIT = "lit";
	
	static {
		NBT_FIELDS.setField(LIT, (holder, reader) -> {
			if (Lightable.class.isInstance(holder)) {
				((Lightable) holder).setLit(reader.readByteTag() == 1);
			} else reader.skipNBT();
		});
	}
	
	public CoreLightable(Material material) {
		super(material);
	}

	@Override
	public boolean isLit() {
		return lit;
	}

	@Override
	public void setLit(boolean lit) {
		this.lit = lit;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeByteTag(LIT, lit);
	}
	
}
