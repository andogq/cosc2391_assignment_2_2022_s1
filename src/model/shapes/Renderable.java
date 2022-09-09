package model.shapes;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import model.sidebar.SidebarValue;

public interface Renderable {
    public abstract Node getNode();

    public abstract void move(double x, double y);
    public abstract double getX();
    public abstract double getY();

    public default String serialize() {
        return String.format("%.2f;%.2f", this.getX(), this.getY());
    };

    public abstract void setDragStart(double[] startPosition);
    public abstract double[] getDragStart();

    public abstract boolean inShape(double x, double y);

    public abstract ArrayList<SidebarValue> getSidebar();

    public default void initialiseListeners() {
        Node node = this.getNode();

        node.setOnMousePressed(e -> this.dragStart(e));
        node.setOnMouseDragged(e -> this.drag(e));
    }

    public default void dragStart(MouseEvent e) {
        this.setDragStart(new double[] { e.getX() - this.getX(), e.getY() - this.getY() });
    }

    public default void drag(MouseEvent e) {
        double[] position = this.getDragStart();

        this.move(e.getX() - position[0], e.getY() - position[1]);
    }
}
