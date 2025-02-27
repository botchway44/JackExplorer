package sound;


import utilities.Sound;
import utilities.SoundAssets;

import javax.sound.sampled.Clip;

public class SoundTest {

    private Sound sound;

    @Before
    public void setUp() {
        sound = new Sound();
    }

    @Test
    public void testSoundUrlMapInitialization() {
        assertNotNull(sound.soundUrlMap);
        assertEquals(5, sound.soundUrlMap.size());
        assertNotNull(sound.soundUrlMap.get(SoundAssets.BACKGROUND));
        assertNotNull(sound.soundUrlMap.get(SoundAssets.COIN));
        assertNotNull(sound.soundUrlMap.get(SoundAssets.POWERUP));
        assertNotNull(sound.soundUrlMap.get(SoundAssets.UNLOCK));
        assertNotNull(sound.soundUrlMap.get(SoundAssets.FANFARE));
    }

    @Test
    public void testSetFile() {
        sound.setFile(SoundAssets.BACKGROUND);
        assertNotNull(sound.clip);
        assertTrue(sound.clip instanceof Clip);
    }

    @Test
    public void testPlay() {
        sound.setFile(SoundAssets.COIN);
        sound.play();
        assertTrue(sound.clip.isRunning());
    }

    @Test
    public void testLoop() {
        sound.setFile(SoundAssets.BACKGROUND);
        sound.loop();
        assertTrue(sound.clip.isRunning());
//        assertEquals(Clip.LOOP_CONTINUOUSLY, sound.clip.getLoopPoints()[1]);
    }

    @Test
    public void testStop() {
        sound.setFile(SoundAssets.POWERUP);
        sound.play();
        sound.stop();
        assertFalse(sound.clip.isRunning());
    }

    @Test(expected = NullPointerException.class)
    public void testSetFileWithInvalidKey() {
        sound.setFile(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testPlayWithoutSettingFile() {
        sound.play();
    }
}
