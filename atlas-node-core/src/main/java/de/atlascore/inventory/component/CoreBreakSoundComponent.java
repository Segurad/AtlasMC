package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.BreakSoundComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.sound.Sound;

public class CoreBreakSoundComponent extends AbstractItemComponent implements BreakSoundComponent {

	private Sound sound;
	
	public CoreBreakSoundComponent(ComponentType type) {
		super(type);
	}

	@Override
	public Sound getSound() {
		return sound;
	}

	@Override
	public void setSound(Sound sound) {
		this.sound = sound;
	}

}
