package de.atlascore.inventory.component;

import static de.atlasmc.io.PacketUtil.readIdentifier;
import static de.atlasmc.io.PacketUtil.readString;
import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeIdentifier;
import static de.atlasmc.io.PacketUtil.writeString;
import static de.atlasmc.io.PacketUtil.writeVarInt;

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
import io.netty.buffer.ByteBuf;

public class CoreBannerPatternsComponent extends AbstractItemComponent implements BannerPatternsComponent {
	
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
	public boolean hasPatterns() {
		return patterns != null && !patterns.isEmpty();
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
