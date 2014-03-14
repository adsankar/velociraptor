package engine;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javazoom.jl.player.Player;

public class MP3 {
	
	private boolean playing = false;
	private int mode = 0;
	private Player playMP3;
	private FileInputStream fis = null;
	private BufferedInputStream bis = null;
	private String filename = "";
	private File file = null;

	public MP3(File f) {
		file = f;
		mode = 1;
	}

	public MP3(String str) {
		filename = str;
		mode = 2;
	}
	
	public void play() {
		play(0);
	}
	
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

	public void verifyFile() {
		if (file != null)
			try {
				fis = new FileInputStream(file);
				playing = true;
			} catch (Exception e) {
			}
	}

	public void close() {
		if (playMP3 != null) {
			playing = false;
			playMP3.close();
		}
	}

	public boolean isPlaying() {
		return playing;
	}
}