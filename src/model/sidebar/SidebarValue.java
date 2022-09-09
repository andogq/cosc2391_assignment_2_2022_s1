package model.sidebar;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SidebarValue {
    VBox box;
    Label label;

    public SidebarValue(String label) {
        this.label = new Label(label);

        this.box = new VBox();
        this.box.getChildren().add(this.label);
    }

    protected void add(Node n) {
        this.box.getChildren().add(n);
    }

    public VBox getBox() {
        return this.box;
    }
}
