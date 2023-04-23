package de.atlascore.io.protocol;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import de.atlasmc.io.protocol.login.PacketInEncryptionResponse;
import de.atlasmc.io.protocol.login.PacketInLoginStart;
import de.atlasmc.io.protocol.login.PacketLogin;
import de.atlasmc.io.protocol.login.PacketOutEncryptionRequest;
import de.atlasmc.io.protocol.login.PacketOutLoginSuccess;
import de.atlascore.io.ProxyConnectionHandler;
import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.io.protocol.ProtocolAdapter;

public class CorePacketListenerLogin implements PacketListener {

	private static final PacketHandler<?>[] HANDLERS;
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
				System.out.println("LoginStartAs: " + packet.getName()); // TODO delete
				PacketOutLoginSuccess packetOut = new PacketOutLoginSuccess();
				packetOut.setUUID(new UUID(0, 0));
				packetOut.setUsername(new String("Segurad"));
				handler.con.sendPacket(packetOut);
				int version = handler.con.getProtocol().getVersion();
				ProtocolAdapter adapter = Atlas.getProtocolAdapter(version);
				Protocol play = adapter.getPlayProtocol();
				handler.con.setProtocol(play, play.createDefaultPacketListener(handler));
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
				secret = cipher.doFinal(packet.getSecret());
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				throw new ProtocolException("Unable to decrypt secret!", e, packet, handler.con.getProtocol());
			}
			try {
				token = cipher.doFinal(packet.getVerifyToken());
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				throw new ProtocolException("Unable to decrypt verify token!", e, packet, handler.con.getProtocol());
			}
			if (!token.equals(handler.verifyToken)) {
				throw new ProtocolException("Client send invalid verify token!");
			}
			SecretKey secretKey = new SecretKeySpec(token, "AES");
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
	}
	
	private static <T extends PacketLogin> void initHandler(Class<T> clazz, PacketHandler<T> handler, boolean async) {
		DefaultPacketID annotation = clazz.getAnnotation(DefaultPacketID.class);
	    if (annotation == null) 
	    	throw new IllegalArgumentException("Class does not contain DefaultPacketID annotation!");
	    int id = annotation.value();
	    HANDLERS[id] = handler;
	    HANDLE_ASYNC[id] = async;
	}

	private final ProxyConnectionHandler con;
	private final Queue<Packet> syncQueue;
	private volatile byte[] verifyToken;

	public CorePacketListenerLogin(ProxyConnectionHandler handler) {
		this.con = handler;
		this.syncQueue = new ConcurrentLinkedQueue<>();
	}
	
	protected void unhandledPacket(Packet packet) {
		throw new IllegalStateException("Not implemented unhandledPacket"); // TODO
	}

	@Override
	public void handlePacket(Packet packet) {
		int id = packet.getID();
		if (id < 0 && id >= PacketLogin.PACKET_COUNT_IN) {
			unhandledPacket(packet);
			return;
		}
		if (HANDLE_ASYNC[id]) {
			internalHandlePacket(packet);
		} else
			syncQueue.add(packet);
	}

	@Override
	public void handleUnregister() {}
	
	@FunctionalInterface
	public static interface PacketHandler<P extends Packet> {
		public void handle(CorePacketListenerLogin handler, P packet);
	}

	@Override
	public void handleSyncPackets() throws IOException {
		Packet packet = null;
		while ((packet = syncQueue.poll()) != null) {
			internalHandlePacket(packet);
		}
	}
	
	private static String createServerHash(PublicKey key, SecretKey secret) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(SERVER_ID.getBytes("ISO_8859_1"));
		md.update(key.getEncoded());
		md.update(secret.getEncoded());
		byte[] data = md.digest();
		return new BigInteger(data).toString(16);
	}
	
	private void internalHandlePacket(Packet packet) {
		int id = packet.getID();
		@SuppressWarnings("unchecked")
		PacketHandler<Packet> handler = (PacketHandler<Packet>) HANDLERS[id];
		handler.handle(this, packet);
	}
	
	private static byte[] generateVerifyToken() {
		byte[] token = new byte[VERIFY_TOKEN_LENGTH];
		for (int i = 0; i < VERIFY_TOKEN_LENGTH; i++)
			token[i] = (byte) RANDOM.nextInt(0x100);
		return token;
	}

}
