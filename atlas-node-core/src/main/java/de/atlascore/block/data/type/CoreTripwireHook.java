package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreAttachable;
import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CorePowerable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.TripwireHook;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTripwireHook extends CoreDirectional4Faces implements TripwireHook {

	private boolean powered, attached;
	
	public CoreTripwireHook(Material material) {
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
	public boolean isAttached() {
		return attached;
	}

	@Override
	public void setAttached(boolean attached) {
		this.attached = attached;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				(powered?0:1)+
				getFaceValue()*2+
				(attached?0:8);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isPowered()) writer.writeByteTag(CorePowerable.NBT_POWERED, true);
		if (isAttached()) writer.writeByteTag(CoreAttachable.NBT_ATTACHED, true);
	}

}
