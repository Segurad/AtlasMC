package de.atlascore.inventory.meta;

import java.io.IOException;

import de.atlasmc.DyeColor;
import de.atlasmc.Material;
import de.atlasmc.entity.TropicalFish.Pattern;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.TropicalFishBucketMeta;
import de.atlasmc.util.Validate;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTropicalFishBucketMeta extends CoreItemMeta implements TropicalFishBucketMeta {

	private DyeColor patternColor, bodyColor;
	private Pattern pattern;
	
	protected static final String BUCKET_VARIANT_TAG = "BucketVariantTag";
	
	static {
		NBT_FIELDS.setField(BUCKET_VARIANT_TAG, (holder, reader) -> {
			if (TropicalFishBucketMeta.class.isInstance(holder)) {
				TropicalFishBucketMeta meta = ((TropicalFishBucketMeta) holder);
				int variant = reader.readIntTag();
				DyeColor[] colors = DyeColor.values();
				meta.setPatternColor(colors[variant >> 24]);
				meta.setBodyColor(colors[(variant >> 16) & 0xFF]);
				meta.setPattern(Pattern.getByDataID(variant));
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
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
		Validate.notNull(color, "Color can not be null!");
		this.bodyColor = color;
	}

	@Override
	public void setPattern(Pattern pattern) {
		Validate.notNull(pattern, "Pattern can not be null!");
		this.pattern = pattern;
	}

	@Override
	public void setPatternColor(DyeColor color) {
		Validate.notNull(color, "Color can not be null!");
		this.patternColor = color;
	}
	
	@Override
	public void toNBT(NBTWriter writer, String local, boolean systemData) throws IOException {
		super.toNBT(writer, local, systemData);
		writer.writeIntTag(BUCKET_VARIANT_TAG, pattern.getDataID() + bodyColor.ordinal() << 16 + patternColor.ordinal() << 24);
	}

}