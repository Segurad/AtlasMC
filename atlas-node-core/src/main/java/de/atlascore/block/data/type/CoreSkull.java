package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CorePowerable;
import de.atlascore.block.data.CoreRotatable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Skull;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSkull extends CoreRotatable implements Skull {

	private boolean powered;
	
	public CoreSkull(Material material) {
		super(material);
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(powered?0:16);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isPowered())
			writer.writeByteTag(CorePowerable.NBT_POWERED, systemData);
	}

}
