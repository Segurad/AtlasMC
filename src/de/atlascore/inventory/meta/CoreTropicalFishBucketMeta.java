package de.atlascore.inventory.meta;

import java.io.IOException;

import de.atlasmc.DyeColor;
import de.atlasmc.Material;
import de.atlasmc.entity.TropicalFish.Pattern;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.TropicalFishBucketMeta;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTropicalFishBucketMeta extends CoreItemMeta implements TropicalFishBucketMeta {
	
	protected static final CharKey NBT_BUCKET_VARIANT_TAG = CharKey.of("BucketVariantTag");
	
	static {
		NBT_FIELDS.setField(NBT_BUCKET_VARIANT_TAG, (holder, reader) -> {
			if (holder instanceof TropicalFishBucketMeta) {
				TropicalFishBucketMeta meta = ((TropicalFishBucketMeta) holder);
				int variant = reader.readIntTag();
				DyeColor[] colors = DyeColor.values();
				meta.setPatternColor(colors[variant >> 24]);
				meta.setBodyColor(colors[(variant >> 16) & 0xFF]);
				meta.setPattern(Pattern.getByDataID(variant));
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	private DyeColor patternColor, bodyColor;
	private Pattern pattern;
	
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
		if (color == null) throw new IllegalArgumentException("Color can not be null!");
		this.bodyColor = color;
	}

	@Override
	public void setPattern(Pattern pattern) {
		if (pattern == null) throw new IllegalArgumentException("Pattern can not be null!");
		this.pattern = pattern;
	}

	@Override
	public void setPatternColor(DyeColor color) {
		if (color == null) throw new IllegalArgumentException("Color can not be null!");
		this.patternColor = color;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_BUCKET_VARIANT_TAG, pattern.getDataID() + bodyColor.ordinal() << 16 + patternColor.ordinal() << 24);
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
		return true;
	}

}
