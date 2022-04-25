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
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreArrow extends CoreAbstractArrow implements Arrow {

	protected static final MetaDataField<Integer>
	META_COLOR = new MetaDataField<>(CoreAbstractArrow.LAST_META_INDEX+1, -1, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreAbstractArrow.LAST_META_INDEX+1;
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final String
	NBT_CUSTOM_POTION_COLOR = "CustomPotionColor",
	NBT_EFFECTS = "Effects",
	NBT_DURATION = "Duration",
	NBT_AMBIENT = "Ambient",
	NBT_AMPLIFIER = "Amplifier",
	NBT_SHOW_PARTICLES = "ShowParticles",
	NBT_SHOW_ICON = "ShowIcon",
	NBT_POTION = "Potion";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreAbstractArrow.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_CUSTOM_POTION_COLOR, (holder, reader) -> {
			if (holder instanceof Arrow) {
				((Arrow) holder).setColor(reader.readIntTag());
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_EFFECTS, (holder, reader) -> {
			if (holder instanceof Arrow) {
				Arrow entity = (Arrow) holder;
				reader.readNextEntry();
				while (reader.getRestPayload() > 0) {
					boolean reduceAmbient = false;
					int amplifier = 0;
					int duration = 0;
					int id = -1;
					boolean showParticles = true;
					boolean showIcon = true;
					while (reader.getType() != TagType.TAG_END) {
						switch (reader.getFieldName()) {
						case NBT_AMBIENT:
							reduceAmbient = reader.readByteTag() == 1;
							break;
						case NBT_AMPLIFIER:
							amplifier = reader.readByteTag();
							break;
						case NBT_DURATION:
							duration = reader.readIntTag();
							break;
						case NBT_ID:
							id = reader.readByteTag();
							break;
						case NBT_SHOW_PARTICLES:
							showParticles = reader.readByteTag() == 1;
							break;
						case NBT_SHOW_ICON:
							showIcon = reader.readByteTag() == 1;
							break;
						default:
							reader.skipTag();
							break;
						}
					}
					PotionEffectType type = PotionEffectType.getByID(id);
					if (duration <= 0 || type == null) {
						reader.readNextEntry();
						continue;
					}
					reader.readNextEntry();
					entity.addPotionEffect(new PotionEffect(type, duration, amplifier, reduceAmbient, showParticles, showIcon));
				}
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_POTION, (holder, reader) -> {
			if (holder instanceof Arrow) {
				((Arrow) holder).setPotionData(PotionData.getByName(reader.readStringTag()));
			} else reader.skipTag();
		});
	}
	
	private List<PotionEffect> effects;
	private PotionData data;
	
	public CoreArrow(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
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
		return color == -1 ? null : new Color(color);
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
