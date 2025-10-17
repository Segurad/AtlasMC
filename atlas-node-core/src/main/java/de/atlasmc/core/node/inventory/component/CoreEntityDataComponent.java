package de.atlasmc.core.node.inventory.component;

import java.io.IOException;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.EntityDataComponent;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class CoreEntityDataComponent extends AbstractItemComponent implements EntityDataComponent {

	private Entity entity;
	
	public CoreEntityDataComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreEntityDataComponent clone() {
		CoreEntityDataComponent clone = (CoreEntityDataComponent) super.clone();
		if (entity != null)
			clone.entity = entity; // TODO copy entity
		return clone;
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
	public void read(ByteBuf buf) throws IOException {
		NBTNIOReader reader = new NBTNIOReader(buf, true);
		reader.readNextEntry();
		entity = Entity.NBT_HANDLER.deserialize(reader, CodecContext.DEFAULT_CLIENT);
		reader.close();
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		NBTNIOWriter writer = new NBTNIOWriter(buf, true);
		writer.writeCompoundTag();
		if (entity != null) {
			@SuppressWarnings("unchecked")
			NBTCodec<Entity> handler = (NBTCodec<Entity>) entity.getNBTCodec();
			handler.serialize(entity, writer, CodecContext.DEFAULT_CLIENT);
		}
		writer.writeEndTag();
	}

}
