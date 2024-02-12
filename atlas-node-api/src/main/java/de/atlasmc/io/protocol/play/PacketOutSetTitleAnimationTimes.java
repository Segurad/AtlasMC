package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SET_TITLE_ANIMATION_TIMES)
public class PacketOutSetTitleAnimationTimes extends AbstractPacket implements PacketPlayOut {
	
	private int fadeIn, stay, fadeOut;

	public int getFadeIn() {
		return fadeIn;
	}

	public int getStay() {
		return stay;
	}

	public int getFadeOut() {
		return fadeOut;
	}

	public void setFadeIn(int fadeIn) {
		this.fadeIn = fadeIn;
	}

	public void setStay(int stay) {
		this.stay = stay;
	}

	public void setFadeOut(int fadeOut) {
		this.fadeOut = fadeOut;
	}

	@Override
	public int getDefaultID() {
		return OUT_SET_TITLE_ANIMATION_TIMES;
	}

}
