package de.atlascore.block.data;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.data.Rail;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreRail extends CoreWaterlogged implements Rail {

	protected static final NBTFieldContainer<CoreRail> NBT_FIELDS;
	
	protected static final CharKey
	NBT_SHAPE = CharKey.literal("shape");
	
	static {
		NBT_FIELDS = CoreBlockData.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_SHAPE, (holder, reader) -> {
			holder.setShape(Shape.getByName(reader.readStringTag()));
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
		return super.getStateID()+shape.ordinal()*2;
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreRail> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeStringTag(NBT_SHAPE, getShape().name().toLowerCase());
	}

}
