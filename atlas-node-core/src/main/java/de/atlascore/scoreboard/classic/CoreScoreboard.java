package de.atlascore.scoreboard.classic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.entity.Player;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutDisplayObjective;
import de.atlasmc.io.protocol.play.PacketOutUpdateObjectives;
import de.atlasmc.io.protocol.play.PacketOutUpdateObjectives.Mode;
import de.atlasmc.io.protocol.play.PacketOutUpdateScore.ScoreAction;
import de.atlasmc.io.protocol.play.PacketOutUpdateTeams;
import de.atlasmc.io.protocol.play.PacketOutUpdateScore;
import de.atlasmc.scoreboard.DisplaySlot;
import de.atlasmc.scoreboard.RenderType;
import de.atlasmc.scoreboard.ScoreboardView;
import de.atlasmc.scoreboard.classic.ClassicScoreboardView;
import de.atlasmc.scoreboard.classic.Objective;
import de.atlasmc.scoreboard.classic.Score;
import de.atlasmc.scoreboard.classic.Scoreboard;
import de.atlasmc.scoreboard.classic.Team;

public class CoreScoreboard implements Scoreboard {

	private final Collection<ScoreboardView> viewers;
	private final Map<String, Objective> objectives;
	private final Map<String, Team> teams;
	private final CoreObjective[] slots;
	
	public CoreScoreboard() {
		viewers = new ArrayList<>();
		objectives = new HashMap<>();
		teams = new HashMap<>();
		slots = new CoreObjective[3];
	}
	
	@Override
	public ClassicScoreboardView createView(Player player, boolean apply) {
		CoreClassicScoreboardView view = new CoreClassicScoreboardView(this, player);
		if (apply)
			player.setScoreboard(view);
		return view;
	}

	@Override
	public List<ScoreboardView> getViewers() {
		return List.copyOf(viewers);
	}

	@Override
	public Objective getObjective(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		return objectives.get(name);
	}

	@Override
	public Objective getObjective(DisplaySlot slot) {
		return slots[slot.getID()];
	}

	@Override
	public List<Objective> getObjectives() {
		return List.copyOf(objectives.values());
	}

	@Override
	public Objective registerNewObjective(String name) {
		return registerNewObjective(name, null);
	}
	
	@Override
	public Objective registerNewObjective(String name, Chat displayName) {
		return registerNewObjective(name, displayName, RenderType.INTEGER);
	}

