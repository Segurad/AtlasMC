package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.DebugStickStateComponent;

public class CoreDebugStickStateComponent extends CoreAbstractBlockDataComponent implements DebugStickStateComponent {

	public CoreDebugStickStateComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreDebugStickStateComponent clone() {
		return (CoreDebugStickStateComponent) super.clone();
	}

}
