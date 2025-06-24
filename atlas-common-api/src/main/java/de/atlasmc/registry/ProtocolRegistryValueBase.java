package de.atlasmc.registry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.configuration.ConfigurationSection;

public abstract class ProtocolRegistryValueBase implements ProtocolRegistryValue {

	protected final NamespacedKey key;
	protected final NamespacedKey clientKey;
	protected final int id;
	
	protected ProtocolRegistryValueBase(NamespacedKey key, NamespacedKey clientKey, int id) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		this.key = key;
		if (clientKey == null) {
			this.clientKey = key;
		} else {
			this.clientKey = clientKey;
		}
		this.id = id;
	}
	
	protected ProtocolRegistryValueBase(String key, String clientKey, int id) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		this.key = NamespacedKey.literal(key);
		if (clientKey == null) {
			this.clientKey = this.key;
		} else {
			this.clientKey = NamespacedKey.literal(clientKey);
		}
		this.id = id;
	}
	
	protected ProtocolRegistryValueBase(ConfigurationSection cfg) {
		this(cfg.getString("key"), cfg.getString("clientKey"), cfg.getInt("id", -1));
	}
	
	protected ProtocolRegistryValueBase(NamespacedKey key, int id) {
		this(key, key, id);
	}
	
	protected ProtocolRegistryValueBase() {
		this(NamespacedKey.INLINE_DEFINITION, -1);
	}
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}
	
	@Override
	public NamespacedKey getClientKey() {
		return clientKey;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public boolean hasNBT() {
		return false;
	}

}
