package window;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;
import window.exploringGameEngine.extras.Mouse;
import window.screen.Screen;

public class GameWindow {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				start();
			}
		});
	}
	
	private static void start() {
		// Pretty typical Swing stuff
		final JFrame window = new JFrame("Hungry Hayes");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Screen screen = new Screen(0, 0, 1000, 625, window);
		
		final WindowEngine windowEngine = new WindowEngine(screen);
		window.add(windowEngine);
		window.setSize(screen.width(), screen.height());
		window.addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent e) { }
			
			@Override
			public void componentMoved(ComponentEvent e) {
				Mouse.refreshComponentLocation(windowEngine);
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				Mouse.refreshComponentLocation(windowEngine);
			}
			
			@Override
			public void componentShown(ComponentEvent e) { }
		});
		window.setVisible(true);
		
		windowEngine.start();
		
		windowEngine.requestFocusInWindow();
		
		// Add audio
		AudioPlayer MGP = AudioPlayer.player;
		AudioStream BGM;
		AudioData MD;
		ContinuousAudioDataStream loop = null;
		try {
			BGM = new AudioStream(new FileInputStream("res/audio/01 Hall of the Mountain King.wav"));
			MD = BGM.getData();
			loop = new ContinuousAudioDataStream(MD);
		} catch (IOException error) {
		}
		
		MGP.start(loop);
	}
	
}