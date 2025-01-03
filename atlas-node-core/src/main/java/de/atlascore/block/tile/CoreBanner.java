package de.atlascore.block.tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.DyeColor;
import de.atlasmc.Material;
import de.atlasmc.block.tile.Banner;
import de.atlasmc.chat.Chat;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBanner extends CoreTileEntity implements Banner {
	
	protected static final NBTFieldSet<CoreBanner> NBT_FIELDS;
	
	protected static final CharKey
	PATTERNS = CharKey.literal("Patterns"),
	COLOR = CharKey.literal("Color"),
	PATTERN = CharKey.literal("Pattern");
	
	static {
		NBT_FIELDS = CoreTileEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(PATTERNS, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				int color = -999;
				String pattern = null;
				while (reader.getType() != TagType.TAG_END) {
					final CharSequence value = reader.getFieldName();
					if (COLOR.equals(value))
						color = reader.readIntTag();
					else if (PATTERN.equals(value))
						pattern = reader.readStringTag();
				}
				reader.skipTag();
				if (color == -999 || pattern == null) continue;
				holder.addPattern(new Pattern(DyeColor.getByID(color), EnumPatternType.getByName(pattern)));
			}
		});
	}
	
	private List<Pattern> patterns;
	private Chat name;
	
	public CoreBanner(Material type) {
		super(type);
	}

	@Override
	public void addPattern(Pattern pattern) {
		if (patterns == null) patterns = new ArrayList<>();
		patterns.add(pattern);
	}

	@Override
	public DyeColor getBaseColor() {
		return DyeColor.getByBanner(getType());
	}

	@Override
	public void setBaseColor(DyeColor color, boolean wall) {
		if (wall) 
			setType(color.getWallBanner());
		else 
			setType(color.getBanner());
	}

	@Override
	public Pattern getPattern(int index) {
		if (patterns == null) return null;
		return patterns.get(index);
	}

	@Override
	public List<Pattern> getPatterns() {
		return patterns;
	}

	@Override
	public int numberOfPatterns() {
		if (patterns == null) return 0;
		return patterns.size();
	}

	@Override
	public Pattern removePattern(int index) {
		if (patterns == null) return null;
		return patterns.remove(index);
	}

	@Override
	public void setPattern(int index, Pattern pattern) {
		if (pattern == null) patterns = new ArrayList<>();
		if (patterns.size() > index) {
			patterns.set(index, pattern);
		} else patterns.add(index, pattern);
	}

	@Override
	public void setPatterns(List<Pattern> pattern) {
		if (patterns == null) patterns = new ArrayList<>();
		else patterns.clear();
		patterns.addAll(pattern);
	}
	
	@Override
	public Chat getCustomName() {
		return name;
	}

	@Override
	public void setCustomName(Chat chat) {
		this.name = chat;
	}
	
	@Override
	public boolean hasCustomName() {
		return name != null;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (systemData) {
			writer.writeStringTag(NBT_CUSTOM_NAME, name.toJsonText());
		}
		if (numberOfPatterns() > 0) {
			writer.writeListTag(PATTERNS, TagType.COMPOUND, numberOfPatterns());
			for (Pattern p : patterns) {
				writer.writeIntTag(COLOR, p.getColor().getID());
				writer.writeStringTag(PATTERN, p.getType().toString());
			}
			writer.writeEndTag();
		}
	}
	
	@Override
	protected NBTFieldSet<? extends CoreBanner> getFieldSetRoot() {
		return NBT_FIELDS;
	}

}
