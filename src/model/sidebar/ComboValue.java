package model.sidebar;

import java.util.List;

import javafx.scene.control.ComboBox;

public class ComboValue extends SidebarValue {
    public ComboValue(String label, String value, List<String> values, ValueChange<String> change) {
        super(label);

        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().addAll(values);
        combo.setValue(value);
        combo.setOnAction(e -> change.change(combo.getValue()));

        this.add(combo);
    }
}
