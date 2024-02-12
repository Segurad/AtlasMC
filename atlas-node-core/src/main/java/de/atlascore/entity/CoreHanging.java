package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Hanging;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreHanging extends CoreEntity implements Hanging {

	protected static final NBTFieldContainer<CorePainting> NBT_FIELDS;
	
	protected static final CharKey
//	NBT_TILE_X = "TileX", TODO unnecessary
//	NBT_TILE_Y = "TileY",
//	NBT_TILE_Z = "TileZ",
	NBT_FACE = CharKey.literal("Facing");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreEntity.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_FACE, (holder, reader) -> {
			switch (reader.readByteTag()) {
			case 0:
				holder.setFacingDirection(BlockFace.DOWN);
				break;
			case 1:
				holder.setFacingDirection(BlockFace.UP);
				break;
			case 3:
				holder.setFacingDirection(BlockFace.SOUTH);
				break;
			case 4:
				holder.setFacingDirection(BlockFace.WEST);
				break;
			case 2:
				holder.setFacingDirection(BlockFace.NORTH);
				break;
			case 5:
				holder.setFacingDirection(BlockFace.EAST);
				break;
			default:
				break;
			}
		});
	}
	
	private BlockFace face;
	
	public CoreHanging(EntityType type, UUID uuid) {
		super(type, uuid);
		face = BlockFace.SOUTH;
	}

	@Override
	public BlockFace getAttachedFace() {
		return face;
	}

	@Override
	public void setFacingDirection(BlockFace face) {
		if (face == null)
			throw new IllegalArgumentException("Face can not be null!");
		if (face.ordinal() > 5)
			throw new IllegalArgumentException("Face not compatible with Painting: " + face.name());
		if (this.face == face)
			return;
		this.face = face;
		loc.set(face.getYaw());
		loc.set(face.getPitch());
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreEntity> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (face != BlockFace.SOUTH) {
			switch(face) {
			case DOWN:
				writer.writeByteTag(NBT_FACE, 0);
				break;
			case UP:
				writer.writeByteTag(NBT_FACE, 1);
				break;
			case SOUTH:
				writer.writeByteTag(NBT_FACE, 3);
				break;
			case WEST:
				writer.writeByteTag(NBT_FACE, 4);
				break;
			case NORTH:
				writer.writeByteTag(NBT_FACE, 2);
				break;
			case EAST:
				writer.writeByteTag(NBT_FACE, 5);
				break;
			default:
				break;
			}
		}
	}

}
