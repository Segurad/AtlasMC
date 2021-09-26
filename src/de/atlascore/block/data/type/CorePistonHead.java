package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.type.PistonHead;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CorePistonHead extends CoreTechnicalPiston implements PistonHead {

	protected static final String
	SHORT = "short";
	
	static {
		NBT_FIELDS.setField(SHORT, (holder, reader) -> {
			if (holder instanceof PistonHead)
			((PistonHead) holder).setShort(reader.readByteTag() == 1);
			else reader.skipTag();
		});
	}
	
	private boolean _short;
	
	public CorePistonHead(Material material) {
		super(material);
	}

	@Override
	public boolean isShort() {
		return _short;
	}

	@Override
	public void setShort(boolean _short) {
		this._short = _short;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+getFaceValue()*4+getType().ordinal()+(_short?0:2);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isShort()) writer.writeByteTag(SHORT, true);
	}

}
