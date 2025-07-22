package de.atlascore.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.joml.Vector3d;
import org.joml.Vector3i;

import de.atlasmc.Color;
import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeInstance;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.entity.Player;
import de.atlasmc.entity.Projectile;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.entity.ProjectileLounchEvent;
import de.atlasmc.inventory.EntityEquipment;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutEntityEffect;
import de.atlasmc.io.protocol.play.PacketOutRemoveEntityEffect;
import de.atlasmc.io.protocol.play.PacketOutSpawnEntity;
import de.atlasmc.io.protocol.play.PacketOutUpdateAttributes;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.util.ViewerSet;
import de.atlasmc.util.map.ArrayListMultimap;
import de.atlasmc.util.map.Multimap;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreLivingEntity extends CoreEntity implements LivingEntity {

	protected static final BiConsumer<Entity, Player>
		VIEWER_ADD_FUNCTION = (holder, viewer) -> {
			PlayerConnection con = viewer.getConnection();
			PacketOutSpawnEntity packet = new PacketOutSpawnEntity();
			packet.setEntity(holder);
			con.sendPacked(packet);
			((CoreLivingEntity) holder).sendMetadata(viewer);
			((CoreLivingEntity) holder).sendEntityEffects(viewer);
			((CoreLivingEntity) holder).sendAttributes(viewer);
		};
	
	/**
	 * 0x01 Is hand active<br>
	 * 0x02 active hand 0 = main hand, 1 = off hand<br>
	 * 0x04 riptide spin attack<br>
	 */
	protected static final MetaDataField<Byte>
	META_HAND_STATES = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Float>
	META_HEALTH = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 1.0f, MetaDataType.FLOAT);
	protected static final MetaDataField<Integer>
	META_POTION_EFFECT_COLOR = new MetaDataField<>(CoreEntity.LAST_META_INDEX+3, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Boolean>
	META_REDUCE_POTION_AMBIENT = new MetaDataField<>(CoreEntity.LAST_META_INDEX+4, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Integer>
	META_DISPLAY_ARROWS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+5, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer>
	META_DISPLAY_BEE_STINGERS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+6, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Long>
	META_OCCUPIED_BED_POSITION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+7, null, MetaDataType.OPT_POSITION);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+7;
	
	private boolean healthChanged;
	private float health;
	private float absorption;
	private Map<Attribute, AttributeInstance> attributes;
	private Consumer<AttributeInstance> attributeUpdateListener;
	private List<PotionEffect> effects;
	private Map<PotionEffectType, PotionEffect> activeEffects;
	private short deathAnimationTime;
	private short hurtTime;
	private short attackTime;
	private boolean fallFlying;
	private boolean removeWhenFaraway;
	private String team;
	private Vector3i sleepingPos;
	private Vector3i homePos;
	private int homeRadius;
	private boolean noAI;
	private int lastHurtTime;
	private boolean canPickupLoot;
	private boolean persistent;
	private boolean isLefHanded;
	
	private boolean updateAttributes;
	private Set<AttributeInstance> changedAttributes;
	
	public CoreLivingEntity(EntityType type) {
		super(type);
	}
	
	@Override
	protected ViewerSet<Entity, Player> createViewerSet() {
		return new ViewerSet<>(this, VIEWER_ADD_FUNCTION, VIEWER_REMOVE_FUNCTION);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(new MetaData<>(META_HAND_STATES));
		metaContainer.set(new MetaData<>(META_HEALTH));
		metaContainer.set(new MetaData<>(META_POTION_EFFECT_COLOR));
		metaContainer.set(new MetaData<>(META_REDUCE_POTION_AMBIENT));
		metaContainer.set(new MetaData<>(META_DISPLAY_ARROWS));
		metaContainer.set(new MetaData<>(META_DISPLAY_BEE_STINGERS));
		metaContainer.set(new MetaData<>(META_OCCUPIED_BED_POSITION));
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public float getHeadPitch() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	protected void prepUpdate() {
		super.prepUpdate();
		if (healthChanged) {
			healthChanged = false;
			metaContainer.get(META_HEALTH).setData((float) health);
		}
	}

	@Override
	public float getHealth() {
		return health;
	}

	@Override
	public void setHealth(float health) {
		this.health = health;
		this.healthChanged = true;
	}
	
	@Override
	public boolean isDead() {
		return health < 0.0;
	}

	@Override
	public int getDisplayedArrows() {
		return metaContainer.getData(META_DISPLAY_ARROWS);
	}

	@Override
	public int getDisplayedBeeStingers() {
		return metaContainer.getData(META_DISPLAY_BEE_STINGERS);
	}

	@Override
	public void setDisplayedArrows(int arrows) {
		metaContainer.get(META_DISPLAY_ARROWS).setData(arrows);		
	}

	@Override
	public void setDisplayedBeeStringers(int stingers) {
		metaContainer.get(META_DISPLAY_BEE_STINGERS).setData(stingers);		
	}

	@Override
	public Color getPotionAmbientColor() {
		return Color.fromRGB(getPotionAmbientColorRGB());
	}

	@Override
	public void setPotionAmbientColor(Color color) {
		setPortalCooldown(color == null ? 0 : color.asRGB());
	}

	@Override
	public int getPotionAmbientColorRGB() {
		return metaContainer.getData(META_POTION_EFFECT_COLOR);
	}

	@Override
	public void setPotionAmbientColor(int rgb) {
		if (rgb < 0 || rgb > 0xFFFFFF)
			throw new IllegalArgumentException("Color out of range: " + rgb);
		metaContainer.get(META_POTION_EFFECT_COLOR).setData(rgb);
	}

	@Override
	public boolean isPotionAmbientReduced() {
		return metaContainer.getData(META_REDUCE_POTION_AMBIENT);
	}

	@Override
	public void setPotionAmbientReduced(boolean reduced) {
		metaContainer.get(META_REDUCE_POTION_AMBIENT).setData(reduced);	
	}

	@Override
	public void setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers) {
		if (attributeModifiers == null)
			throw new IllegalArgumentException("Modifiers can not be null!");
		attributeModifiers.asMap().forEach((attribute, modifiers) -> {
			AttributeInstance instance = getAttribute(attribute);
			instance.setModifiers(modifiers);
		});
	}

	@Override
	public boolean addAttributeModifier(Attribute attribute, AttributeModifier modifier) {
		if (attribute == null)
			throw new IllegalArgumentException("Attribute can not be null!");
		if (modifier == null)
			throw new IllegalArgumentException("Modifier can not be null!");
		AttributeInstance instance = getAttribute(attribute);
		instance.addAttributeModififer(modifier);
		return true;
	}
	
	@Override
	public List<AttributeModifier> getAttributeModifiers(Attribute attribute) {
		if (attribute == null)
			throw new IllegalArgumentException("Attribute can not be null!");
		if (attributes == null || attributes.isEmpty()) 
			return List.of();
		AttributeInstance instance = attributes.get(attribute);
		if (instance == null || !instance.hasModifiers())
			return List.of();
		return instance.getModifiers();
	}

	@Override
	public boolean hasAttributeModifiers() {
		if (!hasAttributes())
			return false;
		for (AttributeInstance instance : attributes.values())
			if (instance.hasModifiers())
				return true;
		return false;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
		if (attributes == null || attributes.isEmpty())
			return Multimap.of();
		Multimap<Attribute, AttributeModifier> map = null;
		for (AttributeInstance instance : attributes.values()) {
			if (!instance.hasModifiers())
				continue;
			if (map == null)
				map = new ArrayListMultimap<>();
			map.putAll(instance.getAttribute(), instance.getModifiers());
		}
		return map == null ? Multimap.of() : map;
	}

	@Override
	public boolean removeAttributeModifier(Attribute attribute) {
		if (attribute == null)
			throw new IllegalArgumentException("Attribute can not be null!");
		if (attributes == null)
			return false;
		AttributeInstance instance = getAttribute(attribute);
		if (instance == null)
			return false;
		return instance.removeModifiers();
	}

	@Override
	public boolean removeAttributeModifier(Attribute attribute, AttributeModifier modifier) {
		if (!hasAttribute(attribute))
			return false;
		AttributeInstance instance = getAttribute(attribute);
		return instance.removeModifier(modifier);
	}

	@Override
	public boolean removeAttributeModifier(EquipmentSlot slot) {
		if (slot == null) 
			throw new IllegalArgumentException("EquipmentSlot can not be null!");
		if (!hasAttributeModifiers()) 
			return false;
		boolean changes = false;
		for (Attribute a : attributes.keySet()) {
			AttributeInstance instance = attributes.get(a);
			if (instance == null || !instance.hasModifiers())
				continue;
			if (instance.removeModifier(slot));
				changes = true;
		}
		return changes;
	}

	@Override
	public float getAbsorption() {
		return absorption;
	}

	@Override
	public void setAbsorption(float absorption) {
		if (absorption < 0)
			absorption = 0;
		this.absorption = absorption;		
	}

	@Override
	public void addPotionEffect(PotionEffectType type, int amplifier, int duration) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		internalAddPotionEffect(type.createEffect(amplifier, duration));
	}
	
	@Override
	public void addPotionEffect(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		internalAddPotionEffect(type.createEffect(amplifier, duration, reducedAmbient, particles, icon));
	}
	
	@Override
	public void addPotionEffect(PotionEffect effect) {
		if (effect == null)
			throw new IllegalArgumentException("Effect can not be null!");
		internalAddPotionEffect(effect.clone());	
	}
	
	@Override
	public void addPotionEffects(List<PotionEffect> effects) {
		if (effects == null)
			throw new IllegalArgumentException("Effects can not be null!");
		for (PotionEffect effect : effects)
			internalAddPotionEffect(effect.clone());
	}
	
	private void internalAddPotionEffect(PotionEffect effect) {
		if (effect.isOnlyOnApply()) {
			effect.addEffect(this);
			return;
		}
		if (activeEffects == null)
			activeEffects = new HashMap<>();
		PotionEffectType type = effect.getType();
		PotionEffect activeEffect = activeEffects.get(type);
		effect = effect.clone();
		if (activeEffect == null) {
			activeEffects.put(type, effect);
		} else if (activeEffect.getAmplifier() > effect.getAmplifier() 
				|| (activeEffect.getAmplifier() == effect.getAmplifier() 
				&& activeEffect.getDuration() >= effect.getDuration())) { 
			// move new effect to effects when amplifier < or duration < when amplifier equals
			if (effects != null)
				effects = new ArrayList<>();
			effects.add(effect);
			return;
		} else {
			activeEffects.put(type, effect);
			if (effects != null)
				effects = new ArrayList<>();
			effects.add(activeEffect);
		}
		effect.addEffect(this);
		sendAddEntityEffect(effect);
	}

	@Override
	public AttributeInstance getAttribute(Attribute attribute) {
		if (attributes == null) {
			attributes = new HashMap<>();
			attributeUpdateListener = (instance) -> {
				updateAttributes = true;
				if (changedAttributes == null)
					changedAttributes = new HashSet<>();
				changedAttributes.add(instance);
			};
		}
		AttributeInstance instance = attributes.get(attribute);
		if (instance != null)
			return instance;
		instance = new AttributeInstance(attribute, 0.0, attributeUpdateListener); // TODO implement getDefaultAttributeValue somewhere
		attributes.put(attribute, instance); 
		return instance;
	}
	
	@Override
	public boolean hasAttribute(Attribute attribute) {
		return attribute != null && attributes.containsKey(attribute);
	}

	@Override
	public void setDeathAnimationTime(int time) {
		this.deathAnimationTime = (short) time;
	}

	@Override
	public void setFallFlying(boolean fallFlying) {
		this.fallFlying = fallFlying; 
	}

	@Override
	public void setHurtAnimationTime(int time) {
		this.hurtTime = (short) time;
	}


	@Override
	public int getHurtAnimationTime() {
		return hurtTime;
	}

	@Override
	public boolean isFallFlying() {
		return fallFlying;
	}

	@Override
	public int getDeathAnimationTime() {
		return deathAnimationTime;
	}

	@Override
	public boolean hasAttributes() {
		return attributes != null && !attributes.isEmpty();
	}
	
	protected boolean hasBrain() {
		return false;
	}
	
	protected void writeBrain(NBTWriter writer, boolean systemData) throws IOException {}

	@Override
	public void setRemoveWhenFarAway(boolean remove) {
		this.removeWhenFaraway = remove;
	}

	@Override
	public boolean getRemoveWhenFarAway() {
		return removeWhenFaraway;
	}

	@Override
	public EntityEquipment getEquipment() {
		return null;
	}

	@Override
	public void setAttackTime(int time) {
		attackTime = (short) time;
	}

	@Override
	public int getAttackTime() {
		return attackTime;
	}

	@Override
	public Collection<PotionEffect> getActivePotionEffects() {
		return activeEffects == null ? List.of() : activeEffects.values();
	}

	@Override
	public boolean hasPotionEffects() {
		return activeEffects != null && !activeEffects.isEmpty();
	}
	
	@Override
	public boolean hasPotionEffect(PotionEffectType type) {
		return activeEffects != null && activeEffects.containsKey(type);
	}
	
	@Override
	public PotionEffect getPotionEffect(PotionEffectType type) {
		return activeEffects != null ? activeEffects.get(type) : null;
	}

	@Override
	public void removePotionEffect(PotionEffectType type) {
		if (!hasPotionEffects())
			return;
		if (!activeEffects.containsKey(type))
			return;
		if (effects != null && !effects.isEmpty()) {
			for (int i = 0; i < effects.size(); i++) {
				if (effects.get(i).getType() == type)
					effects.remove(i--);
			}
		}
		activeEffects.remove(type).removeEffect(this);
		sendRemoveEntityEffect(type);
	}
	
	@Override
	public void removePotionEffects() {
		if (!hasPotionEffects())
			return;
		Set<PotionEffectType> keys = activeEffects.keySet();
		PotionEffectType[] types = new PotionEffectType[keys.size()];
		keys.toArray(types);
		for (PotionEffectType type : types) {
			removePotionEffect(type);
		}
 	}
	
	/**
	 * Sends a new PotionEffect to the current viewers
	 * @param effect
	 */
	protected void sendAddEntityEffect(PotionEffect effect) {
		PacketOutEntityEffect packet = new PacketOutEntityEffect();
		packet.entityID = getID();
		packet.setEffect(effect);
		for (Player viewer : viewers) {
			PlayerConnection con = viewer.getConnection();
			con.sendPacked(packet);
		}
	}
	
	/**
	 * Sends the removal of a PotionEffect to the current viewers
	 * @param type
	 */
	protected void sendRemoveEntityEffect(PotionEffectType type) {
		PacketOutRemoveEntityEffect packet = new PacketOutRemoveEntityEffect();
		packet.entityID = getID();
		packet.effectID = type.getID();
		for (Player viewer : viewers) {
			PlayerConnection con = viewer.getConnection();
			con.sendPacked(packet);
		}
	}
	
	/**
	 * Should be called when a new viewer is added to send the current active effects
	 * @param viewer
	 */
	protected void sendEntityEffects(Player viewer) {
		PlayerConnection con = viewer.getConnection();
		for (PotionEffect effect : getActivePotionEffects()) {
			PacketOutEntityEffect packet = new PacketOutEntityEffect();
			packet.entityID = getID();
			packet.setEffect(effect);
			con.sendPacked(packet);
		}
	}
	
	/**
	 * Should be called when a new viewer is added to send the current attributes
	 * @param viewer
	 */
	protected void sendAttributes(Player viewer) {
		if (updateAttributes) // escape because all attributes are send on he next tick
			return;
		PlayerConnection con = viewer.getConnection();
		PacketOutUpdateAttributes packet = new PacketOutUpdateAttributes();
		packet.entityID = getID();
		packet.setCopyAttributes(attributes.values());
		con.sendPacked(packet);
	}
	
	@Override
	protected void doTick() {
		super.doTick();
		if (hasPotionEffects()) {
			if (effects != null && !effects.isEmpty()) {
				for (int i = 0; i < effects.size(); i++) {
					if (effects.get(i).tick(this, false) > -1)
						continue;
					effects.remove(i--);
				}
				
			}
			Iterator<PotionEffect> it = activeEffects.values().iterator();
			while (it.hasNext()) {
				PotionEffect activeEffect = it.next();
				PotionEffectType type = activeEffect.getType();
				if (activeEffect.tick(this, true) > -1)
					continue;
				// remove expired active effect and try to replace
				activeEffect = null;
				int highest = 0;
				int index = 0;
				for (PotionEffect effect : effects) {
					if (effect.getType() != type) {
						index++;
						continue;
					}
					if (activeEffect == null) {
						activeEffect = effect;
						highest = index++;
						continue;
					}
					if (activeEffect.getAmplifier() > effect.getAmplifier() ||
							activeEffect.getAmplifier() == effect.getAmplifier() && 
							activeEffect.getDuration() >= effect.getDuration()) {
						index++;
						continue;
					}
					activeEffect = effect;
					highest = index++;
				}
				if (activeEffect != null) {
					activeEffects.replace(type, activeEffect).removeEffect(this);
					effects.remove(highest);
					activeEffect.addEffect(this);
					sendAddEntityEffect(activeEffect);
				} else {
					it.remove();
					sendRemoveEntityEffect(type);
				}
			}
		}
	}
	
	@Override
	protected void update() {
		super.update();
		if (updateAttributes) {
			updateAttributes = false;
			PacketOutUpdateAttributes packet = new PacketOutUpdateAttributes();
			packet.entityID = getID();
			packet.setCopyAttributes(changedAttributes);
			changedAttributes.clear();
			for (Player viewer : viewers) {
				PlayerConnection con = viewer.getConnection();
				con.sendPacked(packet);
			}
		}
	}

	@Override
	public void damage(float damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Projectile launchProjectile(Projectile projectile, Vector3d velocity) {
		if (projectile == null)
			throw new IllegalArgumentException("Projectile can not be null!");
		World world = getWorld();
		ProjectileLounchEvent event = new ProjectileLounchEvent(projectile, world, loc.x, loc.y + getEyeHeight(), loc.z, loc.pitch, loc.yaw);
		HandlerList.callEvent(event);
		if (event.isCancelled())
			return null;
		projectile.spawn(world, event.getX(), event.getY(), event.getZ(), event.getPitch(), event.getYaw());
		if (velocity != null)
			projectile.setVelocity(velocity);
		return projectile;
	}

	@Override
	public Projectile launchProjectile(EntityType type, Vector3d velocity) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		Projectile pro = (Projectile) type.createEntity();
		return launchProjectile(pro, velocity);
	}
	
	@Override
	public double getEyeHeight() {
		return 0;
	}

	@Override
	public Location getEyeLocation() {
		return getEyeLocation(new Location(loc));
	}

	@Override
	public Location getEyeLocation(Location location) {
		loc.copyTo(location);
		location.y += getEyeHeight();
		return location;
	}

	@Override
	public SimpleLocation getEyeLocation(SimpleLocation location) {
		loc.copyTo(location);
		location.y += getEyeHeight();
		return location;
	}

	@Override
	public String getTeam() {
		return team;
	}

	@Override
	public void setTeam(String team) {
		this.team = team;
	}

	@Override
	public Vector3i getSleepingPositionUnsafe() {
		return sleepingPos;
	}

	@Override
	public Vector3i getSleepingPosition() {
		return new Vector3i(sleepingPos);
	}

	@Override
	public void setSleeptingPosition(Vector3i pos) {
		sleepingPos.set(pos);
	}

	@Override
	public boolean isPersistent() {
		return persistent;
	}

	@Override
	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}

	@Override
	public boolean hasNoAI() {
		return noAI;
	}

	@Override
	public void setNoAi(boolean ai) {
		this.noAI = ai;
	}

	@Override
	public boolean isLeftHanded() {
		return isLefHanded;
	}

	@Override
	public void setLeftHanded(boolean left) {
		this.isLefHanded = left;
	}

	@Override
	public int getLastHurtTime() {
		return lastHurtTime;
	}

	@Override
	public void setLastHurtTime(int time) {
		this.lastHurtTime = time;
	}

	@Override
	public int getHomeRadius() {
		return homeRadius;
	}

	@Override
	public void setHomeRadius(int radius) {
		this.homeRadius = radius;
	}

	@Override
	public Vector3i getHomePositionUnsafe() {
		return homePos;
	}

	@Override
	public Vector3i getHomePosition() {
		return new Vector3i(homePos);
	}

	@Override
	public void setHomePosition(Vector3i pos) {
		this.homePos.set(pos);
	}

	@Override
	public boolean canPickUpLoot() {
		return canPickupLoot;
	}

	@Override
	public void setPickUpLoot(boolean can) {
		this.canPickupLoot = can;
	}

}
