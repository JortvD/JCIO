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
	private FloatControl volume;
	private FloatControl balance;
	private FloatControl pan;
	private BooleanControl mute;
	private AudioInputStream audioStream;
	private boolean paused = false;
	private long CurrentTime = 0;
	
	public SoundUtils(File file, Mixer m) {
		this.file = file;
		
		this.m = m;
		
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		
		try {
			clip = (Clip)this.m.getLine(dataInfo);
		} 
		catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
		
		try {
			audioStream = AudioSystem.getAudioInputStream(file);
			
			clip.open(audioStream);
		} 
		catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
		
		mute = (BooleanControl)clip.getControl(BooleanControl.Type.MUTE);
		volume = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		balance = (FloatControl)clip.getControl(FloatControl.Type.BALANCE);
		pan = (FloatControl)clip.getControl(FloatControl.Type.PAN);
	}
	

	public void start() {
		clip.start();
	}
	
	public void pause() {
		if(!paused) {
			paused = true;
			CurrentTime = clip.getMicrosecondPosition();
			clip.stop();
		} 
		else {
			paused = false;
			clip.setMicrosecondPosition(CurrentTime);
			clip.start();
		}
	}
	
	public void stop() {
		clip.stop();
	}
	
	public void setVolume(float Volume) {
		volume.setValue(-80+Volume);
	}
	
	public float getVolume() {
		return volume.getValue()+80;
	}
	
	public void setBalance(float Balance) {
		balance.setValue(Balance);
	}
	
	public float getBalance() {
		return balance.getValue();
	}
	
	public float getPan() {
		return pan.getValue();
	}
	
	public void setPan(float Pan) {
		pan.setValue(Pan);
	}
	
	public void setPosition(long Position) {
		clip.setMicrosecondPosition(Position);
		clip.start();
	}
	
	public void setMute(boolean Mute) {
		mute.setValue(Mute);
	}
	
	public boolean isMuted() {
		return mute.getValue();
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
		} 
		catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
	}

	public String getData() {
		return "[File: " + file.getPath() + ", "
			  + "Mixer: " + m.getMixerInfo().getName() + "/" + m.getMixerInfo().getDescription() + "/" + m.getMixerInfo().getVersion()  + "/" + m.getMixerInfo().getVendor() + ", "
			  + "Volume: " + volume.getValue() + ", "
			  + "Muted: " + mute.getValue() + ", "
			  + "Balance: " + balance.getValue() + ", "
			  + "Pan: " + pan.getValue() + ", "
			  + "Paused: " + paused + ", "
			  + "Length: " + clip.getMicrosecondLength() + ", "
			  + "Position: " + clip.getMicrosecondPosition() + "]";
	}
	
}
