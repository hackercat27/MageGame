package game;

import javax.sound.sampled.*;
import java.io.File;

import java.io.IOException;

public class AudioPlayer {

	final static String TITLE_PATH = "resources\\music\\title.wav";
	final static String INTRO_PATH = "resources\\music\\intro.wav";

	final static String CAVE_PATH = "resources\\music\\cave.wav";
	final static String EREBUS_PATH = "resources\\music\\erebus.wav";
	final static String FIELD_PATH = "resources\\music\\field.wav";
	final static String FOREST_PATH = "resources\\music\\forest.wav";
	final static String HOUSE_PATH = "resources\\music\\house.wav";
	final static String TOWER_PATH = "resources\\music\\tower.wav";
	final static String SHOP_PATH = "resources\\music\\shop.wav";
	final static String BATTLE_PATH = "resources\\music\\battle.wav";

	final static String BOULDER_BREAK_PATH = "resources\\sfx\\boulder_break.wav";
	final static String DAMAGE1_PATH = "resources\\sfx\\damage1.wav";
	final static String DAMAGE2_PATH = "resources\\sfx\\damage2.wav";
	final static String DAMAGE3_PATH = "resources\\sfx\\damage3.wav";
	final static String DEATH_PATH = "resources\\sfx\\death.wav";
	final static String FADE_PATH = "resources\\sfx\\fade.wav";
	final static String FIRE_ATTACK_PATH = "resources\\sfx\\fire_attack.wav";
	final static String ITEM_GET_PATH = "resources\\sfx\\item_get.wav";
	final static String LEVELUP_PATH = "resources\\sfx\\levelup.wav";
	final static String SPEAK_PATH = "resources\\sfx\\speak.wav";
	final static String SWITCH_PATH = "resources\\sfx\\switch.wav";
	final static String TEXT_PATH = "resources\\sfx\\text.wav";
	final static String USE_ETHER_PATH = "resources\\sfx\\use_ether.wav";
	final static String USE_POTION_PATH = "resources\\sfx\\use_potion.wav";

	final static String LOW_HEALTH_PATH = "resources\\sfx\\low_health.wav";

	float decibels;

	Clip clip;
	AudioInputStream audioStream;
	FloatControl volumeControl;
	boolean loopAudio;
	
	int loopStartPoint;
	
	public AudioPlayer(String path, boolean aLoop, float vol) {
		try {
			audioStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
		} catch (UnsupportedAudioFileException ex) {
			ex.printStackTrace();
			MagePath.nextLine();
		} catch (IOException ex) {
			ex.printStackTrace();
			MagePath.nextLine();
		}
		loopAudio = aLoop;
		
		try {
			clip = AudioSystem.getClip();
			clip.open(audioStream);
		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
			MagePath.nextLine();
		} catch (IOException ex) {
			ex.printStackTrace();
			MagePath.nextLine();
		}
		
		decibels = vol;
		if (this.decibels < -80.0f) {
			this.decibels = -80.0f;
		} else if (this.decibels > 20.0f) {
			this.decibels = 20.0f;
		}
		
		loopStartPoint = 0;
	}
	
	public AudioPlayer(String path, boolean aLoop, float vol, int startPoint) {
		try {
			audioStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
		} catch (UnsupportedAudioFileException ex) {
			ex.printStackTrace();
			MagePath.nextLine();
		} catch (IOException ex) {
			ex.printStackTrace();
			MagePath.nextLine();
		}
		loopAudio = aLoop;
		
		try {
			clip = AudioSystem.getClip();
			clip.open(audioStream);
		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
			MagePath.nextLine();
		} catch (IOException ex) {
			ex.printStackTrace();
			MagePath.nextLine();
		}
		
		decibels = vol;
		if (this.decibels < -80.0f) {
			this.decibels = -80.0f;
		} else if (this.decibels > 20.0f) {
			this.decibels = 20.0f;
		}
		
		loopStartPoint = startPoint;
	}
	
	public void play() {
		volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		volumeControl.setValue(this.decibels);
		clip.setLoopPoints(this.loopStartPoint, -1);
		if (loopAudio == true) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		clip.start();
	}
	
	public void stop() {
		clip.stop();
		clip.close();
	}
}