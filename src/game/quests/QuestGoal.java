package game.quests;

import game.gameplayStates.GamePlayState;
import game.player.Player;

public abstract class QuestGoal {
	
	public abstract boolean isAccomplished(GamePlayState state, Player player);
	
	public abstract void onAccomplished(GamePlayState state, Player player);
		
	public abstract String[] onStartText();
	
	public abstract String[] onEndText();

}
