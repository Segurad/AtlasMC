package de.atlascore.io.protocol;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import de.atlasmc.Atlas;
import de.atlasmc.AtlasNode;
import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.ProfileHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.io.ProxyConnectionHandler;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.ProtocolAdapter;
import de.atlasmc.io.protocol.login.PacketInEncryptionResponse;
import de.atlasmc.io.protocol.login.PacketInLoginAcknowledged;
import de.atlasmc.io.protocol.login.PacketInLoginPluginResponse;
import de.atlasmc.io.protocol.login.PacketInLoginStart;
import de.atlasmc.io.protocol.login.PacketLogin;
import de.atlasmc.io.protocol.login.PacketOutEncryptionRequest;
import de.atlasmc.io.protocol.login.PacketOutLoginSuccess;
import de.atlasmc.proxy.LocalProxy;

public class CorePacketListenerLoginIn extends CoreAbstractPacketListener<CorePacketListenerLoginIn, Packet> {

	private static final PacketHandler<?, ?>[] HANDLERS;
	private static final boolean[] HANDLE_ASYNC;
	private static final Random RANDOM = new Random();
	private static final int VERIFY_TOKEN_LENGTH = 32;
	private static final String SERVER_ID = "AtlasMC";
	
	static {
		HANDLERS = new PacketHandler[PacketLogin.PACKET_COUNT_IN];
		HANDLE_ASYNC = new boolean[PacketLogin.PACKET_COUNT_IN];
		initHandler(PacketInLoginStart.class, (handler, packet) -> {
			LocalProxy proxy = handler.con.getProxy();
			if (proxy.getConfig().getPlayerAuthentication()) {
				PacketOutEncryptionRequest packetOut = new PacketOutEncryptionRequest();
				packetOut.setServerID(SERVER_ID);
				packetOut.setPublicKey(Atlas.getKeyPair().getPublic().getEncoded());
				byte[] token = generateVerifyToken();
				handler.verifyToken = token;
				packetOut.setVerifyToken(token);
				handler.con.sendPacket(packet);
			} else if (proxy.isSync()) {
				ProfileHandler profiles = AtlasNetwork.getProfileHandler();
				AtlasPlayer player = profiles.getPlayer(packet.uuid);
				if (player == null) {
					// TODO request profile implementation
				}
				PacketOutLoginSuccess packetOut = new PacketOutLoginSuccess();
				packetOut.uuid = player.getInternalUUID();
				packetOut.username = player.getInternalName();
				// TODO packetOut.properties =
				handler.player = player;
				handler.con.sendPacket(packetOut);
			} else {
				handler.syncQueue.add(packet);
			}
		}, true);
		initHandler(PacketInEncryptionResponse.class, (handler, packet) -> {
			PrivateKey privateKey = Atlas.getKeyPair().getPrivate();
			Cipher cipher = null;
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
			byte[] secret = null;
			byte[] token = null;
			try {
				secret = cipher.doFinal(packet.secret);
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				throw new ProtocolException("Unable to decrypt secret!", e, packet, handler.con.getProtocol());
			}
			try {
				token = cipher.doFinal(packet.verifyToken);
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				throw new ProtocolException("Unable to decrypt verify token!", e, packet, handler.con.getProtocol());
			}
			if (!token.equals(handler.verifyToken)) {
				throw new ProtocolException("Client send invalid verify token!");
			}
			SecretKey secretKey = new SecretKeySpec(secret, "AES");
			try {
				handler.con.enableEncryption(secretKey);
			} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
				throw new ProtocolException("Failed to enable encryption!", e);
			}
			try {
				String hash = createServerHash(Atlas.getKeyPair().getPublic(), secretKey);
			} catch (Exception e) {
				throw new ProtocolException("Failed to create server id hash!");
			}
		}, true);
		initHandler(PacketInLoginAcknowledged.class, (handler, packet) -> {
			int version = handler.con.getProtocol().getVersion();
			ProtocolAdapter adapter = AtlasNode.getProtocolAdapter(version);
			Protocol configuration = adapter.getConfigurationProtocol();
			PlayerConnection con = new CorePlayerConnection(handler.player, handler.con, adapter);
			handler.con.setProtocol(configuration, configuration.createDefaultPacketListener(con));
		}, true);
		initHandler(PacketInLoginPluginResponse.class, (handle, packet) -> {
			// TODO handle plugin response
		}, true);
	}
	
	private ProxyConnectionHandler con;
	private AtlasPlayer player;
	private volatile byte[] verifyToken;
	
	private static <T extends PacketLogin> void initHandler(Class<T> clazz, PacketHandler<CorePacketListenerLoginIn, T> handler, boolean async) {
	    int id = Packet.getDefaultPacketID(clazz);
	    HANDLERS[id] = handler;
	    HANDLE_ASYNC[id] = async;
	}

	public CorePacketListenerLoginIn(ProxyConnectionHandler handler) {
		super(null, PacketLogin.PACKET_COUNT_IN);
		holder = this;
		con = handler;
	}
	
	private static String createServerHash(PublicKey key, SecretKey secret) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(SERVER_ID.getBytes("ISO_8859_1"));
		md.update(key.getEncoded());
		md.update(secret.getEncoded());
		byte[] data = md.digest();
		return new BigInteger(data).toString(16);
	}
	
	private static byte[] generateVerifyToken() {
		byte[] token = new byte[VERIFY_TOKEN_LENGTH];
		for (int i = 0; i < VERIFY_TOKEN_LENGTH; i++)
			token[i] = (byte) RANDOM.nextInt(0x100);
		return token;
	}

	@Override
	protected boolean handleAsync(int packetID) {
		return HANDLE_ASYNC[packetID];
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handle(Packet packet) {
		PacketHandler<CorePacketListenerLoginIn, Packet> handler = (PacketHandler<CorePacketListenerLoginIn, Packet>) HANDLERS[packet.getID()];
		handler.handle(holder, packet);
	}

}
