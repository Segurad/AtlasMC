package de.atlascore.atlasnetwork.master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.atlascore.atlasnetwork.CorePermissionProvider;
import de.atlascore.permission.CorePermissionContext;
import de.atlascore.permission.CorePermissionGroup;
import de.atlascore.permission.CorePlayerPermissionHandler;
import de.atlasmc.Atlas;
import de.atlasmc.Color;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.permission.PermissionContext;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.sql.SQLConnectionPool;

public class CoreSQLPermissionProvider extends CorePermissionProvider {

	private final SQLConnectionPool conPool;

	public CoreSQLPermissionProvider(SQLConnectionPool con) {
		this.conPool = con;
	}

	@Override
	protected Future<PermissionHandler> loadHandler(AtlasPlayer player) {
		Connection con = null;
		Future<PermissionHandler> future = null;
		try {
			con = conPool.getConnection();
			future = internalLoadHandler(con, player);
		} catch (SQLException e) {
			Atlas.getLogger().info("Error while loading permission handler!", e);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {}
		}
		return future == null ? CompleteFuture.getNullFuture() : future;
	}

	private Future<PermissionHandler> internalLoadHandler(Connection con, AtlasPlayer player) throws SQLException {
		final int userID = player.getID();
		final CorePlayerPermissionHandler handler = new CorePlayerPermissionHandler();
		// load permissions
		PreparedStatement stmt = con.prepareStatement("SELECT perm, power FROM perm_user_perm WHERE user_id=?");
		stmt.setInt(1, userID);
		ResultSet result = stmt.executeQuery();
		stmt.close();
		while (result.next()) {
			handler.setPermission(result.getString(1), result.getInt(2));
		}
		result.close();
		// load context
		stmt = con.prepareStatement("SELECT ctx_key, ctx_value FROM perm_user_context WHERE user_id=?");
		stmt.setInt(1, userID);
		result = stmt.executeQuery();
		stmt.close();
		while (result.next()) {
			handler.setContext(result.getString(1), result.getString(2));
		}
		result.close();
		// load context permissions
		stmt = con.prepareStatement("SELECT context_id, ctx_key, ctx_value FROM perm_context " 
				+ "INNER JOIN perm_user_perm_context "
				+ "ON context_id=perm_user_perm_context.context_id WHERE user_id=?");
		stmt.setInt(1, userID);
		result = stmt.executeQuery();
		stmt.close();
		while (result.next()) {
			PermissionContext context = getContext(con, result.getInt(1), result.getString(2), result.getString(3));
			handler.addPermissionContext(context);
		}
		result.close();
		// load parents
		stmt = con.prepareStatement("SELECT name FROM perm_groups " 
				+ "INNER JOIN perm_user_group "
				+ "ON group_id=perm_user_group.group_id WHERE perm_user_group.user_id = ?");
		stmt.setInt(1, userID);
		result = stmt.executeQuery();
		stmt.close();
		stmt = null;
		con.close();
		con = null;
		while (result.next()) {
			String parentName = result.getString(1);
			PermissionGroup parentGroup = null;
			try {
				parentGroup = getGroup(parentName, true).get();
			} catch (Exception e) {
				result.close();
				return new CompleteFuture<>(e);
			}
			if (parentGroup != null)
				handler.addPermissionGroup(parentGroup);
		}
		result.close();
		return new CompletableFuture<>();
	}

	@Override
	protected Future<PermissionGroup> loadGroup(String name) {
		Connection con = null;
		Future<PermissionGroup> future = null;
		try {
			con = conPool.getConnection();
			future = internalLoadGroup(con, name);
		} catch (SQLException e) {
			Atlas.getLogger().info("Error while loading permission group!", e);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {}
		}
		return future == null ? CompleteFuture.getNullFuture() : future;
	}

	private Future<PermissionGroup> internalLoadGroup(Connection con, String name) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("SELECT group_id, name, sort_weight, prefix, suffix, chat_color, name_color, power, is_default FROM perm_groups WHERE name=?");
		stmt.setString(1, name);
		ResultSet result = stmt.executeQuery();
		stmt.close();
		if (result.next()) {
			result.close();
			return CompleteFuture.getNullFuture();
		}
		// load group
		int groupID = result.getInt(1);
		CorePermissionGroup group = new CorePermissionGroup(name);
		group.setSortWeight(result.getInt(3));
		group.setPrefix(ChatUtil.toChat(result.getString(4)));
		group.setSuffix(ChatUtil.toChat(result.getString(5)));
		Color color = Color.fromRGB(result.getInt(6));
		ChatColor chatColor = ChatColor.getByColor(color);
		if (chatColor != null)
			group.setChatColor(chatColor);
		else
			group.setChatColor(color);
		group.setNameColor(ChatColor.getByID(result.getInt(7)));
		group.setPower(result.getInt(8));
		group.setDefault(result.getBoolean(9));
		result.close();
		// load permissions
		stmt = con.prepareStatement("SELECT perm, power FROM perm_group_perm WHERE group_id=?");
		stmt.setInt(1, groupID);
		result = stmt.executeQuery();
		stmt.close();
		while (result.next()) {
			group.setPermission(result.getString(1), result.getInt(2));
		}
		result.close();
		// load context
		stmt = con.prepareStatement("SELECT ctx_key, ctx_value FROM perm_group_context WHERE group_id=?");
		stmt.setInt(1, groupID);
		result = stmt.executeQuery();
		stmt.close();
		while (result.next()) {
			group.setContext(result.getString(1), result.getString(2));
		}
		result.close();
		// load context permissions
		stmt = con.prepareStatement("SELECT context_id, ctx_key, ctx_value FROM perm_context " 
				+ "INNER JOIN perm_group_perm_context "
				+ "ON context_id=perm_group_perm_context.context_id WHERE group_id=?");
		stmt.setInt(1, groupID);
		result = stmt.executeQuery();
		stmt.close();
		while (result.next()) {
			PermissionContext context = getContext(con, result.getInt(1), result.getString(2), result.getString(3));
			group.addPermissionContext(context);
		}
		result.close();
		// load parents
		stmt = con.prepareStatement("SELECT name FROM perm_groups " 
				+ "INNER JOIN perm_group_inherit "
				+ "ON group_id=perm_group_inherit.parent_id WHERE perm_group_inherit.group_id = ?");
		stmt.setInt(1, groupID);
		result = stmt.executeQuery();
		stmt.close();
		stmt = null;
		con.close();
		con = null;
		while (result.next()) {
			String parentName = result.getString(1);
			PermissionGroup parentGroup = null;
			try {
				parentGroup = getGroup(parentName, true).get();
			} catch (Exception e) {
				result.close();
				return new CompleteFuture<>(e);
			}
			if (parentGroup != null)
				group.addParent(parentGroup);
		}
		result.close();
		return new CompletableFuture<>();
	}

	private CorePermissionContext getContext(Connection con, int id, String key, String value) throws SQLException {
		CorePermissionContext context = new CorePermissionContext(key, value);
		PreparedStatement stmt = con.prepareStatement("SELECT perm, power FROM perm_context_perm WHERE context_id=?");
		stmt.setInt(1, id);
		ResultSet ctxPermResult = stmt.executeQuery();
		stmt.close();
		while (ctxPermResult.next()) {
			context.setPermission(ctxPermResult.getString(1), ctxPermResult.getInt(2));
		}
		ctxPermResult.close();
		return context;
	}

}
