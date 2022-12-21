package de.atlascore.scoreboard.classic;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutTeams;
import de.atlasmc.io.protocol.play.PacketOutTeams.Mode;
import de.atlasmc.scoreboard.ScoreboardView;
import de.atlasmc.scoreboard.TeamOptionType;
import de.atlasmc.scoreboard.classic.Scoreboard;
import de.atlasmc.scoreboard.classic.Team;

class CoreTeam implements Team {
	
	private final CoreScoreboard board;
	private final String name;
	private ChatColor color;
	private Chat prefix, suffix, display;
	private boolean friendlyFire, seeInvisible;
	private TeamOptionType nameVisibility, collision;
	private List<String> entries;
	private boolean unregistered;

	CoreTeam(CoreScoreboard board, String name) {
		this.name = name;
		this.board = board;
		this.color = ChatColor.WHITE;
		this.prefix = ChatUtil.EMPTY;
		this.suffix = ChatUtil.EMPTY;
		this.display = ChatUtil.EMPTY;
		this.friendlyFire = true;
		nameVisibility = TeamOptionType.ALWAYS;
		collision = TeamOptionType.ALWAYS;
	}

	@Override
	public ChatColor getColor() {
		return color;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setColor(ChatColor color) {
		if (color == null)
			throw new IllegalArgumentException("Color can not be null!");
		this.color = color;
	}

	@Override
	public void unregister() {
		if (unregistered)
			return;
		unregistered = true;
		board.unregisterTeam(this);
	}

	@Override
	public Scoreboard getScoreboard() {
		return board;
	}

	@Override
	public Chat getPrefix() {
		return prefix;
	}

	@Override
	public Chat getSuffix() {
		return suffix;
	}

	@Override
	public void setPrefix(Chat prefix) {
		if (prefix == null)
			prefix = ChatUtil.EMPTY;
		this.prefix = prefix;
	}

	@Override
	public void setSuffix(Chat suffix) {
		if (suffix == null)
			suffix = ChatUtil.EMPTY;
		this.suffix = suffix;
	}

	@Override
	public Chat getDisplayName() {
		return display;
	}

	@Override
	public boolean getAllowFriedlyFire() {
		return friendlyFire;
	}

	@Override
	public boolean canSeeInvisibleTeammeber() {
		return seeInvisible;
	}

	@Override
	public void setDisplayName(Chat display) {
		if (display == null)
			display = ChatUtil.EMPTY;
		this.display = display;
	}

	@Override
	public void setAllowFriedlyFire(boolean allow) {
		this.friendlyFire = allow;
	}

	@Override
	public void setSeeInvisibleTeammeber(boolean see) {
		this.seeInvisible = see;
	}

	@Override
	public TeamOptionType getNameTagVisibility() {
		return nameVisibility;
	}

	@Override
	public TeamOptionType getCollisionRule() {
		return collision;
	}

	@Override
	public void setNameTagVisibility(TeamOptionType option) {
		if (option == null)
			throw new IllegalArgumentException("Option can not be null!");
		nameVisibility = option;
	}

	@Override
	public void setCollisionRule(TeamOptionType option) {
		if (option == null)
			throw new IllegalArgumentException("Option can not be null!");
		collision = option;
	}

	@Override
	public void update() {
		if (unregistered)
			throw new IllegalStateException("Team not registered!");
		updateTeamInfo(this);
	}

	@Override
	public List<String> getEntries() {
		return entries == null ? List.of() : List.copyOf(entries);
	}

	@Override
	public boolean addEntry(String entry) {
		if (unregistered)
			throw new IllegalStateException("Team not registered!");
		if (entry == null)
			throw new IllegalArgumentException("Entry can not be null!");
		if (entry.length() > 40)
			throw new IllegalArgumentException("Entry length > 40: " + entry.length());
		if (entries == null)
			entries = new ArrayList<>();
		else if (entries.contains(entry))
			return false;
		entries.add(entry);
		editEntriesOfTeam(this, List.of(entry), Mode.ADD_ENTITIES);
		return true;
	}

	@Override
	public boolean removeEntry(String entry) {
		if (unregistered)
			throw new IllegalStateException("Team not registered!");
		if (entry == null || entry.length() > 40 || entries == null)
			return false;
		if(!entries.remove(entry))
			return false;
		editEntriesOfTeam(this, List.of(entry), Mode.REMOVE_ENTITIES);
		return true;
	}

	@Override
	public boolean addEntry(List<String> entries) {
		if (unregistered)
			throw new IllegalStateException("Team not registered!");
		if (entries == null || entries.isEmpty())
			return false;
		List<String> changes = null;
		for (String entry : entries) {
			if (entry.length() > 40 || !this.entries.contains(entry))
				continue;
			if (changes == null)
				changes = new ArrayList<>();
			if (this.entries == null)
				this.entries = new ArrayList<>();
			this.entries.add(entry);
			changes.add(entry);
		}
		if (changes == null)
			return false;
		editEntriesOfTeam(this, entries, Mode.ADD_ENTITIES);
		return true;
	}

	@Override
	public boolean removeEntry(List<String> entries) {
		if (unregistered)
			throw new IllegalStateException("Team not registered!");
		if (this.entries == null || entries == null || entries.isEmpty())
			return false;
		List<String> changes = null;
		for (String entry : entries) {
			if (entry.length() > 40 || !this.entries.remove(entry))
				continue;
			if (changes == null)
				changes = new ArrayList<>();
			changes.add(entry);
		}
		if (changes == null)
			return false;
		editEntriesOfTeam(this, entries, Mode.REMOVE_ENTITIES);
		return true;
	}
	
	private void updateTeamInfo(Team team) {
		PacketOutTeams packetTeams = new PacketOutTeams();
		packetTeams.setName(team.getName());
		packetTeams.setMode(PacketOutTeams.Mode.UPDATE_TEAM_INFO);
		packetTeams.setDisplayName(team.getDisplayName().getText());
		packetTeams.setAllowFriedlyFire(team.getAllowFriedlyFire());
		packetTeams.setSeeInvisibleTeammeber(team.canSeeInvisibleTeammeber());
		packetTeams.setCollisionRule(team.getCollisionRule());
		packetTeams.setNameTagVisibility(team.getNameTagVisibility());
		packetTeams.setColor(team.getColor());
		packetTeams.setPrefix(team.getPrefix().getText());
		packetTeams.setSuffix(team.getSuffix().getText());
		for (ScoreboardView view : board.getViewersUnsafe()) {
			PlayerConnection con = view.getViewer().getConnection();
			con.sendPacked(packetTeams);
		}
	}

	private void editEntriesOfTeam(Team team, List<String> entries, PacketOutTeams.Mode mode) {
		PacketOutTeams packetTeams = new PacketOutTeams();
		packetTeams.setName(team.getName());
		packetTeams.setMode(mode);
		packetTeams.setEntities(entries);
		for (ScoreboardView view : board.getViewersUnsafe()) {
			PlayerConnection con = view.getViewer().getConnection();
			con.sendPacked(packetTeams);
		}
	}

	@Override
	public boolean isRegistered() {
		return !unregistered;
	}

}
