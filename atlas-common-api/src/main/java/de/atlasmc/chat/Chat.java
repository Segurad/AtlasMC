package de.atlasmc.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTNIOReader;
import de.atlasmc.nbt.io.NBTNIOWriter;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.OpenCloneable;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public interface Chat extends OpenCloneable, StreamSerializable {
	
	public static final NBTCodec<List<Chat>> CHAT_LIST_NBT_CODEC = new NBTCodec<List<Chat>>() {
		
		@Override
		public Class<?> getType() {
			return List.class;
		}
		
		@Override
		public List<Chat> deserialize(List<Chat> value, NBTReader input, CodecContext context) throws IOException {
			if (value == null)
				value = new ArrayList<>();
			final TagType listType = input.getListType();
			if (listType == TagType.TAG_END || input.getNextPayload() == 0) {
				input.readNextEntry();
				input.readNextEntry();
				return value;
			}
			switch (listType) {
			case COMPOUND:
				input.readNextEntry();
				while (input.getRestPayload() > 0) {
					input.readNextEntry();
					Chat v = ChatComponent.NBT_CODEC.deserialize(input, context);
					value.add(v);
				}
				input.readNextEntry();
				break;
			case STRING:
				input.readNextEntry();
				while (input.getRestPayload() > 0) {
					String raw = input.readStringTag();
					Chat v = ChatUtil.toChat(raw);
					value.add(v);
				}
				input.readNextEntry();
				break;
			default:
				throw new NBTException("Expected list of type COMPOUND or STRING but was: " + listType);
			}
			return value;
		}
		
		@Override
		public boolean serialize(CharSequence key, List<Chat> value, NBTWriter output, CodecContext context) throws IOException {
			final int size = value.size();
			if (value.get(0).isComponent()) {
				output.writeListTag(key, TagType.COMPOUND, size);
				for (int i = 0; i < size; i++) {
					Chat v = value.get(i);
					ChatComponent comp = v.toComponent();
					@SuppressWarnings("unchecked")
					NBTCodec<ChatComponent> handler = (NBTCodec<ChatComponent>) comp.getNBTCodec();
					output.writeCompoundTag();
					handler.serialize(comp, output, context);
					output.writeEndTag();
				}
			} else {
				output.writeListTag(key, TagType.STRING, size);
				for (int i = 0; i < size; i++) {
					Chat v = value.get(i);
					output.writeStringTag(null, v.toText());
				}
			}
			return true;
		}
		
		@Override
		public List<TagType> getTags() {
			return CodecTags.LIST;
		}
	};
	
	public static final NBTCodec<Chat> RAW_NBT_CODEC = NBTCodec.stringToObject(Chat.class, ChatUtil::toChat, Chat::toText);
	
	public static final NBTCodec<Chat> NBT_CODEC = NBTCodec.codecOrElse(Chat.class, RAW_NBT_CODEC, ChatComponent.NBT_CODEC);
	
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
