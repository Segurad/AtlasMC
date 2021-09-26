package de.atlascore.block.data;

import java.io.IOException;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.Directional;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class CoreAbstractDirectional extends CoreBlockData implements Directional {

	private BlockFace face;
	
	protected static final String FACING = "facing";
	
	static {
		NBT_FIELDS.setField(FACING, (holder, reader) -> {
			if (holder instanceof Directional) {
				BlockFace face = BlockFace.getByName(reader.readStringTag());
				((Directional) holder).setFacing(face);
			} else reader.skipTag();
		});
	}
	
	public CoreAbstractDirectional(Material material) {
		this(material, BlockFace.NORTH);
	}
	
	public CoreAbstractDirectional(Material material, BlockFace face) {
		super(material);
		this.face = face;
	}

	@Override
	public abstract Set<BlockFace> getFaces();

	@Override
	public BlockFace getFacing() {
		return face;
	}

	@Override
	public void setFacing(BlockFace face) {
		if (face == null) throw new IllegalArgumentException("BlockFace can not be null!");
		if (getFaceValue(face) == -1) throw new IllegalArgumentException("BlockFace is not valid: " + face.name());
		this.face = face;
	}
	
	@Override
	public abstract int getStateID();
	
	/**
	 * Returns the value of this BlockFace or -1 if invalid
	 * @param face the BlockFace
	 * @return a value or -1
	 */
	protected abstract int getFaceValue(BlockFace face);
	
	protected int getFaceValue() {
		return getFaceValue(face);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getFacing() != BlockFace.NORTH) writer.writeStringTag(FACING, face.name().toLowerCase());
	}

}
