package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Leaves;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreLeaves extends CoreWaterlogged implements Leaves {

	protected static final NBTFieldContainer<CoreLeaves> NBT_FIELDS;
	
	protected static final CharKey
	DISTANCE = CharKey.literal("distance"),
	PERSISTENT = CharKey.literal("persistent");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreBlockData.NBT_FIELDS);
		NBT_FIELDS.setField(DISTANCE, (holder, reader) -> {
			holder.setDistance(reader.readIntTag());
		});
		NBT_FIELDS.setField(PERSISTENT, (holder, reader) -> {
			holder.setPersistent(reader.readByteTag() == 1);
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
		return super.getStateID() + (distance-1)*4+(persistent?0:2);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreLeaves> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getDistance() > 1) 
			writer.writeIntTag(DISTANCE, getDistance());
		if (isPersistent()) 
			writer.writeByteTag(PERSISTENT, true);
	}

}
