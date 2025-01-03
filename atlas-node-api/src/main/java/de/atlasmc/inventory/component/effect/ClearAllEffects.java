package de.atlasmc.inventory.component.effect;

public interface ClearAllEffects extends ComponentEffect {
	
	ClearAllEffects clone();
	
	@Override
	default ComponentEffectType getType() {
		return ComponentEffectType.CLEAR_ALL_EFFECTS;
	}

}
