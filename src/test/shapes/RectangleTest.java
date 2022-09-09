package test.shapes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.paint.Color;
import model.shapes.Rectangle;

public class RectangleTest {
    private Rectangle rectangle;

    @Before
    public void init() {
        this.rectangle = new Rectangle();
        this.rectangle.move(0,0);
        this.rectangle.setHeight(100);
        this.rectangle.setWidth(100);
        this.rectangle.setBorder(Color.BLACK);
        this.rectangle.setBorderWidth(1);
        this.rectangle.setBackground(Color.WHITE);
    }

    @Test
    public void testInShapeOrigin() {
        assertTrue("Origin is in rectangle", this.rectangle.inShape(0.0, 100.0));
    }

    @Test
    public void testInShapeEdge() {
        assertTrue("Point on edge is in rectangle", this.rectangle.inShape(100.0, 100.0));
    }
    
    @Test
    public void testInShapeValid() {
        assertTrue("Point is in rectangle", this.rectangle.inShape(50.0, 25.0));
    }
    
    @Test
    public void testInShapeInvalid() {
        assertFalse("Point isn't in rectangle", this.rectangle.inShape(1000.0, -100.0));
    }
}
