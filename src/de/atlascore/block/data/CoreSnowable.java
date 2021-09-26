package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Snowable;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSnowable extends CoreBlockData implements Snowable {

	protected static final String
	SNWOY = "snowy";
	
	static {
		NBT_FIELDS.setField(SNWOY, (holder, reader) -> {
			if (holder instanceof Snowable)
			((Snowable) holder).setSnowy(reader.readByteTag() == 1);
			else reader.skipTag();
		});
	}
	
	private boolean snowy;
	
	public CoreSnowable(Material material) {
		super(material);
	}

	@Override
	public boolean isSnowy() {
		return snowy;
	}

	@Override
	public void setSnowy(boolean snowy) {
		this.snowy = snowy;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (snowy?0:1);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isSnowy()) writer.writeByteTag(SNWOY, true);
	}
	
}
