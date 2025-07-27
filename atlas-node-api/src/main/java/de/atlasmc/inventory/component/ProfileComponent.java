package de.atlasmc.inventory.component;

import java.util.List;
import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.player.ProfileProperty;

public interface ProfileComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:profile");
	
	String getName();
	
	void setName(String name);
	
	UUID getUUID();
	
	void setUUID(UUID uuid);
	
	List<ProfileProperty> getProperties();
	
	boolean hasProperties();
	
	void addProperty(ProfileProperty property);
	
	void removeProperty(ProfileProperty property);
	
	ProfileComponent clone();

}
