package window.exploringGameEngine.gameEvents;

import java.awt.Point;

import window.exploringGameEngine.ExploringGameEngine;
import window.exploringGameEngine.simObjects.enemies.Enemy;

/**
 * When executed, this event knocks the Enemy specified in the direction away from Hayes.
 *
 */
public class KnockEnemyBackEvent extends ExploringGameEvent {
	
	public KnockEnemyBackEvent(Enemy enemyToBeKnockedBack) {
		this.enemyToBeKnockedBack = enemyToBeKnockedBack;
	}
	
	@Override
	public void exploringExecute(ExploringGameEngine exploringGameEngine) {
		Point hayesPt = exploringGameEngine.getHayes().center();
		Point enemyPt = enemyToBeKnockedBack.center();
		enemyToBeKnockedBack.getVelocity().setUsingRectangularDimensions(enemyPt.x - hayesPt.x, enemyPt.y - hayesPt.y);
		enemyToBeKnockedBack.getVelocity().magnitude = Enemy.KNOCK_BACK_SPEED * .005;
	}
	
	private final Enemy enemyToBeKnockedBack;
	
}
