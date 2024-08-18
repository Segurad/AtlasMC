package de.atlascore.master.permission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import de.atlascore.permission.CoreAbstractPermissionManager;
import de.atlascore.permission.CorePermission;
import de.atlascore.permission.CorePermissionContext;
import de.atlascore.permission.CorePermissionGroup;
import de.atlascore.permission.CorePlayerPermissionHandler;
import de.atlasmc.Atlas;
import de.atlasmc.Color;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.master.AtlasMaster;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionContext;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

public class CoreSQLPermissionManager extends CoreAbstractPermissionManager {

	private final Map<UUID, CompletableFuture<PermissionHandler>> futureHandlers;
	private final Map<Integer, CompletableFuture<PermissionContext>> futureContexts;
	private final Map<String, CompletableFuture<PermissionGroup>> futureGroups;
	
	private final SQLFunction<UUID, PermissionHandler> loadHandler = this::internalLoadHandler;
	private final SQLFunction<Integer, PermissionContext> loadContext = this::internalLoadContext;
	private final SQLFunction<String, PermissionGroup> loadGroup = this::internalLoadGroup;
	
	public CoreSQLPermissionManager() {
		futureHandlers = new ConcurrentHashMap<>();
		futureContexts = new ConcurrentHashMap<>();
		futureGroups = new ConcurrentHashMap<>();
	}
	
	@Override
	public Future<PermissionHandler> loadHandler(UUID uuid) {
		return load(handlers, futureHandlers, uuid, loadHandler);
	}
	
	@Override
	public Future<PermissionContext> createContext(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<PermissionContext> loadContext(int id) {
		return load(contexts, futureContexts, id, loadContext);
	}

	@Override
	public Future<Boolean> deleteContext(PermissionContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Boolean> saveContext(PermissionContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<PermissionGroup> createGroup(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<PermissionGroup> loadGroup(String name) {
		return load(groups, futureGroups, name, loadGroup);
	}

	@Override
	public Future<Boolean> saveGroup(PermissionGroup group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Boolean> deleteGroup(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Boolean> saveHandler(PermissionHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<PermissionHandler> createHandler(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private <K, V> Future<V> load(Map<K, V> cache, Map<K, CompletableFuture<V>> futures, K key, SQLFunction<K, V> loadFunction) {
		V value = cache.get(key);
		if (value != null)
			return CompleteFuture.of(value);
		CompletableFuture<V> future = futures.get(key);
		if (future != null)
			return future;
		synchronized (futureHandlers) {
			value = cache.get(key);
			if (value != null)
				return CompleteFuture.of(value);
			future = futures.get(key);
			if (future != null)
				return future;
			future = new CompletableFuture<>();
			futures.put(key, future);
		}
		Connection con = null;
		try {
			con = AtlasMaster.getDatabase().getConnection();
			value = loadFunction.load(con, key);
		} catch (SQLException e) {
			future.finish(null, e);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {}
		}
		cache.put(key, value);
		synchronized (futures) {
			future.finish(value);
			futureHandlers.remove(key, future);
		}
		return future;
	}

	private PermissionHandler internalLoadHandler(Connection con, UUID uuid) throws SQLException {
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
			PermissionContext context = internalLoadContext(con, result.getInt(1), result.getString(2), result.getString(3));
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
			PermissionGroup parentGroup = getGroup(parentName);
			if (parentGroup != null)
				handler.addPermissionGroup(parentGroup);
		}
		result.close();
		return handler;
	}

	private PermissionGroup internalLoadGroup(Connection con, String name) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("SELECT group_id, name, sort_weight, prefix, suffix, chat_color, name_color, power, is_default FROM perm_groups WHERE name=?");
		stmt.setString(1, name);
		ResultSet result = stmt.executeQuery();
		stmt.close();
		if (result.next()) {
			result.close();
			return null;
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
			PermissionContext context = internalLoadContext(con, result.getInt(1), result.getString(2), result.getString(3));
			group.addPermissionContext(context);
		}
		result.close();
		// load parents
		stmt = con.prepareStatement("SELECT name FROM perm_groups " 
				+ "INNER JOIN perm_group_inherit "
				+ "ON group_id=perm_group_inherit.parent_id WHERE perm_group_inherit.group_id = ?");
		stmt.setInt(1, groupID);
		result = stmt.executeQuery();
		con.close();
		con = null;
		ArrayList<Future<PermissionGroup>> futureGroups = null;
		while (result.next()) {
			String parentName = result.getString(1);
			PermissionGroup parentGroup = getGroup(parentName);
			if (parentGroup == null) {
				Future<PermissionGroup> futureGroup = loadGroup(parentName);
				if (futureGroup.isDone()) {
					parentGroup = futureGroup.getNow();
					continue;
				}
				if (futureGroups == null)
					futureGroups = new ArrayList<>();
				futureGroups.add(futureGroup);
			}
			if (parentGroup != null)
				group.addParent(parentGroup);
		}
		result.close();
		stmt.close();
		group.changedContext();
		group.changedPermissionContexts();
		group.changedGroup();
		group.changedParents();
		group.changedPermissions();
		return group;
	}

	private PermissionContext internalLoadContext(Connection con, int id) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("SELECT ctx_key, ctx_value FROM perm_context WHERE context_id=?");
		stmt.setInt(1, id);
		ResultSet result = stmt.executeQuery();
		if (!result.next())
			return null;
		String key = result.getString(1);
		String value = result.getString(2);
		stmt.close();
		return internalLoadContext(con, id, key, value);
	}
	
	private PermissionContext internalLoadContext(Connection con, int id, String key, String value) throws SQLException {
		CorePermissionContext context = new CorePermissionContext(id, key, value);
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
	
	private static interface SQLFunction<K, R> {
		
		R load(Connection connection, K key) throws SQLException;

	}

}
