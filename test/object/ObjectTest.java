package object;
import org.junit.jupiter.api.Test;
 import static org.junit.jupiter.api.Assertions.*;

import java.awt.Rectangle;

class OBJGenericTest {
    private OBJ_Generic obj;
    private SuperObject superObject;


    @Test
    void testDefaultValues() {
        superObject = new SuperObject();

        assertNull(superObject.image);
        assertNull(superObject.name);
        assertFalse(superObject.collision);
        assertEquals(0, superObject.worldX);
        assertEquals(0, superObject.worldY);
        assertEquals(new Rectangle(0, 0, 48, 48), superObject.solidArea);
        assertEquals(0, superObject.solidAreaDefaultX);
        assertEquals(0, superObject.solidAreaDefaultY);
    }

    @Test
    void testConstructorWithDefaultSize() {
        obj = new OBJ_Generic("/objects/Brown_ruins1.png", "Brown RUINS", true);;
        assertEquals("Brown RUINS", obj.name);
        assertTrue(obj.collision);
        assertNotNull(obj.image);
    }

    @Test
    void testConstructorWithCustomSize() {
        obj = new OBJ_Generic("/objects/Brown_ruins1.png", "Brown RUINS", false, 100, 100);;
        assertEquals("Brown RUINS", obj.name);
        assertFalse(obj.collision);
        assertNotNull(obj.image);
    }

}
