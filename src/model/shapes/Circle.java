package model.shapes;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.sidebar.DoubleValue;
import model.sidebar.SidebarValue;

public class Circle implements Styled {
    private javafx.scene.shape.Circle circle;

    /* Renderable properties */
    private double x;
    private double y;
    private double[] dragStart;

    /* Styled properties */
    private Color border;
    private double borderWidth;
    private Color background;
    
    /* Shape specific properties */
    private double radius;

    public Circle() {
        this.circle = new javafx.scene.shape.Circle();

        this.move(100, 100);
        this.setRadius(100);
        
        this.setBorder(Color.BLACK);
        this.setBorderWidth(0);
        this.setBackground(Color.BLACK);
    }

    /* Shape specific methods */
    public void setRadius(double radius) {
        this.radius = radius;
        this.circle.setRadius(this.radius);
    }
    public double getRadius() {
        return this.radius;
    }

    /* Renderable interface methods */
    @Override
    public String serialize() {
        return String.format("Circle;%s;%.2f", Styled.super.serialize(), this.radius);
    }

    @Override
    public boolean inShape(double x, double y) {
        // Calculate distance from center point
        double distance = Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));

        // If distance is greater than radius it's outside of the circle
        return distance <= this.radius;
    }

    @Override
    public void move(double x, double y) {
        this.x = x;
        this.y = y;
        
        this.circle.setCenterX(this.x);
        this.circle.setCenterY(this.y);
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double[] getDragStart() {
        return this.dragStart;
    }

    @Override
    public void setDragStart(double[] position) {
        this.dragStart = position;
    }

    @Override
    public ArrayList<SidebarValue> getSidebar() {
        ArrayList<SidebarValue> sidebar = Styled.super.getSidebar();

        sidebar.add(new DoubleValue(
            "Radius",
            this.radius,
            radius -> this.setRadius(radius)
        ));

        return sidebar;
    }

    /* Styled interface methods */
    @Override
    public Shape getShape() {
        return this.circle;
    }

    @Override
    public Color getBorder() {
        return this.border;
    }

    @Override
    public Color getBackground() {
        return this.background;
    }

    @Override
    public double getBorderWidth() {
        return this.borderWidth;
    }

    @Override
    public void setBorder(Color border) {
        this.border = border;
        this.style();
    }

    @Override
    public void setBackground(Color background) {
        this.background = background;
        this.style();
    }
    
    @Override
    public void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
        this.style();
    }
}
