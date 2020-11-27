package de.atlasmc.scoreboard.atlas;

import java.util.HashMap;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.scoreboard.Scoreboard;
import de.atlasmc.scoreboard.Team;

final class DefaultTabDisplayHandler implements TabDisplayHandler {

	private final Scoreboard sb;
	
	DefaultTabDisplayHandler() {
		sb = Bukkit.getScoreboardManager().getMainScoreboard();
	}
	
	@Override
	public String getPlayerGroupNameID(PlayerScoreboard board) {
		final Team t = sb.getEntryTeam(board.getPlayer().getName());
		if (t == null) return board.getPlayer().getName();
		return t.getName();
	}

	@Override
	public String getGroupName(PlayerScoreboard board) {
		final Team t = sb.getEntryTeam(board.getPlayer().getName());
		if (t == null) {
			final String name = board.getPlayer().getName();
			return name.length() <= 12 ? name : name.substring(0, 12);
		}
		final String name = t.getName();
		return name.length() <= 12 ? name : name.substring(0, 12);
	}

	@Override
	public String getGroupID(PlayerScoreboard board) {
		final Team t = sb.getEntryTeam(board.getPlayer().getName());
		if (t == null) return "9999";
		final String name = t.getName();
		return name.length() <= 4 ? name : name.substring(0, 4);
	}

	@Override
	public ChatColor getColor(PlayerScoreboard board) {
		final Team t = sb.getEntryTeam(board.getPlayer().getName());
		if (t == null) return ChatColor.WHITE;
		return t.getColor();
	}

	@Override
	public String getPrefix(PlayerScoreboard board) {
		final Team t = sb.getEntryTeam(board.getPlayer().getName());
		if (t == null) return "";
		return t.getPrefix();
	}

	@Override
	public String getSuffix(PlayerScoreboard board) {
		final Team t = sb.getEntryTeam(board.getPlayer().getName());
		if (t == null) return "";
		return t.getSuffix();
	}

	@Override
	public HashMap<Option, OptionStatus> getOptions(PlayerScoreboard board) {
		final Team t = sb.getEntryTeam(board.getPlayer().getName());
		final HashMap<Option, OptionStatus> map = new HashMap<>();
		if (t == null) return map;
		for (Option o : Option.values()) {
			map.put(o, t.getOption(o));
		}
		return map;
	}

}
