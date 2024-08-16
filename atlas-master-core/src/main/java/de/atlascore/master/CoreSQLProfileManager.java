package de.atlascore.master;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNetworkException;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.master.AtlasMaster;
import de.atlasmc.util.AtlasUtil;

public class CoreSQLProfileManager extends CoreProfileManager  {
	
	private AtlasPlayer queryProfile(String key, Object value) {
		Connection con = null;
		try {
			con = AtlasMaster.getDatabase().getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT profile_id, mojang_uuid, internal_uuid, mojang_name, internal_name, join_first, join_last FROM profiles WHERE " + key + "=?");
			stmt.setObject(1, value);
			ResultSet result = stmt.executeQuery();
			CoreAtlasPlayer profile = null;
			if (result.next()) {
				int id = result.getInt(1);
				UUID mUUID = AtlasUtil.uuidFromBytes(result.getBytes(2), 0);
				UUID iUUID = AtlasUtil.uuidFromBytes(result.getBytes(3), 0);
				String mName = result.getString(4);
				String iName = result.getString(5);
				Date firstJoin = result.getDate(6);
				Date lastJoin = result.getDate(7);
				profile = new CoreAtlasPlayer(this, id, mName, mUUID, iName, iUUID, firstJoin, lastJoin);
			}
			stmt.close();
			result.close();
			return profile;
		} catch (SQLException e) {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e1) {}
			throw new AtlasNetworkException("Error while querrying player profile!", e);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {}
		}
	}

	@Override
	protected AtlasPlayer loadPlayer(String name) {
		return queryProfile("internal_name", name);
	}

	@Override
	protected AtlasPlayer loadPlayer(UUID uuid) {
		return queryProfile("internal_uuid", AtlasUtil.uuidToBytes(uuid));
	}

	@Override
	protected AtlasPlayer loadPlayer(int id) {
		return queryProfile("user_id", id);
	}

	@Override
	protected AtlasPlayer loadPlayerByMojang(String name) {
		return queryProfile("mojang_name", name);
	}

	@Override
	protected AtlasPlayer loadPlayerByMojang(UUID uuid) {
		return queryProfile("mojang_uuid", AtlasUtil.uuidToBytes(uuid));
	}

	@Override
	protected void updateLastJoind(AtlasPlayer player, Date date) {
		Connection con = null;
		try {
			con = AtlasMaster.getDatabase().getConnection();
			PreparedStatement stmt = con.prepareStatement("UPDATE profiles SET join_last=? WHERE profile_id=?");
			stmt.setObject(1, date);
			stmt.setInt(2, player.getID());
			stmt.execute();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e1) {}
			throw new AtlasNetworkException("Error while updating player profile!", e);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {}
		}
	}

	@Override
	protected void updateInternalUUID(AtlasPlayer player, UUID uuid) {
		Connection con = null;
		try {
			con = AtlasMaster.getDatabase().getConnection();
			PreparedStatement stmt = con.prepareStatement("UPDATE profiles SET internal_uuid=? WHERE profile_id=?");
			stmt.setBytes(1, AtlasUtil.uuidToBytes(uuid));
			stmt.setInt(2, player.getID());
			stmt.execute();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e1) {}
			throw new AtlasNetworkException("Error while updating player profile!", e);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {}
		}
	}

	@Override
	protected void updateInternalName(AtlasPlayer player, String name) {
		Connection con = null;
		try {
			con = AtlasMaster.getDatabase().getConnection();
			PreparedStatement stmt = con.prepareStatement("UPDATE profiles SET internal_name=? WHERE profile_id=?");
			stmt.setString(1, name);
			stmt.setInt(2, player.getID());
			stmt.execute();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e1) {}
			throw new AtlasNetworkException("Error while updating player profile!", e);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {}
		}
	}

	@Override
	protected AtlasPlayer internalCreatePlayer(UUID mojangUUID, UUID internalUUID, String mojangName, String internalName, Date firstJoin) {
		Connection con = null;
		int id = -1;
		try {
			con = AtlasMaster.getDatabase().getConnection();
			PreparedStatement stmt = con.prepareStatement("INSERT INTO profiles (mojang_uuid, internal_uuid, mojang_name, internal_name, join_first, join_last) VALUES (?, ?, ?, ?, ?, ?)");
			stmt.setBytes(1, AtlasUtil.uuidToBytes(mojangUUID));
			stmt.setBytes(2, AtlasUtil.uuidToBytes(internalUUID));
			stmt.setString(3, mojangName);
			stmt.setString(4, internalName);
			stmt.setDate(5, firstJoin);
			stmt.setDate(6, firstJoin);
			stmt.execute();
			ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			if (result.next()) {
				id = result.getInt(1);
			}
			result.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e1) {}
			throw new AtlasNetworkException("Error while creating player profile!", e);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {}
		}
		if (id == -1) {
			return null;
		}
		return new CoreAtlasPlayer(this, id, mojangName, mojangUUID, internalName, internalUUID, firstJoin, firstJoin);
	}

}
