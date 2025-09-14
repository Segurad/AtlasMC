package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.TrimComponent;

public class CoreTrimComponent extends AbstractItemComponent implements TrimComponent {

	private TrimMaterial material;
	private TrimPattern pattern;
	
	public CoreTrimComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreTrimComponent clone() {
		return (CoreTrimComponent) super.clone();
	}

	@Override
	public TrimMaterial getTrimMaterial() {
		return material;
	}

	@Override
	public void setTrimMaterial(TrimMaterial material) {
		this.material = material;
	}

	@Override
	public TrimPattern getTrimPattern() {
		return pattern;
	}

	@Override
	public void setTrimPattern(TrimPattern pattern) {
		this.pattern = pattern;
	}

}
