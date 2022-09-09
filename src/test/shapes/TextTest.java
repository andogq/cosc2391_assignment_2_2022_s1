package test.shapes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.shapes.Text;

public class TextTest {
    private Text text;

    @Before
    public void init() {
        this.text = new Text();
        this.text.setContent("Hello this is text");
        this.text.setSize(24);
    }

    @Test
    public void testInShapeOrigin() {
        assertTrue("Origin is in rectangle", this.text.inShape(0.0, 0.0));
    }

    @Test
    public void testInShapeEdge() {
        assertTrue("Point on edge is in rectangle", this.text.inShape(10.0, 10.0));
    }
    
    @Test
    public void testInShapeValid() {
        assertTrue("Point is in rectangle", this.text.inShape(50.0, 25.0));
    }
    
    @Test
    public void testInShapeInvalid() {
        assertFalse("Point isn't in rectangle", this.text.inShape(1000.0, -100.0));
    }
}
