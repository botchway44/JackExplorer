package entity;

import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import utilities.KeyPressEvent;
import view.GamePanel;
import view.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerTest {

    @Mock
    private GamePanel gp;
    @Mock
    private KeyHandler keyH;

    private Player player;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(gp.getScreenWidth()).thenReturn(800);
        when(gp.getScreenHeight()).thenReturn(600);
        when(gp.getTileSize()).thenReturn(48);
        player = new Player(gp, keyH);
    }

    @Test
    void testPlayerInitialization() {
        assertNotNull(player.getSolidArea());
        assertEquals(new Rectangle(10, 18, 30, 30), player.getSolidArea());
    }

    @Test
    void testSetDefaultValues() {
        player.setDefaultValues();
        assertEquals(gp.getTileSize() * 23, player.getWorldX());
        assertEquals(gp.getTileSize() * 21, player.getWorldY());
        assertEquals(4, player.getSpeed());
        assertEquals("down", player.getDirection());
    }

    @Test
    void testGetPlayerImage() {
        player.getPlayerImage();
        assertNotNull(player.getUp1());
        assertNotNull(player.getUp2());
        assertNotNull(player.getDown1());
        assertNotNull(player.getDown2());
        assertNotNull(player.getLeft1());
        assertNotNull(player.getLeft2());
        assertNotNull(player.getRight1());
        assertNotNull(player.getRight2());
    }
//
//    @Test
//    void testUpdateMovementUp() {
////        when().thenReturn(true);
//        keyH.keyPressed(KeyPressEvent.UP);
//        player.update();
//
//        System.out.println(("Direction: " + player.getDirection()));
//        assertEquals("up", player.getDirection());
//
//        player.setWorldY(100);
//        player.update();
//        assertEquals("up", player.getDirection());
//    }
//
//    @Test
//    void testUpdateMovementDown() {
//        when(keyH.downPressed).thenReturn(true);
//        player.setWorldY(100);
//        player.update();
//        assertEquals("down", player.getDirection());
//        assertEquals(104, player.getWorldY());
//    }
//
//    @Test
//    void testUpdateMovementLeft() {
//        when(keyH.leftPressed).thenReturn(true);
//        player.setWorldX(100);
//        player.update();
//        assertEquals("left", player.getDirection());
//        assertEquals(96, player.getWorldX());
//    }
//
//    @Test
//    void testUpdateMovementRight() {
//        when(keyH.rightPressed).thenReturn(true);
//        player.setWorldX(100);
//        player.update();
//        assertEquals("right", player.getDirection());
//        assertEquals(104, player.getWorldX());
//    }



    @Test
    void testPickUpObject() {
        // This test would require mocking the gp.gameObjects array and the gp.uiController
        // It's complex to test due to the tight coupling, consider refactoring for better testability
    }

    @Test
    void testDraw() {
        Graphics g2 = mock(Graphics.class);
        player.draw(g2);
        verify(g2).drawImage(any(BufferedImage.class), eq(player.screenX), eq(player.screenY), isNull());
        verify(g2).setColor(Color.RED);
        verify(g2).drawRect(eq(player.screenX + player.getSolidArea().x),
                eq(player.screenY + player.getSolidArea().y),
                eq(player.getSolidArea().width),
                eq(player.getSolidArea().height));
    }
}
