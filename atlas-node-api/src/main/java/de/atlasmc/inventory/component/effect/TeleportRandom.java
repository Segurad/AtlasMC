package de.atlasmc.inventory.component.effect;

public interface TeleportRandom extends ComponentEffect {
	
	float getDiameter();
	
	void setDiameter(float diameter);
	
	@Override
	default ConsumeEffectType getType() {
		return ConsumeEffectType.TELEPORT_RANDOMLY;
	}

}
