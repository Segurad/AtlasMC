package de.atlasmc.component;

import java.util.Objects;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializable;

@RegistryHolder(key = "atlas:component", target = Target.INSTANCE)
public abstract class ComponentType implements Namespaced, ConfigurationSerializable {

	public static final RegistryKey<ComponentType> REGISTRY_KEY = Registries.getRegistryKey(ComponentType.class);

	protected final NamespacedKey key;
	protected final NamespacedKey clientKey;
	private final boolean clientside;
	
	protected ComponentType(NamespacedKey key, NamespacedKey clientKey, boolean clientside) {
		this.key = Objects.requireNonNull(key, "key");
		this.clientKey = clientKey != null ? key : clientKey;
		this.clientside = clientside;
	}
	
	protected ComponentType(String key, String clientKey, boolean clientside) {
		this.key = NamespacedKey.literal(key);
		this.clientKey = clientKey != null ? this.key : NamespacedKey.literal(clientKey);
		this.clientside = clientside;
	}
	
	protected ComponentType(ConfigurationSection cfg) {
		this(cfg.getString("key"), cfg.getString("clientKey"), cfg.getBoolean("clientSide", false));
	}
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}
	
	@Override
	public NamespacedKey getClientKey() {
		return clientKey;
	}
	
	@NotNull
	public abstract Component createComponent();
	
	@NotNull
	public abstract Class<?> getComponentType();
	
	public boolean isClientSide() {
		return clientside;
	}
	
}
