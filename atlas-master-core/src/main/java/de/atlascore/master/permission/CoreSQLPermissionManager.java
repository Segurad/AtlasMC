package de.atlascore.master.permission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlascore.permission.CoreAbstractPermissionManager;
import de.atlascore.permission.CorePermissionContext;
import de.atlascore.permission.CorePermissionGroup;
import de.atlascore.permission.CorePermissionHandler;
import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.master.AtlasMaster;
import de.atlasmc.master.PermissionManager;
import de.atlasmc.permission.ContextProvider;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionContext;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.util.concurrent.future.CumulativeFuture;
import de.atlasmc.util.AtlasUtil;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.sql.SQLFunction;

public class CoreSQLPermissionManager extends CoreAbstractPermissionManager implements PermissionManager {

	private final Map<UUID, CompletableFuture<PermissionHandler>> futureHandlers;
	private final Map<Integer, CompletableFuture<PermissionContext>> futureContexts;
	private final Map<String, CompletableFuture<PermissionGroup>> futureGroups;
	
	private final SQLFunction<UUID, PermissionHandler> loadHandler = this::internalLoadHandler;
	private final SQLFunction<Integer, PermissionContext> loadContext = this::internalLoadContext;
	private final SQLFunction<String, PermissionGroup> loadGroup = this::internalLoadGroup;
	private final SQLFunction<UUID, PermissionHandler> createHandler = this::internalCreateHandler;
	private final SQLFunction<String, PermissionGroup> createGroup = this::internalCreateGroup;
	private final SQLFunction<UUID, Boolean> deleteHandler = this::internalDeleteHandler;
	private final SQLFunction<Integer, Boolean> deleteContext = this::internalDeleteContext;
	private final SQLFunction<String, Boolean> deleteGroup = this::internalDeleteGroup;
	private final SQLFunction<PermissionHandler, Boolean> saveHandler = this::internalSaveHandler;
	private final SQLFunction<PermissionContext, Boolean> saveContext = this::internalSaveContext;
	private final SQLFunction<PermissionGroup, Boolean> saveGroup = this::internalSaveGroup;
	
	public CoreSQLPermissionManager() {
		futureHandlers = new ConcurrentHashMap<>();
		futureContexts = new ConcurrentHashMap<>();
		futureGroups = new ConcurrentHashMap<>();
	}
	
	@Override
	public Future<PermissionHandler> loadHandler(UUID uuid) {
		return loadHandler(null, uuid);
	}
	
	private Future<PermissionHandler> loadHandler(Connection con, UUID uuid) {
		return load(con, handlers, futureHandlers, uuid, loadHandler);
	}
	
	@Override
	public Future<PermissionContext> createContext(String key, String value) {
		PermissionContext ctx;
		try (Connection con = AtlasMaster.getDatabase().getConnection(true)) {
			ctx = internalCreateContext(con, key, value);
		} catch (Exception e) {
			return new CompleteFuture<>(e);
		}
		contexts.put(ctx.getID(), ctx);
		return CompleteFuture.of(ctx);
	}
	
	@Override
	public Future<PermissionContext> loadContext(int id) {
		return loadContext(null, id);
	}

	private Future<PermissionContext> loadContext(Connection con, int id) {
		return load(con, contexts, futureContexts, id, loadContext);
	}

	@Override
	public Future<Boolean> deleteContext(int id) {
		return delete(contexts, futureContexts, id, deleteContext);
	}

	@Override
	public Future<Boolean> saveContext(PermissionContext context) {
		return save(context, saveContext);
	}

	@Override
	public Future<PermissionGroup> createGroup(String name) {
		return create(groups, futureGroups, name, loadGroup, createGroup);
	}

	@Override
	public Future<PermissionGroup> loadGroup(String name) {
		return loadGroup(null, name);
	}
	
	private Future<PermissionGroup> loadGroup(Connection con, String name) {
		return load(con, groups, futureGroups, name, loadGroup);
	}

	@Override
	public Future<Boolean> saveGroup(PermissionGroup group) {
		return save(group, saveGroup);
	}

