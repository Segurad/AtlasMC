package de.atlascore.scoreboard.classic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.io.protocol.play.PacketOutScoreboardObjective;
import de.atlasmc.io.protocol.play.PacketOutUpdateScore;
import de.atlasmc.io.protocol.play.PacketOutScoreboardObjective.Mode;
import de.atlasmc.io.protocol.play.PacketOutUpdateScore.ScoreAction;
import de.atlasmc.scoreboard.DisplaySlot;
import de.atlasmc.scoreboard.RenderType;
import de.atlasmc.scoreboard.ScoreboardView;
import de.atlasmc.scoreboard.classic.Objective;
import de.atlasmc.scoreboard.classic.Score;
import de.atlasmc.scoreboard.classic.Scoreboard;

class CoreObjective implements Objective {
	
	private final CoreScoreboard board;
	private final String name;
	private RenderType render;
	private Chat displayName;
	private Map<String, Integer> scores;
	private DisplaySlot slot;
	private boolean unregistered;
	
	CoreObjective(CoreScoreboard board, String name, Chat displayName, RenderType render) {
		this.board = board;
		this.name = name;
		this.render = render;
		this.displayName = displayName;
	}

	@Override
	public Scoreboard getScoreboard() {
		return board;
	}

	@Override
	public Score getScore(String name) {
		if (name == null)
			throw new IllegalArgumentException("Entry can not be null!");
		if (name.length() > 40)
			throw new IllegalArgumentException("Entry length > 40!");
		return new CoreScore(this, name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public RenderType getRenderType() {
		return render;
	}

	@Override
	public void setRenderType(RenderType render) {
		this.render = render;
	}

	@Override
	public Chat getDisplayName() {
		return displayName;
	}

	@Override
	public void setDisplayName(Chat chat) {
		if (chat == null)
			chat = ChatUtil.EMPTY;
		this.displayName = chat;
	}

	@Override
	public void unregister() {
		if (unregistered)
			return;
		unregistered = true;
		board.unregisterObjective(this);
	}

	@Override
	public DisplaySlot getDisplaySlot() {
		return slot;
	}

	@Override
	public void setDisplaySlot(DisplaySlot slot) {
		this.slot = slot;
		board.setDisplay(this, slot);
	}

	@Override
	public boolean hasScores() {
		return !scores.isEmpty();
	}

	@Override
	public List<Score> getScores() {
		if (scores == null || scores.isEmpty())
			return List.of();
		List<Score> scores = new ArrayList<>(this.scores.size());
		for (String s : this.scores.keySet()) {
			scores.add(new CoreScore(this, s));
		}
		return scores; 
	}

	@Override
	public void update() {
		if (unregistered)
			throw new IllegalStateException("Objective not registered!");
		PacketOutScoreboardObjective packetObj = new PacketOutScoreboardObjective();
		String name = getName();
		packetObj.setName(name);
		packetObj.setMode(Mode.UPDATE_DISPLAY);
		packetObj.setDisplayName(getDisplayName().getText());
		packetObj.setRenderType(getRenderType());
		for (ScoreboardView view : board.getViewersUnsafe()) {
			PlayerConnection con = view.getViewer().getConnection();
			con.sendPacked(packetObj);
		}
	}

	void updateScore(String entry, int value) {
		if (unregistered)
			throw new IllegalStateException("Objective not registered!");
		if (scores == null)
			scores = new HashMap<>();
		scores.put(entry, value);
		PacketOutUpdateScore packetScore = new PacketOutUpdateScore();
		packetScore.setEntry(entry);
		packetScore.setAction(ScoreAction.UPDATE);
		packetScore.setObjective(getName());
		packetScore.setValue(value);
		for (ScoreboardView view : board.getViewersUnsafe()) {
			PlayerConnection con = view.getViewer().getConnection();
			con.sendPacked(packetScore);
		}
	}

	Integer getScoreValue(String entry) {
		return scores == null ? null : scores.get(entry);
	}

	@Override
	public boolean resetScore(String name) {
		if (unregistered)
			throw new IllegalStateException("Objective not registered!");
		if (scores == null || name == null)
			return false;
		Integer val = scores.remove(name);
		if (val == null)
			return false;
		PacketOutUpdateScore packetScore = new PacketOutUpdateScore();
		packetScore.setEntry(name);
		packetScore.setAction(ScoreAction.REMOVE);
		for (ScoreboardView view : board.getViewersUnsafe()) {
			PlayerConnection con = view.getViewer().getConnection();
			con.sendPacked(packetScore);
		}
		return true;
	}

	void resetDisplaySlot() {
		slot = null;
	}

	@Override
	public boolean hasScore(String entry) {
		return scores == null && scores.containsKey(entry);
	}

	@Override
	public boolean isRegistered() {
		return !unregistered;
	}

}
