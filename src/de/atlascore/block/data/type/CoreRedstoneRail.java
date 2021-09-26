package de.atlascore.block.data.type;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import de.atlascore.block.data.CorePowerable;
import de.atlascore.block.data.CoreRail;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.RedstoneRail;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreRedstoneRail extends CoreRail implements RedstoneRail {

	private boolean powered;
	
	public CoreRedstoneRail(Material material) {
		super(material);
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
	public void setShape(Shape shape) {
		if (shape.ordinal() > 6) throw new IllegalArgumentException("No valid Shape: " + shape.name());
		super.setShape(shape);
	}
	
	@Override
	public Set<Shape> getShapes() {
		return EnumSet.range(Shape.NORTH_SOUTH, Shape.ASCENTING_SOUTH);
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(powered?0:6);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isPowered()) writer.writeByteTag(CorePowerable.POWERED, true);
	}

}
