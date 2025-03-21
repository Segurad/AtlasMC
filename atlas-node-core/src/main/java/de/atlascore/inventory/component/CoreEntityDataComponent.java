package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.Entity;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.EntityDataComponent;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

public class CoreEntityDataComponent extends AbstractItemComponent implements EntityDataComponent {

	private Entity entity;
	
	public CoreEntityDataComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreEntityDataComponent clone() {
		CoreEntityDataComponent clone = (CoreEntityDataComponent) super.clone();
		if (entity != null)
			clone.entity = entity; // TODO copy entity
		return clone;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (entity == null)
			return;
		writer.writeCompoundTag(key.toString());
		entity.toNBT(writer, systemData);
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		entity = Entity.getFromNBT(reader);
	}

	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.ENTITY_DATA;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		NBTNIOReader reader = new NBTNIOReader(buf, true);
		reader.readNextEntry();
		entity = Entity.getFromNBT(reader);
		reader.close();
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		NBTNIOWriter writer = new NBTNIOWriter(buf, true);
		writer.writeCompoundTag();
		if (entity != null)
			entity.toNBT(writer, false);
		writer.writeEndTag();
	}

}
