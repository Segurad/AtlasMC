package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBisected;
import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CoreOpenable;
import de.atlascore.block.data.CorePowerable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Door;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreDoor extends CoreDirectional4Faces implements Door {
	
	private Half half;
	private Hinge hinge;
	private boolean open, powered;
	
	protected static final String
	HINGE = "hinge";
	
	static {
		NBT_FIELDS.setField(HINGE, (holder, reader) -> {
			if (Door.class.isInstance(holder)) {
				((Door) holder).setHinge(Hinge.getByName(reader.readStringTag()));
			} else reader.skipTag();
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
		if (half == null) throw new IllegalArgumentException("Half can not be null!");
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
		if (hinge == null) throw new IllegalArgumentException("Hinge can not be null!");
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
		if (getHalf() != Half.BOTTOM) writer.writeStringTag(CoreBisected.HALF, getHalf().name().toLowerCase());
		if (isOpen()) writer.writeByteTag(CoreOpenable.OPEN, true);
		if (isPowered()) writer.writeByteTag(CorePowerable.POWERED, true);
		if (getHinge() != Hinge.LEFT) writer.writeStringTag(HINGE, getHinge().name().toLowerCase());
	}

}
