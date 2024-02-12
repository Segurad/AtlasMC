package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlascore.chat.CorePlayerChatSignatureData;
import de.atlasmc.Gamemode;
import de.atlasmc.atlasnetwork.ProfileProperty;
import de.atlasmc.chat.PlayerChatSignatureData;
import static de.atlasmc.io.AbstractPacket.*;
import static de.atlasmc.io.protocol.play.PacketOutPlayerInfoUpdate.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.io.protocol.play.PacketOutPlayerInfoUpdate;
import de.atlasmc.io.protocol.play.PacketOutPlayerInfoUpdate.PlayerInfo;
import de.atlasmc.util.EncryptionUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPlayerInfoUpdate implements PacketIO<PacketOutPlayerInfoUpdate> {

	@Override
	public void read(PacketOutPlayerInfoUpdate packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		byte actions = in.readByte();
		packet.setActions(actions);
		final int count = readVarInt(in);
		List<PlayerInfo> infos = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			PlayerInfo info = new PlayerInfo(readUUID(in));
			infos.add(info);
			if ((actions & ACTION_ADD_PLAYER) != 0) {
				info.name = readString(in);
				final int propCount = readVarInt(in);
				if (propCount > 0) {
					List<ProfileProperty> props = info.properties = new ArrayList<>(propCount);
					for (int j = 0; j < propCount; j++) {
						String name = readString(in, 32767);
						String value = readString(in, 32767);
						String signature = in.readBoolean() ? readString(in, 32767) : null;
						props.add(new ProfileProperty(name, value, signature));
					}
				}
			}
			if ((actions & ACTION_INIT_CHAT) != 0) {
				if (in.readBoolean()) {
					UUID sid = readUUID(in);
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
					PlayerChatSignatureData sigData = new CorePlayerChatSignatureData(sid, expiration, key, signature);
					info.chatSignature = sigData;
				}
			}
			if ((actions & ACTION_UPDATE_GAMEMODE) != 0) {
				Gamemode gamemode = Gamemode.getByID(readVarInt(in));
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
					info.displayName = readString(in, ACTION_INIT_CHAT);
				}
			}
		}
	}

	@Override
	public void write(PacketOutPlayerInfoUpdate packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		byte actions = packet.getActions();
		out.writeByte(actions);
		List<PlayerInfo> infos = packet.getPlayers();
		writeVarInt(infos.size(), out);
		for (PlayerInfo info : infos) {
			UUID uuid = info.uuid;
			out.writeLong(uuid.getMostSignificantBits());
			out.writeLong(uuid.getLeastSignificantBits());
			if ((actions & ACTION_ADD_PLAYER) != 0) {
				writeString(info.name, out);
				List<ProfileProperty> props = info.properties;
				if (props == null || props.isEmpty()) {
					writeVarInt(0, out);
				} else {
					writeVarInt(props.size(), out);
					for (ProfileProperty prop : props) {
						writeString(prop.getName(), out);
						writeString(prop.getValue(), out);
						String signature = prop.getSignature();
						if (signature == null) {
							out.writeBoolean(false);
						} else {
							out.writeBoolean(true);
							writeString(signature, out);
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
				String name = info.displayName;
				if (name == null) {
					out.writeBoolean(false);
				} else {
					out.writeBoolean(true);
					writeString(name, out);
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
