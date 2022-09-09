package model.sidebar;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class ColorValue extends SidebarValue {
    public ColorValue(String label, Color value, ValueChange<Color> change) {
        super(label);

        ColorPicker color = new ColorPicker(value);
        color.setOnAction(e -> change.change(color.getValue()));

        this.add(color);
    }
}
