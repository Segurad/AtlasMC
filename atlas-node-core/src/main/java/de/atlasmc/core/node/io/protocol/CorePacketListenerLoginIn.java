package de.atlasmc.core.node.io.protocol;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import de.atlasmc.Atlas;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.io.Packet;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.node.AtlasNode;
import de.atlasmc.node.io.protocol.LoginHandler;
import de.atlasmc.node.io.protocol.PlayerConnection;
import de.atlasmc.node.io.protocol.ProtocolAdapter;
import de.atlasmc.node.io.protocol.login.ServerboundCookieResponse;
import de.atlasmc.node.io.protocol.login.ServerboundEncryptionResponse;
import de.atlasmc.node.io.protocol.login.ServerboundLoginAcknowledged;
import de.atlasmc.node.io.protocol.login.ServerboundLoginPluginResponse;
import de.atlasmc.node.io.protocol.login.ServerboundLoginStart;
import de.atlasmc.util.mojang.MojangAPI;
import de.atlasmc.node.io.protocol.login.PacketLogin;

public class CorePacketListenerLoginIn extends CoreAbstractPacketListener<LoginHandler, Packet> {

	private static final PacketHandler<?, ?>[] HANDLERS;
	private static final boolean[] HANDLE_ASYNC;
	
	static {
		HANDLERS = new PacketHandler[PacketLogin.PACKET_COUNT_IN];
		HANDLE_ASYNC = new boolean[PacketLogin.PACKET_COUNT_IN];
		initHandler(ServerboundLoginStart.class, (handler, packet) -> {
			handler.start(packet.name, packet.uuid, packet.getTimestamp());
		}, true);
		initHandler(ServerboundEncryptionResponse.class, (handler, packet) -> {
			Cipher cipher = buildCipher();
			// decrypt and validate send token
			byte[] token;
			try {
				token = cipher.doFinal(packet.verifyToken);
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				throw new ProtocolException("Unable to decrypt verify token!", e, handler.getConnection().getProtocol(), packet);
			}
			if (!handler.isValidToken(token)) {
				throw new ProtocolException("Client send invalid verify token!");
			}
			// decrypt shared secret for encryption
			byte[] secret = null;
			try {
				secret = cipher.doFinal(packet.secret);
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				throw new ProtocolException("Unable to decrypt secret!", e, handler.getConnection().getProtocol(), packet);
			}
			SecretKey secretKey = new SecretKeySpec(secret, "AES");
			handler.enableEncryption(secretKey);
			if (!handler.hasAuthentication()) { // no mojang authentication required
				return;
			}
			String hash;
			try {
				hash = MojangAPI.buildServerIDHash(CoreLoginHandler.SERVER_ID, Atlas.getKeyPair().getPublic(), secretKey);
			} catch (Exception e) {
				throw new ProtocolException("Failed to create server id hash!");
			}
			MojangAPI.getInstance()
				.verifyLoginServerAsync(handler.getLoginName(), hash)
				.setListener((future) -> {
					if (!future.isSuccess()) {
						var error = future.cause();
						handler.disconnect(ChatUtil.toChat(error != null ? error.getMessage() : "Authentication failed!"));
						handler.getConnection().getLogger().error("Mojang authentication failed: " + handler, error);
						return;
					}
					handler.setAuthentication(true);
					handler.setPlayerProfile(future.resultNow());
				});
		}, true);
		initHandler(ServerboundLoginAcknowledged.class, (handler, _) -> {
			var con = handler.getConnection();
			int version = handler.getHandshakeData().version();
			ProtocolAdapter adapter = AtlasNode.getProtocolAdapterManager().getProtocol(version);
			Protocol configuration = adapter.getConfigurationProtocol();
			var futurePlayer = handler.getPlayer();
			futurePlayer.setListener((future) -> {
				PlayerConnection playerCon = new CorePlayerConnection(future.resultNow(), con, adapter, handler.getCookieManager());
				con.setProtocol(configuration);
				con.getInboundListeners().addFirst("default", configuration.createDefaultPacketListenerServerbound(playerCon));
			});
		}, true);
		initHandler(ServerboundLoginPluginResponse.class, (handler, packet) -> {
			// TODO handle plugin response
		}, true);
		initHandler(ServerboundCookieResponse.class, (handler, packet) -> {
			var manager = handler.getCookieManager();
			manager.handleCookieResponse(packet.key, packet.payload);
		}, true);
	}
	
	private static <T extends PacketLogin> void initHandler(Class<T> clazz, PacketHandler<LoginHandler, T> handler, boolean async) {
	    int id = Packet.getDefaultPacketID(clazz);
	    HANDLERS[id] = handler;
	    HANDLE_ASYNC[id] = async;
	}

	public CorePacketListenerLoginIn(LoginHandler handler) {
		super(handler, PacketLogin.PACKET_COUNT_IN, true);
	}
	
	private static Cipher buildCipher() {
		PrivateKey privateKey = Atlas.getKeyPair().getPrivate();
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("RSA");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new ProtocolException("Unable to find RSA cipher!", e);
		}
		try {
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
		} catch (InvalidKeyException e) {
			throw new ProtocolException("Error while initializing cipher", e);
		}
		return cipher;
	}

	@Override
	protected boolean handleAsync(int packetID) {
		return HANDLE_ASYNC[packetID];
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handle(Packet packet) {
		PacketHandler<LoginHandler, Packet> handler = (PacketHandler<LoginHandler, Packet>) HANDLERS[packet.getDefaultID()];
		handler.handle(holder, packet);
	}

}
