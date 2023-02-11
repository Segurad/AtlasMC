package de.atlascore.block.data;

import java.io.IOException;
import java.util.Set;

import de.atlasmc.Axis;
import de.atlasmc.Material;
import de.atlasmc.block.data.Orientable;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreOrientable extends CoreBlockData implements Orientable {

	private static final Set<Axis> ALLOWED_AXIS =
			Set.of(Axis.X,
					Axis.Y,
					Axis.Z);
	
	protected static final CharKey
	AXIS = CharKey.of("axis");
	
	static {
		NBT_FIELDS.setField(AXIS, (holder, reader) -> {
			if (holder instanceof Orientable)
			((Orientable) holder).setAxis(Axis.getByName(reader.readStringTag()));
			else reader.skipTag();
		});
	}
	
	private Axis axis;
	
	public CoreOrientable(Material material) {
		this(material, Axis.Y);
	}
	
	public CoreOrientable(Material material, Axis axis) {
		super(material);
		setAxis(axis);
	}

	@Override
	public Set<Axis> getAxes() {
		return ALLOWED_AXIS;
	}

	@Override
	public Axis getAxis() {
		return axis;
	}

	@Override
	public void setAxis(Axis axis) {
		if (axis == null)
			throw new IllegalArgumentException("Axis can not be null!");
		if (!getAxes().contains(axis))
			throw new IllegalArgumentException("Axis is not valid: " + axis.name());
		this.axis = axis;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+axis.ordinal();
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeStringTag(AXIS, getAxis().name().toLowerCase());
	}

}
