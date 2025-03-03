//package entity;
//
//import model.Entity;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.awt.Rectangle;
//import java.awt.image.BufferedImage;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//
//public class EntityTest {
//
//
//        private Entity entity;
//
//        @Before
//        public void setUp() {
//            entity = new Entity();
//        }
//
//        @Test
//        public void testWorldXGetterSetter() {
//            entity.setWorldX(100);
//            assertEquals(100, entity.getWorldX());
//        }
//
//        @Test
//        public void testWorldYGetterSetter() {
//            entity.setWorldY(200);
//            assertEquals(200, entity.getWorldY());
//        }
//
//        @Test
//        public void testSpeedGetterSetter() {
//            entity.setSpeed(5);
//            assertEquals(5, entity.getSpeed());
//        }
//
//        @Test
//        public void testDirectionGetterSetter() {
//            entity.setDirection("up");
//            assertEquals("up", entity.getDirection());
//        }
//
//        @Test
//        public void testSpriteCounterGetterSetter() {
//            entity.setSpriteCounter(10);
//            assertEquals(10, entity.getSpriteCounter());
//        }
//
//        @Test
//        public void testSpriteNumGetterSetter() {
//            entity.setSpriteNum(2);
//            assertEquals(2, entity.getSpriteNum());
//        }
//
//        @Test
//        public void testSolidAreaDefaultXGetterSetter() {
//            entity.setSolidAreaDefaultX(50);
//            assertEquals(50, entity.getSolidAreaDefaultX());
//        }
//
//        @Test
//        public void testSolidAreaDefaultYGetterSetter() {
//            entity.setSolidAreaDefaultY(60);
//            assertEquals(60, entity.getSolidAreaDefaultY());
//        }
//
//        @Test
//        public void testSolidAreaGetterSetter() {
//            Rectangle rect = new Rectangle(0, 0, 48, 48);
//            entity.setSolidArea(rect);
//            assertEquals(rect, entity.getSolidArea());
//        }
//
//        @Test
//        public void testCollisionOnGetterSetter() {
//            entity.setCollisionOn(true);
//            assertTrue(entity.isCollisionOn());
//        }
//
//        @Test
//        public void testBufferedImageGetterSetter() {
//            BufferedImage img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
//            entity.setUp1(img);
//            assertEquals(img, entity.getUp1());
//        }
//    }
