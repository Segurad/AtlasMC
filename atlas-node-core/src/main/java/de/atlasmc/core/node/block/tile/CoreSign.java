package de.atlasmc.core.node.block.tile;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.Sign;

public class CoreSign extends CoreTileEntity implements Sign {
	
	private boolean waxed;
	private SignText frontText;
	private SignText backText;
	
	public CoreSign(BlockType type) {
		super(type);
	}

	@Override
	public boolean isWaxed() {
		return waxed;
	}

	@Override
	public void setWaxed(boolean waxed) {
		this.waxed = waxed;
	}

	@Override
	public SignText getFrontText() {
		return frontText;
	}

	@Override
	public SignText getBackText() {
		return backText;
	}

	@Override
	public void setFrontText(SignText text) {
		this.frontText = text;
	}

	@Override
	public void setBackText(SignText text) {
		this.backText = text;
	}

}
