package nl.jortenmilo.utils.sound;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

public class MixerUtils {
	
	public static List<AudioMixer> getMixers() {
		List<AudioMixer> mixers = new ArrayList<AudioMixer>();
		Mixer.Info[] mixInfo = AudioSystem.getMixerInfo();
		for(Mixer.Info info : mixInfo) {
			AudioMixer mixer = new AudioMixer();
			mixer.setName(info.getName());
			mixer.setDescription(info.getDescription());
			mixer.setVendor(info.getVendor());
			mixer.setVersion(info.getVersion());
			mixer.setMixer(AudioSystem.getMixer(info));
			mixers.add(mixer);
		}
		return mixers;
	}
	
	public static AudioMixer getMixer(String name) {
		Mixer.Info[] mixInfo = AudioSystem.getMixerInfo();
		for(Mixer.Info info : mixInfo) {
			if(info.getName().equals(name)) {
				AudioMixer mixer = new AudioMixer();
				mixer.setName(info.getName());
				mixer.setDescription(info.getDescription());
				mixer.setVendor(info.getVendor());
				mixer.setVersion(info.getVersion());
				mixer.setMixer(AudioSystem.getMixer(info));
				return mixer;
			}
		}
		return null;
	}
	
	public static class AudioMixer {
		
		private Mixer  m;
		private String n;
		private String v;
		private String e;
		private String d;
		
		private AudioMixer() {}
		
		public Mixer getMixer() {
			return m;
		}

		public void setMixer(Mixer m) {
			this.m = m;
		}

		public String getName() {
			return n;
		}

		public void setName(String n) {
			this.n = n;
		}

		public String getVendor() {
			return v;
		}

		public void setVendor(String v) {
			this.v = v;
		}

		public String getVersion() {
			return e;
		}

		public void setVersion(String e) {
			this.e = e;
		}

		public String getDesciption() {
			return d;
		}

		public void setDescription(String d) {
			this.d = d;
		}
		
	}
	
}
