package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.MapIDComponent;
import io.netty.buffer.ByteBuf;

public class CoreMapIDComponent extends AbstractItemComponent implements MapIDComponent {

	private int mapID;
	
	public CoreMapIDComponent(NamespacedKey key) {
		super(key);
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
	
	@Override
	public ComponentType getType() {
		return ComponentType.MAP_ID;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		// TODO Auto-generated method stub
		super.read(buf);
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		// TODO Auto-generated method stub
		super.write(buf);
	}

}
