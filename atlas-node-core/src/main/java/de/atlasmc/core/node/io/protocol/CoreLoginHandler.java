package de.atlasmc.core.node.io.protocol;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import de.atlasmc.Atlas;
import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.core.node.io.protocol.cookie.CoreCookieManager;
import de.atlasmc.event.HandlerList;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.io.connection.ServerSocketConnectionHandler;
import de.atlasmc.network.player.AtlasPlayer;
import de.atlasmc.node.event.socket.AsyncPlayerLoginAttemptEvent;
import de.atlasmc.node.io.protocol.LoginHandler;
import de.atlasmc.node.io.protocol.cookie.CookieClient;
import de.atlasmc.node.io.protocol.cookie.CookieManager;
import de.atlasmc.node.io.protocol.handshake.HandshakeData;
import de.atlasmc.node.io.protocol.login.ClientboundCookieRequest;
import de.atlasmc.node.io.protocol.login.ClientboundDisconnect;
import de.atlasmc.node.io.protocol.login.ClientboundEncryptionRequest;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.mojang.PlayerProfile;
import io.netty.buffer.ByteBuf;

public class CoreLoginHandler implements LoginHandler, CookieClient {
	
	static final String SERVER_ID = "AtlasMC";
	private static final int VERIFY_TOKEN_LENGTH = 32;
	
	private final ServerSocketConnectionHandler connection;
	private final HandshakeData handshakeData;
	private final CookieManager cookieManager;
	private volatile String name;
	private volatile UUID uuid;
	private volatile long loginTime;
	private volatile boolean closed;
	private volatile Chat disconnectMessage;
	private volatile boolean useAuthentication;
	private volatile CompletableFuture<Boolean> encryptionFuture;
	private volatile byte[] verifyToken;
	private volatile PlayerProfile profile;
	private volatile boolean authenticated;
	
	public CoreLoginHandler(ServerSocketConnectionHandler connection, HandshakeData handshake) {
		this.connection = Objects.requireNonNull(connection, "connection");
		this.handshakeData = Objects.requireNonNull(handshake, "handshake");
		this.cookieManager = new CoreCookieManager(this);
		this.connection.setExceptionHandler((con, e) -> {
			disconnect(ChatUtil.toChat(e.getMessage()));
			con.getLogger().error("Error in login process: " + CoreLoginHandler.this, e);
			return true;
		});
		this.loginTime = handshake.timestamp();
	}
	
	@Override
	public HandshakeData getHandshakeData() {
		return handshakeData;
	}
	
	@Override
	public long getLoginTime() {
		return loginTime;
	}
	
	@Override
	public String getLoginName() {
		return name;
	}
	
	@Override
	public UUID getLoginUUID() {
		return uuid;
	}
	
	@Override
	public ServerSocketConnectionHandler getConnection() {
		return connection;
	}
	
	@Override
	public Chat getDisconnectMessage() {
		return disconnectMessage;
	}
	
	/**
	 * Whether or not the connection is closed
	 * @return
	 */
	@Override
	public boolean isClosed() {
		return closed || connection.isClosed();
	}
	
	@Override
	public synchronized boolean disconnect(Chat message) {
		if (closed)
			return false;
		if (connection.isClosed()) { // connection was externally closed
			closed = true;
			return false;
		}
		this.disconnectMessage = message;
		var packet = new ClientboundDisconnect();
		packet.text = message;
		connection.sendPacket(packet, (_) -> {
			connection.close();
			closed = true;
		});
		return true;
	}

	@Override
	public synchronized Future<Boolean> enableEncryption() {
		var future = this.encryptionFuture;
		if (future != null)
			return future;
		future = new CompletableFuture<>();
		this.encryptionFuture = future;
		var packet = new ClientboundEncryptionRequest();
		packet.serverID = SERVER_ID;
		packet.publicKey = Atlas.getKeyPair().getPublic().getEncoded();
		verifyToken = packet.verifyToken = generateVerifyToken();
		packet.authenticate = useAuthentication;
		connection.sendPacket(packet);
		return future;
	}
	
	@Override
	public boolean isValidToken(byte[] token) {
		byte[] verifyToken = this.verifyToken;
		if (verifyToken == null)
			throw new IllegalStateException("Token not initialized!"); // not authentication started
		return verifyToken.equals(token);
	}
	
	private static byte[] generateVerifyToken() {
		Random rand = new Random();
		byte[] token = new byte[VERIFY_TOKEN_LENGTH];
		for (int i = 0; i < VERIFY_TOKEN_LENGTH; i++)
			token[i] = (byte) rand.nextInt(0x100);
		return token;
	}

	@Override
	public synchronized void setAuthentication(boolean authentication) {
		if (encryptionFuture != null)
			return;
		this.useAuthentication = authentication;
	}

	@Override
	public boolean hasAuthentication() {
		return useAuthentication;
	}
	
	@Override
	public Future<AtlasPlayer> getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public PlayerProfile getPlayerProfile() {
		return profile;
	}

	@Override
	public void setPlayerProfile(PlayerProfile profile) {
		this.profile = profile;
	}

	@Override
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	
	@Override
	public String toString() {
		return "[ " + name + " | " + uuid + " | " + connection.getRemoteAddress() + " ]";
	}

	@Override
	public synchronized void enableEncryption(SecretKey key) {
		if (encryptionFuture == null)
			encryptionFuture = new CompletableFuture<>();
		IvParameterSpec spec = new IvParameterSpec(key.getEncoded());
		Cipher decription;
		Cipher encription;
		try {
			decription = Cipher.getInstance("AES/CFB8/NoPadding");
			encription = Cipher.getInstance("AES/CFB8/NoPadding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new ProtocolException("Unable to find AES cipher!");
		}
		try {
			decription.init(Cipher.DECRYPT_MODE, key, spec);
			encription.init(Cipher.ENCRYPT_MODE, key, spec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			encryptionFuture.complete(false, e);
			throw new ProtocolException("Failed to enable encryption!", e);
		}
		connection.enableEncryption(encription, decription);
		encryptionFuture.complete(true);
	}

	@Override
	public synchronized void start(String name, UUID uuid, long timestamp) {
		if (isStarted())
			throw new ProtocolException("Login already started!");
		name = Objects.requireNonNull(name);
		uuid = Objects.requireNonNull(uuid);
		loginTime = timestamp;
		connection.getLogger().debug("Login started for {} with {} | {}", connection, name, uuid);
		AsyncPlayerLoginAttemptEvent event = new AsyncPlayerLoginAttemptEvent(true, this);
		HandlerList.callEvent(event);
	}

	@Override
	public boolean isStarted() {
		return name != null;
	}

	@Override
	public void requestCookie(NamespacedKey key) {
		var packet = new ClientboundCookieRequest();
		packet.key = key;
		connection.sendPacket(packet);
	}

	@Override
	public void updateCookie(NamespacedKey key, ByteBuf payload) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CookieManager getCookieManager() {
		return cookieManager;
	}

}
