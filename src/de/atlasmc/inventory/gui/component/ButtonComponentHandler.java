package de.atlasmc.inventory.gui.component;

import de.atlasmc.inventory.gui.Button;
import de.atlasmc.inventory.gui.GUI;

public final class ButtonComponentHandler extends AbstractComponentHandler {
	
	protected final Component<Button> comp;
	
	ButtonComponentHandler(Component<Button> comp, GUI gui, int slot, int length, int depth, int x, int y) {
		super(comp, gui, slot, length, depth, x, y);
		this.comp = comp;
	}

	@Override
	public void internalUpdate(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component<Button> getComponent() {
		return comp;
	}

	@Override
	public void updateDisplay(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
