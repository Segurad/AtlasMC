package de.atlascore.inventory.component;

import de.atlasmc.block.tile.Banner.PatternType;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.ProvidesBannerPatternsComponent;
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
