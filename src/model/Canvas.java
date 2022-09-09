package model;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.exceptions.TemplateException;
import model.shapes.Circle;
import model.shapes.Image;
import model.shapes.Rectangle;
import model.shapes.Renderable;
import model.shapes.Text;

public class Canvas {
    private double height;
    private double width;

    private Color backgroundColor;

    private double zoomLevel;

    private ArrayList<Renderable> shapes;

    private Pane pane;

    private int selectedShape;

    public Canvas(double height, double width) {
        this.height = height;
        this.width = width;

        this.backgroundColor = Color.WHITE;
        this.zoomLevel = 1;
        this.shapes = new ArrayList<>();

        this.selectedShape = -1;
    }

    public void addShape(Renderable shape) {
        shape.initialiseListeners();
        
        this.shapes.add(shape);

        this.pane.getChildren().add(shape.getNode());
    }

    public void deleteSelected() {
        if (this.selectedShape != -1) {
            this.pane.getChildren().remove(this.selectedShape);
            this.shapes.remove(this.selectedShape);
        }

        this.selectedShape = -1;
    }

    public void clear() {
        this.shapes.clear();
        this.pane.getChildren().clear();
        this.setBackgroundColor(Color.WHITE);
    }

    public Renderable selectShape(double x, double y) {
        for (int i = this.shapes.size() - 1; i >= 0; i--) {
            Renderable s = this.shapes.get(i);
            if (s.inShape(x, y)) {
                this.selectedShape = i;
                return s;
            }
        }

        this.selectedShape = -1;
        return null;
    }

    public String serialize() {
        String backgroundColor = String.format("%d,%d,%d", (int) (255 * this.backgroundColor.getRed()), (int) (255 * this.backgroundColor.getGreen()), (int) (255 * this.backgroundColor.getBlue()));
        String serialized = String.format("%.2f;%.2f;%s\n", this.height, this.width, backgroundColor);

        for (Renderable s: this.shapes) {
            serialized += s.serialize() + "\n";
        }

        return serialized;
    }

    public void load(File file) {
        try (Scanner scanner = new Scanner(file);) {
            scanner.useDelimiter("\n");

            if (!scanner.hasNext()) throw new TemplateException("Malformed canvas header");

            // Canvas details
            String canvasDetails = scanner.next();
            String[] canvasParts = canvasDetails.split(";");

            if (canvasParts.length != 3) throw new TemplateException("Malformed canvas header");

            // Load dimensions
            double canvasHeight = Double.parseDouble(canvasParts[0]);
            double canvasWidth = Double.parseDouble(canvasParts[1]);
            this.setHeight(canvasHeight);
            this.setWidth(canvasWidth);

            // Load color
            Color color = this.parseColor(canvasParts[2]);
            if (color == null) throw new TemplateException("Malformed color");
            this.setBackgroundColor(color);

            while (scanner.hasNext()) {
                String[] line = scanner.next().split(";");

                double x = 0;
                double y = 0;
                if (line.length >= 3) {
                    // Can parse x and y
                    x = Double.parseDouble(line[1]);
                    y = Double.parseDouble(line[2]);
                }

                Color borderColor = Color.BLACK;
                double borderWidth = 0;
                Color backgroundColor = Color.BLACK;
                if (line.length >= 6 && !line[0].equals("Image")) {
                    borderColor = this.parseColor(line[3]);
                    borderWidth = Double.parseDouble(line[4]);
                    backgroundColor = this.parseColor(line[5]);
                }

                if (line[0].equals("Circle")) {
                    if (line.length != 7) throw new TemplateException("Invalid circle");

                    double radius = Double.parseDouble(line[6]);

                    Circle circle = new Circle();
                    circle.move(x, y);
                    circle.setBorder(borderColor);
                    circle.setBackground(backgroundColor);
                    circle.setBorderWidth(borderWidth);
                    circle.setRadius(radius);
                    
                    this.addShape(circle);
                } else if (line[0].equals("Rectangle")) {
                    if (line.length != 9) throw new TemplateException("Invalid rectangle");

                    double height = Double.parseDouble(line[6]);
                    double width = Double.parseDouble(line[7]);
                    double angle = Double.parseDouble(line[8]);

                    Rectangle rectangle = new Rectangle();
                    rectangle.move(x, y);
                    rectangle.setBorder(borderColor);
                    rectangle.setBackground(backgroundColor);
                    rectangle.setBorderWidth(borderWidth);
                    rectangle.setHeight(height);
                    rectangle.setWidth(width);
                    rectangle.setAngle(angle);

                    this.addShape(rectangle);
                } else if (line[0].equals("Image")) {
                    if (line.length != 7) throw new TemplateException("Invalid image");

                    double height = Double.parseDouble(line[3]);
                    double width = Double.parseDouble(line[4]);
                    double angle = Double.parseDouble(line[5]);
                    String path = line[6];

                    Image image = new Image(path);
                    image.move(x, y);
                    image.setHeight(height);
                    image.setWidth(width);
                    image.setAngle(angle);

                    this.addShape(image);
                } else if (line[0].equals("Text")) {
                    if (line.length != 12) throw new TemplateException("Invalid text");

                    String content = line[6];
                    double size = Double.parseDouble(line[7]);
                    String fontFace = line[8];
                    boolean bold = Boolean.parseBoolean(line[9]);
                    boolean italic = Boolean.parseBoolean(line[10]);
                    double angle = Double.parseDouble(line[11]);

                    Text text = new Text();
                    text.move(x, y);
                    text.setBorder(borderColor);
                    text.setBackground(backgroundColor);
                    text.setBorderWidth(borderWidth);
                    text.setContent(content);
                    text.setSize(size);
                    text.setFontFace(fontFace);
                    text.setBold(bold);
                    text.setItalic(italic);
                    text.setAngle(angle);

                    this.addShape(text);
                } else throw new TemplateException(String.format("Unknown template load type: %s", line[0]));
            }
        } catch (FileNotFoundException e) {
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText("Template loading error");
            a.setContentText(String.format("File not found: {}", file.getAbsolutePath()));
            a.show();
        } catch (TemplateException e) {
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText("Template loading error");
            a.setContentText(e.getMessage());
            a.show();
        }
    }

    public void setPane(Pane pane) {
        this.pane = pane;
        
        // Update everything with the new pane
        this.setBackgroundColor(this.backgroundColor);
        this.setWidth(this.width);
        this.setHeight(this.height);
    }

    public void setZoomLevel(double zoomLevel) {
        this.zoomLevel = zoomLevel;

        this.pane.setScaleX(zoomLevel);
        this.pane.setScaleY(zoomLevel);
    }

    public double getZoomLevel() {
        return this.zoomLevel;
    }

    public double getHeight() {
        return this.height;
    }

    public double getWidth() {
        return this.width;
    }

    public void setHeight(double height) {
        this.height = height;

        this.pane.setPrefHeight(height);
    }

    public void setWidth(double width) {
        this.width = width;

        this.pane.setPrefWidth(width);
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;

        this.pane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
    }

    public ArrayList<Renderable> getShapes() {
        return this.shapes;
    }

    private Color parseColor(String c) {
        String[] colorRaw = c.split(",");
        if (colorRaw.length != 3) return null;
        int r = Integer.parseInt(colorRaw[0]);
        int g = Integer.parseInt(colorRaw[1]);
        int b = Integer.parseInt(colorRaw[2]);

        return Color.rgb(r, g, b);
    }
}
