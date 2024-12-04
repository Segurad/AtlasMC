package de.atlascore.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.Color;
import de.atlasmc.entity.Arrow;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.potion.PotionData;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreArrow extends CoreAbstractArrow implements Arrow {

	protected static final MetaDataField<Integer>
	META_COLOR = new MetaDataField<>(CoreAbstractArrow.LAST_META_INDEX+1, -1, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreAbstractArrow.LAST_META_INDEX+1;
	
	protected static final NBTFieldContainer<CoreArrow> NBT_FIELDS;
	
	protected static final CharKey
	NBT_CUSTOM_POTION_COLOR = CharKey.literal("CustomPotionColor"),
	NBT_EFFECTS = CharKey.literal("Effects"),
	NBT_DURATION = CharKey.literal("Duration"),
	NBT_AMBIENT = CharKey.literal("Ambient"),
	NBT_AMPLIFIER = CharKey.literal("Amplifier"),
	NBT_SHOW_PARTICLES = CharKey.literal("ShowParticles"),
	NBT_SHOW_ICON = CharKey.literal("ShowIcon"),
	NBT_POTION = CharKey.literal("Potion");
	
	static {
		NBT_FIELDS = CoreAbstractArrow.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_CUSTOM_POTION_COLOR, (holder, reader) -> {
			holder.setColor(reader.readIntTag());
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
		NBT_FIELDS.setField(NBT_POTION, (holder, reader) -> {
			holder.setPotionData(PotionData.getByName(reader.readStringTag()));
		});
	}
	
	private List<PotionEffect> effects;
	private PotionData data;
	
	public CoreArrow(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreArrow> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_COLOR);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public Color getColor() {
		int color = getColorRGB();
		return color == -1 ? null : Color.fromRGB(color);
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.ARROW;
	}

	@Override
	public void setColor(Color color) {
		setColor(color.asRGB());
	}

	@Override
	public int getColorRGB() {
		return metaContainer.getData(META_COLOR);
	}

	@Override
	public void setColor(int rgb) {
		if (rgb < 0)
			metaContainer.get(META_COLOR).setData(-1);
		else
			metaContainer.get(META_COLOR).setData(rgb & 0xFFFFFF);
	}

	@Override
	public void addPotionEffect(PotionEffect effect) {
		getPotionEffects().add(effect);
	}

	@Override
	public List<PotionEffect> getPotionEffects() {
		if (effects == null)
			effects = new ArrayList<>();
		return effects;
	}

	@Override
	public boolean hasPotionEffects() {
		return effects != null && !effects.isEmpty();
	}

	@Override
	public void removePotionEffect(PotionEffect effect) {
		if (!hasPotionEffects())
			return;
		getPotionEffects().remove(effect);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_CUSTOM_POTION_COLOR, getColorRGB());
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
		if (getPotionData() != null)
			writer.writeStringTag(NBT_POTION, getPotionData().getName());
	}

	@Override
	public void setPotionData(PotionData data) {
		this.data = data;
	}

	@Override
	public PotionData getPotionData() {
		return data;
	}

}
