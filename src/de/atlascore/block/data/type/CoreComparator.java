package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CorePowerable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Comparator;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreComparator extends CoreDirectional4Faces implements Comparator {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	MODE = CharKey.of("mode");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreDirectional4Faces.NBT_FIELDS);
		NBT_FIELDS.setField(MODE, (holder, reader) -> {
			if (holder instanceof Comparator)
			((Comparator) holder).setMode(Mode.getByName(reader.readStringTag()));
			else reader.skipTag();
		});
	}
	
	private Mode mode;
	private boolean powered;
	
	public CoreComparator(Material material) {
		super(material);
		mode = Mode.COMPARE;
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
	public Mode getMode() {
		return mode;
	}

	@Override
	public void setMode(Mode mode) {
		if (mode == null) throw new IllegalArgumentException("Mode can not be null!");
		this.mode = mode;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(powered?0:1)+
				mode.ordinal()*2+
				getFaceValue()*4;
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getMode() != Mode.COMPARE) writer.writeStringTag(MODE, getMode().name().toLowerCase());
		if (isPowered()) writer.writeByteTag(CorePowerable.POWERED, true);
	}

}
