package model.sidebar;

import javafx.scene.control.TextField;

public class TextValue extends SidebarValue {
    public TextValue(String label, String value, ValueChange<String> change) {
        super(label);

        TextField field = new TextField(value);
        field.setOnKeyTyped(e -> change.change(field.getText()));

        this.add(field);
    }
}
