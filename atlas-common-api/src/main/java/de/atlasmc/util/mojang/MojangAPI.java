package de.atlasmc.util.mojang;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.Future;

/**
 * Web API 
 */
public class MojangAPI {
	
	private static final String
	MINECRAFTSERVICES = "https://api.minecraftservices.com/minecraft",
	SESSION_SERVER = "https://sessionserver.mojang.com/session/minecraft";
	
	private static final int
	/**
	 * valid response
	 */
	HTTP_OK = 200,
	/**
	 * no content (no data?)
	 */
	HTTP_NO_CONTENT = 204,
	/**
	 * missing Content-Type: application/json
	 */
	HTTP_UNSUPPORTED_MEDIA_TYPE = 415,
	/**
	 * Malformed json
	 */
	HTTP_BAD_REQUEST = 400,
	/**
	 * Length of a UUID String without dashes
	 */
	ID_LENGTH = 32,
	/**
	 * Length of a player name
	 */
	PLAYER_NAME_LENGTH = 16;
	
	private static final class LazyHolder {
		
		private static final MojangAPI INSTANCE = new MojangAPI();
		
	}
	
	private final HttpClient client = HttpClient.newHttpClient();
	
	public static MojangAPI getInstance() {
		return LazyHolder.INSTANCE;
	}
	
	private URI playerByNameURI(String name) {
		validateName(name);
		return URI.create(MINECRAFTSERVICES + "/profile/lookup/name/" + name);
	}
	
	@Nullable
	public PlayerInfo playerByName(String name) throws IOException, InterruptedException {
		return toPlayerInfo(getObject(playerByNameURI(name)));
	}
	
	@NotNull
	public Future<PlayerInfo> playerByNameAync(String name) {
		return getObjectAsync(playerByNameURI(name), this::toPlayerInfo);
	}
	
	private URI playerByIDURI(String id) {
		validateID(id);
		return URI.create(MINECRAFTSERVICES + "/profile/lookup/" + id);
	}
	
	@Nullable
	public PlayerInfo playerByUUID(UUID uuid) throws IOException, InterruptedException {
		return playerByUUID(uuid.toString());
	}
	
	@Nullable
	public PlayerInfo playerByUUID(String id) throws IOException, InterruptedException {
		return toPlayerInfo(getObject(playerByIDURI(id)));
	}
	
	@NotNull
	public Future<PlayerInfo> playerByUUIDAsync(UUID uuid) {
		return playerByNameAync(uuid.toString());
	}
	
	@NotNull
	public Future<PlayerInfo> playerByUUIDAsync(String id) {
		return getObjectAsync(playerByIDURI(id), this::toPlayerInfo);
	}
	
	private void validateName(String name) {
		if (name.length() > PLAYER_NAME_LENGTH) {
			throw new IllegalArgumentException("Name can not be longer than " + PLAYER_NAME_LENGTH +  " chars: " + name.length());
		}
	}
	
	private void validateID(String id) {
		if (id.length() != ID_LENGTH || id.length() == 36) {
			throw new IllegalArgumentException("Name can not be longer than " + ID_LENGTH + " chars: " + id.length());
		}
	}
	
	@Nullable
	private PlayerInfo toPlayerInfo(JsonObject object) {
		if (object == null)
			return null;
		JsonElement isLegacy = object.get("legacy");
		JsonElement isDemo = object.get("demo");
		boolean legacy = isLegacy != null ? isLegacy.getAsBoolean() : false;
		boolean demo = isDemo != null ? isDemo.getAsBoolean() : false;
		UUID uuid = uuidFromString(object.get("id").getAsString());
		return new PlayerInfo(uuid, object.get("name").getAsString(), legacy, demo);
	}
	
	private URI playerProfileURI(String id, boolean unsigned) {
		validateID(id);
		var uri = SESSION_SERVER + "/profile/" + id;
		if (!unsigned)
			uri += "?unsigned=false";
		return URI.create(uri);
	}
	
	@Nullable
	public PlayerProfile playerProfile(UUID uuid) throws IOException, InterruptedException {
		return playerProfile(uuid, true);
	}
	
	@Nullable
	public PlayerProfile playerProfile(UUID uuid, boolean unsigned) throws IOException, InterruptedException {
		return playerProfile(uuid.toString(), unsigned);
	}
	
	@Nullable
	public PlayerProfile playerProfile(String id) throws IOException, InterruptedException {
		return playerProfile(id, true);
	}
	
	@Nullable
	public PlayerProfile playerProfile(String id, boolean unsigned) throws IOException, InterruptedException {
		return toPlayerProfile(getObject(playerProfileURI(id, unsigned)));
	}
	
	@Nullable
	public Future<PlayerProfile> playerProfileAsync(UUID uuid) {
		return playerProfileAsync(uuid, true);
	}
	
	@Nullable
	public Future<PlayerProfile> playerProfileAsync(UUID uuid, boolean unsigned) {
		return playerProfileAsync(uuid.toString(), unsigned);
	}
	
	@Nullable
	public Future<PlayerProfile> playerProfileAsync(String id) {
		return playerProfileAsync(id, true);
	}
	
	@Nullable
	public Future<PlayerProfile> playerProfileAsync(String id, boolean unsigned) {
		return getObjectAsync(playerProfileURI(id, unsigned), this::toPlayerProfile);
	}
	
