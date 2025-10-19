package de.atlasmc.node.block.tile;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Sign extends TileEntity {
	
	public static final NBTCodec<Sign>
	NBT_HANDLER = NBTCodec
					.builder(Sign.class)
					.include(TileEntity.NBT_HANDLER)
					.boolField("is_waxed", Sign::isWaxed, Sign::setWaxed)
					.typeCompoundField("front_text", Sign::getFrontText, Sign::setFrontText, SignText.NBT_HANDLER)
					.typeCompoundField("back_text", Sign::getBackText, Sign::setBackText, SignText.NBT_HANDLER)
					.build();
	
	boolean isWaxed();
	
	void setWaxed(boolean waxed);
	
	SignText getFrontText();
	
	SignText getBackText();
	
	void setFrontText(SignText text);
	
	void setBackText(SignText text);
	
	public class SignText implements NBTSerializable {
		
		public static final NBTCodec<SignText>
		NBT_HANDLER = NBTCodec
						.builder(SignText.class)
						.defaultConstructor(SignText::new)
						.boolField("has_glowing_text", SignText::isGlowing, SignText::setGlowing)
						.enumStringField("color", SignText::getColor, SignText::setColor, ChatColor.class, ChatColor.BLACK)
						.chatList("messages", SignText::hasMessages, SignText::getMessages)
						.build();
		
		private boolean glowing;
		private ChatColor color = ChatColor.BLACK;
		private List<Chat> messages;
		
		public boolean isGlowing() {
			return glowing;
		}
		
		public void setGlowing(boolean glowing) {
			this.glowing = glowing;
		}
		
		public ChatColor getColor() {
			return color;
		}
		
		public void setColor(ChatColor color) {
			this.color = color;
		}
		
		public boolean hasMessages() {
			return messages != null && !messages.isEmpty();
		}
		
		public List<Chat> getMessages() {
			if (messages == null)
				messages = new ArrayList<>(4);
			return messages;
		}
		
		@Override
		public NBTCodec<? extends SignText> getNBTCodec() {
			return NBT_HANDLER;
		}
		
	}

}
