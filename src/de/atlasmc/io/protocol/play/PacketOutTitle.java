package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_TITLE)
public class PacketOutTitle extends AbstractPacket implements PacketPlayOut {
	
	private TitleAction action;
	private int fadeIn, stay, fadeOut;
	private String title;
	
	public TitleAction getAction() {
		return action;
	}

	public int getFadeIn() {
		return fadeIn;
	}

	public int getStay() {
		return stay;
	}

	public int getFadeOut() {
		return fadeOut;
	}

	public String getTitle() {
		return title;
	}

	public void setAction(TitleAction action) {
		this.action = action;
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

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int getDefaultID() {
		return OUT_TITLE;
	}
	
	public static enum TitleAction {
		SET_TITLE,
		SET_SUBTITLE,
		SET_ACTION_BAR,
		SET_TIME_AND_DISPLAY,
		HIDE,
		RESET;
		
		private static List<TitleAction> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static TitleAction getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<TitleAction> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}

}
