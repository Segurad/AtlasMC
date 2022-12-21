package de.atlasmc.io.protocol.play;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.atlasmc.attribute.AttributeInstance;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_ENTITY_PROPERTIES)
public class PacketOutEntityProperties extends AbstractPacket implements PacketPlayOut {
	
	private int entityID;
	private List<AttributeInstance> attributes;
	
	public int getEntityID() {
		return entityID;
	}
	
	public List<AttributeInstance> getAttributes() {
		return attributes;
	}
	
	public void setEntity(int entityID) {
		this.entityID = entityID;
	}
	
	public void setAttributes(List<AttributeInstance> attributes) {
		this.attributes = attributes;
	}
	
	public void setCopyAttributes(Collection<AttributeInstance> attributes) {
		this.attributes = new ArrayList<>(attributes.size());
		for (AttributeInstance i : attributes) {
			AttributeInstance copyInstance = new AttributeInstance(i.getAttribute(), i.getDefaultValue(), null);
			copyInstance.setBaseValue(i.getBaseValue());
			List<AttributeModifier> modifiers = new ArrayList<>();
			for (AttributeModifier mod : i.getModifiers()) {
				modifiers.add(mod.clone());
			}
			copyInstance.setModifiers(modifiers);
		}
	}
	
	@Override
	public int getDefaultID() {
		return OUT_ENTITY_PROPERTIES;
	}

}
