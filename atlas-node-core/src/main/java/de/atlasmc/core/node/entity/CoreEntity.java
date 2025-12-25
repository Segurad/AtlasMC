
package de.atlasmc.core.node.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

import org.joml.Vector3d;
import org.joml.Vector3i;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.Location;
import de.atlasmc.node.SoundCategory;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.entity.metadata.type.MetaData;
import de.atlasmc.node.entity.metadata.type.MetaDataContainer;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;
import de.atlasmc.node.io.protocol.PlayerConnection;
import de.atlasmc.node.io.protocol.play.PacketOutRemoveEntities;
import de.atlasmc.node.io.protocol.play.PacketOutSetEntityMetadata;
import de.atlasmc.node.io.protocol.play.PacketOutSetPassengers;
import de.atlasmc.node.io.protocol.play.PacketOutSpawnEntity;
import de.atlasmc.node.io.protocol.play.PacketOutTeleportEntity;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateEntityPositionAndRotation;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateEntityRotation;
import de.atlasmc.node.server.LocalServer;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.node.util.MathUtil;
import de.atlasmc.node.util.TeleportFlags;
import de.atlasmc.node.world.Chunk;
import de.atlasmc.node.world.World;
import de.atlasmc.node.world.entitytracker.EntityPerception;
import de.atlasmc.node.world.entitytracker.TrackerBinding;
import de.atlasmc.util.ViewerSet;

public class CoreEntity implements Entity {
	
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
	 * <tr><td>0x02		</td><td>crouching	    </td></tr>
	 * <tr><td>0x08		</td><td>sprinting		</td></tr>
	 * <tr><td>0x10		</td><td>swimming		</td></tr>
	 * <tr><td>0x20		</td><td>invisible		</td></tr>
	 * <tr><td>0x40		</td><td>glowing		</td></tr>
	 * <tr><td>0x80		</td><td>flying elytra	</td></tr>
	 * </table>
	 */
	protected static final int
	FLAG_ON_FIRE = 0x01,
	FLAG_CROUCHING = 0x02,
	FLAG_SPRINTING = 0x04,
	FLAG_SWIMMING = 0x08,
	FLAG_INVISIBLE = 0x10,
	FLAG_GLOWING = 0x20,
	FLAG_FLYING_ELYTRA = 0x40;
		
