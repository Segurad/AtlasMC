package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.MapIDComponent;

public class CoreMapIDComponent extends AbstractItemComponent implements MapIDComponent {

	private int mapID;
	
	public CoreMapIDComponent(ComponentType type) {
		super(type);
	}
	
	public CoreMapIDComponent clone() {
		return (CoreMapIDComponent) super.clone();
	}

	@Override
	public int getMapID() {
		return mapID;
	}

	@Override
	public void setMapID(int id) {
		this.mapID = id;
	}

}
