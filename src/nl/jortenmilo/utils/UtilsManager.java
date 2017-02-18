package nl.jortenmilo.utils;

import java.io.File;

import javax.sound.sampled.Mixer;

import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;
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
	
	//TODO: Create a Utils class the is implemented in every util. This will contain a getName and a getData method.
	
	private EventManager events;
	
	public UtilsManager(EventManager events) {
		this.events = events;
	}
	
	public IDUtils createIDUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("IDUtils");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new IDUtils();
	}
	
	public StringUtils createStringUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("StringUtils");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new StringUtils();
	}
	
	public SystemUtils createSystemUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("SystemUtils");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new SystemUtils();
	}
	
	public MathUtils createMathUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("MathUtils");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new MathUtils();
	}
	
	public CalculatorUtils createCalculatorUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("CalculatorUtils");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new CalculatorUtils();
	}
	
	public RandomUtils createRandomUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("RandomUtils");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new RandomUtils();
	}
	
	public NetBot createNetBot() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("NetBot");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new NetBot();
	}
	
	public SocketClient createSocketClient() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("SocketClient");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new SocketClient();
	}
	
	public SocketServer createSocketServer() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("SocketServer");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new SocketServer();
	}
	
	public WebClient createWebClient() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("WebClient");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new WebClient();
	}
	
	public WebServer createWebServer() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("WebServer");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new WebServer();
	}
	
	public MidiUtils createMidiUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("MidiUtils");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new MidiUtils();
	}
	
	public MixerUtils createMixerUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("MixerUtils");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new MixerUtils();
	}
	
	public RecorderUtils createRecorderUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("RecorderUtils");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new RecorderUtils();
	}
	
	public SoundUtils createSoundUtils(File f, Mixer m) {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("SoundUtils");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new SoundUtils(f, m);
	}
	
	public ObjectUtils createObjectUtils() {
		UtilsCreatedEvent event = new UtilsCreatedEvent();
		event.setUtilName("ObjectUtils");
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return new ObjectUtils();
	}
	
	public IDUtils cloneIDUtils(IDUtils u) {
		IDUtils nu = new IDUtils();
		nu.setUUDIs(u.getUUIDs());
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("IDUtils");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
	
	public StringUtils cloneStringUtils(StringUtils u) {
		StringUtils nu = new StringUtils();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("StringUtils");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
	
	public SystemUtils cloneSystemUtils(SystemUtils u) {
		SystemUtils nu = new SystemUtils();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("SystemUtils");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
	
	public MathUtils cloneMathUtils(MathUtils u) {
		MathUtils nu = new MathUtils();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("MathUtils");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
	
	public CalculatorUtils cloneCalculatorUtils(CalculatorUtils u) {
		CalculatorUtils nu = new CalculatorUtils();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("CalculatorUtils");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
	
	public RandomUtils cloneRandomUtils(RandomUtils u) {
		RandomUtils nu = new RandomUtils();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("RandomUtils");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
	
	public NetBot cloneNetBot(NetBot u) {
		NetBot nu = new NetBot();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("RandomUtils");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
	
	public SocketClient cloneSocketClient(SocketClient u) {
		SocketClient nu = new SocketClient();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("SocketClient");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
	
	public SocketServer cloneSocketServer(SocketServer u) {
		SocketServer nu = new SocketServer();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("SocketServer");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
	
	public WebClient cloneWebClient(WebClient u) {
		WebClient nu = new WebClient();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("WebClient");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
	
	public WebServer cloneWebServer(WebServer u) {
		WebServer nu = new WebServer();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("WebServer");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
	
	public MidiUtils cloneMidiUtils(MidiUtils u) {
		MidiUtils nu = new MidiUtils();
		nu.setInstruments(u.getInstruments());
		nu.setSynthesizer(u.getSynthesizer());
		nu.setChannels(u.getChannels());
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("WebServer");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
	
	public MixerUtils cloneMixerUtils(MixerUtils u) {
		MixerUtils nu = new MixerUtils();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("MixerUtils");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
	
	public RecorderUtils cloneRecorderUtils(RecorderUtils u) {
		RecorderUtils nu = new RecorderUtils();
		nu.setBigEndian(u.isBigEndian());
		nu.setSigned(u.isSigned());
		nu.setChannels(u.getChannels());
		nu.setFileType(u.getFileType());
		nu.setRecordTime(u.getRecordTime());
		nu.setSampleRate(u.getSampleRate());
		nu.setSampleSizeInBits(u.getSampleSizeInBits());
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("RecorderUtils");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
	
	public SoundUtils cloneSoundUtils(SoundUtils u) {
		SoundUtils nu = new SoundUtils(u.getFile(), u.getMixer());
		nu.setBalance(u.getBalance());
		nu.setMute(u.isMuted());
		nu.setPan(u.getPan());
		nu.setPosition(u.getSoundPosition());
		nu.setVolume(u.getVolume());
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("SoundUtils");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
	
	public ObjectUtils cloneObjectUtils(ObjectUtils u) {
		ObjectUtils nu = new ObjectUtils();
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName("MixerUtils");
		event.setData(u.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		return nu;
	}
}
