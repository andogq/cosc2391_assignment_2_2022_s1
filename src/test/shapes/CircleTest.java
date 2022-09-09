package test.shapes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.paint.Color;
import model.shapes.Circle;

public class CircleTest {
    private Circle circle;

    @Before
    public void init() {
        this.circle = new Circle();
        this.circle.move(0, 0);
        this.circle.setRadius(10);
        this.circle.setBackground(Color.BLACK);
        this.circle.setBorderWidth(1);
        this.circle.setBorder(Color.WHITE);
        
    }

    @Test
    public void testInShapeCenter() {
        assertTrue("Point in center is in circle", this.circle.inShape(0.0, 0.0));
    }

    @Test
    public void testInShapeEdge() {
        assertTrue("Point on edge is in circle", this.circle.inShape(0.0, 10.0));
    }
    
    @Test
    public void testInShapeValid() {
        assertTrue("Point is in circle", this.circle.inShape(2.0, 2.0));
    }
    
    @Test
    public void testInShapeInvalid() {
        assertFalse("Point isn't in circle", this.circle.inShape(100.0, 100.0));
    }
}
