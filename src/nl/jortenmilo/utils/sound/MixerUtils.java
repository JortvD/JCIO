package nl.jortenmilo.utils.sound;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

public class MixerUtils {
	
	public List<AudioMixer> getMixers() {
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
	
	public AudioMixer getMixer(String name) {
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
	
	public String getData() {
		return "";
	}
	
	public class AudioMixer {
		
		private Mixer mixer;
		private String name;
		private String vendor;
		private String version;
		private String desc;
		
		private AudioMixer() {}
		
		public Mixer getMixer() {
			return mixer;
		}

		public void setMixer(Mixer mixer) {
			this.mixer = mixer;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getVendor() {
			return vendor;
		}

		public void setVendor(String vendor) {
			this.vendor = vendor;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getDesciption() {
			return desc;
		}

		public void setDescription(String desc) {
			this.desc = desc;
		}
		
	}
	
}
