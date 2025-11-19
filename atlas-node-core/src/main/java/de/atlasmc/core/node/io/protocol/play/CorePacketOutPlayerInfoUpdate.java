package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;
import static de.atlasmc.node.io.protocol.play.PacketOutPlayerInfoUpdate.ACTION_ADD_PLAYER;
import static de.atlasmc.node.io.protocol.play.PacketOutPlayerInfoUpdate.ACTION_INIT_CHAT;
import static de.atlasmc.node.io.protocol.play.PacketOutPlayerInfoUpdate.ACTION_UPDATE_DISPLAY_NAME;
import static de.atlasmc.node.io.protocol.play.PacketOutPlayerInfoUpdate.ACTION_UPDATE_GAMEMODE;
import static de.atlasmc.node.io.protocol.play.PacketOutPlayerInfoUpdate.ACTION_UPDATE_LATENCY;
import static de.atlasmc.node.io.protocol.play.PacketOutPlayerInfoUpdate.ACTION_UPDATE_LISTED;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.PlayerChatSignatureData;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.codec.UUIDCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.network.player.ProfileProperty;
import de.atlasmc.node.Gamemode;
import de.atlasmc.node.io.protocol.play.PacketOutPlayerInfoUpdate;
import de.atlasmc.node.io.protocol.play.PacketOutPlayerInfoUpdate.PlayerInfo;
import de.atlasmc.util.EncryptionUtil;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPlayerInfoUpdate implements PacketIO<PacketOutPlayerInfoUpdate> {

	@Override
	public void read(PacketOutPlayerInfoUpdate packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		byte actions = in.readByte();
		packet.actions = actions;
		final int count = readVarInt(in);
		final CodecContext context = handler.getCodecContext();
		List<PlayerInfo> infos = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			PlayerInfo info = new PlayerInfo(UUIDCodec.readUUID(in));
			infos.add(info);
			if ((actions & ACTION_ADD_PLAYER) != 0) {
				info.name = StringCodec.readString(in);
				final int propCount = readVarInt(in);
				if (propCount > 0) {
					List<ProfileProperty> props = info.properties = new ArrayList<>(propCount);
					for (int j = 0; j < propCount; j++) {
						String name = StringCodec.readString(in);
						String value = StringCodec.readString(in);
						String signature = in.readBoolean() ? StringCodec.readString(in) : null;
						props.add(new ProfileProperty(name, value, signature));
					}
				}
			}
			if ((actions & ACTION_INIT_CHAT) != 0 && in.readBoolean()) {
				UUID sid = UUIDCodec.readUUID(in);
				long expiration = in.readLong();
				int keySize = readVarInt(in);
				byte[] rawKey = new byte[keySize];
				in.readBytes(rawKey);
				PublicKey key = null;
				try {
					key = EncryptionUtil.publicKeyByBytes(rawKey);
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					throw new ProtocolException("Exception while reading public key!", e);
				}
				int sigSize = readVarInt(in);
				byte[] signature = new byte[sigSize];
				PlayerChatSignatureData sigData = new PlayerChatSignatureData(sid, expiration, key, signature);
				info.chatSignature = sigData;
			}
			if ((actions & ACTION_UPDATE_GAMEMODE) != 0) {
				Gamemode gamemode = EnumUtil.getByID(Gamemode.class, readVarInt(in));
				info.gamemode = gamemode;
			}
			if ((actions & ACTION_UPDATE_LISTED) != 0) {
				info.listed = in.readBoolean();
			}
			if ((actions & ACTION_UPDATE_LATENCY) != 0) {
				info.ping = readVarInt(in);
			}
			if ((actions & ACTION_UPDATE_DISPLAY_NAME) != 0) {
				if (in.readBoolean()) {
					info.displayName = Chat.STREAM_CODEC.deserialize(in, context);
				}
			}
		}
	}

	@Override
	public void write(PacketOutPlayerInfoUpdate packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		byte actions = packet.actions;
		out.writeByte(actions);
		List<PlayerInfo> infos = packet.info;
		final int size = infos.size();
		final CodecContext context = handler.getCodecContext();
		writeVarInt(size, out);
		for (int i = 0; i < size; i++) {
			PlayerInfo info = infos.get(i);
			UUID uuid = info.uuid;
			out.writeLong(uuid.getMostSignificantBits());
			out.writeLong(uuid.getLeastSignificantBits());
			if ((actions & ACTION_ADD_PLAYER) != 0) {
				StringCodec.writeString(info.name, out);
				List<ProfileProperty> props = info.properties;
				if (props == null || props.isEmpty()) {
					writeVarInt(0, out);
				} else {
					writeVarInt(props.size(), out);
					for (ProfileProperty prop : props) {
						StringCodec.writeString(prop.getName(), out);
						StringCodec.writeString(prop.getValue(), out);
						String signature = prop.getSignature();
						if (signature == null) {
							out.writeBoolean(false);
						} else {
							out.writeBoolean(true);
							StringCodec.writeString(signature, out);
						}
					}
				}
			}
			if ((actions & ACTION_INIT_CHAT) != 0) {
				PlayerChatSignatureData chatSignature = info.chatSignature;
				if (chatSignature == null) {
					out.writeBoolean(false);
				} else {
					out.writeBoolean(true);
					UUID sid = chatSignature.getSessionID();
					out.writeLong(sid.getMostSignificantBits());
					out.writeLong(sid.getLeastSignificantBits());
					out.writeLong(chatSignature.getKeyExpiration());
					byte[] rawKey = chatSignature.getPublicKey().getEncoded();
					writeVarInt(rawKey.length, out);
					out.writeBytes(rawKey);
					byte[] signature = chatSignature.getSignature();
					writeVarInt(signature.length, out);
					out.writeBytes(signature);
				}
			}
			if ((actions & ACTION_UPDATE_GAMEMODE) != 0) {
				writeVarInt(info.gamemode.getID(), out);
			}
			if ((actions & ACTION_UPDATE_LISTED) != 0) {
				out.writeBoolean(info.listed);
			}
			if ((actions & ACTION_UPDATE_LATENCY) != 0) {
				writeVarInt(info.ping, out);
			}
			if ((actions & ACTION_UPDATE_DISPLAY_NAME) != 0) {
				Chat name = info.displayName;
				if (name == null) {
					out.writeBoolean(false);
				} else {
					out.writeBoolean(true);
					name.writeToStream(out, context);
				}
			}
		}
	}

	@Override
	public PacketOutPlayerInfoUpdate createPacketData() {
		return new PacketOutPlayerInfoUpdate();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutPlayerInfoUpdate.class);
	}

}
