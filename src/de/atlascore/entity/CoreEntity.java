
package de.atlascore.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.Vector;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Player;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataContainer;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutDestroyEntities;
import de.atlasmc.io.protocol.play.PacketOutEntityMetadata;
import de.atlasmc.io.protocol.play.PacketOutEntityPositionAndRotation;
import de.atlasmc.io.protocol.play.PacketOutEntityRotation;
import de.atlasmc.io.protocol.play.PacketOutEntityTeleport;
import de.atlasmc.io.protocol.play.PacketOutSetPassengers;
import de.atlasmc.io.protocol.play.PacketOutSpawnEntity;
import de.atlasmc.util.MathUtil;
import de.atlasmc.util.ViewerSet;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.CustomTagContainer;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;

public class CoreEntity extends AbstractNBTBase implements Entity {
	
	protected static final BiConsumer<Entity, Player> 
		VIEWER_ADD_FUNCTION = (holder, viewer) -> {
			PlayerConnection con = viewer.getConnection();
			PacketOutSpawnEntity packet = new PacketOutSpawnEntity();
			packet.setEntity(holder);
			con.sendPacked(packet);
			((CoreEntity) holder).sendMetadata(viewer);
		},
		VIEWER_REMOVE_FUNCTION = (holder, viewer) -> {
			PlayerConnection con = viewer.getConnection();
			PacketOutDestroyEntities packet = new PacketOutDestroyEntities();
			packet.setEntityIDs(holder.getID());
			con.sendPacked(packet);
		};
	
