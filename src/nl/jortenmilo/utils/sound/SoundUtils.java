package nl.jortenmilo.utils.sound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Mixer;

public class SoundUtils {
	
	private File file;
	private Mixer m;
	private Clip clip;
	private FloatControl gainControlV;
	private FloatControl gainControlS;
	private FloatControl gainControlP;
	private BooleanControl gainControlM;
	private AudioInputStream audioStream;
	private boolean paused = false;
	private long CurrentTime = 0;
	
	public SoundUtils(File file, Mixer m) {
		this.file = file;
		
		this.m = m;
		
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		try {
			clip = (Clip)this.m.getLine(dataInfo);
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
		}
		
		try {
			audioStream = AudioSystem.getAudioInputStream(file);
			clip.open(audioStream);
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
		}
		
		gainControlM = (BooleanControl)clip.getControl(BooleanControl.Type.MUTE);
		gainControlV = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControlS = (FloatControl)clip.getControl(FloatControl.Type.BALANCE);
		gainControlP = (FloatControl)clip.getControl(FloatControl.Type.BALANCE);
	}
	

	public void start() {
		clip.start();
	}
	
	public void pause() {
		if(!paused) {
			paused = true;
			CurrentTime = clip.getMicrosecondPosition();
			clip.stop();
		} else {
			paused = false;
			clip.setMicrosecondPosition(CurrentTime);
			clip.start();
		}
	}
	
	public void stop() {
		clip.stop();
	}
	
	public void setVolume(float Volume) {
		gainControlV.setValue(-80+Volume);
	}
	
	public float getVolume() {
		return gainControlV.getValue()+80;
	}
	
	public void setBalance(float Balance) {
		gainControlS.setValue(Balance);
	}
	
	public float getPan() {
		return gainControlP.getValue();
	}
	
	public void setPan(float Pan) {
		gainControlP.setValue(Pan);
	}
	
	public float getBalance() {
		return gainControlS.getValue();
	}
	
	public void setPosition(long Position) {
		clip.setMicrosecondPosition(Position);
		clip.start();
	}
	
	public void setMute(boolean Mute) {
		gainControlM.setValue(Mute);
	}
	
	public boolean isMuted() {
		return gainControlM.getValue();
	}
	
	public long getSoundLenght() {
		return clip.getMicrosecondLength();
	}
	
	public long getSoundPosition() {
		return clip.getMicrosecondPosition();
	}

	public File getFile() {
		return file;
	}
	
	public Mixer getMixer() {
		return m;
	}
	
	public void sleepUntilDone() {
		try {
			Thread.sleep(getSoundLenght()-getSoundPosition());
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
		}
	}
	
}
