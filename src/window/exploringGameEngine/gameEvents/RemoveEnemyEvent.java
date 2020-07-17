package window.exploringGameEngine.gameEvents;

import window.exploringGameEngine.ExploringGameEngine;
import window.exploringGameEngine.simObjects.enemies.Enemy;

public class RemoveEnemyEvent extends ExploringGameEvent {
	
	public RemoveEnemyEvent(Enemy enemyToBeRemoved) {
		this.unfortunateEnemy = enemyToBeRemoved;
	}
	
	@Override
	public void exploringExecute(ExploringGameEngine exploringGameEngine) {
		exploringGameEngine.getSimManager().removeEnemy(unfortunateEnemy);
	}
	
	private final Enemy unfortunateEnemy;
	
}