	@Override
	public Objective registerNewObjective(String name, Chat displayName, RenderType renderType) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		if (name.length() > 16)
			throw new IllegalArgumentException("Name length > 16!");
		if (renderType == null)
			throw new IllegalAccessError("RenderType can not be null!");
		if (displayName == null)
			displayName = ChatUtil.EMPTY;
		Objective obj = objectives.get(name);
		if (obj != null)
			return obj;
		obj = new CoreObjective(this, name, displayName, renderType);
		addObjective(obj);
		return null;
	}

	@Override
	public Team registerNewTeam(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		if (name.length() > 16)
			throw new IllegalArgumentException("Name length > 16!");
		Team team = teams.get(name);
		if (team != null)
			return team;
		team = new CoreTeam(this, name);
		addTeam(team);
		return team;
	}

	@Override
	public List<Team> getTeams() {
		return List.copyOf(teams.values());
	}

	@Override
	public Team getTeam(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		return teams.get(name);
	}

	@Override
	public void resetScores(String entry) {
		for (Objective obj : objectives.values()) {
			obj.resetScore(entry);
		}
	}

	@Override
	public void resetScores() {
		for (Objective obj : objectives.values())
			for (Score score : obj.getScores())
				score.reset();
	}
	
	private void addTeam(Team team) {
		teams.put(team.getName(), team);
		PacketOutUpdateTeams packetTeams = new PacketOutUpdateTeams();
		packetTeams.setName(team.getName());
		packetTeams.setMode(PacketOutUpdateTeams.Mode.CREATE_TEAM);
		packetTeams.setDisplayName(team.getDisplayName().toText());
		packetTeams.setAllowFriedlyFire(team.getAllowFriedlyFire());
		packetTeams.setSeeInvisibleTeammeber(team.canSeeInvisibleTeammeber());
		packetTeams.setCollisionRule(team.getCollisionRule());
		packetTeams.setNameTagVisibility(team.getNameTagVisibility());
		packetTeams.setColor(team.getColor());
		packetTeams.setPrefix(team.getPrefix().toText());
		packetTeams.setSuffix(team.getSuffix().toText());
		packetTeams.setEntities(List.copyOf(team.getEntries()));
		for (ScoreboardView view : viewers) {
			PlayerConnection con = view.getViewer().getConnection();
			con.sendPacked(packetTeams);
		}
	}
	
	private void addObjective(Objective obj) {
		objectives.put(obj.getName(), obj);
		PacketOutUpdateObjectives packetObj = new PacketOutUpdateObjectives();
		String name = obj.getName();
		packetObj.setName(name);
		packetObj.setMode(Mode.CREATE);
		packetObj.setDisplayName(obj.getDisplayName().toText());
		packetObj.setRenderType(obj.getRenderType());
		for (ScoreboardView view : viewers) {
			PlayerConnection con = view.getViewer().getConnection();
			con.sendPacked(packetObj);
		}
	}

	void unregisterTeam(Team team) {
		teams.remove(team.getName());
		PacketOutUpdateTeams packetTeams = new PacketOutUpdateTeams();
		packetTeams.setName(team.getName());
		packetTeams.setMode(PacketOutUpdateTeams.Mode.REMOVE_TEAM);
		for (ScoreboardView view : viewers) {
			PlayerConnection con = view.getViewer().getConnection();
			con.sendPacked(packetTeams);
		}
	}
	
	void add(ScoreboardView view) {
		viewers.add(view);
		PlayerConnection con = view.getViewer().getConnection();
		if (!objectives.isEmpty()) {
			for (Objective obj : objectives.values()) {
				PacketOutUpdateObjectives packetObj = new PacketOutUpdateObjectives();
				String name = obj.getName();
				packetObj.setName(name);
				packetObj.setMode(Mode.CREATE);
				packetObj.setDisplayName(obj.getDisplayName().toText());
				packetObj.setRenderType(obj.getRenderType());
				con.sendPacked(packetObj);
				if (!obj.hasScores())
					continue;
				for (Score score : obj.getScores()) {
					PacketOutUpdateScore packetScore = new PacketOutUpdateScore();
					packetScore.setEntry(score.getName());
					packetScore.setAction(ScoreAction.UPDATE);
					packetScore.setObjective(name);
					packetScore.setValue(score.getScore());
					con.sendPacked(packetScore);
				}
			}
		}
		if (!teams.isEmpty()) {
			for (Team team : teams.values()) {
				PacketOutUpdateTeams packetTeams = new PacketOutUpdateTeams();
				packetTeams.setName(team.getName());
				packetTeams.setMode(PacketOutUpdateTeams.Mode.CREATE_TEAM);
				packetTeams.setDisplayName(team.getDisplayName().toText());
				packetTeams.setAllowFriedlyFire(team.getAllowFriedlyFire());
				packetTeams.setSeeInvisibleTeammeber(team.canSeeInvisibleTeammeber());
				packetTeams.setCollisionRule(team.getCollisionRule());
				packetTeams.setNameTagVisibility(team.getNameTagVisibility());
				packetTeams.setColor(team.getColor());
				packetTeams.setPrefix(team.getPrefix().toText());
				packetTeams.setSuffix(team.getSuffix().toText());
				packetTeams.setEntities(List.copyOf(team.getEntries()));
				con.sendPacked(packetTeams);
			}
		}
		for (int i = 0; i < 3; i++) {
			Objective display = slots[i];
			if (display == null) 
				continue;
			PacketOutDisplayObjective packetDisplay = new PacketOutDisplayObjective();
			packetDisplay.setPosition(display.getDisplaySlot());
			packetDisplay.setObjective(display.getName());
			con.sendPacked(packetDisplay);
		}
	}
	
	void remove(ScoreboardView view) {
		viewers.remove(view);
		PlayerConnection con = view.getViewer().getConnection();
		if (!objectives.isEmpty()) {
			for (Objective obj : objectives.values()) {
				PacketOutUpdateObjectives packetObj = new PacketOutUpdateObjectives();
				String name = obj.getName();
				packetObj.setName(name);
				packetObj.setMode(Mode.REMOVE);
				con.sendPacked(packetObj);
			}
		}
		if (!teams.isEmpty()) {
			for (Team team : teams.values()) {
				PacketOutUpdateTeams packetTeams = new PacketOutUpdateTeams();
				packetTeams.setName(team.getName());
				packetTeams.setMode(PacketOutUpdateTeams.Mode.REMOVE_TEAM);
				con.sendPacked(packetTeams);
			}
		}
	}
	
	Collection<ScoreboardView> getViewersUnsafe() {
		return viewers;
	}

	void unregisterObjective(CoreObjective obj) {
		DisplaySlot slot = obj.getDisplaySlot();
		if (slot != null)
			slots[slot.getID()] = null;
		PacketOutUpdateObjectives packetObj = new PacketOutUpdateObjectives();
		String name = obj.getName();
		packetObj.setName(name);
		packetObj.setMode(Mode.REMOVE);
		for (ScoreboardView view : viewers) {
			PlayerConnection con = view.getViewer().getConnection();
			con.sendPacked(packetObj);
		}
	}

	void setDisplay(CoreObjective object, DisplaySlot slot) {
		CoreObjective obj = slots[slot.getID()];
		if (obj != null)
			obj.resetDisplaySlot();
		for (ScoreboardView view : viewers) {
			PlayerConnection con = view.getViewer().getConnection();
			PacketOutDisplayObjective packetDisplay = new PacketOutDisplayObjective();
			packetDisplay.setPosition(slot);
			packetDisplay.setObjective(object.getName());
			con.sendPacked(packetDisplay);
		}
	}

}
