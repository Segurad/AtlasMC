package de.atlascore.inventory.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.DyeColor;
import de.atlasmc.NamespacedKey;
import de.atlasmc.block.tile.Banner.EnumPatternType;
import de.atlasmc.block.tile.Banner.Pattern;
import de.atlasmc.block.tile.Banner.PatternType;
import de.atlasmc.block.tile.Banner.ResourcePatternType;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.BannerPatternsComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreBannerPatternsComponent extends AbstractItemComponent implements BannerPatternsComponent {

	protected static final CharKey
	NBT_COLOR = CharKey.literal("color"),
	NBT_PATTERN = CharKey.literal("pattern"),
	NBT_ASSET_ID = CharKey.literal("asset_id"),
	NBT_TRANSLATION_KEY = CharKey.literal("translation_key");
	
	private static final int DEFAULT_PATTERNS_SIZE = 5;
	
	private List<Pattern> patterns;
	
	public CoreBannerPatternsComponent(NamespacedKey key) {
		super(key);
	}

	@Override
	public void addPattern(Pattern pattern) {
		if (pattern == null)
			throw new IllegalArgumentException("Pattern can not be null!");
		getPatterns().add(pattern);
	}

	@Override
	public Pattern getPattern(int index) {
		return patterns.get(index);
	}

	@Override
	public List<Pattern> getPatterns() {
		if (patterns == null)
			patterns = new ArrayList<>(DEFAULT_PATTERNS_SIZE);
		return patterns;
	}

	@Override
	public int numberOfPatterns() {
		return patterns == null ? 0 : patterns.size();
	}

	@Override
	public Pattern removePattern(int index) {
		if (patterns == null)
			return null;
		return patterns.remove(index);
	}

	@Override
	public void setPattern(int index, Pattern pattern) {
		getPatterns().set(index, pattern);
	}

	@Override
	public void setPatterns(List<Pattern> pattern) {
		List<Pattern> current = getPatterns();
		current.clear();
		current.addAll(pattern);
	}
	
	@Override
	public CoreBannerPatternsComponent clone() {
		CoreBannerPatternsComponent clone = (CoreBannerPatternsComponent) super.clone();
		if (clone == null)
			return null;
		if (patterns != null && !patterns.isEmpty()) {
			clone.patterns = new ArrayList<>(patterns);
		}
		return clone;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (patterns == null || patterns.isEmpty())
			return;
		final int size = patterns.size();
		writer.writeListTag(getNamespacedKeyRaw(), TagType.COMPOUND, size);
		for (int i = 0; i < size; i++) {
			Pattern pattern = patterns.get(i);
			writer.writeStringTag(NBT_COLOR, pattern.getColor().getName());
			PatternType type = pattern.getType();
			if (type instanceof EnumPatternType enumType) {
				writer.writeStringTag(NBT_PATTERN, enumType.getName());
			} else if (type instanceof ResourcePatternType resource) {
				writer.writeCompoundTag(NBT_PATTERN);
				writer.writeStringTag(NBT_ASSET_ID, resource.getAssetID().toString());
				writer.writeStringTag(NBT_TRANSLATION_KEY, resource.getTranslationKey());
				writer.writeEndTag();
			}
		}
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			DyeColor color = null;
			PatternType pattern = null;
			while (reader.getType() != TagType.TAG_END) {
				CharSequence key = reader.getFieldName();
				if (NBT_COLOR.equals(key)) {
					color = DyeColor.getByName(reader.readStringTag());
				} else if (NBT_PATTERN.equals(key)) {
					if (reader.getType() == TagType.COMPOUND) {
						NamespacedKey asset = null;
						String translation = null;
						reader.readNextEntry();
						while (reader.getType() != TagType.TAG_END) {
							CharSequence pKey = reader.getFieldName();
							if (NBT_ASSET_ID.equals(pKey)) {
								asset = reader.readNamespacedKey();
							} else if (NBT_TRANSLATION_KEY.equals(pKey)) {
								translation = reader.readStringTag();
							} else {
								reader.skipTag();
							}
						}
						reader.readNextEntry();
						pattern = new ResourcePatternType(asset, translation);
					} else {
						pattern = EnumPatternType.getByName(reader.readStringTag());
					}
				} else {
					reader.skipTag();
				}
			}
			reader.readNextEntry();
			addPattern(new Pattern(color, pattern));
		}
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.BANNER_PATTERNS;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) {
		final int count = readVarInt(buf);
		if (count == 0) {
			return;
		}
		List<Pattern> patterns = getPatterns();
		patterns.clear();
		for (int i = 0; i < count; i++) {
			final int typeID = readVarInt(buf);
			PatternType type;
			if (typeID > 0) {
				type = EnumPatternType.getByID(typeID-1);
			} else {
				NamespacedKey key = readIdentifier(buf);
				String translation = readString(buf);
				type = new ResourcePatternType(key, translation);
			}
			DyeColor color = DyeColor.getByID(readVarInt(buf));
			patterns.add(new Pattern(color, type));
		}
	}
	
	@Override
	public void write(ByteBuf buf) {
		if (patterns == null || patterns.isEmpty()) {
			writeVarInt(0, buf);
			return;
		}
		final int size = patterns.size();
		writeVarInt(size, buf);
		for (int i = 0; i < size; i++) {
			Pattern pattern = patterns.get(i);
			PatternType type = pattern.getType();
			if (type instanceof EnumPatternType enumType) {
				writeVarInt(enumType.getID() + 1, buf);
			} else if (type instanceof ResourcePatternType resource){
				writeVarInt(0, buf);
				writeIdentifier(resource.getAssetID(), buf);
				writeString(resource.getTranslationKey(), buf);
			}
			writeVarInt(pattern.getColor().getID(), buf);
		}
	}

}
