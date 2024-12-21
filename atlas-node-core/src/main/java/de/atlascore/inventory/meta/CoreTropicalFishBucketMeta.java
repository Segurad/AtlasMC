package de.atlascore.inventory.meta;

import java.io.IOException;
import java.util.List;

import de.atlasmc.DyeColor;
import de.atlasmc.Material;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.TropicalFish.Pattern;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.TropicalFishBucketMeta;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTropicalFishBucketMeta extends CoreItemMeta implements TropicalFishBucketMeta {
	
	protected static final CharKey 
	NBT_BUCKET_VARIANT_TAG = CharKey.literal("BucketVariantTag"),
	NBT_ENTITY_TAG = CharKey.literal("EntityTag");
	
	static {
		NBT_FIELDS.setField(NBT_BUCKET_VARIANT_TAG, (holder, reader) -> {
			if (holder instanceof TropicalFishBucketMeta) {
				TropicalFishBucketMeta meta = ((TropicalFishBucketMeta) holder);
				int variant = reader.readIntTag();
				List<DyeColor> colors = DyeColor.getValues();
				meta.setPatternColor(colors.get(variant >> 24));
				meta.setBodyColor(colors.get((variant >> 16) & 0xFF));
				meta.setPattern(Pattern.getByDataID(variant));
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
		NBT_FIELDS.setField(NBT_ENTITY_TAG, (holder, reader) -> {
			if (holder instanceof TropicalFishBucketMeta meta) { 
				reader.readNextEntry();
				EntityType type = null;
				if (!NBT_ID.equals(reader.getFieldName())) {
					reader.mark();
					reader.search(NBT_ID);
					type = EntityType.getByName(reader.readStringTag());
					reader.reset();
				} else type = EntityType.getByName(reader.readStringTag());
				Entity ent = type.create(null);
				ent.fromNBT(reader);
				meta.setEntity(ent);
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	private DyeColor patternColor;
	private DyeColor bodyColor;
	private Pattern pattern;
	private Entity entity;
	
	public CoreTropicalFishBucketMeta(Material material) {
		super(material);
		bodyColor = DyeColor.ORANGE;
		patternColor = DyeColor.WHITE;
		pattern = Pattern.KOB;
	}

	@Override
	public CoreTropicalFishBucketMeta clone() {
		return (CoreTropicalFishBucketMeta) super.clone();
	}

	@Override
	public DyeColor getBodyColor() {
		return bodyColor;
	}

	@Override
	public Pattern getPattern() {
		return pattern;
	}

	@Override
	public DyeColor getPatternColor() {
		return patternColor;
	}

	@Override
	public void setBodyColor(DyeColor color) {
		if (color == null) 
			throw new IllegalArgumentException("Color can not be null!");
		this.bodyColor = color;
	}

	@Override
	public void setPattern(Pattern pattern) {
		if (pattern == null) 
			throw new IllegalArgumentException("Pattern can not be null!");
		this.pattern = pattern;
	}

	@Override
	public void setPatternColor(DyeColor color) {
		if (color == null) 
			throw new IllegalArgumentException("Color can not be null!");
		this.patternColor = color;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_BUCKET_VARIANT_TAG, pattern.getDataID() + bodyColor.ordinal() << 16 + patternColor.ordinal() << 24);
		if (entity != null) {
			writer.writeCompoundTag(NBT_ENTITY_TAG);
			entity.toNBT(writer, systemData);
			writer.writeEndTag();
		}
	}
	
	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage) {
		if (!super.isSimilar(meta, ignoreDamage))
			return false;
		TropicalFishBucketMeta bucketMeta = (TropicalFishBucketMeta) meta;
		if (getBodyColor() != bucketMeta.getBodyColor())
			return false;
		if (getPattern() != bucketMeta.getPattern())
			return false;
		if (getPatternColor() != bucketMeta.getPatternColor())
			return false;
		if (entity == null) {
			if (bucketMeta.getEntity() != null)
				return false;
		} else if (!entity.equals(bucketMeta.getEntity()))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bodyColor == null) ? 0 : bodyColor.hashCode());
		result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
		result = prime * result + ((patternColor == null) ? 0 : patternColor.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		return result;
	}

	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
