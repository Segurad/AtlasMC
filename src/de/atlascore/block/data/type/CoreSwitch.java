package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CoreFaceAttachable;
import de.atlascore.block.data.CorePowerable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Switch;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSwitch extends CoreDirectional4Faces implements Switch {
	
	private AttachedFace face;
	private boolean powered;
	
	public CoreSwitch(Material material) {
		super(material);
		face = AttachedFace.WALL;
	}

	@Override
	public AttachedFace getAttachedFace() {
		return face;
	}

	@Override
	public void setAttachedFace(AttachedFace face) {
		if (face == null) throw new IllegalArgumentException("AttachedFace can not be null!");
		this.face = face;
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
		return getMaterial().getBlockID()+
				(powered?0:1)+
				getFaceValue()*2+
				face.ordinal()*8;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isPowered()) writer.writeByteTag(CorePowerable.POWERED, true);
		if (getAttachedFace() != AttachedFace.WALL) writer.writeStringTag(CoreFaceAttachable.FACE, getAttachedFace().name().toLowerCase());
	}

}
