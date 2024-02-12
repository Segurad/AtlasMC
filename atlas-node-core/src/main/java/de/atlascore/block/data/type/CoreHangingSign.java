package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreAttachable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.HangingSign;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreHangingSign extends CoreSign implements HangingSign {

	private boolean attached;
	
	public CoreHangingSign(Material material) {
		super(material);
	}

	@Override
	public boolean isAttached() {
		return attached;
	}

	@Override
	public void setAttached(boolean attached) {
		this.attached = attached;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(attached?0:32);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (attached)
			writer.writeByteTag(CoreAttachable.NBT_ATTACHED, true);
	}

}
