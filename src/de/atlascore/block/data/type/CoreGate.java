package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CoreOpenable;
import de.atlascore.block.data.CorePowerable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Gate;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreGate extends CoreDirectional4Faces implements Gate {

	protected static final CharKey
	IN_WALL = CharKey.of("in_wall");
	
	static {
		NBT_FIELDS.setField(IN_WALL, (holder, reader) -> {
			if (holder instanceof Gate)
			((Gate) holder).setInWall(reader.readByteTag() == 1);
			else reader.skipTag();
		});
	}
	
	private boolean open, powered, inWall;
	
	public CoreGate(Material material) {
		super(material);
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public void setOpen(boolean open) {
		this.open = open;
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
	public boolean isInWall() {
		return inWall;
	}

	@Override
	public void setInWall(boolean inWall) {
		this.inWall = inWall;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(powered?0:1)+
				(open?0:2)+
				(inWall?0:4)+
				getFaceValue()*8;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isOpen()) writer.writeByteTag(CoreOpenable.OPEN, true);
		if (isPowered()) writer.writeByteTag(CorePowerable.POWERED, true);
		if (isInWall()) writer.writeByteTag(IN_WALL, true);
	}

}
