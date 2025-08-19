package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.DebugStickStateComponent;

public class CoreDebugStickStateComponent extends CoreAbstractBlockDataComponent implements DebugStickStateComponent {

	public CoreDebugStickStateComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreDebugStickStateComponent clone() {
		return (CoreDebugStickStateComponent) super.clone();
	}

}
