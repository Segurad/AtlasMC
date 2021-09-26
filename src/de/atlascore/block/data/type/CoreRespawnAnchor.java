package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.RespawnAnchor;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreRespawnAnchor extends CoreBlockData implements RespawnAnchor {

	protected static final String
	CHARGES = "charges";
	
	static {
		NBT_FIELDS.setField(CHARGES, (holder, reader) -> {
			if (holder instanceof RespawnAnchor)
			((RespawnAnchor) holder).setCharges(reader.readIntTag());
			else reader.skipTag();
		});
	}
	
	private int charges;
	
	public CoreRespawnAnchor(Material material) {
		super(material);
	}

	@Override
	public int getCharges() {
		return charges;
	}

	@Override
	public int getMaxCharges() {
		return 4;
	}

	@Override
	public void setCharges(int charges) {
		if (charges >  4 || charges < 0) throw new IllegalArgumentException("Charges is not between 0 and 4: " + charges);
		this.charges = charges;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+charges;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getCharges() > 0) writer.writeIntTag(CHARGES, getCharges());
	}

}
