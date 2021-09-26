package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreAnaloguePowerable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.DaylightDetectore;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreDaylightDetector extends CoreAnaloguePowerable implements DaylightDetectore {

	protected static final String
	INVERTED = "inverted";
	
	static {
		NBT_FIELDS.setField(INVERTED, (holder, reader) -> {
			if (holder instanceof DaylightDetectore)
			((DaylightDetectore) holder).setInverted(reader.readByteTag() == 1);
			else reader.skipTag();
		});
	}
	
	private boolean inverted;
	
	public CoreDaylightDetector(Material material) {
		super(material);
	}

	@Override
	public boolean isInverted() {
		return inverted;
	}

	@Override
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				(inverted?0:16);
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isInverted()) writer.writeByteTag(INVERTED, true);
	}
}
