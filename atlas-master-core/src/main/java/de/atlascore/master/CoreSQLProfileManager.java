package de.atlascore.master;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNetworkException;
import de.atlasmc.atlasnetwork.player.AtlasPlayer;
import de.atlasmc.master.AtlasMaster;
import de.atlasmc.util.AtlasUtil;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

public class CoreSQLProfileManager extends CoreProfileManager  {
	
	private Future<AtlasPlayer> queryProfile(String key, Object value) {
		AtlasPlayer player;
		try (Connection con = AtlasMaster.getDatabase().getConnection(true)) {
			PreparedStatement stmt = con.prepareStatement("SELECT mojang_uuid, internal_uuid, mojang_name, internal_name, join_first, join_last FROM profiles WHERE " + key + "=?");
			stmt.setObject(1, value);
			ResultSet result = stmt.executeQuery();
			CoreAtlasPlayer profile = null;
			if (result.next()) {
				UUID mUUID = AtlasUtil.uuidFromBytes(result.getBytes(1), 0);
				UUID iUUID = AtlasUtil.uuidFromBytes(result.getBytes(2), 0);
				String mName = result.getString(3);
				String iName = result.getString(4);
				Date firstJoin = result.getDate(5);
				Date lastJoin = result.getDate(6);
				profile = new CoreAtlasPlayer(this, mName, mUUID, iName, iUUID, firstJoin, lastJoin);
			}
			player = profile;
		} catch (Exception e) {
			return new CompleteFuture<>(null, new AtlasNetworkException("Error while querrying player profile!", e));
		}
		if (player == null)
			return CompleteFuture.getNullFuture();
		player = cache(player);
		return CompleteFuture.of(player);
	}

	@Override
	public Future<AtlasPlayer> loadPlayer(String name) {
		AtlasPlayer player = internalGet(by_name, name);
		if (player != null)
			return CompleteFuture.of(player);
		return queryProfile("internal_name", name);
	}

	@Override
	public Future<AtlasPlayer> loadPlayer(UUID uuid) {
		AtlasPlayer player = internalGet(by_uuid, uuid);
		if (player != null)
			return CompleteFuture.of(player);
		return queryProfile("internal_uuid", AtlasUtil.uuidToBytes(uuid));
	}

	@Override
	public Future<AtlasPlayer> loadPlayerByMojang(String name) {
		AtlasPlayer player = internalGet(by_mojang_name, name);
		if (player != null)
			return CompleteFuture.of(player);
		return queryProfile("mojang_name", name);
	}

	@Override
	public Future<AtlasPlayer> loadPlayerByMojang(UUID uuid) {
		AtlasPlayer player = internalGet(by_mojang_uuid, uuid);
		if (player != null)
			return CompleteFuture.of(player);
		return queryProfile("mojang_uuid", AtlasUtil.uuidToBytes(uuid));
	}

	protected void updateLastJoind(AtlasPlayer player, Date date) {
		try (Connection con = AtlasMaster.getDatabase().getConnection(true)) {
			PreparedStatement stmt = con.prepareStatement("UPDATE profiles SET join_last=? WHERE internal_uuid=?");
			stmt.setObject(1, date);
			stmt.setBytes(2, AtlasUtil.uuidToBytes(player.getInternalUUID()));
			stmt.execute();
		} catch (Exception e) {
			throw new AtlasNetworkException("Error while updating player profile!", e);
		}
	}

	@Override
	protected void updateInternalName(AtlasPlayer player, String name) {
		try (Connection con = AtlasMaster.getDatabase().getConnection(true)) {
			PreparedStatement stmt = con.prepareStatement("UPDATE profiles SET internal_name=? WHERE internal_uuid=?");
			stmt.setString(1, name);
			stmt.setBytes(2, AtlasUtil.uuidToBytes(player.getInternalUUID()));
			stmt.execute();
		} catch (Exception e) {
			throw new AtlasNetworkException("Error while updating player profile!", e);
		}
	}

	@Override
	protected AtlasPlayer internalCreatePlayer(UUID mojangUUID, UUID internalUUID, String mojangName, String internalName, Date firstJoin) {
		try (Connection con = AtlasMaster.getDatabase().getConnection(true)) {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO profiles (mojang_uuid, internal_uuid, mojang_name, internal_name, join_first, join_last) VALUES (?, ?, ?, ?, ?, ?)");
			stmt.setBytes(1, AtlasUtil.uuidToBytes(mojangUUID));
			stmt.setBytes(2, AtlasUtil.uuidToBytes(internalUUID));
			stmt.setString(3, mojangName);
			stmt.setString(4, internalName);
			stmt.setDate(5, firstJoin);
			stmt.setDate(6, firstJoin);
			stmt.execute();
		} catch (Exception e) {
			throw new AtlasNetworkException("Error while creating player profile!", e);
		}
		return new CoreAtlasPlayer(this, mojangName, mojangUUID, internalName, internalUUID, firstJoin, firstJoin);
	}

}
