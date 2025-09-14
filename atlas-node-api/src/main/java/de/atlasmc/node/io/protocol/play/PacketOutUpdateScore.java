package de.atlasmc.node.io.protocol.play;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_UPDATE_SCORE, definition = "set_score")
public class PacketOutUpdateScore extends AbstractPacket implements PacketPlayOut {

	public String entry;
	public String objective;
	public int value;
	public Chat displayName;
	/**
	 * <ul>
	 * <li>0 = blank</li>
	 * <li>1 = styled</li>
	 * <li>2 = fixed</li>
	 * </ul>
	 */
	public NumberFormatType formatType;
	public ChatComponent numberFormat;

	@Override
	public int getDefaultID() {
		return OUT_UPDATE_SCORE;
	}
	
	public static enum NumberFormatType {
		BLANK,
		STYLED,
		FIXED;

		public static NumberFormatType getByID(int id) {
			switch (id) {
			case 0:
				return BLANK;
			case 1:
				return STYLED;
			case 2:
				return FIXED;
			default:
				return null;
			}
		}
		
		public int getID() {
			return ordinal();
		}
	}
	
}
