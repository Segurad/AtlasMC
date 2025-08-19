package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.MapIDComponent;
import io.netty.buffer.ByteBuf;

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
