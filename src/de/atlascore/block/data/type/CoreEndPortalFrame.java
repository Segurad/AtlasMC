package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.EndPortalFrame;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreEndPortalFrame extends CoreDirectional4Faces implements EndPortalFrame {

	private boolean eye;
	
	protected static final String EYE = "eye";
	
	static {
		NBT_FIELDS.setField(EYE, (holder, reader) -> {
			if (EndPortalFrame.class.isInstance(holder)) {
				((EndPortalFrame) holder).setEye(reader.readByteTag() == 1);
			} else reader.skipNBT();
		});
	}
	
	public CoreEndPortalFrame(Material material) {
		super(material);
	}

	@Override
	public boolean hasEye() {
		return eye;
	}

	@Override
	public void setEye(boolean eye) {
		this.eye = eye;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(eye?0:1)+
				getFaceValue()*2;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeByteTag(EYE, eye);
	}

}
