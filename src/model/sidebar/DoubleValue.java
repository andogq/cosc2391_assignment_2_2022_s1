package model.sidebar;

public class DoubleValue extends TextValue {
    public DoubleValue(String label, double value, ValueChange<Double> change) {
        super(label, String.format("%.2f", value), (newValue) -> {
            try {
                double newDouble = Double.parseDouble(newValue);
                change.change(newDouble);
            } catch (NumberFormatException e) {
                // Can safely ignore it
            }
        });
    }
}
