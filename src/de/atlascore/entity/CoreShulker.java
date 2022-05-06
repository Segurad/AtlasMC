package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.DyeColor;
import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Shulker;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.MathUtil;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreShulker extends CoreMob implements Shulker {

	protected static final MetaDataField<BlockFace>
	META_DIRECTION = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, BlockFace.DOWN, MetaDataType.DIRECTION);
	protected static final MetaDataField<Long>
	META_ATTACHMENT_POS = new MetaDataField<>(CoreMob.LAST_META_INDEX+2, null, MetaDataType.OPT_POSITION);
	protected static final MetaDataField<Byte>
	META_SHIELD_HEIGHT = new MetaDataField<>(CoreMob.LAST_META_INDEX+3, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Byte>
	META_COLOR = new MetaDataField<>(CoreMob.LAST_META_INDEX+4, (byte) DyeColor.PURPLE.getID(), MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+4;
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	NBT_ATTACHED_POS_X = CharKey.of("APX"),
	NBT_ATTACHED_POS_Y = CharKey.of("APY"),
	NBT_ATTACHED_POS_Z = CharKey.of("APZ"),
	NBT_ATTACHED_FACE = CharKey.of("AttachFace"),
	NBT_COLOR = CharKey.of("Color"),
	NBT_PEEK = CharKey.of("Peek");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreMob.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_ATTACHED_POS_X, (holder, reader) -> {
			((Shulker) holder).setAttachedX(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_ATTACHED_POS_Y, (holder, reader) -> {
			((Shulker) holder).setAttachedY(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_ATTACHED_POS_Z, (holder, reader) -> {
			((Shulker) holder).setAttachedZ(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_ATTACHED_FACE, (holder, reader) -> {
			int faceID = reader.readByteTag();
			BlockFace face = BlockFace.DOWN;
			switch (faceID) {				
			case 1:
				face = BlockFace.UP;
				break;
			case 2:
				face = BlockFace.NORTH;
				break;
			case 3:
				face = BlockFace.SOUTH;
				break;
			case 4:
				face = BlockFace.WEST;
				break;
			case 5:
				face = BlockFace.EAST;
				break;
			}
			((Shulker) holder).setAttachedFace(face);
		});
		NBT_FIELDS.setField(NBT_COLOR, (holder, reader) -> {
			((Shulker) holder).setColor(DyeColor.getByID(reader.readByteTag()));
		});
		NBT_FIELDS.setField(NBT_PEEK, (holder, reader) -> {
			((Shulker) holder).setShieldHeight(reader.readByteTag());
		});
	}
	
	private int apX, apY, apZ;
	private boolean changedAttachedPosition;
	
	public CoreShulker(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_DIRECTION);
		metaContainer.set(META_ATTACHMENT_POS);
		metaContainer.set(META_SHIELD_HEIGHT);
		metaContainer.set(META_COLOR);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public BlockFace getAttachedFace() {
		return metaContainer.getData(META_DIRECTION);
	}

	@Override
	public void setAttachedFace(BlockFace attached) {
		metaContainer.get(META_DIRECTION).setData(attached);
	}

	@Override
	public Location getAttachedPosition(Location loc) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		Long pos = metaContainer.getData(META_ATTACHMENT_POS);
		if (pos == null)
			return null;
		return loc.setLocation(getWorld(), apX, apY, apZ, 0, 0);
	}
	
	@Override
	public Location getAttachedPosition() {
		Long pos = metaContainer.getData(META_ATTACHMENT_POS);
		if (pos == null)
			return null;
		return new Location(getWorld(), apX, apY, apZ);
	}

	@Override
	public void setAttachedPosition(SimpleLocation loc) {
		if (loc == null) {
			metaContainer.get(META_ATTACHMENT_POS).setData(null);
			apX = apY = apZ = 0;
		} else {
			apX = loc.getBlockX();
			apY = loc.getBlockY();
			apZ = loc.getBlockZ();
			changedAttachedPosition = true;
		}
	}

	@Override
	public void setAttachedPosition(int x, int y, int z) {
		apX = x;
		apY = y;
		apZ = z;
		changedAttachedPosition = true;
	}

	@Override
	public int getShieldHeight() {
		return metaContainer.getData(META_SHIELD_HEIGHT);
	}

	@Override
	public void setShieldHeight(int height) {
		if (height < 0 || height > 100)
			throw new IllegalArgumentException("Height must be between 0 and 100: " + height);
		metaContainer.get(META_SHIELD_HEIGHT).setData((byte) height);
	}

	@Override
	public DyeColor getColor() {
		return DyeColor.getByID(metaContainer.getData(META_COLOR));
	}

	@Override
	public void setColor(DyeColor color) {
		if (color == null)
			throw new IllegalArgumentException("Color can not be null!");
		metaContainer.get(META_COLOR).setData((byte) color.getID());
	}

	@Override
	public int getAttachedX() {
		return apX;
	}

	@Override
	public int getAttachedY() {
		return apY;
	}

	@Override
	public int getAttachedZ() {
		return apZ;
	}

	@Override
	public boolean hasAttachedPosition() {
		return metaContainer.getData(META_ATTACHMENT_POS) != null;
	}

	@Override
	public void setAttachedX(int posX) {
		this.apX = posX;
		changedAttachedPosition = true;
	}

	@Override
	public void setAttachedY(int posY) {
		this.apY = posY;
		changedAttachedPosition = true;
	}

	@Override
	public void setAttachedZ(int posZ) {
		this.apZ = posZ;
		changedAttachedPosition = true;
	}

	@Override
	protected void prepUpdate() {
		super.prepUpdate();
		if (changedAttachedPosition) {
			changedAttachedPosition = false;
			metaContainer.get(META_ATTACHMENT_POS).setData(MathUtil.toPosition(apX, apY, apZ));	
		}
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasAttachedPosition()) {
			writer.writeIntTag(NBT_ATTACHED_POS_X, apX);
			writer.writeIntTag(NBT_ATTACHED_POS_Y, apY);
			writer.writeIntTag(NBT_ATTACHED_POS_Z, apZ);
		}
		switch (getAttachedFace()) {
		case DOWN:
			writer.writeByteTag(NBT_ATTACHED_FACE, 0);
			break;
		case UP:
			writer.writeByteTag(NBT_ATTACHED_FACE, 1);
			break;
		case NORTH:
			writer.writeByteTag(NBT_ATTACHED_FACE, 2);
			break;
		case SOUTH:
			writer.writeByteTag(NBT_ATTACHED_FACE, 3);
			break;
		case WEST:
			writer.writeByteTag(NBT_ATTACHED_FACE, 4);
			break;
		case EAST:
			writer.writeByteTag(NBT_ATTACHED_FACE, 5);
			break;
		default:
			break;
		}
		writer.writeByteTag(NBT_COLOR, getColor().getID());
		if (getShieldHeight() > 0)
			writer.writeByteTag(NBT_PEEK, getShieldHeight());
	}
	
}
