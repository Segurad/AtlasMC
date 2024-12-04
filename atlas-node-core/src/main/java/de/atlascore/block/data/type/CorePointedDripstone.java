package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.PointedDripstone;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CorePointedDripstone extends CoreWaterlogged implements PointedDripstone {

	protected static NBTFieldContainer<CorePointedDripstone> NBT_FIELDS;
	
	private static final CharKey
	NBT_THICKNESS = CharKey.literal("thickness"),
	NBT_VERTICAL_DIRECTION = CharKey.literal("vertical_direction");
	
	static {
		NBT_FIELDS = CoreWaterlogged.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_THICKNESS, (holder, reader) -> {
			holder.setThickness(Thickness.getByNameID(reader.readStringTag()));
		});
		NBT_FIELDS.setField(NBT_VERTICAL_DIRECTION, (holder, reader) -> {
			holder.setDirection(VerticalDirection.getByNameID(reader.readStringTag()));
		});
	}
	
	private Thickness thickness;
	private VerticalDirection direction;
	
	public CorePointedDripstone(Material material) {
		super(material);
		thickness = Thickness.TIP;
		direction = VerticalDirection.UP;
	}

	@Override
	public Thickness getThickness() {
		return thickness;
	}

	@Override
	public void setThickness(Thickness thickness) {
		if (thickness == null)
			throw new IllegalArgumentException("Thickness can not be null!");
		this.thickness = thickness;
	}

	@Override
	public VerticalDirection getDirection() {
		return direction;
	}

	@Override
	public void setDirection(VerticalDirection direction) {
		if (direction == null)
			throw new IllegalArgumentException("Direction can not be null!");
		this.direction = direction;
	}

	@Override
	public int getStateID() {
		return super.getStateID() + direction.ordinal()*2 + thickness.ordinal()*4;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (thickness != Thickness.TIP)
			writer.writeStringTag(NBT_THICKNESS, thickness.getNameID());
		if (direction != VerticalDirection.UP)
			writer.writeStringTag(NBT_VERTICAL_DIRECTION, direction.getNameID());
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreBlockData> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public CorePointedDripstone clone() {
		return (CorePointedDripstone) super.clone();
	}

}
