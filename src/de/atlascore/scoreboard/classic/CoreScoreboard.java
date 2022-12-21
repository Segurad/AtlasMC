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
import de.atlasmc.io.protocol.play.PacketOutDisplayScoreboard;
import de.atlasmc.io.protocol.play.PacketOutScoreboardObjective;
import de.atlasmc.io.protocol.play.PacketOutScoreboardObjective.Mode;
import de.atlasmc.io.protocol.play.PacketOutUpdateScore.ScoreAction;
import de.atlasmc.io.protocol.play.PacketOutTeams;
import de.atlasmc.io.protocol.play.PacketOutUpdateScore;
import de.atlasmc.scoreboard.DisplaySlot;
import de.atlasmc.scoreboard.RenderType;
import de.atlasmc.scoreboard.ScoreboardView;
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
	public ScoreboardView createView(Player player, boolean apply) {
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
		PacketOutTeams packetTeams = new PacketOutTeams();
		packetTeams.setName(team.getName());
		packetTeams.setMode(PacketOutTeams.Mode.CREATE_TEAM);
		packetTeams.setDisplayName(team.getDisplayName().getText());
		packetTeams.setAllowFriedlyFire(team.getAllowFriedlyFire());
		packetTeams.setSeeInvisibleTeammeber(team.canSeeInvisibleTeammeber());
		packetTeams.setCollisionRule(team.getCollisionRule());
		packetTeams.setNameTagVisibility(team.getNameTagVisibility());
		packetTeams.setColor(team.getColor());
		packetTeams.setPrefix(team.getPrefix().getText());
		packetTeams.setSuffix(team.getSuffix().getText());
		packetTeams.setEntities(List.copyOf(team.getEntries()));
		for (ScoreboardView view : viewers) {
			PlayerConnection con = view.getViewer().getConnection();
			con.sendPacked(packetTeams);
		}
	}
	
	private void addObjective(Objective obj) {
		objectives.put(obj.getName(), obj);
		PacketOutScoreboardObjective packetObj = new PacketOutScoreboardObjective();
		String name = obj.getName();
		packetObj.setName(name);
		packetObj.setMode(Mode.CREATE);
		packetObj.setDisplayName(obj.getDisplayName().getText());
		packetObj.setRenderType(obj.getRenderType());
		for (ScoreboardView view : viewers) {
			PlayerConnection con = view.getViewer().getConnection();
			con.sendPacked(packetObj);
		}
	}

	void unregisterTeam(Team team) {
		teams.remove(team.getName());
		PacketOutTeams packetTeams = new PacketOutTeams();
		packetTeams.setName(team.getName());
		packetTeams.setMode(PacketOutTeams.Mode.REMOVE_TEAM);
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
				PacketOutScoreboardObjective packetObj = new PacketOutScoreboardObjective();
				String name = obj.getName();
				packetObj.setName(name);
				packetObj.setMode(Mode.CREATE);
				packetObj.setDisplayName(obj.getDisplayName().getText());
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
				PacketOutTeams packetTeams = new PacketOutTeams();
				packetTeams.setName(team.getName());
				packetTeams.setMode(PacketOutTeams.Mode.CREATE_TEAM);
				packetTeams.setDisplayName(team.getDisplayName().getText());
				packetTeams.setAllowFriedlyFire(team.getAllowFriedlyFire());
				packetTeams.setSeeInvisibleTeammeber(team.canSeeInvisibleTeammeber());
				packetTeams.setCollisionRule(team.getCollisionRule());
				packetTeams.setNameTagVisibility(team.getNameTagVisibility());
				packetTeams.setColor(team.getColor());
				packetTeams.setPrefix(team.getPrefix().getText());
				packetTeams.setSuffix(team.getSuffix().getText());
				packetTeams.setEntities(List.copyOf(team.getEntries()));
				con.sendPacked(packetTeams);
			}
		}
		for (int i = 0; i < 3; i++) {
			Objective display = slots[i];
			if (display == null) 
				continue;
			PacketOutDisplayScoreboard packetDisplay = new PacketOutDisplayScoreboard();
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
				PacketOutScoreboardObjective packetObj = new PacketOutScoreboardObjective();
				String name = obj.getName();
				packetObj.setName(name);
				packetObj.setMode(Mode.REMOVE);
				con.sendPacked(packetObj);
			}
		}
		if (!teams.isEmpty()) {
			for (Team team : teams.values()) {
				PacketOutTeams packetTeams = new PacketOutTeams();
				packetTeams.setName(team.getName());
				packetTeams.setMode(PacketOutTeams.Mode.REMOVE_TEAM);
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
		PacketOutScoreboardObjective packetObj = new PacketOutScoreboardObjective();
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
			PacketOutDisplayScoreboard packetDisplay = new PacketOutDisplayScoreboard();
			packetDisplay.setPosition(slot);
			packetDisplay.setObjective(object.getName());
			con.sendPacked(packetDisplay);
		}
	}

}
