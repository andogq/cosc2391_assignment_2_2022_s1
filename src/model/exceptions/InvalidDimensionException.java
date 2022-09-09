package model.exceptions;

public class InvalidDimensionException extends Exception {
    public InvalidDimensionException(double dimension) {
        super(String.format("Dimension can't be %s", dimension < 0 ? "negative" : "zero"));
    }
}
