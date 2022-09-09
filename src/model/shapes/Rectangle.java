package model.shapes;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.sidebar.DoubleValue;
import model.sidebar.SidebarValue;

public class Rectangle implements Styled {
    private javafx.scene.shape.Rectangle rectangle;

    /* Renderable properties */
    private double x;
    private double y;
    private double[] dragStart;

    /* Styled properties */
    private Color border;
    private double borderWidth;
    private Color background;

    /* Shape specific properties */
    private double width;
    private double height;
    private double angle;

    public Rectangle() {
        this.rectangle = new javafx.scene.shape.Rectangle();

        this.move(0, 0);
        this.setHeight(100);
        this.setWidth(100);
        this.setAngle(0);
        
        this.setBorder(Color.BLACK);
        this.setBorderWidth(0);
        this.setBackground(Color.BLACK);
    }

    /* Shape specific methods */
    public void setHeight(double height) {
        this.height = height;
        this.rectangle.setHeight(height);
    }

    public void setWidth(double width) {
        this.width = width;
        this.rectangle.setWidth(width);
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        this.rectangle.setRotate(angle);
    }

    public double getAngle() {
        return this.angle;
    }

    /* Renderable interface methods */
    @Override
    public String serialize() {
        return String.format("Rectangle;%s;%.2f;%.2f;%.2f;", Styled.super.serialize(), this.height, this.width, this.angle);
    }

    @Override
    public boolean inShape(double x, double y) {
        return (
            x >= this.x &&
            x <= this.x + this.width &&
            y >= this.y &&
            y <= this.y + this.height
        );
    }

    @Override
    public void move(double x, double y) {
        this.x = x;
        this.y = y;

        this.rectangle.setX(x);
        this.rectangle.setY(y);
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
            "Height",
            this.height,
            height -> this.setHeight(height))
        );
        sidebar.add(new DoubleValue(
            "Width",
            this.width,
            width -> this.setWidth(width))
        );
        sidebar.add(new DoubleValue(
            "Rotation",
            this.angle,
            angle -> this.setAngle(angle))
        );
        
        return sidebar;
    }

    /* Styled interface methods */
    @Override
    public Shape getShape() {
        return this.rectangle;
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
