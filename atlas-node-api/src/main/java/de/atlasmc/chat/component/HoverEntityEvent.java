package de.atlasmc.chat.component;

import java.io.IOException;
import java.io.StringWriter;

import de.atlasmc.entity.Entity;
import de.atlasmc.util.nbt.io.SNBTWriter;

public class HoverEntityEvent implements HoverEvent {

	private final Entity entity;
	
	public HoverEntityEvent(Entity entity) {
		this.entity = entity;
	}
	
	@Override
	public String getValue() {
		StringWriter swriter = new StringWriter();
		SNBTWriter writer = new SNBTWriter(swriter);
		String string = null;
		try {
			entity.toNBT(writer, false);
			string = swriter.toString();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return string;
	}

	@Override
	public HoverAction getAction() {
		return HoverAction.SHOW_ENTITY;
	}

}
