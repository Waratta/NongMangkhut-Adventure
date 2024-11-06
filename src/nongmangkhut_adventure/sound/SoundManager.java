package nongmangkhut_adventure.sound;


import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
    private Clip clip;

    public void playSound(String soundFile) {
        try {
            // ตรวจสอบว่า `clip` ที่เล่นอยู่แล้วหรือไม่ และหยุดก่อนที่จะเล่นเสียงใหม่
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }

            File file = new File(getClass().getResource(soundFile).getFile());
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}