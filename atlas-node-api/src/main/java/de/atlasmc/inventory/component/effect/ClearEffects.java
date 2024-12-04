package de.atlasmc.inventory.component.effect;

public interface ClearEffects extends ComponentEffect {
	
	@Override
	default ConsumeEffectType getType() {
		return ConsumeEffectType.CLEAR_ALL_EFFECTS;
	}

}
