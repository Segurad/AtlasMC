package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Bell;
import de.atlasmc.util.Validate;

public class CoreBell extends CoreDirectional4Faces implements Bell {

	private boolean powered;
	private Attachment attachment;
	
	public CoreBell(Material material) {
		super(material);
		attachment = Attachment.FLOOR;
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
	public Attachment getAttachment() {
		return attachment;
	}

	@Override
	public void setAttachment(Attachment attachment) {
		Validate.notNull(attachment, "Attachment can not be null!");
		this.attachment = attachment;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(powered?0:1)+
				getFaceValue()*2+
				attachment.ordinal()*8;
	}

}
