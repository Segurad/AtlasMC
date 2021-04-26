package de.atlascore.entity;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.Vector;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataContainer;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.nbt.AbstractNBTBuildable;
import de.atlasmc.util.nbt.CustomTagContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreEntity extends AbstractNBTBuildable implements Entity {
	
	private final MetaDataContainer metaContainer;
	private final int id;
	private final EntityType type;
	private final UUID uuid;
	private short air;
	private String customName;
	private boolean customNameVisible;
	private float fallDistance;
	private short fire;
	private boolean glowing;
	private boolean invulnerable;
	private Vector motion;
	private boolean noGravity;
	private boolean onGround;
	private List<Entity> passengers;
	private int portalCooldown;
	private Location loc;
	private boolean silent;
	private List<String> scoreboardTags;
	
	protected static final int
	META_BASE_FLAGS = 0,
	META_AIR_TICKS = 1,
	META_CUSTOM_NAME = 2,
	META_CUSTOM_NAME_VISIBLE = 3,
	META_IS_SILENT = 4,
	META_HAS_NO_GRAVITY = 5,
	META_POSE = 6;
	
	
	public CoreEntity(int id, EntityType type, Location loc, UUID uuid) {
		this.id = id;
		this.type = type;
		this.uuid = uuid;
		this.loc = loc.clone();
		this.motion = new Vector(0, 0, 0);
		metaContainer = new MetaDataContainer(getMetaContainerSize());
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSwimming() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInvisble() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGlowing() {
		return glowing;
	}

	@Override
	public boolean isFlyingWithElytra() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getAirTicks() {
		return air;
	}

	@Override
	public String getCustomName() {
		return customName;
	}

	@Override
	public boolean isSilent() {
		return silent;
	}

	@Override
	public boolean isCustomNameVisible() {
		return customNameVisible;
	}

	@Override
	public boolean hasGravity() {
		return !(boolean) metaContainer.get(META_HAS_NO_GRAVITY).getData();
	}

	@Override
	public Pose getPose() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCustomNameVisible(boolean value) {
		this.customNameVisible = value;
	}

	@Override
	public void setCustomName(String name) {
		this.customName = name;
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
	public int getObjectData() {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected NBTFieldContainer getRootFieldContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CustomTagContainer getCustomTagContainer() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFallDistance(float distance) {
		// TODO Auto-generated method stub
		
	}

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
}
