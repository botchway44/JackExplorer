package utilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Sound {
    public Clip clip;
    ArrayList<URL> soundUrlList = new ArrayList<>();

    public HashMap<SoundAssets, URL> soundUrlMap = new HashMap<>();

    public Sound() {
        soundUrlMap.put(SoundAssets.BACKGROUND, getClass().getResource("/sound/BlueBoyAdventure.wav"));
        soundUrlMap.put(SoundAssets.COIN, getClass().getResource("/sound/coin.wav"));
        soundUrlMap.put(SoundAssets.POWERUP, getClass().getResource("/sound/powerup.wav"));
        soundUrlMap.put(SoundAssets.UNLOCK, getClass().getResource("/sound/unlock.wav"));
        soundUrlMap.put(SoundAssets.FANFARE, getClass().getResource("/sound/fanfare.wav"));

    }


    public void setFile(SoundAssets key) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrlMap.get(key));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
