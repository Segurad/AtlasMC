package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Bell;

public class CoreBell extends CoreDirectional4Faces implements Bell {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, 
				BlockDataProperty.POWERED,
				BlockDataProperty.ATTACHED);
	}
	
	private boolean powered;
	private Attachment attachment;
	
	public CoreBell(BlockType type) {
		super(type);
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
		if (attachment == null) 
			throw new IllegalArgumentException("Attachment can not be null!");
		this.attachment = attachment;
	}
	
	@Override
	public int getStateID() {
		return type.getBlockStateID()+
				(powered?0:1)+
				getFaceValue()*2+
				attachment.ordinal()*8;
	}

	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
