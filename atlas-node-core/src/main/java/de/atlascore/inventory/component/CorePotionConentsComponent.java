package de.atlascore.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.PotionContentsComponent;
import de.atlasmc.potion.PotionData;

public class CorePotionConentsComponent extends CoreAbstractPotionEffectComponent implements PotionContentsComponent {

	private PotionData potion;
	private Color customColor;
	private Chat customName;
	
	public CorePotionConentsComponent(ComponentType type) {
		super(type);
	}

	@Override
	public PotionData getPotionData() {
		return potion;
	}

	@Override
	public void setPotionData(PotionData data) {
		this.potion = data;
	}

	@Override
	public boolean hasCustomData() {
		return customColor != null || customName != null || hasEffects();
	}

	@Override
	public Color getCustomColor() {
		return customColor;
	}

	@Override
	public void setCustomColor(Color color) {
		this.customColor = color;
	}

	@Override
	public Chat getCustomName() {
		return customName;
	}

	@Override
	public void setCustomName(Chat name) {
		this.customName = name;
	}
	
	@Override
	public CorePotionConentsComponent clone() {
		return (CorePotionConentsComponent) super.clone();
	}

}
