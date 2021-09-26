package de.atlascore.block.tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.DyeColor;
import de.atlasmc.Material;
import de.atlasmc.block.tile.Banner;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreBanner extends CoreTileEntity implements Banner {
	
	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	PATTERNS = "Patterns",
	COLOR = "Color",
	PATTERN = "Pattern";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreTileEntity.NBT_FIELDS);
		NBT_FIELDS.setField(PATTERNS, (holder, reader) -> {
			if (holder instanceof Banner) {
				Banner banner = (Banner) holder;
				reader.readNextEntry();
				while (reader.getRestPayload() > 0) {
					final int depth = reader.getDepth();
					int color = -999;
					String pattern = null;
					while (depth <= reader.getDepth()) {
						switch (reader.getFieldName()) {
						case COLOR:
							color = reader.readIntTag();
							break;
						case PATTERN:
							pattern = reader.readStringTag();
							break;
						}
					}
					if (color == -999 || pattern == null) continue;
					banner.addPattern(new Pattern(DyeColor.getByID(color), PatternType.getByIdentifier(pattern)));
				}
			} else reader.skipTag();
		});
	}
	
	private List<Pattern> patterns;
	private ChatComponent name;
	
	public CoreBanner(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
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
		if (wall) setType(color.getWallBanner());
		else setType(color.getBanner());
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
	public ChatComponent getCustomName() {
		return name;
	}

	@Override
	public void setCustomName(ChatComponent chat) {
		this.name = chat;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (systemData) {
			writer.writeStringTag(NBT_CUSTOM_NAME, name.getJsonText());
		}
		if (numberOfPatterns() > 0) {
			writer.writeListTag(PATTERNS, TagType.COMPOUND, numberOfPatterns());
			for (Pattern p : patterns) {
				writer.writeIntTag(COLOR, p.getColor().getID());
				writer.writeStringTag(PATTERN, p.getType().getIdentifier());
			}
			writer.writeEndTag();
		}
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

}
