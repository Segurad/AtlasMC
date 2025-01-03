package de.atlasmc.inventory.component.effect;

public interface TeleportRandomly extends ComponentEffect {
	
	float getDiameter();
	
	void setDiameter(float diameter);
	
	TeleportRandomly clone();
	
	@Override
	default ComponentEffectType getType() {
		return ComponentEffectType.TELEPORT_RANDOMLY;
	}

}
