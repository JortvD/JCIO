package nl.jortenmilo.utils.sound;

import java.io.File;
import java.io.OutputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

public class RecorderUtils {
	
	private long RECORD_TIME = 5000;
    
    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private TargetDataLine line;
    
	private float sampleRate = 16000;
	private int sampleSizeInBits = 8;
	private int channels = 2;
	private boolean signed = true;
	private boolean bigEndian = true;
    
    public AudioInputStream record(Mixer m) {
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(RECORD_TIME);
                } catch(Error | Exception e) {
        			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
        		}
                finish();
            }
        });
        stopper.start();
    	
        try {
            AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);;
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            
            
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            
            line = (TargetDataLine) m.getLine(info);
            line.open(format);
            line.start();
 
            AudioInputStream ais = new AudioInputStream(line);
            return ais;
        } catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
		}
        return null;
    }
    
    public void writeToFile(AudioInputStream ais, File file) {
    	try {
			AudioSystem.write(ais, fileType, file);
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
		}
    }
    
    public void writeToStream(AudioInputStream ais, OutputStream os) {
    	try {
			AudioSystem.write(ais, fileType, os);
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
		}
    }
    
    private void finish() {
        line.stop();
        line.close();
    }
    

    public long getRecordTime() {
		return RECORD_TIME;
	}

	public void setRecordTime(long RECORD_TIME) {
		this.RECORD_TIME = RECORD_TIME;
	}

	public float getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(float sampleRate) {
		this.sampleRate = sampleRate;
	}

	public int getSampleSizeInBits() {
		return sampleSizeInBits;
	}

	public void setSampleSizeInBits(int sampleSizeInBits) {
		this.sampleSizeInBits = sampleSizeInBits;
	}

	public int getChannels() {
		return channels;
	}

	public void setChannels(int channels) {
		this.channels = channels;
	}

	public boolean isSigned() {
		return signed;
	}

	public void setSigned(boolean signed) {
		this.signed = signed;
	}

	public boolean isBigEndian() {
		return bigEndian;
	}

	public void setBigEndian(boolean bigEndian) {
		this.bigEndian = bigEndian;
	}

	public AudioFileFormat.Type getFileType() {
		return fileType;
	}

	public void setFileType(AudioFileFormat.Type fileType) {
		this.fileType = fileType;
	}

	public String getData() {
		return "[Record_Time: " + RECORD_TIME + ", FileType: " + fileType + ", Signed: " + signed + ", BigEndian: " + bigEndian + ", SampleRate: " + sampleRate + ", SampleSize: " + sampleSizeInBits + ", Channels: " + channels + "]";
	}
	
}
