package window;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import window.exploringGameEngine.ExploringGameEngine;
import window.exploringGameEngine.PaintMachine;
import window.exploringGameEngine.realm.Realm;
import window.exploringGameEngine.realm.RealmFactory;
import window.exploringGameEngine.simObjects.hayes.Hayes;
import window.gameEvents.AddMessageImageEvent;
import window.screen.Screen;

@SuppressWarnings("serial")
public class WindowEngine extends JPanel /*implements ComponentListener*/ {
	
	public WindowEngine(Screen screen) {
		// Initialize many stuffs
		keyboardInputHandler = new KeyboardInputHandler();
		addKeyListener(keyboardInputHandler);
		mouseInputHandler = new MouseInputHandler();
		addMouseListener(mouseInputHandler);
		addMouseMotionListener(mouseInputHandler);
//		addMouseWheelListener(mouseInputHandler);	//TODO for zoom?
		
		this.screen = screen;
		paintMachine = new PaintMachine(screen);
		
		central = RealmFactory.newCentralRealm();
		homeGameEngine = new ExploringGameEngine(RealmFactory.ID_CENTRAL);
	}
		
    @Override
    protected void paintComponent(Graphics g) {
    	// Method called on AWT thread
        g.setClip(0, 0, 1000, 625);
        super.paintComponent(g);
        
        synchronized (synchronizer) {
            paintMachine.setGraphics(g);            // TODO Might there be a way to do this just once? Is it necessary?
            if (currentGameEngine != null) {
                currentGameEngine.paint(paintMachine);
                BufferedImage message = currentGameEngine.peekCurrentMessageQueue();
                if (message != null) {
                	paintMachine.paintImageRelativeToScreen(message, 500 - message.getWidth() / 2, 
                			screen.height() - 60 - message.getHeight(), message.getWidth(), message.getHeight());
                }
            }
        }
    }
        
    public void start() {
    	// Method called to start the two threads
    	
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
        
        switchToEngine(homeGameEngine);
        
        homeGameEngine.gameEventQueue().addEvent(new AddMessageImageEvent(PaintMachine.getImageFromName("textbox/opening 2.png")));
        homeGameEngine.gameEventQueue().addEvent(new AddMessageImageEvent(PaintMachine.getImageFromName("textbox/opening 1.png")));
        
        executor.scheduleAtFixedRate(new Painter(), 0, 17, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(new Ticker(), 0, 5, TimeUnit.MILLISECONDS);
        
        requestFocusInWindow();
        
        // TODO opening screen engine?
    }
    
    private void switchToEngine(GameEngine nextGameEngine) {
        Hayes hayes = null;
        
        if (currentGameEngine != null) {            // it will be null the first time
            hayes = currentGameEngine.sayGoodbye();
        }
        
        if (hayes == null) {
            hayes = central.newHayes();
        }
        
        currentGameEngine = nextGameEngine;
        currentGameEngine.start(screen, hayes);
        keyboardInputHandler.setGameEventQueue(currentGameEngine.gameEventQueue());
        mouseInputHandler.setGameEventQueue(currentGameEngine.gameEventQueue());
    }
    
    private class Painter implements Runnable {
        @Override
        public void run() {
            repaint();
            
//          if (currentGameEngine.isSwitchFlag()) {
//              switchToEngine(currentGameEngine.getNextGameEngine());
//          }
        }
    }
    
    private class Ticker implements Runnable {
        @Override
        public void run() {
//        	try {
	            synchronized (synchronizer) {
	            	if (currentGameEngine.peekCurrentMessageQueue() == null) {
	            		currentGameEngine.tick(timePerTick);
	            	} else {		// still execute GameEvents - some may come from user input, etc.
	            		currentGameEngine.gameEventQueue().executeAllEvents();
	            	}
	            	
	                if (currentGameEngine.isSwitchFlag()) {
	                    if (currentGameEngine.getNextGameEngine() == null) {
	                        switchToEngine(homeGameEngine);
	                    } else {
	                        switchToEngine(currentGameEngine.getNextGameEngine());
	                    }
	                }
	                
	                // Check if the game is won
	                if (homeGameEngine.allCribsOccupied()) {
	                	System.out.println("Wow, you won. That's impossible.");	// print congratulatory message
	                	System.exit(0);
	                }
	            }
//        	} catch (Throwable t) { t.printStackTrace(); }			// TODO when finished
        }
    }
    
    private KeyboardInputHandler keyboardInputHandler;
    private MouseInputHandler mouseInputHandler;
    
    private final PaintMachine paintMachine;
    private final Screen screen;
    
    private final Realm central;          // the home Realm
    
    private GameEngine currentGameEngine;                   // contains the current game engine (varying)
    private final ExploringGameEngine homeGameEngine;       // contains the home game engine with the map of Central, even if the player is not in it currently.
    
    private final double timePerTick = .005;
    
    private final Object synchronizer = new Object();
}
