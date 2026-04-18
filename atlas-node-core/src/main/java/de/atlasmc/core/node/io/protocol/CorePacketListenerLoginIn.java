package de.atlasmc.core.node.io.protocol;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import de.atlasmc.Atlas;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.event.HandlerList;
import de.atlasmc.io.Packet;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.connection.ServerSocketConnectionHandler;
import de.atlasmc.node.AtlasNode;
import de.atlasmc.node.event.socket.AsyncPlayerLoginAttemptEvent;
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

public class CorePacketListenerLoginIn extends CoreAbstractPacketListener<CorePacketListenerLoginIn, Packet> {

	private static final PacketHandler<?, ?>[] HANDLERS;
	private static final boolean[] HANDLE_ASYNC;
	
	static {
		HANDLERS = new PacketHandler[PacketLogin.PACKET_COUNT_IN];
		HANDLE_ASYNC = new boolean[PacketLogin.PACKET_COUNT_IN];
		initHandler(ServerboundLoginStart.class, (handler, packet) -> {
			AsyncPlayerLoginAttemptEvent event = new AsyncPlayerLoginAttemptEvent(handler.createLoginHandler(packet.name, packet.uuid, packet.getTimestamp()));
			HandlerList.callEvent(event);
		}, true);
		initHandler(ServerboundEncryptionResponse.class, (handler, packet) -> {
			Cipher cipher = buildCipher();
			// decrypt and validate send token
			byte[] token;
			try {
				token = cipher.doFinal(packet.verifyToken);
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				throw new ProtocolException("Unable to decrypt verify token!", e, handler.con.getProtocol(), packet);
			}
			if (!handler.handler.isValidToken(token)) {
				throw new ProtocolException("Client send invalid verify token!");
			}
			// decrypt shared secret for encryption
			byte[] secret = null;
			try {
				secret = cipher.doFinal(packet.secret);
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				throw new ProtocolException("Unable to decrypt secret!", e, handler.con.getProtocol(), packet);
			}
			SecretKey secretKey = new SecretKeySpec(secret, "AES");
			handler.handler.enableEncryption(secretKey);
			if (!handler.handler.hasAuthentication()) { // no mojang authentication required
				return;
			}
			String hash;
			try {
				hash = MojangAPI.buildServerIDHash(CoreLoginHandler.SERVER_ID, Atlas.getKeyPair().getPublic(), secretKey);
			} catch (Exception e) {
				throw new ProtocolException("Failed to create server id hash!");
			}
			MojangAPI.getInstance()
				.verifyLoginServerAsync(handler.handler.getLoginName(), hash)
				.setListener((future) -> {
					final var loginHandler = handler.handler;
					if (!future.isSuccess()) {
						var error = future.cause();
						loginHandler.disconnect(ChatUtil.toChat(error != null ? error.getMessage() : "Authentication failed!"));
						loginHandler.getConnection().getLogger().error("Mojang authentication failed: " + loginHandler, error);
						return;
					}
					loginHandler.setAuthentication(true);
					loginHandler.setPlayerProfile(future.resultNow());
				});
		}, true);
		initHandler(ServerboundLoginAcknowledged.class, (handler, _) -> {
			int version = handler.con.getProtocol().getVersion();
			ProtocolAdapter adapter = AtlasNode.getProtocolAdapter(version);
			Protocol configuration = adapter.getConfigurationProtocol();
			var futurePlayer = handler.handler.getPlayer();
			futurePlayer.setListener((future) -> {
				PlayerConnection con = new CorePlayerConnection(future.resultNow(), handler.con, adapter);
				handler.con.setProtocol(configuration);
				handler.con.getInboundListeners().addFirst("default", configuration.createDefaultPacketListenerServerbound(con));
			});
		}, true);
		initHandler(ServerboundLoginPluginResponse.class, (handler, packet) -> {
			// TODO handle plugin response
		}, true);
		initHandler(ServerboundCookieResponse.class, (handler, packet) -> {
			// TODO handle cookie response
		}, false);
	}
	
	private final ServerSocketConnectionHandler con;
	private volatile LoginHandler handler;
	
	private static <T extends PacketLogin> void initHandler(Class<T> clazz, PacketHandler<CorePacketListenerLoginIn, T> handler, boolean async) {
	    int id = Packet.getDefaultPacketID(clazz);
	    HANDLERS[id] = handler;
	    HANDLE_ASYNC[id] = async;
	}

	public CorePacketListenerLoginIn(ConnectionHandler handler) {
		super(null, PacketLogin.PACKET_COUNT_IN);
		holder = this;
		con = (ServerSocketConnectionHandler) handler;
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
	
	private synchronized LoginHandler createLoginHandler(String name, UUID uuid, long loginStart) {
		if (handler != null) {
			throw new ProtocolException("Login already initialized!");
		}
		var handler = new CoreLoginHandler(con, name, uuid, loginStart);
		this.handler = handler;
		return handler;
	}

	@Override
	protected boolean handleAsync(int packetID) {
		return HANDLE_ASYNC[packetID];
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handle(Packet packet) {
		PacketHandler<CorePacketListenerLoginIn, Packet> handler = (PacketHandler<CorePacketListenerLoginIn, Packet>) HANDLERS[packet.getDefaultID()];
		handler.handle(holder, packet);
	}

}
