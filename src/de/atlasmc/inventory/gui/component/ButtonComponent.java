package de.atlasmc.inventory.gui.component;

import de.atlasmc.inventory.gui.Button;
import de.atlasmc.inventory.gui.GUI;

public class ButtonComponent extends AbstractComponent<Button> {

	public ButtonComponent(int x, int y) {
		super(Button.class, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ComponentHandler createHandler(GUI gui, int slot, int length, int depth, int x, int y) {
		ComponentHandler h = new ButtonComponentHandler(this , gui, slot, length, depth, x, y);
		handlers.add(h);
		return h;
	}

}
