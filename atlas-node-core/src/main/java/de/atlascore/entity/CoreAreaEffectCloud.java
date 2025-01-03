package de.atlascore.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.Particle;
import de.atlasmc.entity.AreaEffectCloud;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.potion.PotionData;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.particle.ParticleObject;

public class CoreAreaEffectCloud extends CoreEntity implements AreaEffectCloud {
	
	protected static final MetaDataField<Float> 
	META_RADIUS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 0.5f, MetaDataType.FLOAT);
	protected static final MetaDataField<Integer>
	META_COLOR = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Boolean>
	META_IGNORE_RADIUS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<ParticleObject>
	META_PARTICLE = new MetaDataField<>(CoreEntity.LAST_META_INDEX + 4, new ParticleObject(Particle.EFFECT), MetaDataType.PARTICLE);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+4;
	
	protected static final NBTFieldSet<CoreAreaEffectCloud> NBT_FIELDS;
	
	protected static final CharKey
	NBT_AGE = CharKey.literal("age"),
	NBT_COLOR = CharKey.literal("Color"),
	NBT_DURATION = CharKey.literal("Duration"),
	NBT_EFFECTS = CharKey.literal("Effects"),
	NBT_AMBIENT = CharKey.literal("Ambient"),
	NBT_AMPLIFIER = CharKey.literal("Amplifier"),
	NBT_SHOW_PARTICLES = CharKey.literal("ShowParticles"),
	NBT_SHOW_ICON = CharKey.literal("ShowIcon"),
	NBT_OWNER = CharKey.literal("Owner"),
	NBT_PARTICLE = CharKey.literal("Particle"),
	NBT_RADIUS = CharKey.literal("Radius"),
	NBT_POTION = CharKey.literal("Potion"),
	NBT_RADIUS_ON_USE = CharKey.literal("RadiusOnUse"),
	NBT_RADIUS_PER_TICK = CharKey.literal("RadiusPerTick"),
	NBT_REAPPLICATION_DELAY = CharKey.literal("ReapplicationDelay"),
	NBT_WAIT_TIME = CharKey.literal("WaitTime");
	
	static {
		NBT_FIELDS = CoreEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_AGE, (holder, reader) -> {
			holder.setAge(reader.readShortTag());
		});
		NBT_FIELDS.setField(NBT_COLOR, (holder, reader) -> {
			holder.setColor(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_DURATION, (holder, reader) -> {
			holder.setMaxDuration(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_EFFECTS, (holder, reader) -> {
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
				holder.addPotionEffect(type.createEffect(amplifier, duration, reduceAmbient, showParticles, showIcon));
			}
		});
		NBT_FIELDS.setField(NBT_OWNER, (holder, reader) -> {
			holder.setOwner(reader.readUUID());
		});
		NBT_FIELDS.setField(NBT_PARTICLE, (holder, reader) -> {
			String raw = reader.readStringTag();
			Particle particle = Particle.getByName(raw.substring(0, raw.indexOf(' ')));
			if (particle == null)
				return;
			// TODO add Particle Object data
			holder.setParticle(new ParticleObject(particle));
		});
		NBT_FIELDS.setField(NBT_RADIUS, (holder, reader) -> {
			holder.setRadius(reader.readFloatTag());
		});
		NBT_FIELDS.setField(NBT_POTION, (holder, reader) -> {
			holder.setPotionData(PotionData.getByName(reader.readStringTag()));
		});
		NBT_FIELDS.setField(NBT_RADIUS_ON_USE, (holder, reader) -> {
			holder.setRadiusOnUse(reader.readFloatTag());
		});
		NBT_FIELDS.setField(NBT_RADIUS_PER_TICK, (holder, reader) -> {
			holder.setRadiusPerTick(reader.readFloatTag());
		});
		NBT_FIELDS.setField(NBT_REAPPLICATION_DELAY, (holder, reader) -> {
			holder.setReapplicationDelay(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_WAIT_TIME, (holder, reader) -> {
			holder.setInactiveTime(reader.readIntTag());
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
	
	public CoreAreaEffectCloud(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreAreaEffectCloud> getFieldSetRoot() {
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
		writer.writeStringTag(NBT_PARTICLE, getParticle().getParticle().getName());
		writer.writeFloatTag(NBT_RADIUS, getRadius());
		if (getPotionData() != null)
			writer.writeStringTag(NBT_POTION, getPotionData().getName());
		writer.writeFloatTag(NBT_RADIUS_ON_USE, getRadiusOnUse());
		writer.writeFloatTag(NBT_RADIUS_PER_TICK, getRadiusPerTick());
		writer.writeIntTag(NBT_REAPPLICATION_DELAY, getReapplicationDelay());
		writer.writeIntTag(NBT_WAIT_TIME, getInactiveTime());
	}

}
