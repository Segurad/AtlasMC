package de.atlascore.block.data.type;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import de.atlascore.block.data.CoreAbstractMultipleFacing;
import de.atlascore.block.data.CoreAttachable;
import de.atlascore.block.data.CorePowerable;
import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.type.Tripwire;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTripwire extends CoreAbstractMultipleFacing implements Tripwire {

	protected static final CharKey
	DISARMED = CharKey.of("disarmed");
	
	static {
		NBT_FIELDS.setField(DISARMED, (holder, reader) -> {
			if (holder instanceof Tripwire)
			((Tripwire) holder).setDisarmed(reader.readByteTag() == 1);
			else reader.skipTag();
		});
	}
	
	private boolean attached, powered, disarmed;
	
	public CoreTripwire(Material material) {
		super(material, 4);
	}

	@Override
	public boolean isAttached() {
		return attached;
	}

	@Override
	public void setAttached(boolean attached) {
		this.attached = attached;
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
	public boolean isDisarmed() {
		return disarmed;
	}

	@Override
	public void setDisarmed(boolean disarmed) {
		this.disarmed = disarmed;
	}

	@Override
	public Set<BlockFace> getAllowedFaces() {
		return EnumSet.range(BlockFace.NORTH, BlockFace.WEST);
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(hasFace(BlockFace.WEST)?0:1)+
				(hasFace(BlockFace.SOUTH)?0:2)+
				(powered?0:4)+
				(hasFace(BlockFace.NORTH)?0:8)+
				(hasFace(BlockFace.EAST)?0:16)+
				(disarmed?0:32)+
				(attached?0:64);
	}

	@Override
	public boolean isValid(BlockFace face) {
		if (face == null) throw new IllegalArgumentException("BlockFace can not be null!");
		return face.ordinal() < 4;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isAttached()) writer.writeByteTag(CoreAttachable.ATTACHED, true);
		if (isDisarmed()) writer.writeByteTag(DISARMED, true);
		if (isPowered()) writer.writeByteTag(CorePowerable.POWERED, true);
	}

}
