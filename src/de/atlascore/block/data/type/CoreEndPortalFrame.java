package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.EndPortalFrame;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreEndPortalFrame extends CoreDirectional4Faces implements EndPortalFrame {
	
	protected static final CharKey
	EYE = CharKey.of("eye");
	
	static {
		NBT_FIELDS.setField(EYE, (holder, reader) -> {
			if (EndPortalFrame.class.isInstance(holder)) {
				((EndPortalFrame) holder).setEye(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	private boolean eye;
	
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
				getFaceValue()+
				(eye?0:4);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasEye()) writer.writeByteTag(EYE, true);
	}

}
