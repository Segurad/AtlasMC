package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Leaves;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreLeaves extends CoreBlockData implements Leaves {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	DISTANCE = "distance",
	PERSISTENT = "persistent";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreBlockData.NBT_FIELDS);
		NBT_FIELDS.setField(DISTANCE, (holder, reader) -> {
			if (holder instanceof Leaves)
			((Leaves) holder).setDistance(reader.readIntTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(PERSISTENT, (holder, reader) -> {
			if (holder instanceof Leaves)
			((Leaves) holder).setPersistent(reader.readByteTag() == 1);
			else reader.skipTag();
		});
	}
	
	private int distance;
	private boolean persistent;
	
	public CoreLeaves(Material material) {
		super(material);
		distance = 7;
	}

	@Override
	public int getDistance() {
		return distance;
	}

	@Override
	public boolean isPersistent() {
		return persistent;
	}

	@Override
	public void setDistance(int distance) {
		if (distance < 1 || distance > 7) throw new IllegalArgumentException("Distance is not between 1 and 7: " + distance);
		this.distance = distance;
	}

	@Override
	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (distance-1)*2+(persistent?0:1);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getDistance() > 1) writer.writeIntTag(DISTANCE, getDistance());
		if (isPersistent()) writer.writeByteTag(PERSISTENT, true);
	}

}
