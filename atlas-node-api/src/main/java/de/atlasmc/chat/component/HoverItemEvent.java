package de.atlasmc.chat.component;

import java.io.IOException;
import java.io.StringWriter;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.io.SNBTWriter;

public class HoverItemEvent implements HoverEvent {
	
	private ItemStack item;
	
	public HoverItemEvent(ItemStack item) {
		this.item = item;
	}

	@Override
	public HoverAction getAction() {
		return HoverAction.SHOW_ITEM;
	}

	@Override
	public String getValue() {
		StringWriter swriter = new StringWriter();
		SNBTWriter writer = new SNBTWriter(swriter);
		String string = null;
		try {
			item.toNBT(writer, false);
			string = swriter.toString();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return string;
	}

}
