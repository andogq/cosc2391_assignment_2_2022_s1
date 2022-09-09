package model.shapes;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.sidebar.ColorValue;
import model.sidebar.DoubleValue;
import model.sidebar.SidebarValue;

public interface Styled extends Renderable {
    public abstract Shape getShape();

    @Override
    public default Node getNode() {
        return this.getShape();
    }

    public abstract Color getBorder();
    public abstract Color getBackground();
    public abstract double getBorderWidth();
    
    public abstract void setBorder(Color border);
    public abstract void setBackground(Color background);
    public abstract void setBorderWidth(double borderWidth);

    @Override
    public default String serialize() {
        String borderColor = String.format("%d,%d,%d", (int) (255 * this.getBorder().getRed()), (int) (255 * this.getBorder().getGreen()), (int) (255 * this.getBorder().getBlue()));
        String backgroundColor = String.format("%d,%d,%d", (int) (255 * this.getBackground().getRed()), (int) (255 * this.getBackground().getGreen()), (int) (255 * this.getBackground().getBlue()));
        return String.format("%s;%s;%.2f;%s", Renderable.super.serialize(), borderColor, this.getBorderWidth(), backgroundColor);
    }
    
    default ArrayList<SidebarValue> getSidebar() {
        ArrayList<SidebarValue> sidebar = new ArrayList<>();

        sidebar.add(new ColorValue(
            "Background",
            this.getBackground(),
            background -> this.setBackground(background))
        );
        sidebar.add(new ColorValue(
            "Border Color",
            this.getBorder(),
            border -> this.setBorder(border))
        );
        sidebar.add(new DoubleValue(
            "Border Width",
            this.getBorderWidth(),
            borderWidth -> this.setBorderWidth(borderWidth))
        );

        return sidebar;
    }

    public default void style() {
        javafx.scene.shape.Shape shape = this.getShape();

        shape.setStroke(this.getBorder());
        shape.setFill(this.getBackground());
        shape.setStrokeWidth(this.getBorderWidth());
    }
}
