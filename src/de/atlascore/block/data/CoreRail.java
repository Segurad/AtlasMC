package de.atlascore.block.data;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.data.Rail;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreRail extends CoreBlockData implements Rail {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	SHAPE = "shape";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreBlockData.NBT_FIELDS);
		NBT_FIELDS.setField(SHAPE, (holder, reader) -> {
			if (holder instanceof Rail)
			((Rail) holder).setShape(Shape.getByName(reader.readStringTag()));
			else reader.skipTag();
		});
	}
	
	private Shape shape;
	
	public CoreRail(Material material) {
		super(material);
		shape = Shape.NORTH_SOUTH;
	}

	@Override
	public Shape getShape() {
		return shape;
	}

	@Override
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	@Override
	public Set<Shape> getShapes() {
		return EnumSet.allOf(Shape.class);
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+shape.ordinal();
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeStringTag(SHAPE, getShape().name().toLowerCase());
	}

}
