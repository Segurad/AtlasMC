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
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.joml.Vector3d;

import de.atlasmc.Color;
import de.atlasmc.Location;
import de.atlasmc.NamespacedKey;
import de.atlasmc.SimpleLocation;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeInstance;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;
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
import de.atlasmc.inventory.ItemStack;
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
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;
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
	
	protected static final NBTFieldSet<CoreLivingEntity> NBT_FIELDS;
	
	protected static final CharKey
	NBT_ABSORPTION_AMOUNT = CharKey.literal("AbsorptionAmount"),
	NBT_ACTIVE_EFFECTS = CharKey.literal("ActiveEffects"),
	NBT_AMBIENT = CharKey.literal("Ambient"),
	NBT_AMPLIFIER = CharKey.literal("Amplifier"),
	NBT_DURATION = CharKey.literal("Duration"),
	NBT_SHOW_PARTICLES = CharKey.literal("ShowParticles"),
	NBT_SHOW_ICON = CharKey.literal("ShowIcon"),
	NBT_ATTRIBUTES = CharKey.literal("Attributes"),
	NBT_NAME = CharKey.literal("Name"),
	NBT_BASE = CharKey.literal("Base"),
	NBT_MODIFIERS = CharKey.literal("Modifiers"),
	NBT_AMOUNT = CharKey.literal("amount"),
	NBT_OPERATION = CharKey.literal("operation"),
	NBT_BRAIN = CharKey.literal("Brain"),
	NBT_DEATH_TIME = CharKey.literal("DeathTime"),
	NBT_FALL_FLYING = CharKey.literal("FallFlying"),
	NBT_HEALTH = CharKey.literal("Health"),
	NBT_HURT_BY_TIMESTAMP = CharKey.literal("HurtByTimeStamp"),
	NBT_HURT_TIME = CharKey.literal("HurtTime"),
	NBT_ARMOR_DROP_CHANCE = CharKey.literal("ArmorDropChance"),
	NBT_ARMOR_ITEMS = CharKey.literal("ArmorItems"),
	NBT_ATTACK_TIME = CharKey.literal("AttackTime"),
	NBT_DEATH_LOOT_TABLE = CharKey.literal("DeathLootTable"),
	NBT_DEATH_LOOT_TABLE_SEED = CharKey.literal("DeathLootTableSeed"),
	NBT_HAND_DROP_CHANCE = CharKey.literal("HandDropChance"),
	NBT_HAND_ITEMS = CharKey.literal("HandItems"),
	NBT_LEASH = CharKey.literal("Leash"),
	NBT_LEASHED = CharKey.literal("Leashed"),
	NBT_PERSISTENCE_REQUIRED = CharKey.literal("PeristenceRequired");
	
	static {
		NBT_FIELDS = CoreEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_ABSORPTION_AMOUNT, (holder, reader) -> {
			holder.setAbsorption(reader.readFloatTag());
		});
		NBT_FIELDS.setField(NBT_ACTIVE_EFFECTS, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				PotionEffect effect = PotionEffect.getFromNBT(reader);
				if (effect == null)
					continue;
				holder.internalAddPotionEffect(effect);
			}
			reader.readNextEntry();
		});
		NBT_FIELDS.setField(NBT_ATTRIBUTES, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) { // read list of Attribute
				Attribute attribute = null;
				double base = 0;
				List<AttributeModifier> modifiers = null;
				while (reader.getType() != TagType.TAG_END) { // read Attribute compound
					final CharSequence attributeKey = reader.getFieldName();
					if (NBT_NAME.equals(attributeKey))
						attribute = Attribute.getByName(reader.readStringTag());
					else if (NBT_BASE.equals(attributeKey))
						base = reader.readDoubleTag();
					else if (NBT_MODIFIERS.equals(attributeKey)) {
						reader.readNextEntry();
						while (reader.getRestPayload() > 0) { // read list of AttributeModifier
							double amount = 0;
							Operation operation = null;
							NamespacedKey id = null;
							while (reader.getType() != TagType.TAG_END) { // read AttributeModifier compound
								final CharSequence modifierKey = reader.getFieldName();
								if (NBT_AMOUNT.equals(modifierKey))
									amount = reader.readDoubleTag();
								else if (NBT_ID.equals(modifierKey))
									id = reader.readNamespacedKey();
								else if (NBT_OPERATION.equals(modifierKey))
									operation = Operation.getByName(reader.readStringTag());
								else
									reader.skipTag();
							}
							reader.readNextEntry(); // assemble Modifier
							if (modifiers == null)
								modifiers = new ArrayList<>();
							modifiers.add(new AttributeModifier(id, amount, operation, null));
						}
					}
				}
				reader.readNextEntry(); // assemble Attribute
				if (attribute == null)
					continue;
				AttributeInstance instance = holder.getAttribute(attribute);
				instance.setBaseValue(base);
				instance.setModifiers(modifiers);
			}
		});
		NBT_FIELDS.setSet(NBT_BRAIN).setUnknownFieldHandler(NBTField.skip()); // TODO skipped unknown brain contents till further use
		NBT_FIELDS.setField(NBT_DEATH_TIME, (holder, reader) -> {
			holder.setDeathAnimationTime(reader.readShortTag());
		});
		NBT_FIELDS.setField(NBT_FALL_FLYING, (holder, reader) -> {
			holder.setFallFlying(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_HEALTH, (holder, reader) -> {
			holder.setHealth(reader.readFloatTag());
		});
		NBT_FIELDS.setField(NBT_HURT_BY_TIMESTAMP, NBTField.skip()); // TODO useless
		NBT_FIELDS.setField(NBT_HURT_TIME, (holder, reader) -> {
			holder.setHurtAnimationTime(reader.readShortTag());
		});
		NBT_FIELDS.setField(NBT_ARMOR_DROP_CHANCE, (holder, reader) -> {
			EntityEquipment equip = holder.getEquipment();
			if (equip == null) {
				reader.skipTag();
				return;
			}
			int index = 0;
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				if (index > 3) {
					reader.skipTag();
					continue;
				}
				switch (index) {
				case 0:
					equip.setBootsDropChance(reader.readFloatTag());
					break;
				case 1:
					equip.setLeggingsDropChance(reader.readFloatTag());
					break;
				case 2:
					equip.setChestplateDropChance(reader.readFloatTag());
					break;
				case 3:
					equip.setHelmetDropChance(reader.readFloatTag());
					break;
				}
				index++;
			}
		});
		NBT_FIELDS.setField(NBT_ARMOR_ITEMS, (holder, reader) -> {
			EntityEquipment equip = holder.getEquipment();
			if (equip == null) {
				reader.skipTag();
				return;
			}
			int index = 0;
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				if (index > 3) {
					reader.skipTag();
					continue;
				}
				reader.readNextEntry();
				ItemStack item = ItemStack.getFromNBT(reader);
				item.fromNBT(reader);
				switch (index) {
				case 0:
					equip.setBoots(item);
					break;
				case 1:
					equip.setLeggings(item);
					break;
				case 2:
					equip.setChestplate(item);
					break;
				case 3:
					equip.setHelmet(item);
					break;
				}
				index++;
			}
			reader.readNextEntry();
		});
		NBT_FIELDS.setField(NBT_ATTACK_TIME, (holder, reader) -> {
			holder.setAttackTime(reader.readShortTag());
		});
		NBT_FIELDS.setField(NBT_DEATH_LOOT_TABLE, NBTField.skip()); // TODO skipped until loot table implementation
		NBT_FIELDS.setField(NBT_DEATH_LOOT_TABLE_SEED, NBTField.skip()); // TODO skipped until loot table implementation
		NBT_FIELDS.setField(NBT_HAND_DROP_CHANCE, (holder, reader) -> {
			EntityEquipment equip = holder.getEquipment();
			if (equip == null) {
				reader.skipTag();
				return;
			}
			int index = 0;
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				if (index > 1) {
					reader.skipTag();
					continue;
				}
				switch (index) {
				case 0:
					equip.setMainHandDropChance(reader.readFloatTag());
					break;
				case 1:
					equip.setOffHandChance(reader.readFloatTag());
					break;
				}
				index++;
			}
		});
		NBT_FIELDS.setField(NBT_HAND_ITEMS, (holder, reader) -> {
			EntityEquipment equip = holder.getEquipment();
			if (equip == null) {
				reader.skipTag();
				return;
			}
			int index = 0;
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				if (index > 1) {
					reader.skipTag();
					continue;
				}
				reader.readNextEntry();
				ItemStack item = ItemStack.getFromNBT(reader);
				switch (index) {
				case 0:
					equip.setMainHand(item);
					break;
				case 1:
					equip.setOffHand(item);
					break;
				}
				index++;
			}
		});
		NBT_FIELDS.setField(NBT_LEASH, NBTField.skip()); // TODO skipped for reasons i dont know
		NBT_FIELDS.setField(NBT_LEASHED, NBTField.skip()); // TODO see NBT_LEASH
		NBT_FIELDS.setField(NBT_PERSISTENCE_REQUIRED, (holder, reader) -> {
			holder.setRemoveWhenFarAway(reader.readByteTag() == 0);
		});
	}
	
	private boolean healthChanged;
	private double health;
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
	
	private boolean updateAttributes;
	private Set<AttributeInstance> changedAttributes;
	
	public CoreLivingEntity(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	protected NBTFieldSet<? extends CoreLivingEntity> getFieldSetRoot() {
		return NBT_FIELDS;
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
	public double getHealth() {
		return health;
	}

	@Override
	public void setHealth(double health) {
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
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeFloatTag(NBT_ABSORPTION_AMOUNT, getAbsorption());
		if (hasPotionEffects()) {
			int effectCount = activeEffects.size() + (effects != null ? effects.size() : 0);
			writer.writeListTag(NBT_ACTIVE_EFFECTS, TagType.COMPOUND, effectCount);
			for (PotionEffect effect : activeEffects.values()) {
				PotionEffect.toNBT(effect, writer, systemData);
				writer.writeEndTag();
			}
			if (effects != null)
				for (PotionEffect effect : effects) {
					PotionEffect.toNBT(effect, writer, systemData);
					writer.writeEndTag();
				}
		}
		if (hasAttributes()) {
			writer.writeListTag(NBT_ATTRIBUTES, TagType.COMPOUND, attributes.size());
			for (AttributeInstance instance : attributes.values()) {
				writer.writeStringTag(NBT_NAME, instance.getAttribute().getName());
				writer.writeDoubleTag(NBT_BASE, instance.getBaseValue());
				if (!instance.hasModifiers()) {
					writer.writeEndTag();
					continue;
				}
				writer.writeListTag(NBT_MODIFIERS, TagType.COMPOUND, instance.getModifierCount());
				for (AttributeModifier modifier : instance.getModifiers()) {
					writer.writeDoubleTag(NBT_AMOUNT, modifier.getAmount());
					writer.writeNamespacedKey(NBT_ID, modifier.getID());
					writer.writeStringTag(NBT_OPERATION, modifier.getOperation().getName());
					writer.writeEndTag();
				}
				writer.writeEndTag();
			}
		}
		if (hasBrain()) {
			writer.writeCompoundTag(NBT_BRAIN);
			writeBrain(writer, systemData);
			writer.writeEndTag();
		}
		writer.writeShortTag(NBT_DEATH_TIME, getDeathAnimationTime());
		if (isFallFlying())
			writer.writeByteTag(NBT_FALL_FLYING, true);
		writer.writeFloatTag(NBT_HEALTH, (float) getHealth());
		// TODO useless writer.writeIntTag(NBT_HURT_BY_TIMESTAMP, 0);
		writer.writeShortTag(NBT_HURT_TIME, getHurtAnimationTime());
		if (getEquipment() != null) {
			EntityEquipment equip = getEquipment();
			if (equip.hasArmorDropChance()) {
				writer.writeListTag(NBT_ARMOR_DROP_CHANCE, TagType.FLOAT, 4);
				writer.writeFloatTag(null, equip.getBootsDropChance());
				writer.writeFloatTag(null, equip.getLeggingsDropChance());
				writer.writeFloatTag(null, equip.getChestplateDropChance());
				writer.writeFloatTag(null, equip.getHelmetDropChance());
			}
			if (equip.hasArmor()) {
				writer.writeListTag(NBT_ARMOR_ITEMS, TagType.COMPOUND, 4);
				equip.getBoots().toNBT(writer, systemData);
				writer.writeEndTag();
				equip.getLeggings().toNBT(writer, systemData);
				writer.writeEndTag();
				equip.getChestplate().toNBT(writer, systemData);
				writer.writeEndTag();
				equip.getHelmet().toNBT(writer, systemData);
				writer.writeEndTag();
			}
			if (equip.hasHandItemDropChance()) {
				writer.writeListTag(NBT_HAND_DROP_CHANCE, TagType.FLOAT, 2);
				writer.writeFloatTag(null, equip.getMainHandDropChance());
				writer.writeFloatTag(null, equip.getOffHandDropChance());
			}
			if (equip.hasHandItem()) {
				writer.writeListTag(NBT_HAND_ITEMS, TagType.COMPOUND, 2);
				equip.getMainHand().toNBT(writer, systemData);
				writer.writeEndTag();
				equip.getOffHand().toNBT(writer, systemData);
				writer.writeEndTag();
			}
		}
		if (getAttackTime() > 0)
			writer.writeShortTag(NBT_ATTACK_TIME, getAttackTime());
		if (getRemoveWhenFarAway())
			writer.writeByteTag(NBT_PERSISTENCE_REQUIRED, true);
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
	public void damage(double damage) {
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
		Projectile pro = (Projectile) type.create(getWorld());
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

}
