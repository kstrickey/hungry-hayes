package window.gameEvents;

import java.util.ArrayList;

import window.GameEngine;

public abstract class GameEventQueue {
	
	public GameEventQueue(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		gameEvents = new ArrayList<GameEvent>();
	}
	
	/**
	 * Called after every tick cycle to execute all gameEvents in the queue.
	 */
	public void executeAllEvents() {
		ArrayList<GameEvent> eventsToBeExecuted;
		synchronized (this) {
			eventsToBeExecuted = gameEvents;
			gameEvents = new ArrayList<GameEvent>();
		}
		for (GameEvent e : eventsToBeExecuted) {
			e.execute(gameEngine);
		}
	}
	
	/**
	 * Adds a GameEvent to the queue of events to be executed after the current tick cycle by the GameEngine.
	 * @param e : GameEvent
	 */
	public void addEvent(GameEvent e) {
		synchronized (this) {
			gameEvents.add(e);
		}
	}
	
	public void clear() {
		synchronized (this) {
			gameEvents = new ArrayList<GameEvent>();
		}
	}
	
	private GameEngine gameEngine;
	
	private ArrayList<GameEvent> gameEvents;
	
}
