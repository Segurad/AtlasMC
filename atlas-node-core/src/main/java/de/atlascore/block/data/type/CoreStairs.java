package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBisected;
import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Stairs;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreStairs extends CoreDirectional4Faces implements Stairs {

	protected static final NBTFieldContainer<CoreStairs> NBT_FIELDS;
	
	protected static final CharKey
	SHAPE = CharKey.literal("shape");
	
	static {
		NBT_FIELDS = CoreDirectional4Faces.NBT_FIELDS.fork();
		NBT_FIELDS.setField(SHAPE, (holder, reader) -> {
			holder.setShape(Shape.getByName(reader.readStringTag()));
		});
	}
	
	private Half half;
	private boolean waterlogged;
	private Shape shape;
	
	public CoreStairs(Material material) {
		super(material);
		this.half = Half.BOTTOM;
		this.shape = Shape.STRAIGHT;
	}

	@Override
	public Half getHalf() {
		return half;
	}

	@Override
	public void setHalf(Half half) {
		if (half == null) 
			throw new IllegalArgumentException("Half can not be null!");
		if (half == Half.LOWER || half == Half.UPPER)
			throw new IllegalArgumentException("Invalid half: " + half.name());
		this.half = half;
	}

	@Override
	public boolean isWaterlogged() {
		return waterlogged;
	}

	@Override
	public void setWaterlogged(boolean waterlogged) {
		this.waterlogged = waterlogged;
	}

	@Override
	public Shape getShape() {
		return shape;
	}

	@Override
	public void setShape(Shape shape) {
		if (shape == null) 
			throw new IllegalArgumentException("Shape can not be null!");
		this.shape = shape;
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				(waterlogged?0:1)+
				shape.ordinal()*2+
				half.getID()*10+
				getFaceValue()*20;
	}

	@Override
	protected NBTFieldContainer<? extends CoreStairs> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isWaterlogged()) 
			writer.writeByteTag(CoreWaterlogged.NBT_WATERLOGGED, true);
		if (getHalf() != Half.BOTTOM) 
			writer.writeStringTag(CoreBisected.NBT_HALF, half.getNameID());
		if (getShape() != Shape.STRAIGHT) 
			writer.writeStringTag(SHAPE, getShape().name().toLowerCase());
	}
	
}
