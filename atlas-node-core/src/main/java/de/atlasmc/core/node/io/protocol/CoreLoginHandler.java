package de.atlasmc.core.node.io.protocol;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import javax.crypto.SecretKey;

import de.atlasmc.Atlas;
import de.atlasmc.chat.Chat;
import de.atlasmc.io.connection.ServerSocketConnectionHandler;
import de.atlasmc.node.io.protocol.LoginHandler;
import de.atlasmc.node.io.protocol.login.ClientboundDisconnect;
import de.atlasmc.node.io.protocol.login.ClientboundEncryptionRequest;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.Future;

public class CoreLoginHandler implements LoginHandler {
	
	private static final int VERIFY_TOKEN_LENGTH = 32;
	private static final String SERVER_ID = "AtlasMC";
	
	private final ServerSocketConnectionHandler connection;
	private final String name;
	private final UUID uuid;
	private volatile boolean closed;
	private volatile Chat disconnectMessage;
	private volatile boolean useAuthentication;
	private volatile CompletableFuture<Boolean> encryptionFuture;
	
	public CoreLoginHandler(ServerSocketConnectionHandler connection, String name, UUID uuid) {
		this.connection = Objects.requireNonNull(connection);
		this.name = Objects.requireNonNull(name);
		this.uuid = Objects.requireNonNull(uuid);
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
		});
		return true;
	}

	@Override
	public synchronized Future<Boolean> enableEncryption(boolean authentication) {
		var future = this.encryptionFuture;
		if (future != null)
			return future;
		this.useAuthentication = authentication;
		future = new CompletableFuture<>();
		this.encryptionFuture = future;
		var packet = new ClientboundEncryptionRequest();
		packet.serverID = SERVER_ID;
		packet.publicKey = Atlas.getKeyPair().getPublic().getEncoded();
		packet.verifyToken = generateVerifyToken();
		packet.authenticate = authentication;
		connection.sendPacket(packet);
		return future;
	}
	
	private static byte[] generateVerifyToken() {
		Random rand = new Random();
		byte[] token = new byte[VERIFY_TOKEN_LENGTH];
		for (int i = 0; i < VERIFY_TOKEN_LENGTH; i++)
			token[i] = (byte) rand.nextInt(0x100);
		return token;
	}
	
	private static String createServerHash(PublicKey key, SecretKey secret) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(SERVER_ID.getBytes("ISO-8859-1"));
		md.update(key.getEncoded());
		md.update(secret.getEncoded());
		byte[] data = md.digest();
		return new BigInteger(data).toString(16);
	}

}
