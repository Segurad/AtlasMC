package de.atlascore.entity;

import de.atlasmc.Particle;
import de.atlasmc.entity.AreaEffectCloud;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.potion.PotionData;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;
import de.atlasmc.world.particle.ParticleObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CoreAreaEffectCloud extends CoreEntity implements AreaEffectCloud {
	
	protected static final MetaDataField<Float> 
	META_RADIUS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 0.5f, MetaDataType.FLOAT);
	protected static final MetaDataField<Integer>
	META_COLOR = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 0, MetaDataType.INT);
	protected static final MetaDataField<Boolean>
	META_IGNORE_RADIUS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<ParticleObject>
	META_PARTICLE = new MetaDataField<>(CoreEntity.LAST_META_INDEX + 4, new ParticleObject(Particle.EFFECT), MetaDataType.PARTICLE);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+4;
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	NBT_AGE = CharKey.of("age"),
	NBT_COLOR = CharKey.of("Color"),
	NBT_DURATION = CharKey.of("Duration"),
	NBT_EFFECTS = CharKey.of("Effects"),
	NBT_AMBIENT = CharKey.of("Ambient"),
	NBT_AMPLIFIER = CharKey.of("Amplifier"),
	NBT_SHOW_PARTICLES = CharKey.of("ShowParticles"),
	NBT_SHOW_ICON = CharKey.of("ShowIcon"),
	NBT_OWNER = CharKey.of("Owner"),
	NBT_PARTICLE = CharKey.of("Particle"),
	NBT_RADIUS = CharKey.of("Radius"),
	NBT_POTION = CharKey.of("Potion"),
	NBT_RADIUS_ON_USE = CharKey.of("RadiusOnUse"),
	NBT_RADIUS_PER_TICK = CharKey.of("RadiusPerTick"),
	NBT_REAPPLICATION_DELAY = CharKey.of("ReapplicationDelay"),
	NBT_WAIT_TIME = CharKey.of("WaitTime");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreEntity.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_AGE, (holder, reader) -> {
			if (holder instanceof AreaEffectCloud) {
				((AreaEffectCloud) holder).setAge(reader.readShortTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_COLOR, (holder, reader) -> {
			if (holder instanceof AreaEffectCloud) {
				((AreaEffectCloud) holder).setColor(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_DURATION, (holder, reader) -> {
			if (holder instanceof AreaEffectCloud) {
				((AreaEffectCloud) holder).setMaxDuration(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_EFFECTS, (holder, reader) -> {
			if (holder instanceof AreaEffectCloud) {
				AreaEffectCloud entity = (AreaEffectCloud) holder;
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
					entity.addPotionEffect(type.createEffect(amplifier, duration, reduceAmbient, showParticles, showIcon));
				}
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_OWNER, (holder, reader) -> {
			if (holder instanceof AreaEffectCloud) {
				((AreaEffectCloud) holder).setOwner(reader.readUUID());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_PARTICLE, (holder, reader) -> {
			if (holder instanceof AreaEffectCloud) {
				String raw = reader.readStringTag();
				Particle particle = Particle.getByNameID(raw.substring(0, raw.indexOf(' ')));
				if (particle == null)
					return;
				// TODO add Particle Object data
				((AreaEffectCloud) holder).setParticle(new ParticleObject(particle));
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_RADIUS, (holder, reader) -> {
			if (holder instanceof AreaEffectCloud) {
				((AreaEffectCloud) holder).setRadius(reader.readFloatTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_POTION, (holder, reader) -> {
			if (holder instanceof AreaEffectCloud) {
				((AreaEffectCloud) holder).setPotionData(PotionData.getByName(reader.readStringTag()));
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_RADIUS_ON_USE, (holder, reader) -> {
			if (holder instanceof AreaEffectCloud) {
				((AreaEffectCloud) holder).setRadiusOnUse(reader.readFloatTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_RADIUS_PER_TICK, (holder, reader) -> {
			if (holder instanceof AreaEffectCloud) {
				((AreaEffectCloud) holder).setRadiusPerTick(reader.readFloatTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_REAPPLICATION_DELAY, (holder, reader) -> {
			if (holder instanceof AreaEffectCloud) {
				((AreaEffectCloud) holder).setReapplicationDelay(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_WAIT_TIME, (holder, reader) -> {
			if (holder instanceof AreaEffectCloud) {
				((AreaEffectCloud) holder).setInactiveTime(reader.readIntTag());
			} else reader.skipTag();
		});
	}
	
	private int age;
	private int maxDuration;
	private float radiusPerTick;
	private float radiusOnUse;
	private int reapplicationDelay;
	private int inactiveTime;
	private UUID owner;
	private List<PotionEffect> potionEffects;
	private PotionData data;
	
	public CoreAreaEffectCloud(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_RADIUS);
		metaContainer.set(META_COLOR);
		metaContainer.set(META_IGNORE_RADIUS);
		metaContainer.set(META_PARTICLE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public float getRadius() {
		return metaContainer.getData(META_RADIUS);
	}

	@Override
	public int getColor() {
		return metaContainer.getData(META_COLOR);
	}

	@Override
	public boolean getIgnoreRadius() {
		return metaContainer.getData(META_IGNORE_RADIUS);
	}

	@Override
	public ParticleObject getParticle() {
		return metaContainer.getData(META_PARTICLE);
	}

	@Override
	public void setRadius(float radius) {
		metaContainer.get(META_RADIUS).setData(radius);
	}

	@Override
	public void setColor(int color) {
		metaContainer.get(META_COLOR).setData(color);
	}

	@Override
	public void setIngnoreRadius(boolean ignore) {
		metaContainer.get(META_IGNORE_RADIUS).setData(ignore);
	}

	@Override
	public void setParticle(ParticleObject particle) {
		metaContainer.get(META_PARTICLE).setData(particle);
	}

	@Override
	public void setAge(int ticks) {
		this.age = ticks;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setMaxDuration(int ticks) {
		this.maxDuration = ticks;
	}

	@Override
	public int getMaxDuration() {
		return maxDuration;
	}

	@Override
	public void addPotionEffect(PotionEffect potionEffect) {
		getPotionEffects().add(potionEffect);
	}

	@Override
	public List<PotionEffect> getPotionEffects() {
		if (potionEffects == null)
			potionEffects = new ArrayList<>();
		return potionEffects;
	}

	@Override
	public boolean hasPotionEffects() {
		return potionEffects != null && !potionEffects.isEmpty();
	}

	@Override
	public void removePotionEffect(PotionEffect effect) {
		if (!hasPotionEffects())
			return;
		getPotionEffects().remove(effect);
	}

	@Override
	public void setOwner(UUID owner) {
		this.owner = owner;
	}

	@Override
	public UUID getOwner() {
		return owner;
	}

	@Override
	public void setPotionData(PotionData data) {
		this.data = data;
	}

	@Override
	public PotionData getPotionData() {
		return data;
	}

	@Override
	public void setRadiusOnUse(float radius) {
		this.radiusOnUse = radius;
	}

	@Override
	public float getRadiusOnUse() {
		return radiusOnUse;
	}

	@Override
	public void setRadiusPerTick(float radius) {
		this.radiusPerTick = radius;
	}

	@Override
	public float getRadiusPerTick() {
		return radiusPerTick;
	}

	@Override
	public void setReapplicationDelay(int ticks) {
		this.reapplicationDelay = ticks;
	}

	@Override
	public int getReapplicationDelay() {
		return reapplicationDelay;
	}

	@Override
	public void setInactiveTime(int ticks) {
		this.inactiveTime = ticks;
	}

	@Override
	public int getInactiveTime() {
		return inactiveTime;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeShortTag(NBT_AGE, getAge());
		writer.writeIntTag(NBT_COLOR, getColor());
		writer.writeIntTag(NBT_DURATION, getMaxDuration());
		if (hasPotionEffects()) {
			List<PotionEffect> effects = getPotionEffects();
			writer.writeListTag(NBT_EFFECTS, TagType.COMPOUND, effects.size());
			for (PotionEffect effect : effects) {
				writer.writeByteTag(NBT_AMBIENT, effect.hasReducedAmbient());
				writer.writeByteTag(NBT_AMPLIFIER, effect.getAmplifier());
				writer.writeIntTag(NBT_DURATION, effect.getDuration());
				writer.writeIntTag(NBT_ID, effect.getType().getID());
				writer.writeByteTag(NBT_SHOW_PARTICLES, effect.hasParticels());
				writer.writeByteTag(NBT_SHOW_ICON, effect.isShowingIcon());
				writer.writeEndTag();
			}
		}
		if (getOwner() != null)
			writer.writeUUID(NBT_OWNER, getOwner());
		writer.writeStringTag(NBT_PARTICLE, getParticle().getParticle().getNameID());
		writer.writeFloatTag(NBT_RADIUS, getRadius());
		if (getPotionData() != null)
			writer.writeStringTag(NBT_POTION, getPotionData().getName());
		writer.writeFloatTag(NBT_RADIUS_ON_USE, getRadiusOnUse());
		writer.writeFloatTag(NBT_RADIUS_PER_TICK, getRadiusPerTick());
		writer.writeIntTag(NBT_REAPPLICATION_DELAY, getReapplicationDelay());
		writer.writeIntTag(NBT_WAIT_TIME, getInactiveTime());
	}

}