	/**
	 * Flags contains the following Data<br>
	 * <table>
	 * <tr><th>Bit mask</th><th>Meaning</th></tr>
	 * <tr><td>0x01		</td><td>on fire		</td></tr>
	 * <tr><td>0x02		</td><td>on ground		</td></tr>
	 * <tr><td>0x08		</td><td>sprinting		</th></tr>
	 * <tr><td>0x10		</td><td>swimming		</td></tr>
	 * <tr><td>0x20		</td><td>invisible		</td></tr>
	 * <tr><td>0x40		</td><td>glowing		</td></tr>
	 * <tr><td>0x80		</td><td>flying elytra	</td></tr>
	 * </table>
	 */
	protected static final MetaDataField<Byte> META_ENTITY_FLAGS = new MetaDataField<>(0, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Integer> META_AIR_TICKS = new MetaDataField<>(1, 300, MetaDataType.INT);
	protected static final MetaDataField<Chat> META_CUSTOM_NAME = new MetaDataField<>(2, null, MetaDataType.OPT_CHAT); 
	protected static final MetaDataField<Boolean> META_CUSTOM_NAME_VISIBLE = new MetaDataField<>(3, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean> META_IS_SILENT = new MetaDataField<>(4, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean> META_HAS_NO_GRAVITY = new MetaDataField<>(5, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Pose> META_POSE = new MetaDataField<>(6, Pose.STANDING, MetaDataType.POSE);
	//protected static final MetaDataField<Integer> META_TICKS_FROZEN = new MetaDataField<>(7, 0, MetaDataType.INT); TODO for 1.17
	protected static final int LAST_META_INDEX = 6;
	
	protected static final NBTFieldContainer NBT_FIELDS = new NBTFieldContainer();
	
	protected static final CharKey
	NBT_AIR = CharKey.of("Air"),
	NBT_CUSTOM_NAME = CharKey.of("CustomName"),
	NBT_CUSTOM_NAME_VISIBLE = CharKey.of("CustomNameVisible"),
	NBT_DIMENSION = CharKey.of("Dimension"),
	NBT_FALL_DISTANCE = CharKey.of("FallDistance"),
	NBT_FIRE = CharKey.of("Fire"),
	NBT_GLOWING = CharKey.of("Glowing"),
	NBT_ID = CharKey.of("id"),
	NBT_INVULNERABLE = CharKey.of("Invulnerable"),
	NBT_MOTION = CharKey.of("Motion"), 
	NBT_NO_GRAVITY = CharKey.of("NoGravity"),
	NBT_ON_GROUND = CharKey.of("OnGround"),
	NBT_PASSENGERS = CharKey.of("Passengers"),
	NBT_PORTAL_COOLDOWN = CharKey.of("PortalCooldown"),
	NBT_POS = CharKey.of("Pos"),
	NBT_ROTATION = CharKey.of("Rotation"),
	NBT_SILENT = CharKey.of("Silent"),
	NBT_TAGS = CharKey.of("Tags"),
	NBT_UUID = CharKey.of("UUID");
	
	static {
		NBT_FIELDS.setField(NBT_AIR, (holder, reader) -> {
			((Entity) holder).setAirTicks(reader.readShortTag());
		});
		NBT_FIELDS.setField(NBT_CUSTOM_NAME, (holder, reader) -> {
			((Entity) holder).setCustomName(ChatUtil.toChat(reader.readStringTag()));
		});
		NBT_FIELDS.setField(NBT_CUSTOM_NAME_VISIBLE, (holder, reader) -> {
			((Entity) holder).setCustomNameVisible(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_DIMENSION, NBTField.SKIP);
		NBT_FIELDS.setField(NBT_FALL_DISTANCE, (holder, reader) -> {
			((Entity) holder).setFallDistance(reader.readFloatTag());
		});
		NBT_FIELDS.setField(NBT_FIRE, (holder, reader) -> {
			((Entity) holder).setFireTicks(reader.readShortTag());
		});
		NBT_FIELDS.setField(NBT_GLOWING, (holder, reader) -> {
			((Entity) holder).setGlowing(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_ID, NBTField.SKIP); // TODO skipped
		NBT_FIELDS.setField(NBT_INVULNERABLE, (holder, reader) -> {
			((Entity) holder).setInvulnerable(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_MOTION, (holder, reader) -> {
			double x = reader.readDoubleTag();
			double y = reader.readDoubleTag();
			double z = reader.readDoubleTag();
			((Entity) holder).setVelocity(x, y, z);
		});
		NBT_FIELDS.setField(NBT_NO_GRAVITY, (holder, reader) -> {
			((Entity) holder).setGravity(reader.readByteTag() == 0);
		});
		NBT_FIELDS.setField(NBT_ON_GROUND, (holder, reader) -> {
			((Entity) holder).setOnGround(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_PASSENGERS, NBTField.SKIP); // TODO nbt load passenger
		NBT_FIELDS.setField(NBT_PORTAL_COOLDOWN, (holder, reader) -> {
			((Entity) holder).setPortalCooldown(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_POS, (holder, reader) -> {
			double x = reader.readDoubleTag();
			double y = reader.readDoubleTag();
			double z = reader.readDoubleTag();
			((Entity) holder).teleport(x, y, z);
		});
		NBT_FIELDS.setField(NBT_ROTATION, (holder, reader) -> {
			float yaw = reader.readFloatTag();
			float pitch = reader.readFloatTag();
			Entity ent = (Entity) holder;
			ent.teleport(ent.getX(), ent.getY(), ent.getZ(), yaw, pitch);
		});
		NBT_FIELDS.setField(NBT_SILENT, (holder, reader) -> {
			((Entity) holder).setSilent(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_TAGS, (holder, reader) -> {
			Entity ent = (Entity) holder;
			while(reader.getRestPayload() > 0) {
				ent.addScoreboardTag(reader.readStringTag());
			}
		});
		NBT_FIELDS.setField(NBT_UUID, NBTField.SKIP);
	}
	
	private CustomTagContainer customTags;
	protected final MetaDataContainer metaContainer;
	private int id;
	private final EntityType type;
	protected final ViewerSet<Entity, Player> viewers;
	private UUID uuid;
	private short air;
	private float fallDistance;
	private short fire;
	private boolean glowing;
	private boolean invulnerable;
	private boolean removed;
	private volatile boolean aremoved;
	protected Vector motion;
	private List<Entity> passengers;
	private int portalCooldown;
	protected final Location loc;
	protected final Location oldLoc;
	private Chunk chunk;
	private List<String> scoreboardTags;
	
	// Update flags
	private boolean teleported;
	private boolean passengersChanged;
	
	/**
	 * Creates a new CoreEntity with id 0 and removed status.<br>
	 * Use {@link #spawn(int, World, double, double, double, float, float)} to spawn it in a {@link World}
	 * @param type the EntityType of this Entity
	 * @param loc the Location of this Entity (the Location will be copied)
	 * @param uuid the UUID of this entity
	 */
	public CoreEntity(EntityType type, UUID uuid, World world) {
		this.removed = true;
		this.aremoved = true;
		this.type = type;
		this.uuid = uuid;
		this.loc = new Location(world, 0, 0, 0);
		this.oldLoc = new Location(loc);
		this.motion = new Vector(0, 0, 0);
		this.viewers = createViewerSet();
		metaContainer = new MetaDataContainer(getMetaContainerSize());
		initMetaContainer();
	}
	
	protected ViewerSet<Entity, Player> createViewerSet() {
		return new ViewerSet<>(this, VIEWER_ADD_FUNCTION, VIEWER_REMOVE_FUNCTION);
	}

	@Override
	public boolean isOnFire() {
		return fire > 0;
	}

	@Override
	public boolean isSprinting() {
		return (metaContainer.getData(META_ENTITY_FLAGS) & 0x08) == 0x08;
	}

	@Override
	public boolean isSwimming() {
		return (metaContainer.getData(META_ENTITY_FLAGS) & 0x10) == 0x10;
	}

	@Override
	public boolean isInvisible() {
		return (metaContainer.getData(META_ENTITY_FLAGS) & 0x20) == 0x20;
	}

	@Override
	public void setInvisible(boolean invisible) {
		MetaData<Byte> data = this.metaContainer.get(META_ENTITY_FLAGS);
		if (invisible)
			data.setData((byte) (data.getData() | 0x20));
		else
			data.setData((byte) (data.getData() & 0xDF));
	}
	
	@Override
	public boolean isGlowing() {
		return glowing;
	}

	@Override
	public boolean isFlyingWithElytra() {
		return (metaContainer.getData(META_ENTITY_FLAGS) & 0x80) == 0x80;
	}

	@Override
	public int getAirTicks() {
		return air;
	}

	@Override
	public Chat getCustomName() {
		return metaContainer.getData(META_CUSTOM_NAME);
	}

	@Override
	public boolean isSilent() {
		return metaContainer.getData(META_IS_SILENT);
	}

	@Override
	public boolean isCustomNameVisible() {
		return metaContainer.getData(META_CUSTOM_NAME_VISIBLE);
	}

	@Override
	public boolean hasGravity() {
		return !metaContainer.getData(META_HAS_NO_GRAVITY);
	}

	@Override
	public Pose getPose() {
		return metaContainer.getData(META_POSE);
	}

	@Override
	public synchronized void remove() {
		if (removed)
			return;
		removed = true;
		if (chunk != null)
			chunk.removeEntity(this);
		chunk = null;
		if (passengers != null)
			passengers.clear();
		viewers.clear();
		aremoved = true;
		
		// TODO implement remove
	}

	@Override
	public void setCustomNameVisible(boolean value) {
		metaContainer.get(META_CUSTOM_NAME_VISIBLE).setData(value);
	}

	@Override
	public void setCustomName(Chat name) {
		metaContainer.get(META_CUSTOM_NAME).setData(name);
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
	public float getPitch() {
		return loc.getPitch();
	}

	@Override
	public float getYaw() {
		return loc.getYaw();
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(NBT_ID, type.getNamespacedName());
		writer.writeShortTag(NBT_AIR, air);
		if (hasCustomName()) 
			writer.writeStringTag(NBT_CUSTOM_NAME, getCustomName().getJsonText());
		if (isCustomNameVisible())
			writer.writeByteTag(NBT_CUSTOM_NAME_VISIBLE, true);
		writer.writeFloatTag(NBT_FALL_DISTANCE, fallDistance);
		writer.writeShortTag(NBT_FIRE, fire);
		if (isGlowing())
			writer.writeByteTag(NBT_GLOWING, true);
		writer.writeByteTag(NBT_INVULNERABLE, isInvulnerable());
		writer.writeListTag(NBT_MOTION, TagType.DOUBLE, 3);
			writer.writeDoubleTag(null, motion.getX());
			writer.writeDoubleTag(null, motion.getY());
			writer.writeDoubleTag(null, motion.getZ());
		if (!hasGravity())
			writer.writeByteTag(NBT_NO_GRAVITY, true);
		writer.writeByteTag(NBT_ON_GROUND, isOnGround());
		writer.writeIntTag(NBT_PORTAL_COOLDOWN, portalCooldown);
		writer.writeListTag(NBT_POS, TagType.DOUBLE, 3);
			writer.writeDoubleTag(null, loc.getX());
			writer.writeDoubleTag(null, loc.getY());
			writer.writeDoubleTag(null, loc.getZ());
		writer.writeListTag(NBT_ROTATION, TagType.FLOAT, 2);
			writer.writeFloatTag(null, loc.getYaw());
			writer.writeFloatTag(null, loc.getPitch());
		if (isSilent())
			writer.writeByteTag(NBT_SILENT, true);
		if (hasScoreboardTags())
			writer.writeListTag(NBT_TAGS, TagType.STRING, scoreboardTags.size());
			for (String tag : scoreboardTags)
				writer.writeStringTag(null, tag);
		writer.writeUUID(NBT_CUSTOM_NAME, uuid);
	}

	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public CustomTagContainer getCustomTagContainer() {
		if (customTags == null) 
			customTags = new CustomTagContainer();
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
		metaContainer.set(META_ENTITY_FLAGS);
		metaContainer.set(META_AIR_TICKS);
		metaContainer.set(META_CUSTOM_NAME);
		metaContainer.set(META_CUSTOM_NAME_VISIBLE);
		metaContainer.set(META_IS_SILENT);
		metaContainer.set(META_HAS_NO_GRAVITY);
		metaContainer.set(META_POSE);
	}
	
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public void setPose(Pose pose) {
		metaContainer.get(META_POSE).setData(pose);
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
		loc.setLocation(x, y, z);
		loc.copyTo(oldLoc);
	}

	@Override
	public void teleport(double x, double y, double z, float yaw, float pitch) {
		teleported = true;
		loc.setLocation(x, y, z, yaw, pitch);
		loc.copyTo(oldLoc);
	}

	@Override
	public void setVelocity(double x, double y, double z) {
		// TODO velocity
	}

	@Override
	public void setGravity(boolean gravity) {
		metaContainer.get(META_HAS_NO_GRAVITY).setData(!gravity);
	}

	@Override
	public void setOnGround(boolean onGround) {
		MetaData<Byte> meta = metaContainer.get(META_ENTITY_FLAGS);
		meta.setData((byte) (onGround ? meta.getData() | 0x02 : meta.getData() & 0xFD));
	}

	@Override
	public void setPortalCooldown(int cooldown) {
		this.portalCooldown = cooldown;
	}

	@Override
	public void setSilent(boolean silent) {
		metaContainer.get(META_IS_SILENT).setData(silent);
	}

	@Override
	public void addScoreboardTag(String tag) {
		getScoreboardTags().add(tag);
	}

	@Override
	public void setUUID(UUID uuid) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		if (!isRemoved())
			throw new IllegalStateException("Can not change UUID while not removed!");
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

	@Override
	public synchronized void spawn(int entityID, World world, double x, double y, double z, float pitch, float yaw) {
		if (!asyncIsRemoved() || !isRemoved())
			throw new IllegalStateException("Unable to spawn not removed Entity! call remove() first...");
		if (uuid == null)
			uuid = UUID.randomUUID();
		removed = false;
		aremoved = false;
		if (passengers != null) 
			passengers.clear();
		this.id = entityID;
		this.loc.setLocation(world, x, y, z, yaw, pitch);
		chunk = getWorld().getChunk(loc);
		fallDistance = 0;
		fire = 0;
		metaContainer.setChanged(false); // reset changes made by EntitySpawnEvent to avoid duplications on first tick
	}

	@Override
	public Chunk getChunk() {
		return chunk;
	}

	@Override
	public boolean asyncIsRemoved() {
		return aremoved;
	}

	@Override
	public boolean isRemoved() {
		return removed;
	}

	@Override
	public void tick() {
		if (removed)
			return;
		doTick();
		prepUpdate();
		if (isDead() || removed)
			return;
		update();
		// TODO implement entity tick
	}
	
	/**
	 * Called before {@link #update()} to make last changes before the entity is updated to the client
	 */
	protected void prepUpdate() {
		
	}
	
	/**
	 * Updates changes made by the last tick to the client.<br>
	 * {@link MetaDataContainer} will be updated by {@link CoreEntity}
	 */
	protected void update() {
		if (passengersChanged) {
			passengersChanged = false;
			int[] passengerIDs = new int[passengers.size()];
			int index = 0;
			for (Entity ent : passengers)
				passengerIDs[index++] = ent.getID();
			PacketOutSetPassengers packet = new PacketOutSetPassengers();
			packet.setVehicleID(getID());
			packet.setPassengers(passengerIDs);
			for (Player viewer : viewers) {
				PlayerConnection con = viewer.getConnection();
				con.sendPacked(packet);
			}
		}
		if (!oldLoc.matches(loc)) { // Entity has moved
			if (oldLoc.getDistanceSquared(loc) > 64)
				teleported = true; // teleport entity when moved more than 8 blocks
			else {
				if (!loc.matchPosition(oldLoc)) {
					PacketOutEntityPositionAndRotation packet = new PacketOutEntityPositionAndRotation();
					short dx = MathUtil.delta(loc.getX(), oldLoc.getX());
					short dy = MathUtil.delta(loc.getY(), oldLoc.getY());
					short dz = MathUtil.delta(loc.getZ(), oldLoc.getZ());
					packet.setEntityID(getID());
					packet.setLocation(dx, dy, dz, loc.getYaw(), loc.getPitch());
					packet.setOnGround(isOnGround());
					for (Player viewer : viewers) {
						PlayerConnection con = viewer.getConnection();
						con.sendPacked(packet);
					}
				} else {
					PacketOutEntityRotation packet = new PacketOutEntityRotation();
					packet.setEntityID(getID());
					packet.setYaw(loc.getYaw());
					packet.setPitch(loc.getPitch());
					packet.setOnGround(isOnGround());
					for (Player viewer : viewers) {
						PlayerConnection con = viewer.getConnection();
						con.sendPacked(packet);
					}
				}
			}
			oldLoc.setLocation(loc);
		}
		if (teleported) {
			teleported = false;
			PacketOutEntityTeleport packet = new PacketOutEntityTeleport();
			packet.setEntityID(getID());
			packet.setLocation(getX(), getY(), getZ(), getPitch(), getYaw());
			packet.setOnGround(isOnGround());
			for (Player viewer : viewers) {
				PlayerConnection con = viewer.getConnection();
				con.sendPacked(packet);
			}
		}
		if (metaContainer.hasChanges()) {
			PacketOutEntityMetadata packet = new PacketOutEntityMetadata();
			packet.setEntityID(getID());
			packet.setChangedData(metaContainer);
			for (Player viewer : viewers) {
				PlayerConnection con = viewer.getConnection();
				con.sendPacked(packet);
			}
			metaContainer.setChanged(false);
		}
	}
	
	/**
	 * Processes the tick
	 */
	protected void doTick() {
		loc.add(motion);
	}
	

	@Override
	public void causeSound(Sound sound, SoundCategory category, float volume, float pitch) {
		getWorld().playSound(this, sound, category, volume, pitch);
	}

	@Override
	public boolean isDead() {
		return false; // TODO entity is dead
	}

	@Override
	public boolean isOnGround() {
		return (metaContainer.get(META_ENTITY_FLAGS).getData() & 0x2) == 0x2;
	}

	@Override
	public int getPortalCooldown() {
		return portalCooldown;
	}

	@Override
	public ViewerSet<Entity, Player> getViewers() {
		return viewers;
	}

	protected void sendMetadata(Player player) {
		PlayerConnection con = player.getConnection();
		PacketOutEntityMetadata packet = new PacketOutEntityMetadata();
		packet.setEntityID(getID());
		packet.setNonDefaultData(metaContainer);
		con.sendPacked(packet);
	}
	
}
