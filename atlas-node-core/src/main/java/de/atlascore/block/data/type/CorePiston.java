package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Piston;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CorePiston extends CoreDirectional6Faces implements Piston {

	protected static final CharKey
	EXTENDED = CharKey.literal("extended");
	
	static {
		NBT_FIELDS.setField(EXTENDED, (holder, reader) -> {
			if (holder instanceof Piston)
			((Piston) holder).setExtended(reader.readByteTag() == 1);
			else reader.skipTag();
		});
	}
	
	private boolean extended;
	
	public CorePiston(Material material) {
		super(material);
	}

	@Override
	public boolean isExtended() {
		return extended;
	}

	@Override
	public void setExtended(boolean extended) {
		this.extended = extended;
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+getFaceValue()+(extended?0:6);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isExtended()) writer.writeByteTag(EXTENDED, true);
	}

}
