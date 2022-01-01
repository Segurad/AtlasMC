package de.atlascore.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.Vector;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataContainer;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.CustomTagContainer;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreEntity extends AbstractNBTBase implements Entity {
	
	protected static final NBTFieldContainer NBT_FIELDS = new NBTFieldContainer();
	private CustomTagContainer customTags;
	protected final MetaDataContainer metaContainer;
	private int id;
	private final EntityType type;
	private UUID uuid;
	private short air;
	private float fallDistance;
	private short fire;
	private boolean glowing;
	private boolean invulnerable;
	private Vector motion;
	private boolean teleported;
	private List<Entity> passengers;
	private int portalCooldown;
	private final Location loc, oldLoc;
	private List<String> scoreboardTags;
	
	/**
	 * Flags contains the following Data
	 * Bit mask | Meaning
	 * 0x01		| on fire
	 * 0x02		| on ground
	 * 0x04 	| unused
	 * 0x08		| sprinting
	 * 0x10		| swimming
	 * 0x20		| invisible
	 * 0x40		| glowing
	 * 0x80		| flying elytra
	 */
	protected static final int
	META_BASE_FLAGS = 0,
	META_AIR_TICKS = 1,
	META_CUSTOM_NAME = 2,
	META_CUSTOM_NAME_VISIBLE = 3,
	META_IS_SILENT = 4,
	META_HAS_NO_GRAVITY = 5,
	META_POSE = 6;
	
	protected static final String
	AIR = "Air",
	CUSTOM_NAME = "CustomName",
	CUSTOM_NAME_VISIBLE = "CustomNameVisible",
	DIMENSION = "Dimension",
	FALL_DISTANCE = "FallDistance",
	FIRE = "Fire",
	GLOWING = "Glowing",
	ID = "id",
	INVULNERABLE = "Invulnerable",
	MOTION = "Motion", 
	NO_GRAVITY = "NoGravity",
	ON_GROUND = "OnGround",
	PASSENGERS = "Passengers",
	PORTAL_COOLDOWN = "PortalCooldown",
	POS = "Pos",
	ROTATION = "Rotation",
	SILENT = "Silent",
	TAGS = "Tags",
	UUID = "UUID";
	
	static {
		NBT_FIELDS.setField(AIR, (holder, reader) -> {
			((Entity) holder).setAirTicks(reader.readShortTag());
		});
		NBT_FIELDS.setField(CUSTOM_NAME, (holder, reader) -> {
			((Entity) holder).setCustomName(ChatUtil.toChat(reader.readStringTag()));
		});
		NBT_FIELDS.setField(CUSTOM_NAME_VISIBLE, (holder, reader) -> {
			((Entity) holder).setCustomNameVisible(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(DIMENSION, NBTField.SKIP);
		NBT_FIELDS.setField(FALL_DISTANCE, (holder, reader) -> {
			((Entity) holder).setFallDistance(reader.readFloatTag());
		});
		NBT_FIELDS.setField(FIRE, (holder, reader) -> {
			((Entity) holder).setFireTicks(reader.readShortTag());
		});
		NBT_FIELDS.setField(GLOWING, (holder, reader) -> {
			((Entity) holder).setGlowing(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(ID, NBTField.SKIP); // TODO skipped
		NBT_FIELDS.setField(INVULNERABLE, (holder, reader) -> {
			((Entity) holder).setInvulnerable(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(MOTION, (holder, reader) -> {
			double x = reader.readDoubleTag();
			double y = reader.readDoubleTag();
			double z = reader.readDoubleTag();
			((Entity) holder).setVelocity(x, y, z);
		});
		NBT_FIELDS.setField(NO_GRAVITY, (holder, reader) -> {
			((Entity) holder).setGravity(reader.readByteTag() == 0);
		});
		NBT_FIELDS.setField(ON_GROUND, (holder, reader) -> {
			((Entity) holder).setOnGround(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(PASSENGERS, NBTField.SKIP); // TODO nbt load passenger
		NBT_FIELDS.setField(PORTAL_COOLDOWN, (holder, reader) -> {
			((Entity) holder).setPortalCooldown(reader.readIntTag());
		});
		NBT_FIELDS.setField(POS, (holder, reader) -> {
			double x = reader.readDoubleTag();
			double y = reader.readDoubleTag();
			double z = reader.readDoubleTag();
			((Entity) holder).teleport(x, y, z);
		});
		NBT_FIELDS.setField(ROTATION, (holder, reader) -> {
			float yaw = reader.readFloatTag();
			float pitch = reader.readFloatTag();
			Entity ent = (Entity) holder;
			ent.teleport(ent.getX(), ent.getY(), ent.getZ(), yaw, pitch);
		});
		NBT_FIELDS.setField(SILENT, (holder, reader) -> {
			((Entity) holder).setSilent(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(TAGS, (holder, reader) -> {
			Entity ent = (Entity) holder;
			while(reader.getRestPayload() > 0) {
				ent.addScoreboardTag(reader.readStringTag());
			}
		});
		NBT_FIELDS.setField(UUID, NBTField.SKIP);
	}
	
	/**
	 * Creates a new CoreEntity
	 * @param id the unique entityID of this entity or -1 if not or later defined (entities with a id of -1 are not send to the player)
	 * @param type the EntityType of this Entity
	 * @param loc the Location of this Entity (the Location will be copied)
	 * @param uuid the UUID of this entity
	 */
	public CoreEntity(int id, EntityType type, Location loc, UUID uuid) {
		this.id = id;
		this.type = type;
		this.uuid = uuid;
		this.loc = new Location(loc);
		this.oldLoc = new Location(loc);
		this.motion = new Vector(0, 0, 0);
		metaContainer = new MetaDataContainer(getMetaContainerSize());
		initMetaContainer();
	}

	@Override
	public boolean isOnFire() {
		return fire > 0;
	}

	@Override
	public boolean isCrouching() {
		// TODO Auto-generated method 
		return false;
	}

	@Override
	public boolean isSprinting() {
		return (metaContainer.getData(META_BASE_FLAGS, MetaDataType.BYTE) & 0x08) == 0x08;
	}

	@Override
	public boolean isSwimming() {
		return (metaContainer.getData(META_BASE_FLAGS, MetaDataType.BYTE) & 0x10) == 0x10;
	}

	@Override
	public boolean isInvisble() {
		return (metaContainer.getData(META_BASE_FLAGS, MetaDataType.BYTE) & 0x20) == 0x20;
	}

	@Override
	public boolean isGlowing() {
		return glowing;
	}

	@Override
	public boolean isFlyingWithElytra() {
		return (metaContainer.getData(META_BASE_FLAGS, MetaDataType.BYTE) & 0x80) == 0x80;
	}

	@Override
	public int getAirTicks() {
		return air;
	}

	@Override
	public ChatComponent getCustomName() {
		return metaContainer.getData(META_CUSTOM_NAME, MetaDataType.OPT_CHAT);
	}

	@Override
	public boolean isSilent() {
		return metaContainer.getData(META_IS_SILENT, MetaDataType.BOOLEAN);
	}

	@Override
	public boolean isCustomNameVisible() {
		return metaContainer.getData(META_CUSTOM_NAME_VISIBLE, MetaDataType.BOOLEAN);
	}

	@Override
	public boolean hasGravity() {
		return !metaContainer.getData(META_HAS_NO_GRAVITY, MetaDataType.BOOLEAN);
	}

	@Override
	public Pose getPose() {
		return metaContainer.getData(META_POSE, MetaDataType.POSE);
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCustomNameVisible(boolean value) {
		metaContainer.get(META_CUSTOM_NAME_VISIBLE, MetaDataType.BOOLEAN).setData(value);
	}

	@Override
	public void setCustomName(ChatComponent name) {
		metaContainer.get(META_CUSTOM_NAME, MetaDataType.OPT_CHAT).setData(name);
	}

	@Override
	public Location getLocation() {
		return loc.clone();
	}

	@Override
	public Location getLocation(Location loc) {
		return this.loc.copyTo(loc);
	}

	@Override
	public SimpleLocation getLocation(SimpleLocation loc) {
		return this.loc.copyTo(loc);
	}

	@Override
	public World getWorld() {
		return loc.getWorld();
	}

	@Override
	public LocalServer getServer() {
		return loc.getWorld().getServer();
	}

	@Override
	public EntityType getType() {
		return type;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public Vector getVelocity() {
		return motion.clone();
	}

	@Override
	public Vector getVelocity(Vector vec) {
		vec.setX(motion.getX());
		vec.setY(motion.getY());
		vec.setZ(motion.getZ());
		return vec;
	}

	@Override
	public boolean hasVelocity() {
		return motion.getX() != 0 || motion.getY() != 0 || motion.getZ() != 0;
	}

	@Override
	public double getX() {
		return loc.getX();
	}

	@Override
	public double getY() {
		return loc.getY();
	}

	@Override
	public double getZ() {
		return loc.getZ();
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeShortTag(AIR, air);
		if (hasCustomName()) writer.writeStringTag(CUSTOM_NAME, getCustomName().getJsonText());
		// TODO toNBT
	}

	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public CustomTagContainer getCustomTagContainer() {
		if (customTags == null) customTags = new CustomTagContainer();
		return customTags;
	}

	@Override
	public short getFireTicks() {
		return fire;
	}

	@Override
	public void setFireTicks(int ticks) {
		this.fire = (short) ticks;
	}

	@Override
	public void setGlowing(boolean glowing) {
		this.glowing = glowing;
	}

	@Override
	public float getFallDistance() {
		return fallDistance;
	}

	@Override
	public void setFallDistance(float distance) {
		fallDistance = distance;
	}

	/**
	 * Init all MetaData fields of an Entity
	 */
	protected void initMetaContainer() {
		metaContainer.set(new MetaData<Byte>(META_BASE_FLAGS, MetaDataType.BYTE, (byte) 0));
		metaContainer.set(new MetaData<Integer>(META_AIR_TICKS, MetaDataType.INT, 300));
		metaContainer.set(new MetaData<ChatComponent>(META_CUSTOM_NAME, MetaDataType.OPT_CHAT));
		metaContainer.set(new MetaData<Boolean>(META_CUSTOM_NAME_VISIBLE, MetaDataType.BOOLEAN, false));
		metaContainer.set(new MetaData<Boolean>(META_IS_SILENT, MetaDataType.BOOLEAN, false));
		metaContainer.set(new MetaData<Boolean>(META_HAS_NO_GRAVITY, MetaDataType.BOOLEAN, false));
		metaContainer.set(new MetaData<Pose>(META_POSE, MetaDataType.POSE, Pose.STANDING));
	}
	
	protected int getMetaContainerSize() {
		return 6;
	}

	@Override
	public void setPose(Pose pose) {
		@SuppressWarnings("unchecked")
		MetaData<Pose> data = (MetaData<Pose>) metaContainer.get(META_POSE);
		data.setData(pose);
	}

	@Override
	public void setAirTicks(int air) {
		this.air = (short) air;
	}

	@Override
	public void setInvulnerable(boolean invulnerable) {
		this.invulnerable = invulnerable;
	}

	@Override
	public void teleport(double x, double y, double z) {
		teleported = true;
		loc.copyTo(oldLoc);
		loc.setLocation(x, y, z);
	}

	@Override
	public void teleport(double x, double y, double z, float yaw, float pitch) {
		teleported = true;
		loc.copyTo(oldLoc);
		loc.setLocation(x, y, z, yaw, pitch);
	}

	@Override
	public void setVelocity(double x, double y, double z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGravity(boolean gravity) {
		metaContainer.get(META_HAS_NO_GRAVITY, MetaDataType.BOOLEAN).setData(!gravity);
	}

	@Override
	public void setOnGround(boolean onGorund) {
		MetaData<Byte> meta = metaContainer.get(META_BASE_FLAGS, MetaDataType.BYTE);
		meta.setData((byte) (meta.getData() & 0x02));
	}

	@Override
	public void setPortalCooldown(int cooldown) {
		this.portalCooldown = cooldown;
	}

	@Override
	public void setSilent(boolean silent) {
		metaContainer.get(META_IS_SILENT, MetaDataType.BOOLEAN).setData(silent);
	}

	@Override
	public void addScoreboardTag(String tag) {
		getScoreboardTags().add(tag);
	}

	@Override
	public void setID(int id) {
		this.id = id;
	}

	@Override
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public List<String> getScoreboardTags() {
		if (scoreboardTags == null) scoreboardTags = new ArrayList<String>();
		return scoreboardTags;
	}

	@Override
	public boolean hasScoreboardTags() {
		return scoreboardTags != null && !scoreboardTags.isEmpty();
	}

	@Override
	public boolean hasCustomName() {
		return metaContainer.get(META_CUSTOM_NAME).hasData();
	}

	@Override
	public boolean isInvulnerable() {
		return invulnerable;
	}
}
