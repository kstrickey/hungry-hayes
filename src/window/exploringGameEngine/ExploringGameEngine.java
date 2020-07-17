package window.exploringGameEngine;

import resources.classes.SquareSide;
import window.GameEngine;
import window.exploringGameEngine.gameEvents.ExploringGameEventQueue;
import window.exploringGameEngine.realm.Realm;
import window.exploringGameEngine.realm.RealmFactory;
import window.exploringGameEngine.simObjects.SimManager;
import window.exploringGameEngine.simObjects.background.Background;
import window.exploringGameEngine.simObjects.hayes.Hayes;
import window.gameEvents.AddMessageImageEvent;
import window.screen.Screen;

public class ExploringGameEngine extends GameEngine {
	
	public ExploringGameEngine(int realmID) {
		started = false;
		this.realmID = realmID;
		
		// Initialize gameEventQueue. This must be present from the beginning
		exploringGameEventQueue = new ExploringGameEventQueue(this);
		
		displayedControls = true;
		
		if (realmID == RealmFactory.ID_CENTRAL) {			// if not central, then construct in the start() method
			realm = RealmFactory.newRealm(realmID);
			
			background = realm.newBackground();
			simManager = realm.newSimManager();
			
			tempScreenX = tempScreenY = 0;
			
			displayedControls = false;			// only if Central do we want the controls to be displayed
		}
		
	}
	
	@Override
	public void start(Screen screen, Hayes hayes) {
		started = false;
		super.start(screen, hayes);
		
		if (realmID != RealmFactory.ID_CENTRAL) {
			realm = RealmFactory.newRealm(realmID);
			
			background = realm.newBackground();
			simManager = realm.newSimManager();
			
			tempScreenX = tempScreenY = 0;
		}
		
		screen.setX(tempScreenX);					// TODO initialX, initialY (in constructor?)
		screen.setY(tempScreenY);
		realm.applyRealmDimensions(screen);
		this.hayes = hayes;
		this.hayes.setScreen(screen);
		
		this.hayes.setMouse(getMouse());
		
		/* SET POSITION SEQUENCE:
		 * Get the initialX and initialY locations before they are reset.
		 * These MAY be needed if Hayes is returning to Central
		 * (since there are multiple locations he might return to, depending on which Realm he comes from).
		 */
		double x = this.hayes.getEdgeCoordinate(SquareSide.LEFT);
		double y = this.hayes.getEdgeCoordinate(SquareSide.BOTTOM);
		double d = this.hayes.getDirection();
		// Apply data such as size, location, etc. to Hayes
		realm.applyRealmData(this.hayes);
		// If Hayes is returning to Central, set him to the correct initial location (taken above)
		if (realmID == RealmFactory.ID_CENTRAL && hayes.isPastOneRealm()) {
			this.hayes.setX(x);
			this.hayes.setY(y);
			this.hayes.setDirection(d);
			
			if (this.hayes.isRetrievedBaby()) {
				simManager.setCribFilled(this.hayes.getPreviousRealmID(), true);
			}
		}
		
		simManager.setTargetHayes(this.hayes);
		
		started = true;
		this.hayes.setRetrievedBaby(false);
	}
	
	@Override
	public Hayes sayGoodbye() {
		tempScreenX = getScreen().x();
		tempScreenY = getScreen().y();
		
		/*
		 * Sets Hayes' new position that he will take up when he returns to Central
		 * (if he returns to Central - if he doesn't, this will be disregarded and reset anyway).
		 * This will be checked in the start method of the next ExploringGameEngine.
		 */
		hayes.setX(realm.getXUponReturnToCentral());
		hayes.setY(realm.getYUponReturnToCentral());
		hayes.setDirection(realm.getDirectionUponReturnToCentral());
		hayes.realmSaysGoodbye(realmID);
		
		return hayes;
	}
	
	@Override
	public void paint(PaintMachine paintMachine) {
		if (started) {
			// Paint all owned Paintables
			background.paint(paintMachine);
			simManager.paint(paintMachine);
			hayes.paint(paintMachine);
			
			getMouse().paint(paintMachine);
		}
	}
	
	@Override
	public ExploringGameEventQueue gameEventQueue() {
		return exploringGameEventQueue;
	}
	
	@Override
	public void setArrowKey(SquareSide direction, boolean pressed) {
		hayes.setArrowKey(direction, pressed);
	}
	
	@Override
	public void setPunchKey(boolean pressed) {
		super.setPunchKey(pressed);
		hayes.setPunchKey(pressed);
	}
	
	public Background getBackground() {
		return background;
	}
	
	public Hayes getHayes() {
		return hayes;
	}
	
	public SimManager getSimManager() {
		return simManager;
	}

	@Override
	public void tick(double timeElapsed) {
		// Tick all owned Tickables
		background.tick(timeElapsed, exploringGameEventQueue);
		hayes.tick(timeElapsed, exploringGameEventQueue);
		simManager.tick(timeElapsed, exploringGameEventQueue);
		
//		getMouse().tick(timeElapsed, exploringGameEventQueue);
		
		// If it hasn't displayed controls yet, do so
		if (!displayedControls) {
			// The first time starting up, add controls messages
			exploringGameEventQueue.addEvent(new AddMessageImageEvent(PaintMachine.getImageFromName("textbox/controls 1.png")));
			exploringGameEventQueue.addEvent(new AddMessageImageEvent(PaintMachine.getImageFromName("textbox/controls 2.png")));
			displayedControls = true;
		}
		
		// Execute all gameEvents
		exploringGameEventQueue.executeAllEvents();
	}
	
	/**
	 * Method to determine if the player has won by checking if all cribs are occupied.
	 * @return true if all cribs occupied, false otherwise
	 */
	public boolean allCribsOccupied() {
		return simManager.allCribsOccupied();
	}
	
	
	private boolean started;				// indicates whether or not paint() should actually paint yet
	private boolean displayedControls;		// indicates whether or not an event should be fired to explain controls
	
	private final int realmID;
	private Realm realm;
	
	private Hayes hayes;
	private Background background;
	private SimManager simManager;
	
	private ExploringGameEventQueue exploringGameEventQueue;
	
	private double tempScreenX;
	private double tempScreenY;
//	
//	private Point hayesStartingPoint;
//	private double hayesStartingDirection;
}
