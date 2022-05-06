package de.atlascore.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

import de.atlascore.inventory.EntityEquipment;
import de.atlasmc.Color;
import de.atlasmc.Material;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeInstance;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.entity.Player;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutEntityEffect;
import de.atlasmc.io.protocol.play.PacketOutRemoveEntityEffect;
import de.atlasmc.io.protocol.play.PacketOutSpawnLivingEntity;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.util.ViewerSet;
import de.atlasmc.util.map.ArrayListMultimap;
import de.atlasmc.util.map.Multimap;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreLivingEntity extends CoreEntity implements LivingEntity {

	protected static final BiConsumer<Entity, Player>
		VIEWER_ADD_FUNCTION = (holder, viewer) -> {
			PlayerConnection con = viewer.getConnection();
			PacketOutSpawnLivingEntity packet = con.createPacket(PacketOutSpawnLivingEntity.class);
			packet.setEntity((LivingEntity) holder);
			con.sendPacked(packet);
			((CoreLivingEntity) holder).sendMetadata(viewer);
			((CoreLivingEntity) holder).sendEntityEffects(viewer);
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
	META_POTION_EFFECT_COLOR = new MetaDataField<>(CoreEntity.LAST_META_INDEX+3, 0, MetaDataType.INT);
	protected static final MetaDataField<Boolean>
	META_REDUCE_POTION_AMBIENT = new MetaDataField<>(CoreEntity.LAST_META_INDEX+4, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Integer>
	META_DISPLAY_ARROWS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+5, 0, MetaDataType.INT);
	protected static final MetaDataField<Integer>
	META_DISPLAY_BEE_STINGERS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+6, 0, MetaDataType.INT);
	protected static final MetaDataField<Long>
	META_OCCUPIED_BED_POSITION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+7, null, MetaDataType.OPT_POSITION);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+7;
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	NBT_ABSORPTION_AMOUNT = CharKey.of("AbsorptionAmount"),
	NBT_ACTIVE_EFFECTS = CharKey.of("ActiveEffects"),
	NBT_AMBIENT = CharKey.of("Ambient"),
	NBT_AMPLIFIER = CharKey.of("Amplifier"),
	NBT_DURATION = CharKey.of("Duration"),
	NBT_SHOW_PARTICLES = CharKey.of("ShowParticles"),
	NBT_SHOW_ICON = CharKey.of("ShowIcon"),
	NBT_ATTRIBUTES = CharKey.of("Attributes"),
	NBT_NAME = CharKey.of("Name"),
	NBT_BASE = CharKey.of("Base"),
	NBT_MODIFIERS = CharKey.of("Modifiers"),
	NBT_AMOUNT = CharKey.of("Amount"),
	NBT_OPERATION = CharKey.of("Operation"),
	NBT_BRAIN = CharKey.of("Brain"),
	NBT_DEATH_TIME = CharKey.of("DeathTime"),
	NBT_FALL_FLYING = CharKey.of("FallFlying"),
	NBT_HEALTH = CharKey.of("Health"),
	NBT_HURT_BY_TIMESTAMP = CharKey.of("HurtByTimeStamp"),
	NBT_HURT_TIME = CharKey.of("HurtTime"),
	NBT_ARMOR_DROP_CHANCE = CharKey.of("ArmorDropChance"),
	NBT_ARMOR_ITEMS = CharKey.of("ArmorItems"),
	NBT_ATTACK_TIME = CharKey.of("AttackTime"),
	NBT_DEATH_LOOT_TABLE = CharKey.of("DeathLootTable"),
	NBT_DEATH_LOOT_TABLE_SEED = CharKey.of("DeathLootTableSeed"),
	NBT_HAND_DROP_CHANCE = CharKey.of("HandDropChance"),
	NBT_HAND_ITEMS = CharKey.of("HandItems"),
	NBT_LEASH = CharKey.of("Leash"),
	NBT_LEASHED = CharKey.of("Leashed"),
	NBT_PERSISTENCE_REQUIRED = CharKey.of("PeristenceRequired");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreEntity.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_ABSORPTION_AMOUNT, (holder, reader) -> {
			if (holder instanceof LivingEntity) {
				((LivingEntity) holder).setAbsorption(reader.readFloatTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_ACTIVE_EFFECTS, (holder, reader) -> {
			if (holder instanceof LivingEntity) {
				LivingEntity entity = (LivingEntity) holder;
				reader.readNextEntry();
				while (reader.getRestPayload() > 0) {
					boolean reduceAmbient = false;
					int amplifier = 0;
					int duration = 0;
					int id = -1;
					boolean showParticles = true;
					boolean showIcon = true;
					while (reader.getType() != TagType.TAG_END) {
						final CharSequence value = reader.getFieldName();
						if (NBT_AMBIENT.equals(value))
							reduceAmbient = reader.readByteTag() == 1;
						else if (NBT_AMPLIFIER.equals(value))
							amplifier = reader.readByteTag();
						else if (NBT_DURATION.equals(value))
							duration = reader.readIntTag();
						else if (NBT_ID.equals(value))
							id = reader.readByteTag();
						else if (NBT_SHOW_PARTICLES.equals(value))
							showParticles = reader.readByteTag() == 1;
						else if (NBT_SHOW_ICON.equals(value))
							showIcon = reader.readByteTag() == 1;
						else
							reader.skipTag();
					}
					PotionEffectType type = PotionEffectType.getByID(id);
					if (duration <= 0 || type == null) {
						reader.readNextEntry();
						continue;
					}
					reader.readNextEntry();
					entity.addPotionEffect(type, amplifier, duration, reduceAmbient, showParticles, showIcon);
				}
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_ATTRIBUTES, (holder, reader) -> {
			if (holder instanceof LivingEntity) {
				LivingEntity entity = (LivingEntity) holder;
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
								String name = null;
								Operation operation = null;
								UUID uuid = null;
								while (reader.getType() != TagType.TAG_END) { // read AttributeModifier compound
									final CharSequence modifierKey = reader.getFieldName();
									if (NBT_AMOUNT.equals(modifierKey))
										amount = reader.readDoubleTag();
									else if (NBT_NAME.equals(modifierKey))
										name = reader.readStringTag();
									else if (NBT_OPERATION.equals(modifierKey))
										operation = Operation.getByID(reader.readIntTag());
									else if (NBT_UUID.equals(modifierKey))
										uuid = reader.readUUID();
									else
										reader.skipTag();
								}
								reader.readNextEntry(); // assemble Modifier
								if (modifiers == null)
									modifiers = new ArrayList<>();
								modifiers.add(new AttributeModifier(uuid, name, amount, operation, null));
							}
						}
					}
					reader.readNextEntry(); // assemble Attribute
					if (attribute == null)
						continue;
					AttributeInstance instance = entity.getAttribute(attribute);
					instance.setBaseValue(base);
					instance.setModifiers(modifiers);
				}
			} else reader.skipTag();
		});
		NBT_FIELDS.setContainer(NBT_BRAIN).setUnknownFieldHandler(NBTField.SKIP); // TODO skipped unknown brain contents till further use
		NBT_FIELDS.setField(NBT_DEATH_TIME, (holder, reader) -> {
			if (holder instanceof LivingEntity) {
				((LivingEntity) holder).setDeathAnimationTime(reader.readShortTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_FALL_FLYING, (holder, reader) -> {
			if (holder instanceof LivingEntity) {
				((LivingEntity) holder).setFallFlying(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_HEALTH, (holder, reader) -> {
			if (holder instanceof LivingEntity) {
				((LivingEntity) holder).setHealth(reader.readFloatTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_HURT_BY_TIMESTAMP, NBTField.SKIP); // TODO useless
		NBT_FIELDS.setField(NBT_HURT_TIME, (holder, reader) -> {
			if (holder instanceof LivingEntity) {
				((LivingEntity) holder).setHurtAnimationTime(reader.readShortTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_ARMOR_DROP_CHANCE, (holder, reader) -> {
			if (holder instanceof LivingEntity) {
				EntityEquipment equip = ((LivingEntity) holder).getEquipment();
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
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_ARMOR_ITEMS, (holder, reader) -> {
			if (holder instanceof LivingEntity) {
				EntityEquipment equip = ((LivingEntity) holder).getEquipment();
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
					Material mat = null;
					if (!NBT_ID.equals(reader.getFieldName())) {
						reader.mark();
						reader.search(NBT_ID);
						mat = Material.getByName(reader.readStringTag());
						reader.reset();
					} else mat = Material.getByName(reader.readStringTag());
					ItemStack item = new ItemStack(mat);
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
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_ATTACK_TIME, (holder, reader) -> {
			if (holder instanceof LivingEntity) {
				((LivingEntity) holder).setAttackTime(reader.readShortTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_DEATH_LOOT_TABLE, NBTField.SKIP); // TODO skipped until loot table implementation
		NBT_FIELDS.setField(NBT_DEATH_LOOT_TABLE_SEED, NBTField.SKIP); // TODO skipped until loot table implementation
		NBT_FIELDS.setField(NBT_HAND_DROP_CHANCE, (holder, reader) -> {
			if (holder instanceof LivingEntity) {
				EntityEquipment equip = ((LivingEntity) holder).getEquipment();
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
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_HAND_ITEMS, (holder, reader) -> {
			if (holder instanceof LivingEntity) {
				EntityEquipment equip = ((LivingEntity) holder).getEquipment();
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
					Material mat = null;
					if (!NBT_ID.equals(reader.getFieldName())) {
						reader.mark();
						reader.search(NBT_ID);
						mat = Material.getByName(reader.readStringTag());
						reader.reset();
					} else mat = Material.getByName(reader.readStringTag());
					ItemStack item = new ItemStack(mat);
					item.fromNBT(reader);
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
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_LEASH, NBTField.SKIP); // TODO skipped for reasons i dont know
		NBT_FIELDS.setField(NBT_LEASHED, NBTField.SKIP); // TODO see NBT_LEASH
		NBT_FIELDS.setField(NBT_PERSISTENCE_REQUIRED, (holder, reader) -> {
			if (holder instanceof LivingEntity) {
				((LivingEntity) holder).setRemoveWhenFarAway(reader.readByteTag() == 0);
			} else reader.skipTag();
		});
	}
	
	private float health;
	private float absorption;
	private Map<Attribute, AttributeInstance> attributes;
	private List<PotionEffect> effects;
	private Map<PotionEffectType, PotionEffect> activeEffects;
	private short deathAnimationTime;
	private short hurtTime;
	private short attackTime;
	private boolean fallFlying;
	private boolean removeWhenFaraway;
	
	public CoreLivingEntity(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected ViewerSet<Entity, Player> createViewerSet() {
		return new ViewerSet<Entity, Player>(this, VIEWER_ADD_FUNCTION, VIEWER_REMOVE_FUNCTION);
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
		metaContainer.get(META_HEALTH).setData(health);
	}

	@Override
	public float getHealth() {
		return health;
	}

	@Override
	public void setHealth(float health) {
		this.health = health;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addAttributeModifier(Attribute attribute, AttributeModifier modifier) {
		return false;
	}

	@Override
	public boolean hasAttributeModifiers() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return false;
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
		internalAddPotionEffect(type.createEffect(amplifier, duration));
	}
	
	@Override
	public void addPotionEffect(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon) {
		internalAddPotionEffect(type.createEffect(amplifier, duration, reducedAmbient, particles, icon));
	}
	
	@Override
	public void addPotionEffect(PotionEffect effect) {
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
		if (attributes == null)
			attributes = new HashMap<>();
		AttributeInstance instance = attributes.get(attribute);
		if (instance != null)
			return instance;
		instance = new AttributeInstance(attribute, 0.0); // TODO implement getDefaultAttributeValue somewhere
		attributes.put(attribute, instance); 
		return instance;
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
				writer.writeByteTag(NBT_AMBIENT, effect.hasReducedAmbient());
				writer.writeByteTag(NBT_AMPLIFIER, effect.getAmplifier());
				writer.writeIntTag(NBT_DURATION, effect.getDuration());
				writer.writeByteTag(NBT_ID, effect.getType().getID());
				writer.writeByteTag(NBT_SHOW_PARTICLES, effect.hasParticels());
				writer.writeByteTag(NBT_SHOW_ICON, effect.isShowingIcon());
				writer.writeEndTag();
			}
			if (effects != null)
				for (PotionEffect effect : effects) {
					writer.writeByteTag(NBT_AMBIENT, effect.hasReducedAmbient());
					writer.writeByteTag(NBT_AMPLIFIER, effect.getAmplifier());
					writer.writeIntTag(NBT_DURATION, effect.getDuration());
					writer.writeByteTag(NBT_ID, effect.getType().getID());
					writer.writeByteTag(NBT_SHOW_PARTICLES, effect.hasParticels());
					writer.writeByteTag(NBT_SHOW_ICON, effect.isShowingIcon());
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
					writer.writeStringTag(NBT_NAME, modifier.getName());
					writer.writeIntTag(NBT_OPERATION, modifier.getOperation().getID());
					writer.writeUUID(NBT_UUID, modifier.getUUID());
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
		writer.writeFloatTag(NBT_HEALTH, getHealth());
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
		if (effects != null || !effects.isEmpty()) {
			for (int i = 0; i < effects.size(); i++) {
				if (effects.get(i).getType() == type)
					effects.remove(i--);
			}
		}
		activeEffects.remove(type).removeEffect(this);
		sendRemoveEntityEffect(type);
	}
	
	protected void sendAddEntityEffect(PotionEffect effect) {
		for (Player viewer : viewers) {
			PlayerConnection con = viewer.getConnection();
			PacketOutEntityEffect packet = con.createPacket(PacketOutEntityEffect.class);
			packet.setEntityID(getID());
			packet.setEffect(effect);
			con.sendPacked(packet);
		}
	}
	
	protected void sendRemoveEntityEffect(PotionEffectType type) {
		for (Player viewer : viewers) {
			PlayerConnection con = viewer.getConnection();
			PacketOutRemoveEntityEffect packet = con.createPacket(PacketOutRemoveEntityEffect.class);
			packet.setEntityID(getID());
			packet.setEffectID(type.getID());
			con.sendPacked(packet);
		}
	}
	
	protected void sendEntityEffects(Player viewer) {
		PlayerConnection con = viewer.getConnection();
		for (PotionEffect effect : getActivePotionEffects()) {
			PacketOutEntityEffect packet = con.createPacket(PacketOutEntityEffect.class);
			packet.setEntityID(getID());
			packet.setEffect(effect);
			con.sendPacked(packet);
		}
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
	public void damage(double damage) {
		// TODO Auto-generated method stub
		
	}

}
