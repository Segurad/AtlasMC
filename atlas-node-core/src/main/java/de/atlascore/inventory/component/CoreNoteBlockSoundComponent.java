package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.NoteBlockSoundComponent;
import de.atlasmc.sound.EnumSound;

public class CoreNoteBlockSoundComponent extends AbstractItemComponent implements NoteBlockSoundComponent {

	private EnumSound sound;
	
	public CoreNoteBlockSoundComponent(ComponentType type) {
		super(type);
	}

	@Override
	public EnumSound getSound() {
		return sound;
	}

	@Override
	public void setSound(EnumSound sound) {
		this.sound = sound;
	}
	
	@Override
	public CoreNoteBlockSoundComponent clone() {
		return (CoreNoteBlockSoundComponent) super.clone();
	}

}
