package de.atlasmc.event.inventory;

public enum ClickType {
	
	CONTROL_DROP,
	CREATIV,
	DOUBLE_CLICK,
	DROP,
	LEFT,
	MIDDLE,
	NUMBER_KEY,
	RIGHT,
	SHIFT_LEFT,
	SHIFT_RIGHT,
	SWAP_OFFHAND,
	UNKNOWN,
	WINDOW_BORDER_LEFT,
	WINDOW_BORDER_RIGHT;
	
	public boolean isCreativAction() {
		return this == CREATIV || this == MIDDLE;
	}
	
	public boolean isKeyboardClick() {
		return this == NUMBER_KEY || this == DROP || this == CONTROL_DROP;
	}
	
	public boolean isLeftClick() {
		return this == LEFT || this == SHIFT_LEFT || this == DOUBLE_CLICK || this == CREATIV;
	}
	
	public boolean isRightClick() {
		return this == RIGHT || this == SHIFT_RIGHT;
	}
	
	public boolean isShiftClick() {
		return this == SHIFT_LEFT || this == SHIFT_RIGHT || this == CONTROL_DROP;
	}

}
