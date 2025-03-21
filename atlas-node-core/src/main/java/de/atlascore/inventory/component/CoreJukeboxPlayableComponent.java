package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.JukeboxPlayableComponent;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreJukeboxPlayableComponent extends AbstractItemComponent implements JukeboxPlayableComponent {

	protected static final NBTFieldSet<CoreJukeboxPlayableComponent> NBT_FIELDS;
	
	protected static final CharKey
	NBT_SONG = CharKey.literal("song"),
	NBT_SHOW_IN_TOOLTIP = CharKey.literal("show_in_tooltip");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_SHOW_IN_TOOLTIP, null);
	}
	
	private NamespacedKey song;
	private boolean showInTooltip;
	
	public CoreJukeboxPlayableComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreJukeboxPlayableComponent clone() {
		return (CoreJukeboxPlayableComponent) super.clone();
	}

	@Override
	public boolean isShowTooltip() {
		return showInTooltip;
	}

	@Override
	public void setShowTooltip(boolean show) {
		this.showInTooltip = show;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NamespacedKey getSong() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSong(NamespacedKey song) {
		// TODO Auto-generated method stub
		
	}

}