	@Nullable
	private PlayerProfile toPlayerProfile(JsonObject object) {
		if (object == null)
			return null;
		String name = object.get("name").getAsString();
		UUID uuid = uuidFromString(object.get("id").getAsString());
		JsonElement isLegacy = object.get("legacy");
		boolean legacy = isLegacy != null ? isLegacy.getAsBoolean() : false;
		PlayerProfile profile = new PlayerProfile(name);
		profile.setUUID(uuid);
		profile.setLegacy(legacy);
		JsonArray array = object.getAsJsonArray("properties");
		if (array == null)
			throw new MojangAPIException("Missing properties on player profile");
		final int size = array.size();
		for (int i = 0; i < size; i++) {
			var entry = array.get(i).getAsJsonObject();
			JsonElement rawSignature = entry.get("signature");
			var signature = rawSignature != null ? rawSignature.getAsString() : null;
			var property = new ProfileProperty(
					entry.get("name").getAsString(), 
					entry.get("value").getAsString(), 
					signature);
			profile.addProperty(property);
		}
		return profile;
	}
	
	private URI verifyLoginServerURI(String name, String serverID, String ip) {
		validateName(name);
		var uri = SESSION_SERVER + "/minecraft/HasJoined?username=" + name + "&serverId=" + serverID;
		if (ip != null)
			uri += "&ip=" + ip;
		return URI.create(uri);
	}
	
	@Nullable
	public PlayerProfile verifyLoginServer(String name, String serverID) throws IOException, InterruptedException {
		return verifyLoginServer(name, serverID, null);
	}
	
	@Nullable
	public PlayerProfile verifyLoginServer(String name, String serverID, String ip) throws IOException, InterruptedException {
		return toPlayerProfile(getObject(verifyLoginServerURI(name, serverID, ip)));
	}
	
	@Nullable
	public Future<PlayerProfile> verifyLoginServerAsync(String name, String serverID) {
		return verifyLoginServerAsync(name, serverID, null);
	}
	
	@Nullable
	public Future<PlayerProfile> verifyLoginServerAsync(String name, String serverID, String ip) {
		return getObjectAsync(verifyLoginServerURI(name, serverID, ip), this::toPlayerProfile);
	}
	
	@Nullable
	private JsonObject getObject(URI uri) throws IOException, InterruptedException {
		final var request = HttpRequest.newBuilder()
				.uri(uri)
				.GET()
				.build();
		final var response = requestAPI(request);
		return response != null ? response.getAsJsonObject() : null;
	}
	
	private <V> Future<V> getObjectAsync(URI uri, Function<JsonObject, V> serializer) {
		final var request = HttpRequest.newBuilder()
				.uri(uri)
				.GET()
				.build();
		return requetObjectAsync(request, serializer);
	}
	
	private <V> Future<V> requetObjectAsync(HttpRequest request, Function<JsonObject, V> serializer) {
		final var future = new CompletableFuture<V>();
		client.sendAsync(request, BodyHandlers.ofString())
			.whenComplete((response, exception) -> {
				if (response == null) {
					future.complete(null, exception);
					return;
				}
				final var code = response.statusCode();
				switch (code) {
				case HTTP_NO_CONTENT:
					future.complete(null);
					return;
				case HTTP_OK:
					var json = JsonParser.parseString(response.body());
					future.complete(serializer.apply(json.getAsJsonObject()), exception);
					return;
				case HTTP_UNSUPPORTED_MEDIA_TYPE:
					future.complete(null, new MojangAPIException("Missing media Content-Type: \"application/json\"", response));
					return;
				case HTTP_BAD_REQUEST:
					future.complete(null, new MojangAPIException("Malformed request!", response));
					return;
				default:
					future.complete(null, new MojangAPIException(response));
					return;
				}
			});
		return future;
	}
	
	@Nullable
	private JsonElement requestAPI(HttpRequest request) throws IOException, InterruptedException {
		final var response = client.send(request, BodyHandlers.ofString());
		final var code = response.statusCode();
		switch (code) {
		case HTTP_NO_CONTENT:
			return null;
		case HTTP_OK:
			return JsonParser.parseString(response.body());
		case HTTP_UNSUPPORTED_MEDIA_TYPE:
			throw new MojangAPIException("Missing media Content-Type: \"application/json\"", response);
		case HTTP_BAD_REQUEST:
			throw new MojangAPIException("Malformed request!", response);
		default:
			throw new MojangAPIException(response);
		}
	}
	
	@NotNull
	public static String buildServerIDHash(String serverID, PublicKey key, SecretKey secret) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(serverID.getBytes("ISO-8859-1"));
		md.update(key.getEncoded());
		md.update(secret.getEncoded());
		byte[] data = md.digest();
		return new BigInteger(data).toString(16);
	}
	
	@NotNull
	public static String uuidToIDString(UUID uuid) {
		return uuid.toString().replace("-", "");
	}
	
	@NotNull
	public static UUID uuidFromString(String id) {
		if (id.length() == 36)
			return UUID.fromString(id);
		return UUID.fromString(spaceIDString(id));
	}
	
	@NotNull
	public static String spaceIDString(String id) {
		if (id.length() != 32)
			throw new IllegalArgumentException("Invalid id length expected 32: " + id.length());
		char[] chars = new char[36];
		int i = 0;
		for (; i < 8; i++) {
			chars[i] = id.charAt(i);
		}
		chars[i++] = '-';
		chars[i++] = id.charAt(8);
		chars[i++] = id.charAt(9);
		chars[i++] = id.charAt(10);
		chars[i++] = id.charAt(11);
		chars[i++] = '-';
		chars[i++] = id.charAt(12);
		chars[i++] = id.charAt(13);
		chars[i++] = id.charAt(14);
		chars[i++] = id.charAt(15);
		chars[i++] = '-';
		chars[i++] = id.charAt(16);
		chars[i++] = id.charAt(17);
		chars[i++] = id.charAt(18);
		chars[i++] = id.charAt(19);
		chars[i++] = '-';
		for (int j = 20; j < 32; j++) {
			chars[i++] = id.charAt(j);
		}
		return new String(chars);
	}
	
	public void close() {
		client.close();
	}
	

}
