package de.atlascore.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.DebugStickStateComponent;

public class CoreDebugStickStateComponent extends CoreAbstractBlockDataComponent implements DebugStickStateComponent {

	public CoreDebugStickStateComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreDebugStickStateComponent clone() {
		return (CoreDebugStickStateComponent) super.clone();
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.DEBUG_STICK_STATE;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}

}
