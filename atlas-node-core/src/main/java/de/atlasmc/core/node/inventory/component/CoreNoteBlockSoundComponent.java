package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.NoteBlockSoundComponent;
import de.atlasmc.node.sound.EnumSound;

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
