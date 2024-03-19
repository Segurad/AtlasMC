
package de.atlascore.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

import org.joml.Vector3d;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
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
import de.atlasmc.io.protocol.play.PacketOutRemoveEntities;
import de.atlasmc.io.protocol.play.PacketOutSetEntityMetadata;
import de.atlasmc.io.protocol.play.PacketOutUpdateEntityPositionAndRotation;
import de.atlasmc.io.protocol.play.PacketOutUpdateEntityRotation;
import de.atlasmc.server.LocalServer;
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
import de.atlasmc.util.nbt.tag.NBT;
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
			PacketOutRemoveEntities packet = new PacketOutRemoveEntities();
			packet.setEntityIDs(holder.getID());
			con.sendPacked(packet);
		};
	
	/**
	 * Flags contains the following Data<br>
	 * <table>
	 * <tr><th>Bit mask</th><th>Meaning</th></tr>
	 * <tr><td>0x01		</td><td>on fire		</td></tr>
	 * <tr><td>0x02		</td><td>on crouching	</td></tr>
	 * <tr><td>0x08		</td><td>sprinting		</td></tr>
	 * <tr><td>0x10		</td><td>swimming		</td></tr>
	 * <tr><td>0x20		</td><td>invisible		</td></tr>
	 * <tr><td>0x40		</td><td>glowing		</td></tr>
	 * <tr><td>0x80		</td><td>flying elytra	</td></tr>
	 * </table>
	 */
	protected static final MetaDataField<Byte> META_ENTITY_FLAGS = new MetaDataField<>(0, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Integer> META_AIR_TICKS = new MetaDataField<>(1, 300, MetaDataType.VAR_INT);
	protected static final MetaDataField<Chat> META_CUSTOM_NAME = new MetaDataField<>(2, null, MetaDataType.OPT_CHAT); 
	protected static final MetaDataField<Boolean> META_CUSTOM_NAME_VISIBLE = new MetaDataField<>(3, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean> META_IS_SILENT = new MetaDataField<>(4, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean> META_HAS_NO_GRAVITY = new MetaDataField<>(5, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Pose> META_POSE = new MetaDataField<>(6, Pose.STANDING, MetaDataType.POSE);
	protected static final MetaDataField<Integer> META_TICKS_FROZEN = new MetaDataField<>(7, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = 7;
	
	protected static final NBTFieldContainer<CoreEntity> NBT_FIELDS = new NBTFieldContainer<>();
	
	protected static final CharKey
	NBT_AIR = CharKey.literal("Air"),
	NBT_CUSTOM_NAME = CharKey.literal("CustomName"),
	NBT_CUSTOM_NAME_VISIBLE = CharKey.literal("CustomNameVisible"),
	NBT_DIMENSION = CharKey.literal("Dimension"),
	NBT_FALL_DISTANCE = CharKey.literal("FallDistance"),
	NBT_FIRE = CharKey.literal("Fire"),
	NBT_GLOWING = CharKey.literal("Glowing"),
	NBT_ID = CharKey.literal("id"),
	NBT_INVULNERABLE = CharKey.literal("Invulnerable"),
	NBT_MOTION = CharKey.literal("Motion"), 
	NBT_NO_GRAVITY = CharKey.literal("NoGravity"),
	NBT_ON_GROUND = CharKey.literal("OnGround"),
	NBT_PASSENGERS = CharKey.literal("Passengers"),
	NBT_PORTAL_COOLDOWN = CharKey.literal("PortalCooldown"),
	NBT_POS = CharKey.literal("Pos"),
	NBT_ROTATION = CharKey.literal("Rotation"),
	NBT_SILENT = CharKey.literal("Silent"),
	NBT_TAGS = CharKey.literal("Tags"),
	NBT_ATLASMC = CharKey.literal("AtlasMC"),
	NBT_TICKS_FROZEN = CharKey.literal("TicksFrozen"),
	NBT_UUID = CharKey.literal("UUID");
	
	static {
		NBT_FIELDS.setContainer(NBT_ATLASMC).setUnknownFieldHandler((holder, reader) -> {
			holder.getCustomTagContainer().addSystemTag(reader.readNBT());
		});
		NBT_FIELDS.setField(NBT_AIR, (holder, reader) -> {
			holder.setAirTicks(reader.readShortTag());
		});
		NBT_FIELDS.setField(NBT_CUSTOM_NAME, (holder, reader) -> {
			holder.setCustomName(ChatUtil.toChat(reader.readStringTag()));
		});
		NBT_FIELDS.setField(NBT_CUSTOM_NAME_VISIBLE, (holder, reader) -> {
			holder.setCustomNameVisible(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_DIMENSION, NBTField.skip());
		NBT_FIELDS.setField(NBT_FALL_DISTANCE, (holder, reader) -> {
			holder.setFallDistance(reader.readFloatTag());
		});
		NBT_FIELDS.setField(NBT_FIRE, (holder, reader) -> {
			holder.setFireTicks(reader.readShortTag());
		});
		NBT_FIELDS.setField(NBT_GLOWING, (holder, reader) -> {
			holder.setGlowing(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_ID, NBTField.skip()); // TODO skipped
		NBT_FIELDS.setField(NBT_INVULNERABLE, (holder, reader) -> {
			holder.setInvulnerable(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_MOTION, (holder, reader) -> {
			double x = reader.readDoubleTag();
			double y = reader.readDoubleTag();
			double z = reader.readDoubleTag();
			holder.setVelocity(x, y, z);
		});
		NBT_FIELDS.setField(NBT_NO_GRAVITY, (holder, reader) -> {
			holder.setGravity(reader.readByteTag() == 0);
		});
		NBT_FIELDS.setField(NBT_ON_GROUND, (holder, reader) -> {
			// TODO skipped on ground
			//((Entity) holder).setOnGround(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_PASSENGERS, NBTField.skip()); // TODO nbt load passenger
		NBT_FIELDS.setField(NBT_PORTAL_COOLDOWN, (holder, reader) -> {
			holder.setPortalCooldown(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_POS, (holder, reader) -> {
			double x = reader.readDoubleTag();
			double y = reader.readDoubleTag();
			double z = reader.readDoubleTag();
			holder.teleport(x, y, z);
		});
		NBT_FIELDS.setField(NBT_ROTATION, (holder, reader) -> {
			float yaw = reader.readFloatTag();
			float pitch = reader.readFloatTag();
			holder.teleport(holder.getX(), holder.getY(), holder.getZ(), yaw, pitch);
		});
		NBT_FIELDS.setField(NBT_SILENT, (holder, reader) -> {
			holder.setSilent(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_TAGS, (holder, reader) -> {
			while(reader.getRestPayload() > 0) {
				holder.addScoreboardTag(reader.readStringTag());
			}
		});
		NBT_FIELDS.setField(NBT_UUID, NBTField.skip());
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
	private volatile LocalServer server;
	protected final Vector3d motion;
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
	 * @param uuid the UUID of this entity
	 */
	public CoreEntity(EntityType type, UUID uuid) {
		this.removed = true;
		this.aremoved = true;
		this.type = type;
		this.uuid = uuid;
		this.loc = new Location(null, 0, 0, 0);
		this.oldLoc = new Location(loc);
		this.motion = new Vector3d();
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
		return server;
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
	public Vector3d getVelocity() {
		return new Vector3d(motion);
	}

	@Override
	public Vector3d getVelocity(Vector3d vec) {
		vec.set(motion);
		return vec;
	}

	@Override
	public boolean hasVelocity() {
		return motion.x != 0 || motion.y != 0 || motion.z != 0;
	}

	@Override
	public double getX() {
		return loc.x;
	}

	@Override
	public double getY() {
		return loc.y;
	}

	@Override
	public double getZ() {
		return loc.z;
	}
	
	@Override
	public float getPitch() {
		return loc.pitch;
	}

	@Override
	public float getYaw() {
		return loc.yaw;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(NBT_ID, type.getNamespacedKeyRaw());
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
			writer.writeDoubleTag(null, motion.x);
			writer.writeDoubleTag(null, motion.y);
			writer.writeDoubleTag(null, motion.z);
		if (!hasGravity())
			writer.writeByteTag(NBT_NO_GRAVITY, true);
		writer.writeByteTag(NBT_ON_GROUND, isOnGround());
		writer.writeIntTag(NBT_PORTAL_COOLDOWN, portalCooldown);
		writer.writeListTag(NBT_POS, TagType.DOUBLE, 3);
			writer.writeDoubleTag(null, loc.x);
			writer.writeDoubleTag(null, loc.y);
			writer.writeDoubleTag(null, loc.z);
		writer.writeListTag(NBT_ROTATION, TagType.FLOAT, 2);
			writer.writeFloatTag(null, loc.yaw);
			writer.writeFloatTag(null, loc.pitch);
		if (isSilent())
			writer.writeByteTag(NBT_SILENT, true);
		if (hasScoreboardTags())
			writer.writeListTag(NBT_TAGS, TagType.STRING, scoreboardTags.size());
			for (String tag : scoreboardTags)
				writer.writeStringTag(null, tag);
		writer.writeUUID(NBT_CUSTOM_NAME, uuid);
		if (hasCustomData()) {
			writeCustomData(writer);
		}
		if (systemData && hasSystemData()) {
			writer.writeCompoundTag(NBT_ATLASMC);
			writeSystemData(writer);
			writer.writeEndTag();
		}
	}
	
	/**
	 * Returns true if custom data is present. 
	 * In {@link CustomTagContainer#getCustomTags()} or some child implementation.
	 * @return true if custom data is present
	 */
	protected boolean hasCustomData() {
		return customTags != null && customTags.hasCustomTags();
	}
	
	/**
	 * Writes the custom data of {@link CustomTagContainer#getCustomTags()} and child implementations.
	 * @param writer
	 * @throws IOException
	 */
	protected void writeCustomData(NBTWriter writer) throws IOException {
		if (customTags != null && customTags.hasCustomTags()) {
			for (NBT nbt : customTags.getCustomTags()) {
				writer.writeNBT(nbt);
			}
		}
	}
	
	/**
	 * Returns true if custom system data is present. 
	 * In {@link CustomTagContainer#getSystemTags()} or some child implementation.
	 * @implNote custom system data will not be send to client
	 * @return true if custom data is present
	 */
	protected boolean hasSystemData() {
		return customTags != null && customTags.hasSystemTags();
	}
	
	/**
	 * Writes the custom system data of {@link CustomTagContainer#getSystemTags()} and child implementations.
	 * @param writer
	 * @implNote custom system data will not be send to client. 
	 * This data will be written to a CompoundTag with id {@link #NBT_ATLASMC}
	 * @throws IOException
	 */
	protected void writeSystemData(NBTWriter writer) throws IOException {
		writer.writeCompoundTag(NBT_ATLASMC);
		if (customTags != null && customTags.hasSystemTags()) {
			for (NBT nbt : customTags.getSystemTags()) {
				writer.writeNBT(nbt);
			}
		}
		writer.writeEndTag();
	}

	@Override
	protected NBTFieldContainer<? extends CoreEntity> getFieldContainerRoot() {
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
		metaContainer.set(META_TICKS_FROZEN);
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
		loc.set(x, y, z);
		loc.copyTo(oldLoc);
	}

	@Override
	public void teleport(double x, double y, double z, float yaw, float pitch) {
		teleported = true;
		loc.set(x, y, z, yaw, pitch);
		loc.copyTo(oldLoc);
	}

	@Override
	public void setVelocity(double x, double y, double z) {
		motion.set(x, y, z);
	}
	
	@Override
	public void setVelocity(Vector3d vec) {
		motion.set(vec);
	}

	@Override
	public void setGravity(boolean gravity) {
		metaContainer.get(META_HAS_NO_GRAVITY).setData(!gravity);
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
		if (scoreboardTags == null) scoreboardTags = new ArrayList<>();
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
		if (world == null)
			throw new IllegalArgumentException("World can not be null!");
		if (!asyncIsRemoved() || !isRemoved())
			throw new IllegalStateException("Unable to spawn not removed Entity! call remove() first...");
		if (uuid == null)
			uuid = UUID.randomUUID();
		removed = false;
		aremoved = false;
		server = world.getServer();
		if (passengers != null) 
			passengers.clear();
		this.id = entityID;
		this.loc.set(world, x, y, z, yaw, pitch);
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
					PacketOutUpdateEntityPositionAndRotation packet = new PacketOutUpdateEntityPositionAndRotation();
					short dx = MathUtil.delta(loc.x, oldLoc.x);
					short dy = MathUtil.delta(loc.y, oldLoc.y);
					short dz = MathUtil.delta(loc.z, oldLoc.z);
					packet.setEntityID(getID());
					packet.setLocation(dx, dy, dz, loc.yaw, loc.pitch);
					packet.setOnGround(isOnGround());
					for (Player viewer : viewers) {
						PlayerConnection con = viewer.getConnection();
						con.sendPacked(packet);
					}
				} else {
					PacketOutUpdateEntityRotation packet = new PacketOutUpdateEntityRotation();
					packet.setEntityID(getID());
					packet.setYaw(loc.yaw);
					packet.setPitch(loc.pitch);
					packet.setOnGround(isOnGround());
					for (Player viewer : viewers) {
						PlayerConnection con = viewer.getConnection();
						con.sendPacked(packet);
					}
				}
			}
			oldLoc.set(loc);
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
			PacketOutSetEntityMetadata packet = new PacketOutSetEntityMetadata();
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
		oldLoc.set(loc);
		loc.add(motion);
	}
	

	@Override
	public void causeSound(Sound sound, SoundCategory category, float volume, float pitch, long seed) {
		getWorld().playSound(this, sound, category, volume, pitch, seed);
	}
	
	@Override
	public void causeSound(String sound, SoundCategory category, float volume, float pitch, long seed, boolean fixedRange, float range) {
		getWorld().playSound(this, sound, category, volume, pitch, seed, fixedRange, range);
	}

	@Override
	public boolean isDead() {
		return false; // TODO entity is dead
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
		PacketOutSetEntityMetadata packet = new PacketOutSetEntityMetadata();
		packet.setEntityID(getID());
		packet.setNonDefaultData(metaContainer);
		con.sendPacked(packet);
	}

	@Override
	public boolean isOnGround() {
		// TODO on ground
		return true;
	}

	@Override
	public int getFreezeTicks() {
		return metaContainer.getData(META_TICKS_FROZEN);
	}

	@Override
	public void setFreezeTicks(int ticks) {
		metaContainer.get(META_TICKS_FROZEN).setData(ticks);
	}
	
}
