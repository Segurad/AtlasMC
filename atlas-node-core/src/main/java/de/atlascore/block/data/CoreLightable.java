package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Lightable;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreLightable extends CoreBlockData implements Lightable {

	private boolean lit;
	
	public static final CharKey
	NBT_LIT = CharKey.literal("lit");
	
	static {
		NBT_FIELDS.setField(NBT_LIT, (holder, reader) -> {
			if (holder instanceof Lightable) {
				((Lightable) holder).setLit(reader.readByteTag() == 1);
			} else reader.skipTag();
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
	public int getStateID() {
		return getMaterial().getBlockStateID()+(lit?0:1);
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeByteTag(NBT_LIT, lit);
	}
	
}
