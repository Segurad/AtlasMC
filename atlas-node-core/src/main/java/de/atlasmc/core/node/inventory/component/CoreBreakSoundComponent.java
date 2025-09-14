package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.BreakSoundComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.sound.Sound;

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
