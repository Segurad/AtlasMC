package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.block.tile.Banner.PatternType;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.ProvidesBannerPatternsComponent;
import de.atlasmc.tag.TagKey;

public class CoreProvidesBannerPatternsComponent extends AbstractItemComponent implements ProvidesBannerPatternsComponent {

	private TagKey<PatternType> tag;
	
	public CoreProvidesBannerPatternsComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreProvidesBannerPatternsComponent clone() {
		return (CoreProvidesBannerPatternsComponent) super.clone();
	}

	@Override
	public TagKey<PatternType> getPatterns() {
		return tag;
	}

	@Override
	public void setPatterns(TagKey<PatternType> tag) {
		this.tag = tag;
	}

}
