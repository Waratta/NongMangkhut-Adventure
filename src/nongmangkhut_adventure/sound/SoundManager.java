package nongmangkhut_adventure.sound;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

            // ใช้ getResourceAsStream เพื่อโหลดไฟล์เสียงจาก resources
            InputStream soundStream = getClass().getResourceAsStream("/nongmangkhut_adventure/sound/" + soundFile);
            if (soundStream == null) {
                System.err.println("Error: Sound file not found: " + soundFile);
                return;
            }

            // Read the input stream into a byte array and then into a ByteArrayInputStream
            byte[] audioBytes = soundStream.readAllBytes();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioBytes);

            // Create an AudioInputStream from the ByteArrayInputStream
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
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