	@Override
	public Future<Boolean> deleteGroup(String name) {
		return delete(groups, futureGroups, name, deleteGroup);
	}

	@Override
	public Future<Boolean> saveHandler(PermissionHandler handler) {
		return save(handler, saveHandler);
	}
	
	@Override
	public Future<Boolean> deleteHandler(UUID uuid) {
		return delete(handlers, futureHandlers, uuid, deleteHandler);
	}

	@Override
	public Future<PermissionHandler> createHandler(UUID uuid) {
		return create(handlers, futureHandlers, uuid, loadHandler, createHandler);
	}
	
	private <V> Future<Boolean> save(V value, SQLFunction<V, Boolean> saveFunction) {
		try (Connection con = AtlasMaster.getDatabase().getConnection(true)) {
			saveFunction.apply(con, value);
		} catch (Exception e) {
			return new CompleteFuture<>(e);
		}
		return CompleteFuture.getBooleanFuture(true);
	}
	
	private <K, V> Future<V> load(Connection con, Map<K, V> cache, Map<K, CompletableFuture<V>> futures, K key, SQLFunction<K, V> loadFunction) {
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
		if (con == null) {
			try (Connection c = AtlasMaster.getDatabase().getConnection(true)) {
				value = loadFunction.apply(c, key);
			} catch (Exception e) {
				future.finish(null, e);
				return future;
			}
		} else {
			try {
				value = loadFunction.apply(con, key);
			} catch (SQLException e) {
				future.finish(null, e);
				return future;
			}
		}
		cache.put(key, value);
		synchronized (futures) {
			future.finish(value);
			futureHandlers.remove(key, future);
		}
		return future;
	}
	
	private <K, V> Future<Boolean> delete(Map<K, V> cache, Map<K, CompletableFuture<V>> futures, K key, SQLFunction<K, Boolean> deleteFunction) {
		CompletableFuture<V> future = futures.get(key);
		final CompletableFuture<Boolean> result = new CompletableFuture<>();
		if (future != null && !future.isDone()) {
			future.setListener((f) -> {
				delete0(result, cache, futures, key, deleteFunction);
			});
		}
		delete0(result, cache, futures, key, deleteFunction);
		return result;
	}
	
	private <K, V> void delete0(CompletableFuture<Boolean> future, Map<K, V> cache, Map<K, CompletableFuture<V>> futures, K key, SQLFunction<K, Boolean> deleteFunction) {
		boolean success = false;
		try (Connection con = AtlasMaster.getDatabase().getConnection()) {
			success = deleteFunction.apply(con, key);
		} catch (SQLException e) {
			future.finish(null, e);
			return;
		}
		if (success)
			cache.remove(key);
		future.finish(success);
	}
	
	private <K, V> Future<V> create(Map<K, V> cache, Map<K, CompletableFuture<V>> futures, K key, SQLFunction<K, V> loadFunction, SQLFunction<K, V> createFunction) {
		V value = cache.get(key);
		if (value != null)
			return CompleteFuture.of(value);
		CompletableFuture<V> oldFuture = null;
		CompletableFuture<V> future = null;
		synchronized (futureHandlers) {
			value = cache.get(key);
			if (value != null)
				return CompleteFuture.of(value);
			future = new CompletableFuture<>();
			oldFuture = futures.get(key);
			if (oldFuture != null) {
				final CompletableFuture<V> createFuture = future;
				oldFuture.setListener((f) -> {
					V v = f.getNow();
					if (v != null) {
						createFuture.finish(v);
						return;
					}
					create0(createFuture, cache, futures, key, loadFunction, createFunction);
				});
				return future;
			}
			futures.put(key, future);
		}
		create0(future, cache, futures, key, loadFunction, createFunction);
		return future;
	}
	
	private <K, V> void create0(CompletableFuture<V> future, Map<K, V> cache, Map<K, CompletableFuture<V>> futures, K key, SQLFunction<K, V> loadFunction, SQLFunction<K, V> createFunction) {
		V value = null;
		try (Connection con = AtlasMaster.getDatabase().getConnection(true)) {
			value = loadFunction.apply(con, key);
			if (value == null) {
				value = createFunction.apply(con, key);
			}
		} catch (Exception e) {
			future.finish(null, e);
			return;
		}
		cache.put(key, value);
		synchronized (futures) {
			future.finish(value);
			futureHandlers.remove(key, future);
		}
	}

