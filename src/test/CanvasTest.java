package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import org.junit.Before;
import org.junit.Test;

import model.Canvas;
import model.shapes.Circle;
import model.shapes.Renderable;

public class CanvasTest {
    private Canvas canvas;

    @Before
    public void init() {
        this.canvas = new Canvas(100, 100);

        this.canvas.setPane(new Pane());

        Circle a = new Circle();
        a.move(10, 10);
        a.setRadius(5);
        a.setBackground(Color.BLACK);
        a.setBorderWidth(3);
        a.setBorder(Color.WHITE);
        this.canvas.addShape(a);

        Circle b = new Circle();
        b.move(92, 70);
        b.setRadius(8);
        b.setBackground(Color.BLACK);
        b.setBorderWidth(3);
        b.setBorder(Color.WHITE);
        this.canvas.addShape(b);

        Circle c = new Circle();
        c.move(28, 82);
        c.setRadius(13);
        c.setBackground(Color.BLACK);
        c.setBorderWidth(3);
        c.setBorder(Color.WHITE);
        this.canvas.addShape(c);
    }

    @Test
    public void testGetShape() {
        Circle shape = new Circle();
        shape.move(50, 50);
        shape.setRadius(10);
        shape.setBackground(Color.BLACK);
        shape.setBorderWidth(3);
        shape.setBorder(Color.WHITE);
        this.canvas.addShape(shape);

        Renderable selectedShape = this.canvas.selectShape(50, 50);

        assertEquals("Shape should be returned", selectedShape, shape);
    }

    @Test
    public void testGetShapeNoShape() {
        Renderable selectedShape = this.canvas.selectShape(0, 0);

        assertEquals("Shape should be returned", selectedShape, null);
    }


    @Test
    public void testAddShape() {
        Circle shape = new Circle();
        shape.move(50, 50);
        shape.setRadius(10);
        shape.setBackground(Color.BLACK);
        shape.setBorderWidth(3);
        shape.setBorder(Color.WHITE);
        this.canvas.addShape(shape);
        
        assertEquals("Canvas should have 4 objects", this.canvas.getShapes().size(), 4);
    }

    @Test
    public void testClear() {
        this.canvas.clear();
        assertEquals("Canvas should have 0 objects", this.canvas.getShapes().size(), 0);
    }

    @Test
    public void testDeleteShape() {
        Circle shape = new Circle();
        shape.move(50, 50);
        shape.setRadius(10);
        shape.setBackground(Color.BLACK);
        shape.setBorderWidth(3);
        shape.setBorder(Color.WHITE);
        this.canvas.addShape(shape);

        this.canvas.selectShape(50, 50);
        this.canvas.deleteSelected();

        assertEquals("Canvas should have 3 objects", this.canvas.getShapes().size(), 3);
    }

    @Test
    public void testResize() {
        this.canvas.setHeight(200);
        this.canvas.setWidth(250);
    
        assertArrayEquals("Canvas should be 200 x 250", new double[] {this.canvas.getHeight(), this.canvas.getWidth()}, new double[] { 200, 250 }, 0);
    }

    @Test
    public void testZoomIn() {
        this.canvas.setZoomLevel(2);
        assertEquals("Zoom level should be 2", this.canvas.getZoomLevel(), 2, 0);
    }
}
