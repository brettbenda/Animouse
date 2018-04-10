import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public enum SoundEffect {
    TIM_WALKING("./res/AudioAssets/walk.wav"),
    JACK_WALKING("./res/AudioAssets/walk.wav"),
    JUMPING("./res/AudioAssets/jump.wav"),
    MUSIC("./res/AudioAssets/music.wav");

    private Clip clip;

    SoundEffect(String fileName) {
        try {
            File soundFile = new File(fileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playOnce() {
        if (clip.isRunning()) {
            clip.stop();
        }

        clip.setFramePosition(0);
        clip.start();
    }

    public void playLoop() {
        if (clip.isRunning()) {
            clip.stop();
        }

        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (clip.isRunning()) {
            clip.stop();
        }
    }

    static void init() {
        values();
    }

}