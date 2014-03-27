package engine;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javazoom.jl.player.Player;
/**
 * Plays music as the user plays the game
 * @author 930983
 *
 */
public class MP3 {
	private boolean playing = false;
	private int mode = 0;
	private Player playMP3;
	private FileInputStream fis = null;
	private BufferedInputStream bis = null;
	private String filename = "";
	private File file = null;
	/**
	 * Creates new instance of MP3, using a file as input
	 * @param f
	 */
	public MP3(File f) {
		file = f;
		mode = 1;
	}
	/**
	 * Creates new instance of MP3, using a string as input
	 * @param str
	 */
	public MP3(String str) {
		filename = str;
		mode = 2;
	}
	/**
	 * Plays the music, looping continuously
	 */
	public void play() {
		play(0);
	}
	/**
	 * Plays the music for a given number of times
	 * @param loop
	 */
	public void play(final int loop) {
		if(mode == 1)
			verifyFile();
		else
			playing = true;
		new Thread() {
			public void run() {
				try {
					int loops = 0;
					while (playing){
						if(mode == 1){
							fis = new FileInputStream(file);
							playMP3 = new Player(fis);
						}
						if(mode == 2){
							InputStream is = getClass().getResourceAsStream(filename);
							bis = new BufferedInputStream(is);
							playMP3 = new Player(bis);
						}
						playMP3.play();
						playMP3.close();
						loops++;
						if(loop > 0 && loops == loop)
							playing = false;
					}
				} catch (Exception e) {
				}
			}
		}.start();
	}
	/**
	 * Checks to see if the file is valid and starts to play it if it is
	 */
	public void verifyFile() {
		if (file != null)
			try {
				fis = new FileInputStream(file);
				playing = true;
			} catch (Exception e) {
			}
	}
	/**
	 * Stops the music from playing
	 */
	public void close() {
		if (playMP3 != null) {
			playing = false;
			playMP3.close();
		}
	}
	/**
	 * Checks if the music is playing
	 * @return
	 */
	public boolean isPlaying() {
		return playing;
	}
}