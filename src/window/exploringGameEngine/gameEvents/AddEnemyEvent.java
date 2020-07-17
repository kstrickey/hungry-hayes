package window.exploringGameEngine.gameEvents;

import window.exploringGameEngine.ExploringGameEngine;
import window.exploringGameEngine.simObjects.enemies.Enemy;

public class AddEnemyEvent extends ExploringGameEvent {

    private Enemy enemy;
    
    /**
     * Construct an AddEnemyEvent with the specified to-be-added enemy.
     */
    public AddEnemyEvent (Enemy enemy) {
        this.enemy = enemy;
    }
    
    public void exploringExecute(ExploringGameEngine ege) {
        ege.getSimManager().addEnemy(enemy);        
    }

}
