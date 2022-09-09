package test.shapes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.shapes.Image;

public class ImageTest {
    private Image image;

    @Before
    public void init() {
        this.image = new Image("./logo.png", true);
    }

    @Test
    public void testInShapeOrigin() {
        assertTrue("Origin is in image", this.image.inShape(0.0, 100.0));
    }

    @Test
    public void testInShapeEdge() {
        assertTrue("Point on edge is in image", this.image.inShape(100.0, 100.0));
    }
    
    @Test
    public void testInShapeValid() {
        assertTrue("Point is in image", this.image.inShape(50.0, 25.0));
    }
    
    @Test
    public void testInShapeInvalid() {
        assertFalse("Point isn't in image", this.image.inShape(1000.0, -100.0));
    }
}
