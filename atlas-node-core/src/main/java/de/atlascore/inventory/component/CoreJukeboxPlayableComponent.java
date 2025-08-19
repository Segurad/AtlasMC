package de.atlascore.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.JukeboxPlayableComponent;

public class CoreJukeboxPlayableComponent extends AbstractItemComponent implements JukeboxPlayableComponent {
	
	private NamespacedKey song;
	
	public CoreJukeboxPlayableComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreJukeboxPlayableComponent clone() {
		return (CoreJukeboxPlayableComponent) super.clone();
	}

	@Override
	public NamespacedKey getSong() {
		return song;
	}

	@Override
	public void setSong(NamespacedKey song) {
		this.song = song;
	}

}
