package de.atlascore.block.data.type;

import java.io.IOException;
import java.util.Set;

import de.atlascore.block.data.CoreAbstractDirectional;
import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.type.Hopper;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreHopper extends CoreAbstractDirectional implements Hopper {

	private static final Set<BlockFace> ALLOWED_FACES =
			Set.of(BlockFace.NORTH,
					BlockFace.EAST,
					BlockFace.SOUTH,
					BlockFace.WEST,
					BlockFace.DOWN);
	
	protected static final CharKey
	ENABLED = CharKey.of("enabled");
	
	static {
		NBT_FIELDS.setField(ENABLED, (holder, reader) -> {
			if (holder instanceof Hopper)
			((Hopper) holder).setEnabled(reader.readByteTag() == 1);
			else reader.skipTag();
		});
	}
	
	private boolean enabled;
	
	public CoreHopper(Material material) {
		super(material, BlockFace.DOWN);
		this.enabled = true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;		
	}

	@Override
	public Set<BlockFace> getFaces() {
		return ALLOWED_FACES;
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				getFaceValue()+
				(enabled?0:5);
	}

	@Override
	protected int getFaceValue(BlockFace face) {
		switch(face) {
		case DOWN: return 0;
		case NORTH: return 1;
		case SOUTH: return 2;
		case WEST: return 3;
		case EAST: return 4;
		default: return -1;
		}
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isEnabled()) writer.writeByteTag(ENABLED, true);
	}

}
