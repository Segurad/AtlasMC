package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;
import java.util.function.BiConsumer;

import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Painting;
import de.atlasmc.entity.Player;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutDestroyEntities;
import de.atlasmc.io.protocol.play.PacketOutSpawnPainting;
import de.atlasmc.util.ViewerSet;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CorePainting extends CoreEntity implements Painting {

	protected static final BiConsumer<Entity, Player>
		VIEWER_ADD_FUNCTION = (holder, viewer) -> {
			PlayerConnection con = viewer.getConnection();
			PacketOutSpawnPainting spawn = con.createPacket(PacketOutSpawnPainting.class);
			spawn.setEntity((Painting) holder);
			con.sendPacked(spawn);
			((CorePainting) holder).sendMetadata(viewer);
		};

	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	NBT_MOTIVE = CharKey.of("Motive"),
//	NBT_TILE_X = "TileX", TODO unnecessary
//	NBT_TILE_Y = "TileY",
//	NBT_TILE_Z = "TileZ",
	NBT_FACE = CharKey.of("Facing");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreEntity.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_MOTIVE, (holder, reader) -> {
			if (holder instanceof Painting) {
				((Painting) holder).setMotive(Motive.getByNameID(reader.readStringTag()));
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_FACE, (holder, reader) -> {
			if (holder instanceof Painting) {
				switch (reader.readByteTag()) {
				case 0:
					((Painting) holder).setFacingDirection(BlockFace.SOUTH);
					break;
				case 1:
					((Painting) holder).setFacingDirection(BlockFace.WEST);
					break;
				case 2:
					((Painting) holder).setFacingDirection(BlockFace.NORTH);
					break;
				case 3:
					((Painting) holder).setFacingDirection(BlockFace.EAST);
					break;
				default:
					break;
				}
			} else reader.skipTag();
		});
	}
		
	private BlockFace attachedFace;
	private Motive motive;
	private boolean changedFaceOrMotive;
	
	public CorePainting(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
		attachedFace = BlockFace.SOUTH;
		motive = Motive.KEBAB;
	}

	@Override
	protected ViewerSet<Entity, Player> createViewerSet() {
		return new ViewerSet<Entity, Player>(this, VIEWER_ADD_FUNCTION, VIEWER_REMOVE_FUNCTION);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public BlockFace getAttachedFace() {
		return attachedFace;
	}

	@Override
	public void setFacingDirection(BlockFace face) {
		if (face == null)
			throw new IllegalArgumentException("Face can not be null!");
		if (face.ordinal() > 3)
			throw new IllegalArgumentException("Face not compatible with Painting: " + face.name());
		if (attachedFace == face)
			return;
		this.attachedFace = face;
		this.changedFaceOrMotive = true;
	}

	@Override
	public Motive getMotive() {
		return motive;
	}

	@Override
	public void setMotive(Motive motive) {
		if (motive == null)
			throw new IllegalAccessError("Motive can not be null!");
		this.motive = motive;
		this.changedFaceOrMotive = true;
	}
	
	@Override
	public void spawn(int entityID, World world, double x, double y, double z, float pitch, float yaw) {
		changedFaceOrMotive = false;
		super.spawn(entityID, world, x, y, z, pitch, yaw);
	}
	
	@Override
	protected void update() {
		super.update();
		if (changedFaceOrMotive) {
			for (Player viewer : viewers) {
				PlayerConnection con = viewer.getConnection();
				PacketOutDestroyEntities destroy = con.createPacket(PacketOutDestroyEntities.class);
				destroy.setEntityID(getID());
				con.sendPacked(destroy);
				PacketOutSpawnPainting spawn = con.createPacket(PacketOutSpawnPainting.class);
				spawn.setEntity(this);
				con.sendPacked(spawn);
				sendMetadata(viewer);
			}
			changedFaceOrMotive = false;
		}
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeStringTag(NBT_MOTIVE, getMotive().getNameID());
		switch(getAttachedFace()) {
		case WEST:
			writer.writeByteTag(NBT_FACE, 1);
			break;
		case NORTH:
			writer.writeByteTag(NBT_FACE, 2);
			break;
		case EAST:
			writer.writeByteTag(NBT_FACE, 3);
			break;
		default:
			break;
		}
	}

}
