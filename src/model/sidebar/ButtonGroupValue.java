package model.sidebar;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class ButtonGroupValue extends SidebarValue {
    public ButtonGroupValue(String label, String[] options, ValueChange<boolean[]> change) {
        super(label);
        
        HBox container = new HBox();
        boolean[] values = new boolean[options.length];

        for (int i = 0; i < options.length; i++) {
            ToggleButton button = new ToggleButton(options[i]);
            button.setFont(Font.font("Sans Serif", FontWeight.BOLD, FontPosture.REGULAR, 13));

            int valueI = i;

            button.setOnAction(e -> {
                values[valueI] = button.isSelected();

                change.change(values);
            });

            container.getChildren().add(button);
        }
        this.add(container);
    }
    
}
