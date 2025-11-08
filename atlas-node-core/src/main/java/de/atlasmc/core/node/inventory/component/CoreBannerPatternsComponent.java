package de.atlasmc.core.node.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.block.tile.Banner.Pattern;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.BannerPatternsComponent;
import de.atlasmc.node.inventory.component.ComponentType;

public class CoreBannerPatternsComponent extends AbstractItemComponent implements BannerPatternsComponent {
	
	private static final int DEFAULT_PATTERNS_SIZE = 5;
	
	private List<Pattern> patterns;
	
	public CoreBannerPatternsComponent(ComponentType type) {
		super(type);
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

}
