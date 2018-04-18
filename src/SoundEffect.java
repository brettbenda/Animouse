import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public enum SoundEffect {
    TIM_WALKING("./res/AudioAssets/walk.wav"),
    JACK_WALKING("./res/AudioAssets/walk.wav"),
    JUMPING("./res/AudioAssets/jump.wav"),
    CLICK("./res/AudioAssets/jump.wav"),
    RAIN("./res/AudioAssets/jump.wav"),
    GRAPPLE("./res/AudioAssets/snap.wav"),
    OUTSIDEAMBIENT("./res/AudioAssets/jump.wav"),
    BGMUSIC0("./res/AudioAssets/bathroom.wav"),
    BGMUSIC1("./res/AudioAssets/office1.wav"),
    BGMUSIC2("./res/AudioAssets/bedroom1.wav"),
    BGMUSIC3("./res/AudioAssets/kitchen.wav"),
    BGMUSIC4("./res/AudioAssets/office2.wav"),
    BGMUSIC5("./res/AudioAssets/bedroom2.wav"),
    BGMUSIC6("./res/AudioAssets/livingroom.wav"),
    BGMUSIC7("./res/AudioAssets/outside.wav");

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

        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-20.0f);

        clip.setFramePosition(0);
        clip.start();
    }

    public void playLoop() {
        if (clip.isRunning()) {
            clip.stop();
        }

        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-30.0f);

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