	private PermissionHandler internalLoadHandler(final Connection con,  final UUID uuid) throws SQLException {
		final CorePermissionHandler handler = new CorePermissionHandler(uuid, this);
		final byte[] uuidBytes = AtlasUtil.uuidToBytes(uuid);
		// load context
		try (PreparedStatement stmt = con.prepareStatement("SELECT ctx_key, ctx_value FROM perm_user_context WHERE user_id=?")) {
			stmt.setBytes(1, uuidBytes);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				handler.getContext().set(result.getString(1), result.getString(2));
			}
		}
		// load groups
		ArrayList<Future<PermissionGroup>> futureGroups = null;
		try (PreparedStatement stmt = con.prepareStatement("SELECT group FROM perm_user_group WHERE user_id = ?")) {
			stmt.setBytes(1, uuidBytes);
			ResultSet result = stmt.executeQuery();
			if (!result.isBeforeFirst()) {
				futureGroups = new ArrayList<>();
				while (result.next()) {
					String parentName = result.getString(1);
					futureGroups.add(loadGroup(con, parentName));
				}
			}
		}
		if (futureGroups != null && !futureGroups.isEmpty()) {
			for (PermissionGroup group : Future.getFutureResults(futureGroups)) {
				if (group == null)
					continue;
				handler.addPermissionGroup(group);
			}
		}
		handler.getContext().changedContext();
		handler.groupsChanged();
		return handler;
	}

	private PermissionGroup internalLoadGroup(final Connection con, String name) throws SQLException {
		final int groupID;
		final CorePermissionGroup group;
		// load group
		try (PreparedStatement stmt = con.prepareStatement("SELECT group_id, name, sort_weight, prefix, suffix, chat_color, name_color, power, is_default FROM perm_groups WHERE name=?")) {
			stmt.setString(1, name);
			ResultSet result = stmt.executeQuery();
			if (!result.next()) {
				return null;
			}
			groupID = result.getInt(1);
			group = new CorePermissionGroup(groupID, name);
			group.setSortWeight(result.getInt(3));
			group.setPrefix(ChatUtil.toChat(result.getString(4)));
			group.setSuffix(ChatUtil.toChat(result.getString(5)));
			int rawColor = result.getInt(6);
			if (!result.wasNull()) {
				Color color = Color.fromRGB(rawColor);
				ChatColor chatColor = ChatColor.getByColor(color);
				if (chatColor != null)
					group.setChatColor(chatColor);
				else
					group.setChatColor(color);
			}
			rawColor = result.getInt(7);
			if (!result.wasNull()) {
				Color color = Color.fromRGB(rawColor);
				ChatColor chatColor = ChatColor.getByColor(color);
				if (chatColor != null)
					group.setNameColor(chatColor);
				else
					group.setNameColor(color);
			}
			group.setPower(result.getInt(8));
			group.setDefault(result.getBoolean(9));
		}
		// load permissions
		try (PreparedStatement stmt = con.prepareStatement("SELECT perm, power FROM perm_group_perm WHERE group_id=?")) {
			stmt.setInt(1, groupID);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				group.setPermission(result.getString(1), result.getInt(2));
			}
		}
		// load context
		try (PreparedStatement stmt = con.prepareStatement("SELECT ctx_key, ctx_value FROM perm_group_context WHERE group_id=?")) {
			stmt.setInt(1, groupID);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				group.getContext().set(result.getString(1), result.getString(2));
			}
		}
		// load context permissions
		ArrayList<Future<PermissionContext>> futureCtxs = null;
		try (PreparedStatement stmt = con.prepareStatement("SELECT context_id FROM perm_group_perm_context WHERE group_id=?")) {
			stmt.setInt(1, groupID);
			ResultSet result = stmt.executeQuery();
			if (!result.isBeforeFirst()) {
				futureCtxs = new ArrayList<>();
				while (result.next()) {
					futureCtxs.add(loadContext(con, result.getInt(1)));
				}
			}
		}
		if (futureCtxs != null && !futureCtxs.isEmpty()) {
			for (PermissionContext ctx : Future.getFutureResults(futureCtxs)) {
				if (ctx == null)
					continue;
				group.addPermissionContext(ctx);
			}
		}
		group.getContext().changedContext();
		group.changedPermissionContexts();
		group.changedGroup();
		group.changedPermissions();
		return group;
	}

	private PermissionContext internalLoadContext(final Connection con, final int id) throws SQLException {
		try (PreparedStatement stmt = con.prepareStatement("SELECT ctx_key, ctx_value FROM perm_context WHERE context_id=?")) {
			stmt.setInt(1, id);
			ResultSet result = stmt.executeQuery();
			if (!result.next())
				return null;
			String key = result.getString(1);
			String value = result.getString(2);
			return internalLoadContext(con, id, key, value);
		}
	}
	
	private PermissionContext internalLoadContext(final Connection con, int id, String key, String value) throws SQLException {
		CorePermissionContext context = new CorePermissionContext(id, key, value);
		try (PreparedStatement stmt = con.prepareStatement("SELECT perm, power FROM perm_context_perm WHERE context_id=?")) {
			stmt.setInt(1, id);
			ResultSet ctxPermResult = stmt.executeQuery();
			while (ctxPermResult.next()) {
				context.setPermission(ctxPermResult.getString(1), ctxPermResult.getInt(2));
			}
			return context;
		}
	}
	
	private PermissionHandler internalCreateHandler(final Connection con, UUID uuid) throws SQLException {
		return new CorePermissionHandler(uuid, this);
	}
	
	private PermissionGroup internalCreateGroup(final Connection con, String name) throws SQLException {
		try (PreparedStatement stmt = con.prepareStatement("INSERT INTO perm_groups (name) VALUES (?)")) {
			stmt.setString(1, name);
			stmt.execute();
		}
		try (Statement stmt = con.createStatement()) {
			ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			if (!result.next()) {
				throw new SQLException("Error while inserting group " + name + "! (No id returned)");
			}
			final int groupID = result.getInt(1);
			return new CorePermissionGroup(groupID, name);
		}
	}
	
	private PermissionContext internalCreateContext(Connection con, String key, String value) throws SQLException {
		try (PreparedStatement stmt = con.prepareStatement("INSERT INTO perm_context (ctx_key, ctx_value) VALUES (?, ?)")) {
			stmt.setString(1, key);
			stmt.setString(2, value);
			stmt.execute();
		}
		try (Statement stmt = con.createStatement()) {
			ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			if (!result.next()) {
				throw new SQLException("Error while inserting context " + key + ":" + value + "! (No id returned)");
			}
			final int ctxID = result.getInt(1);
			return new CorePermissionContext(ctxID, key, value);
		}
	}
	
	private boolean internalDeleteGroup(Connection con, String name) throws SQLException {
		try (PreparedStatement stmt = con.prepareStatement("DELETE FROM perm_groups WHERE name = ?")) {
			stmt.setString(1, name);
			return stmt.executeUpdate() > 0;
		}
	}
	
	private boolean internalDeleteContext(Connection con, int id) throws SQLException {
		try (PreparedStatement stmt = con.prepareStatement("DELETE FROM perm_context WHERE context_id = ?")) {
			stmt.setInt(1, id);
			return stmt.executeUpdate() > 0;
		}
	}
	
	private boolean internalDeleteHandler(Connection con, UUID uuid) throws SQLException {
		final byte[] uuidBytes = AtlasUtil.uuidToBytes(uuid);
		try (PreparedStatement stmt = con.prepareStatement("DELETE FROM perm_user_context WHERE user_id = ?")) {
			stmt.setBytes(1, uuidBytes);
			stmt.execute();
		}
		try (PreparedStatement stmt = con.prepareStatement("DELETE FROM perm_user_group WHERE user_id = ?")) {
			stmt.setBytes(1, uuidBytes);
			stmt.execute();
		}
		return true;
	}
	
	private boolean internalSaveHandler(Connection con, PermissionHandler handler) throws SQLException {
		byte[] uuidBytes = null;
		ContextProvider context = handler.getContext();
		if (context.hasChangedContext()) {
			try (PreparedStatement stmt = con.prepareStatement("DELETE FROM perm_user_context WHERE user_id = ?")) {
				if (uuidBytes == null)
					uuidBytes = AtlasUtil.uuidToBytes(handler.getUUID());
				stmt.setBytes(1, uuidBytes);
				stmt.execute();
			}
			Map<String, String> ctx = context.getContext();
			if (!ctx.isEmpty()) {
				try (PreparedStatement stmt = con.prepareStatement("INSERT INTO perm_user_context (user_id, ctx_key, ctx_value) VALUES (?, ?, ?)")) {
					stmt.setBytes(1, uuidBytes);
					for (Entry<String, String> entry : ctx.entrySet()) {
						stmt.setString(2, entry.getKey());
						stmt.setString(3, entry.getValue());
						stmt.addBatch();
					}
					stmt.executeBatch();
				}
			}
			context.changedContext();
		}
		if (handler.hasGroupsChanged()) {
			try (PreparedStatement stmt = con.prepareStatement("DELETE FROM perm_user_group WHERE user_id = ?")) {
				if (uuidBytes == null)
					uuidBytes = AtlasUtil.uuidToBytes(handler.getUUID());
				stmt.setBytes(1, uuidBytes);
				stmt.execute();
			}
			Collection<PermissionGroup> groups = handler.getGroups();
			if (!groups.isEmpty()) {
				try (PreparedStatement stmt = con.prepareStatement("INSERT INTO perm_user_group (user_id, group) VALUES (?, ?)")) {
					stmt.setBytes(1, uuidBytes);
					for (PermissionGroup group : groups) {
						stmt.setString(2, group.getName());
						stmt.addBatch();
					}
					stmt.executeBatch();
				}
			}
			handler.groupsChanged();
		}
		return true;
	}
	
	private boolean internalSaveGroup(Connection con, PermissionGroup group) throws SQLException {
		int id = group.getID();
		if (group.hasGroupChanged()) {
			try (PreparedStatement stmt = con.prepareStatement("UPDATE perm_groups SET sort_weight = ?, prefix = ?, suffix = ?, chat_color = ?, name_color = ?, power = ?, is_default = ? WHERE group_id = ?")) {
				stmt.setInt(1, group.getSortWeight());
				Chat prefix = group.getPrefix();
				stmt.setString(2, prefix != null ? prefix.toText() : null);
				Chat suffix = group.getSuffix();
				stmt.setString(3, suffix != null ? suffix.toText() : null);
				Color chatColor = group.getChatColor();
				if (chatColor != null) {
					stmt.setInt(4, chatColor.asRGB());
				} else {
					stmt.setNull(4, Types.INTEGER);
				}
				Color nameColor = group.getNameColor();
				if (nameColor != null) {
					stmt.setInt(5, nameColor.asRGB());
				} else {
					stmt.setNull(5, Types.INTEGER);
				}
				stmt.setInt(6, group.getPower());
				stmt.setBoolean(7, group.isDefault());
				stmt.execute();
			}
			group.changedGroup();
		}
		if (group.hasChangedPermissions()) {
			try (PreparedStatement stmt = con.prepareStatement("DELETE FROM perm_group_perm WHERE group_id = ?")) {
				stmt.setInt(1, id);
				stmt.execute();
			}
			Collection<Permission> perms = group.getPermissions();
			if (!perms.isEmpty()) {
				try (PreparedStatement stmt = con.prepareStatement("INSERT INTO perm_group_perm (group_id, perm, power) VALUES (?, ?, ?)")) {
					stmt.setInt(1, id);
					for (Permission perm : perms) {
						stmt.setString(2, perm.permission());
						stmt.setInt(3, perm.value());
						stmt.addBatch();
					}
					stmt.executeBatch();
				}
			}
			group.changedPermissions();
		}
		if (group.hasChangedPermissionContext()) {
			try (PreparedStatement stmt = con.prepareStatement("DELETE FROM perm_group_perm_context WHERE group_id = ?")) {
				stmt.setInt(1, id);
				stmt.execute();
			}
			Collection<PermissionContext> ctxs = group.getPermissionContexts();
			if (!ctxs.isEmpty()) {
				try (PreparedStatement stmt = con.prepareStatement("INSERT INTO perm_group_perm_context (group_id, context_id) VALUES (?, ?)")) {
					stmt.setInt(1, id);
					for (PermissionContext ctx : ctxs) {
						stmt.setInt(2, ctx.getID());
						stmt.addBatch();
					}
					stmt.executeBatch();
				}
			}
			group.changedPermissionContexts();
		}
		ContextProvider context = group.getContext();
		if (context.hasChangedContext()) {
			try (PreparedStatement stmt = con.prepareStatement("DELETE FROM perm_group_context WHERE group_id = ?")) {
				stmt.setInt(1, id);
				stmt.execute();
			}
			Map<String, String> ctx = context.getContext();
			if (!ctx.isEmpty()) {
				try (PreparedStatement stmt = con.prepareStatement("INSERT INTO perm_group_context (group_id, ctx_key, ctx_value) VALUES (?, ?, ?)")) {
					stmt.setInt(1, id);
					for (Entry<String, String> entry : ctx.entrySet()) {
						stmt.setString(2, entry.getKey());
						stmt.setString(3, entry.getValue());
						stmt.addBatch();
					}
					stmt.executeBatch();
				}
			}
			context.changedContext();
		}
		updateGroup(group);
		return true;
	}
	
	private boolean internalSaveContext(Connection con, PermissionContext context) throws SQLException {
		if (!context.hasChangedPermissions())
			return true;
		int id = context.getID();
		try (PreparedStatement stmt = con.prepareStatement("DELETE FROM perm_context_perm WHERE context_id = ?")) {
			stmt.setInt(1, context.getID());
			stmt.execute();
		}
		Collection<Permission> perms = context.getPermissions();
		if (perms.isEmpty())
			return true;
		try (PreparedStatement stmt = con.prepareStatement("INSERT INTO perm_context_perm (context_id, perm, power) VALUES (?, ?, ?)")) {
			stmt.setInt(1, id);
			for (Permission perm : perms) {
				stmt.setString(2, perm.permission());
				stmt.setInt(2, perm.value());
				stmt.addBatch();
			}
			stmt.executeBatch();
		}
		return true;
	}

	@Override
	public Future<Collection<PermissionGroup>> loadDefaultGroups() {
		String[] groupNames = null;
		try (Connection con = AtlasMaster.getDatabase().getConnection(true)) {
			PreparedStatement stmt = con.prepareStatement("SELECT name FROM perm_groups WHERE is_default = 1");
			ResultSet result = stmt.executeQuery();
			if (result.last()) {
				groupNames = new String[result.getRow()];
				result.beforeFirst();
			} else {
				return CompleteFuture.getEmptyListFuture();
			}
			int i = 0;
			while (result.next()) {
				groupNames[i] = result.getString(1);
			}
		} catch (Exception e) {
			return new CompleteFuture<>(e);
		}
		List<Future<PermissionGroup>> futureGroups = new ArrayList<>(groupNames.length);
		for (String name : groupNames) {
			futureGroups.add(loadGroup(name));
		}
		final CompletableFuture<Collection<PermissionGroup>> future = new CompletableFuture<>();
		CumulativeFuture<PermissionGroup> groupsFuture = new CumulativeFuture<>(futureGroups);
		groupsFuture.setListener((f) -> {
			Collection<Future<PermissionGroup>> futures = f.getNow();
			ArrayList<PermissionGroup> groups = new ArrayList<>(futures.size());
			for (Future<PermissionGroup> groupFuture : futures) {
				PermissionGroup group = groupFuture.getNow();
				if (group == null)
					continue;
				groups.add(group);
			}
			future.finish(groups);
		});
		return future;
	}

}
