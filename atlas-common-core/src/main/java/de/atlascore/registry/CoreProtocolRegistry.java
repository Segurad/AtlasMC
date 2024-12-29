package de.atlascore.registry;

import java.util.function.ToIntFunction;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.RegistryEntry;
import de.atlasmc.registry.RegistryHolder.Target;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class CoreProtocolRegistry<T extends Namespaced> extends CoreInstanceRegistry<T> implements ProtocolRegistry<T> {

	private volatile Serializer<T> serializer;
	private volatile Deserializer<T> deserializer;
	private volatile ToIntFunction<T> idSupplier;
	
	private final Int2ObjectMap<RegistryEntry<T>> ENTRY_BY_ID;
	
	public CoreProtocolRegistry(NamespacedKey key, Class<?> type) {
		super(key, type);
		ENTRY_BY_ID = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());
	}
	
	@Override
	protected void onAdd(RegistryEntry<T> entry) {
		ENTRY_BY_ID.remove(idSupplier.applyAsInt(entry.value()), entry);
	}
	
	@Override
	protected void onRemove(RegistryEntry<T> entry) {
		ENTRY_BY_ID.remove(idSupplier.applyAsInt(entry.value()), entry);
	}
	
	@Override
	public Target getTarget() {
		return Target.PROTOCOL;
	}

	@Override
	public T getByID(int id) {
		RegistryEntry<T> entry = ENTRY_BY_ID.get(id);
		return entry == null ? null : entry.value();
	}
	
	@Override
	public RegistryEntry<T> getEntryByID(int id) {
		return ENTRY_BY_ID.get(id);
	}

	@Override
	public void setIDSupplier(ToIntFunction<T> idSupplier) {
		this.idSupplier = idSupplier;
	}
	
	@Override
	public ToIntFunction<T> getIDSupplier() {
		return idSupplier;
	}

	@Override
	public void setDeserializer(Deserializer<T> deserializer) {
		this.deserializer = deserializer;
	}

	@Override
	public void setSerializer(Serializer<T> serializer) {
		this.serializer = serializer;
	}

	@Override
	public Deserializer<T> getDeserializer() {
		return deserializer;
	}

	@Override
	public Serializer<T> getSerializer() {
		return serializer;
	}

}
