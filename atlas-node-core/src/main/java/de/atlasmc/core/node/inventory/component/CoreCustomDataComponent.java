package de.atlasmc.core.node.inventory.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.CustomDataComponent;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;
import de.atlasmc.util.nbt.tag.NBT;
import io.netty.buffer.ByteBuf;

public class CoreCustomDataComponent extends AbstractItemComponent implements CustomDataComponent {

	private Map<String, NBT> values;
	
	public CoreCustomDataComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreCustomDataComponent clone() {
		CoreCustomDataComponent clone = (CoreCustomDataComponent) super.clone();
		if (values != null) {
			clone.values = new HashMap<>(values);
		}
		return clone;
	}

	@Override
	public void add(NBT nbt) {
		if (nbt == null)
			throw new IllegalArgumentException("NBT can not b null!");
		String key = nbt.getName();
		if (key == null)
			throw new IllegalArgumentException("NBT does not have name!");
		getValues().put(key, nbt);
	}

	@Override
	public void removeNBT(NBT nbt) {
		if (values != null)
			values.remove(nbt.getName(), nbt);
	}

	@Override
	public void removeNBT(String key) {
		if (values != null)
			values.remove(key);
	}

	@Override
	public Map<String, NBT> getValues() {
		if (values == null)
			values = new HashMap<>();
		return values;
	}

	@Override
	public boolean hasValues() {
		return values != null && !values.isEmpty();
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		NBTNIOWriter writer = new NBTNIOWriter(buf, true);
		writeToNBT(writer, NBTSerializationContext.DEFAULT_CLIENT);
		writer.close();
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		NBTNIOReader reader = new NBTNIOReader(buf, true);
		if (values != null)
			values.clear();
		@SuppressWarnings("unchecked")
		NBTSerializationHandler<CustomDataComponent> handler = (NBTSerializationHandler<CustomDataComponent>) getNBTHandler();
		handler.deserialize(this, reader, NBTSerializationContext.DEFAULT_CLIENT);
		reader.close();
	}

}
