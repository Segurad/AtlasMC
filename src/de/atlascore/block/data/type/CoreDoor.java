package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Door;
import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreDoor extends CoreDirectional4Faces implements Door {
	
	private Half half;
	private Hinge hinge;
	private boolean open, powered;
	
	protected static final String
	HALF = "half",
	OPEN = "open",
	POWERED = "powered",
	HINGE = "hinge";
	
	static {
		NBT_FIELDS.setField(HINGE, (holder, reader) -> {
			if (Door.class.isInstance(holder)) {
				((Door) holder).setHinge(Hinge.getByName(reader.readStringTag()));
			} else reader.skipNBT();
		});
	}
	
	public CoreDoor(Material material) {
		super(material);
		half = Half.BOTTOM;
		hinge = Hinge.LEFT;
	}

	@Override
	public Half getHalf() {
		return half;
	}

	@Override
	public void setHalf(Half half) {
		Validate.notNull(half, "Half can not be null!");
		this.half = half;
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
	public Hinge getHinge() {
		return hinge;
	}

	@Override
	public void setHinge(Hinge hinge) {
		Validate.notNull(hinge, "Hinge can not be null!");
		this.hinge = hinge;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(powered?0:1)+
				(open?0:2)+
				hinge.ordinal()*4+
				half.ordinal()*8+
				getFaceValue()*16;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeStringTag(HALF, half.name().toLowerCase());
		writer.writeByteTag(OPEN, open);
		writer.writeByteTag(POWERED, powered);
		writer.writeStringTag(HINGE, hinge.name().toLowerCase());
	}

}
