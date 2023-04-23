package de.atlascore.atlasnetwork.master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.PooledConnection;

import de.atlascore.atlasnetwork.CoreAtlasPlayer;
import de.atlascore.atlasnetwork.CorePlayerProfileHandler;
import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.util.MathUtil;

public class CoreSQLPlayerProfileHandler extends CorePlayerProfileHandler {

	private PooledConnection conPool;
	
	public CoreSQLPlayerProfileHandler(PooledConnection conPool) {
		this.conPool = conPool;
	}

	@Override
	public AtlasPlayer getPlayer(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		if (name.length() > 16)
			throw new IllegalArgumentException("Name can not be longer than 16 chars: " + name.length());
		CoreAtlasPlayer profile = getCached(name);
		if (profile != null)
			return profile.createRev();
		synchronized (this) {
			profile = getCached(name);
			if (profile != null)
				return profile.createRev();
			return queryProfile("internal_name", name);
		}
	}

	@Override
	public AtlasPlayer getPlayerByMojang(UUID uuid) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		CoreAtlasPlayer profile = getCachedByMojang(uuid);
		if (profile != null)
			return profile.createRev();
		synchronized (this) {
			profile = getCachedByMojang(uuid);
			if (profile != null)
				return profile.createRev();
			return queryProfile("mojang_uuid", MathUtil.uuidToBytes(uuid));
		}
	}

	@Override
	public AtlasPlayer getPlayerByMojang(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		if (name.length() > 16)
			throw new IllegalArgumentException("Name can not be longer than 16 chars: " + name.length());
		CoreAtlasPlayer profile = getCachedByMojang(name);
		if (profile != null)
			return profile.createRev();
		synchronized (this) {
			profile = getCachedByMojang(name);
			if (profile != null)
				return profile.createRev();
			return queryProfile("mojang_name", name);
		}
	}

	@Override
	public AtlasPlayer getPlayer(int id) {
		CoreAtlasPlayer profile = getCached(id);
		if (profile != null)
			return profile.createRev();
		synchronized (this) {
			profile = getCached(id);
			if (profile != null)
				return profile.createRev();
			return queryProfile("user_id", id);
		}
	}
	
	private AtlasPlayer queryProfile(String key, Object value) {
		Connection con = null;
		try {
			con = conPool.getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT profile_id, mojang_uuid, internal_uuid, mojang_name, internal_name FROM users WHERE ?=?");
			stmt.setString(0, key);
			stmt.setObject(1, value);
			ResultSet result = stmt.executeQuery();
			CoreAtlasPlayer profile = null;
			if (result.first()) {
				int id = result.getInt(0);
				UUID mUUID = MathUtil.uuidFromBytes(result.getBytes(1), 0);
				UUID iUUID = MathUtil.uuidFromBytes(result.getBytes(2), 0);
				String mName = result.getString(3);
				String iName = result.getString(4);
				profile = new CoreAtlasPlayer(this, id, mName, mUUID, iName, iUUID);
			}
			stmt.close();
			result.close();
			con.close();
			return profile.createRev();
		} catch (SQLException e) {
			Atlas.getLogger().error("Error while querrying player profile", e);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {}
		}
		return null;
	}

	@Override
	protected void save(CoreAtlasPlayer player) {
		Connection con = null;
		try {
			con = conPool.getConnection();
			PreparedStatement stmt = con.prepareStatement("UPDATE users SET internal_uuid=?, internal_name=? WHERE profile_id=?");
			stmt.setBytes(0, MathUtil.uuidToBytes(player.getInternalUUID()));
			stmt.setString(1, player.getInternalName());
			stmt.setInt(2, player.getID());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			Atlas.getLogger().error("Error while saving player profile", e);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {}
		}
	}

}
