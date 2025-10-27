package de.atlasmc.chat;

import java.io.IOException;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

public interface Chat extends Cloneable, StreamSerializable {
	
	public static final StreamCodec<Chat>
	STREAM_CODEC = new StreamCodec<Chat>() {
		
		@Override
		public boolean serialize(Chat value, ByteBuf output, CodecContext context) throws IOException {
			if (value instanceof ChatComponent comp) {
				try (NBTWriter writer = new NBTNIOWriter(output, true)) {
					comp.writeToNBT(writer, context);
				}
			} else {
				try (NBTWriter writer = new NBTNIOWriter(output, true)) {
					writer.writeStringTag(null, value.toJsonText());
				}
			}
			return true;
		}
		
		@Override
		public Class<? extends Chat> getType() {
			return Chat.class;
		}
		
		@Override
		public Chat deserialize(Chat value, ByteBuf input, CodecContext context) throws IOException {
			try (NBTReader reader = new NBTNIOReader(input, true)) {
				final TagType type = reader.getType();
				if (type == TagType.STRING) {
					return ChatUtil.toChat(reader.readStringTag());
				} else if (type == TagType.COMPOUND) {
					return ChatComponent.NBT_CODEC.deserialize(reader, context);
				} else {
					throw new NBTException("Unexpected type: " + type);
				}
			}
		}
	};

	/**
	 * Always returns the text in legacy format.
	 * @return text in legacy
	 */
	String toLegacyText();
	
	/**
	 * Always returns the text in json format.
	 * @return text in json
	 */
	String toJsonText();
	
	/**
	 * Returns the text in json format if present otherwise it will return in legacy format
	 * @return text in json or legacy
	 */
	String toText();
	
	/**
	 * Returns the raw text without any format codes
	 * @return raw text
	 */
	String toRawText();
	
	/**
	 * Returns the text as {@link ChatComponent}
	 * @return component
	 */
	ChatComponent toComponent();
	
	/**
	 * Indicates whether or not legacy text is stored.
	 * However calling {@link #toLegacyText()} will always return legacy text
	 * @return true if legacy is stored
	 */
	boolean hasLegacy();
	
	/**
	 * Indicates whether or not legacy text is stored.
	 * However calling {@link #toJsonText()} will always return json text
	 * @return true if json is stored
	 */
	boolean hasJson();
	
	/**
	 * Whether or not the chat can be casted directly to {@link ChatComponent}
	 * @return true if can be casted
	 */
	boolean isComponent();
	
	Chat clone();
	
	@Override
	default StreamCodec<? extends StreamSerializable> getStreamCodec() {
		return STREAM_CODEC;
	}
	
}
