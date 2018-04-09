import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public enum SoundEffect {
    TIM_WALKING("tim_walking.wav"),
    JACK_WALKING("jack_walking.wav"),
    JUMPING("jumping.wav");

    private Clip clip;

    SoundEffect(String fileName) {
        try {
            URL url = this.getClass().getClassLoader().getResource(fileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
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

    public void play() {
        if (clip.isRunning()) {
            clip.stop();
        }

        clip.setFramePosition(0);
        clip.start();
    }

    static void init() {
        values();
    }

}