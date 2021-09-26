package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.TNT;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTNT extends CoreBlockData implements TNT {

	protected static final String
	UNSTABLE = "unstable";
	
	static {
		NBT_FIELDS.setField(UNSTABLE, (holder, reader) -> {
			if (holder instanceof TNT)
			((TNT) holder).setUnstable(reader.readByteTag() == 1);
			else reader.skipTag();
		});
	}
	
	private boolean unstable;
	
	public CoreTNT(Material material) {
		super(material);
	}

	@Override
	public boolean isUnstable() {
		return unstable;
	}

	@Override
	public void setUnstable(boolean stable) {
		this.unstable = stable;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(unstable?0:1);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isUnstable()) writer.writeByteTag(UNSTABLE, true);
	}

}
