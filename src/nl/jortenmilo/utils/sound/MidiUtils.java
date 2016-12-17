package nl.jortenmilo.utils.sound;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Patch;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class MidiUtils {
	
	private Synthesizer synth;
	private MidiChannel[] channels;
	
	//Init
	public MidiUtils() {
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			channels = synth.getChannels();
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
		}
	}
	
	
	//Load Instruments
	public void loadInstruments(Soundbank bank) {
		synth.loadAllInstruments(bank);
	}
	
	public void loadAllInstruments() {
		synth.loadAllInstruments(synth.getDefaultSoundbank());
	}
	
	
	//Set Instrument
	public void setInstrument(int channel, Instrument instrument) {
		Patch patch = instrument.getPatch();
        channels[channel].programChange(patch.getBank(), patch.getProgram());
	}
	
	
	//Get Instrument
	public Instrument getInstrument(String instrument) {
		for(Instrument in : synth.getLoadedInstruments()) {
			if(in.getName().replace(" ", "").equals(instrument.replace(" ", ""))) {
				return in;
			}
		}
		return null;
	}
	
	public Instrument[] getInstruments() {
		return synth.getLoadedInstruments();
	}
	
	public void setInstruments(Instrument[] instruments) {
		for(Instrument instrument : instruments) {
			synth.loadInstrument(instrument);
		}
	}
	
	public Synthesizer getSynthesizer() {
		return synth;
	}
	
	public void playSound(int channel, int time, int velocity, int tone) throws InterruptedException {
		channels[channel].noteOn(tone, velocity);
        Thread.sleep(time);
        channels[channel].noteOff(tone);
	}
	
	public void startSound(int channel, int velocity, int tone) throws InterruptedException {
		channels[channel].noteOn(tone, velocity);
	}
	
	public void stopSound(int channel, int tone) throws InterruptedException {
		channels[channel].noteOff(tone);
	}
	
	public void playThreadedSound(int channel, int time, int velocity, int tone) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
		        try {
		        	channels[channel].noteOn(tone, velocity);
					Thread.sleep(time);
					channels[channel].noteOff(tone);
				} catch(Error | Exception e) {
					new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
				}
			}
		});
		t.start();
	}
	
	public Soundbank getDefaultSoundbank() {
		return synth.getDefaultSoundbank();
	}
	
	public Info getInfo() {
		return synth.getDeviceInfo();
	}
	
	public long getLatency() {
		return synth.getLatency();
	}

	public String getData() {
		return "[Latency: " + synth.getLatency() + ", Info: " + synth.getDeviceInfo().getName() + "/" +  synth.getDeviceInfo().getDescription() + "/" +  synth.getDeviceInfo().getVersion()  + "/" +  synth.getDeviceInfo().getVendor() + "]";
	}
	
}
