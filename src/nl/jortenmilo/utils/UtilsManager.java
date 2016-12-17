package nl.jortenmilo.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Mixer;

import nl.jortenmilo.utils.math.CalculatorUtils;
import nl.jortenmilo.utils.math.MathUtils;
import nl.jortenmilo.utils.math.RandomUtils;
import nl.jortenmilo.utils.net.NetBot;
import nl.jortenmilo.utils.net.SocketClient;
import nl.jortenmilo.utils.net.SocketServer;
import nl.jortenmilo.utils.net.WebClient;
import nl.jortenmilo.utils.net.WebServer;
import nl.jortenmilo.utils.sound.MidiUtils;
import nl.jortenmilo.utils.sound.MixerUtils;
import nl.jortenmilo.utils.sound.RecorderUtils;
import nl.jortenmilo.utils.sound.SoundUtils;

public class UtilsManager {
	
	private List<UtilsEventListener> listeners = new ArrayList<UtilsEventListener>();
	
	public IDUtils createIDUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("IDUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new IDUtils();
	}
	
	public StringUtils createStringUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("StringUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new StringUtils();
	}
	
	public SystemUtils createSystemUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("SystemUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new SystemUtils();
	}
	
	public MathUtils createMathUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("MathUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new MathUtils();
	}
	
	public CalculatorUtils createCalculatorUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("CalculatorUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new CalculatorUtils();
	}
	
	public RandomUtils createRandomUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("RandomUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new RandomUtils();
	}
	
	public NetBot createNetBot() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("NetBot");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new NetBot();
	}
	
	public SocketClient createSocketClient() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("SocketClient");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new SocketClient();
	}
	
	public SocketServer createSocketServer() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("SocketServer");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new SocketServer();
	}
	
	public WebClient createWebClient() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("WebClient");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new WebClient();
	}
	
	public WebServer createWebServer() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("WebServer");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new WebServer();
	}
	
	public MidiUtils createMidiUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("MidiUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new MidiUtils();
	}
	
	public MixerUtils createMixerUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("MixerUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new MixerUtils();
	}
	
	public RecorderUtils createRecorderUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("RecorderUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new RecorderUtils();
	}
	
	public SoundUtils createSoundUtils(File f, Mixer m) {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setName("SoundUtils");
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCreated(event);
		}
		
		return new SoundUtils(f, m);
	}
	
	public IDUtils cloneIDUtils(IDUtils u) {
		IDUtils nu = new IDUtils();
		nu.setUUDIs(u.getUUIDs());
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setName("IDUtils");
		event.setData(u.getData());
		
		for(UtilsEventListener listener : listeners) {
			listener.onUtilsCloned(event);
		}
		
		return nu;
	}
	
	public StringUtils cloneStringUtils(StringUtils u) {
		return new StringUtils();
	}
	
	public SystemUtils cloneSystemUtils(SystemUtils u) {
		return new SystemUtils();
	}
	
	public MathUtils cloneMathUtils(MathUtils u) {
		return new MathUtils();
	}
	
	public CalculatorUtils cloneCalculatorUtils(CalculatorUtils u) {
		return new CalculatorUtils();
	}
	
	public RandomUtils cloneRandomUtils(RandomUtils u) {
		return new RandomUtils();
	}
	
	public NetBot cloneNetBot(NetBot u) {
		return new NetBot();
	}
	
	public SocketClient cloneSocketClient(SocketClient u) {
		return new SocketClient();
	}
	
	public SocketServer cloneSocketServer(SocketServer u) {
		return new SocketServer();
	}
	
	public WebClient cloneWebClient(WebClient u) {
		return new WebClient();
	}
	
	public WebServer cloneWebServer(WebServer u) {
		return new WebServer();
	}
	
	public MidiUtils cloneMidiUtils(MidiUtils u) {
		return new MidiUtils();
	}
	
	public MixerUtils cloneMixerUtils(MixerUtils u) {
		return new MixerUtils();
	}
	
	public RecorderUtils cloneRecorderUtils(RecorderUtils u) {
		return new RecorderUtils();
	}
	
	public SoundUtils cloneSoundUtils(SoundUtils u) {
		return new SoundUtils(u.getFile(), u.getMixer());
	}
}
