package window.exploringGameEngine.gameEvents;

import window.exploringGameEngine.ExploringGameEngine;
import window.exploringGameEngine.simObjects.enemies.Enemy;

public class EnemyHitsHayesEvent extends ExploringGameEvent {
	
	public EnemyHitsHayesEvent(Enemy enemy) {
		this.enemy = enemy;
	}
	
	@Override
	public void exploringExecute(ExploringGameEngine exploringGameEngine) {
		exploringGameEngine.getHayes().hitByEnemy(enemy);
	}
	
	private final Enemy enemy;
	
}