	protected static final MetaDataField<Byte> META_ENTITY_FLAGS = new MetaDataField<>(0, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Integer> META_AIR_TICKS = new MetaDataField<>(1, 300, MetaDataType.VAR_INT);
	protected static final MetaDataField<Chat> META_CUSTOM_NAME = new MetaDataField<>(2, null, MetaDataType.OPT_CHAT); 
	protected static final MetaDataField<Boolean> META_CUSTOM_NAME_VISIBLE = new MetaDataField<>(3, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean> META_IS_SILENT = new MetaDataField<>(4, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean> META_HAS_NO_GRAVITY = new MetaDataField<>(5, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Pose> META_POSE = new MetaDataField<>(6, Pose.STANDING, MetaDataType.POSE);
	protected static final MetaDataField<Integer> META_TICKS_FROZEN = new MetaDataField<>(7, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = 7;
	
	protected final MetaDataContainer metaContainer;
	private final EntityType type;
	protected final ViewerSet<Entity, Player> viewers;
	protected TrackerBinding tracker;
	private UUID uuid;
	private short air;
	private double fallDistance;
	private short fire;
	private boolean glowing;
	private boolean invulnerable;
	private boolean removed;
	private volatile boolean aremoved;
	private volatile LocalServer server;
	protected final Vector3d motion;
	private List<Entity> passengers;
	private int portalCooldown;
	protected final WorldLocation loc;
	protected final WorldLocation oldLoc;
	private Chunk chunk;
	private List<String> scoreboardTags;
	private EntityPerception perception;
	private double perceptionDistance;
	private boolean ticking;
	private boolean onGround;
	
	// Update flags
	private boolean teleported;
	private boolean passengersChanged;
	
	/**
	 * Creates a new CoreEntity with id 0 and removed status.<br>
	 * Use {@link #spawn(int, World, double, double, double, float, float)} to spawn it in a {@link World}
	 * @param type the EntityType of this Entity
	 * @param uuid the UUID of this entity
	 */
	public CoreEntity(EntityType type) {
		this.removed = true;
		this.aremoved = true;
		this.ticking = true;
		this.type = type;
		this.loc = new WorldLocation(null, 0, 0, 0);
		this.oldLoc = new WorldLocation(loc);
		this.motion = new Vector3d();
		this.viewers = createViewerSet();
		metaContainer = new MetaDataContainer(getMetaContainerSize());
		initMetaContainer();
	}
	
	protected ViewerSet<Entity, Player> createViewerSet() {
		return new ViewerSet<>(this, VIEWER_ADD_FUNCTION, VIEWER_REMOVE_FUNCTION);
	}

	@Override
	public boolean isSprinting() {
		return (metaContainer.getData(META_ENTITY_FLAGS) & FLAG_SPRINTING) == FLAG_SPRINTING;
	}

	@Override
	public boolean isSwimming() {
		return (metaContainer.getData(META_ENTITY_FLAGS) & FLAG_SWIMMING) == FLAG_SWIMMING;
	}

	@Override
	public boolean isInvisible() {
		return (metaContainer.getData(META_ENTITY_FLAGS) & FLAG_INVISIBLE) == FLAG_INVISIBLE;
	}

	@Override
	public void setInvisible(boolean invisible) {
		MetaData<Byte> data = this.metaContainer.get(META_ENTITY_FLAGS);
		if (invisible)
			data.setData((byte) (data.getData() | FLAG_INVISIBLE));
		else
			data.setData((byte) (data.getData() & ~FLAG_INVISIBLE));
	}
	
	@Override
	public boolean isGlowing() {
		return glowing;
	}

	@Override
	public boolean isFlyingWithElytra() {
		return (metaContainer.getData(META_ENTITY_FLAGS) & FLAG_FLYING_ELYTRA) == FLAG_FLYING_ELYTRA;
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
	public boolean hasNoGravity() {
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
			;
		chunk = null;
		if (passengers != null)
			passengers.clear();
		viewers.clear();
		aremoved = true;
		tracker.unregister();
		tracker = null;
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
	public WorldLocation getLocation() {
		return loc.clone();
	}

	@Override
	public WorldLocation getLocation(WorldLocation loc) {
		return this.loc.copyTo(loc);
	}

	@Override
	public Location getLocation(Location loc) {
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
		return tracker.getID();
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
	public double getFallDistance() {
		return fallDistance;
	}

	@Override
	public void setFallDistance(double distance) {
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
	public void teleport(Location loc, Vector3d velocity, int flags) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		teleported = true;
		TeleportFlags.set(this.loc, loc, flags);
		loc.copyTo(oldLoc);
		if (velocity != null) {
			TeleportFlags.set(this.motion, velocity.x, velocity.y, velocity.z, flags);
			// TODO apply rotation to velocity
		}
	}
	
	@Override
	public void teleport(double x, double y, double z, int flags) {
		teleported = true;
		TeleportFlags.set(this.loc, x, y, z, flags);
		loc.copyTo(oldLoc);
	}

	@Override
	public void teleport(double x, double y, double z, float pitch, float yaw, int flags) {
		teleported = true;
		TeleportFlags.set(this.loc, x, y, z, pitch, yaw, flags);
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
	public void setNoGravity(boolean gravity) {
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
	public synchronized void spawn(World world, double x, double y, double z, float pitch, float yaw) {
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
		this.loc.set(world, x, y, z, yaw, pitch);
		tracker = world.getEntityTracker().register(this, perception);
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
		// implemented in child
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
			packet.vehicleID = getID();
			packet.setPassengers(passengerIDs);
			for (Player viewer : viewers) {
				PlayerConnection con = viewer.getConnection();
				con.sendPacked(packet);
			}
		}
		if (!oldLoc.matches(loc)) { // Entity has moved
			tracker.updatePosition(loc.x, loc.y, loc.z);
			if (oldLoc.getDistanceSquared(loc) > 64) {
				teleported = true; // teleport entity when moved more than 8 blocks
			} else {
				if (!loc.matchPosition(oldLoc)) {
					PacketOutUpdateEntityPositionAndRotation packet = new PacketOutUpdateEntityPositionAndRotation();
					short dx = MathUtil.delta(loc.x, oldLoc.x);
					short dy = MathUtil.delta(loc.y, oldLoc.y);
					short dz = MathUtil.delta(loc.z, oldLoc.z);
					packet.entityID = getID();
					packet.deltaX = dx;
					packet.deltaY = dy;
					packet.deltaZ = dz;
					packet.yaw = loc.yaw;
					packet.pitch = loc.pitch;
					packet.onGround = isOnGround();
					for (Player viewer : viewers) {
						PlayerConnection con = viewer.getConnection();
						con.sendPacked(packet);
					}
				} else {
					PacketOutUpdateEntityRotation packet = new PacketOutUpdateEntityRotation();
					packet.entityID = getID();
					packet.yaw = loc.yaw;
					packet.pitch = loc.pitch;
					packet.onGround = isOnGround();
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
			PacketOutTeleportEntity packet = new PacketOutTeleportEntity();
			packet.entityID = getID();
			packet.x = loc.x;
			packet.y = loc.y;
			packet.z = loc.z;
			packet.pitch = loc.pitch;
			packet.yaw = loc.yaw;
			packet.onGround = isOnGround();
			for (Player viewer : viewers) {
				PlayerConnection con = viewer.getConnection();
				con.sendPacked(packet);
			}
		}
		if (metaContainer.hasChanges()) {
			PacketOutSetEntityMetadata packet = new PacketOutSetEntityMetadata();
			packet.entityID = getID();
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
		packet.entityID = getID();
		packet.setNonDefaultData(metaContainer);
		con.sendPacked(packet);
	}

	@Override
	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public int getFreezeTicks() {
		return metaContainer.getData(META_TICKS_FROZEN);
	}

	@Override
	public void setFreezeTicks(int ticks) {
		metaContainer.get(META_TICKS_FROZEN).setData(ticks);
	}

	@Override
	public EntityPerception getPerception() {
		return perception;
	}

	@Override
	public void setPerception(EntityPerception perception) {
		this.perception = perception;
		if (tracker != null)
			tracker.updatePerception(perception);
	}

	@Override
	public double getPerceptionDistance() {
		return perceptionDistance;
	}

	@Override
	public void setPerceptionDistance(double distance) {
		this.perceptionDistance = distance;
		if (tracker != null)
			tracker.updatePerceptionRange();
	}
	
	@Override
	public void setTicking(boolean ticking) {
		this.ticking = ticking;
		TrackerBinding tracker = this.tracker;
		if (tracker != null)
			tracker.updateTicking(ticking);
	}
	
	@Override
	public boolean isTicking() {
		return ticking;
	}

	@Override
	public boolean hasVisualFire() {
		return (metaContainer.getData(META_ENTITY_FLAGS) & FLAG_ON_FIRE) == FLAG_ON_FIRE;
	}

	@Override
	public void setVisualFire(boolean fire) {
		MetaData<Byte> data = this.metaContainer.get(META_ENTITY_FLAGS);
		if (fire)
			data.setData((byte) (data.getData() | FLAG_INVISIBLE));
		else
			data.setData((byte) (data.getData() & ~FLAG_INVISIBLE));
	}

	@Override
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	@Override
	public WorldLocation getLocationUnsafe() {
		return loc;
	}
	
	@Override
	public Vector3d getLocation(Vector3d vec) {
		return vec.set(loc);
	}
	
	@Override
	public Vector3i getLocation(Vector3i vec) {
		return vec.set(loc);
	}

	@Override
	public void setLocation(Vector3d loc) {
		loc.set(loc);
	}

	@Override
	public void setLocation(Location loc) {
		loc.set(loc);
	}

	@Override
	public Vector3d getVelocityUnsafe() {
		return motion;
	}
	
}
