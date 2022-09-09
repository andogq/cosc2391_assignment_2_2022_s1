package model.shapes;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import model.sidebar.ButtonGroupValue;
import model.sidebar.ComboValue;
import model.sidebar.DoubleValue;
import model.sidebar.SidebarValue;
import model.sidebar.TextValue;

public class Text implements Styled {
    private javafx.scene.text.Text text;

    /* Renderable properties */
    private double x;
    private double y;
    private double[] dragStart;

    /* Styled properties */
    private Color border;
    private double borderWidth;
    private Color background;

    /* Shape specific properties */
    private String content;
    private double size;
    private String fontFace;
    private boolean bold;
    private boolean italic;
    private TextAlignment justification;

    private double angle;

    public Text() {
        this.text = new javafx.scene.text.Text();

        this.text.setTextOrigin(VPos.TOP);
        
        this.move(0, 0);

        this.setBorder(Color.BLACK);
        this.setBorderWidth(0);
        this.setBackground(Color.BLACK);

        this.setContent("Text");
        this.setSize(13);
        this.setFontFace("Arial");
        this.setBold(false);
        this.setItalic(false);
        this.setJustification(TextAlignment.LEFT);
        this.setAngle(0);
    }

    /* Shape specific methods */
    public double getHeight() {
        return this.text.getLayoutBounds().getHeight();
    }
    public double getWidth() {
        return this.text.getLayoutBounds().getWidth();
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
        this.text.setText(content);
    }

    public double getSize() {
        return size;
    }
    public void setSize(double size) {
        this.size = size;

        this.updateFont();
    }

    public String getFontFace() {
        return fontFace;
    }
    public void setFontFace(String fontFace) {
        this.fontFace = fontFace;

        this.updateFont();
    }

    public boolean isBold() {
        return bold;
    }
    public void setBold(boolean bold) {
        this.bold = bold;

        this.updateFont();
    }

    public boolean isItalic() {
        return italic;
    }
    public void setItalic(boolean italic) {
        this.italic = italic;

        this.updateFont();
    }

    public TextAlignment getJustification() {
        return justification;
    }
    public void setJustification(TextAlignment justification) {
        this.justification = justification;

        this.text.setTextAlignment(justification);
    }

    public void updateFont() {
        Font font = Font.font(
            this.fontFace,
            (FontWeight) (this.bold ? FontWeight.BOLD : FontWeight.NORMAL),
            (FontPosture) (this.italic ? FontPosture.ITALIC : FontPosture.REGULAR),
            this.size
        );

        this.text.setFont(font);
    }

    public void setAngle(double angle) {
        this.angle = angle;
        this.text.setRotate(angle);
    }

    public double getAngle() {
        return this.angle;
    }

    /* Renderable interface methods */
    @Override
    public String serialize() {
        return String.format("Text;%s;%s;%.2f;%s;%b;%b;%.2f", Styled.super.serialize(), this.content, this.size, this.fontFace, this.bold, this.italic, this.angle);
    }

    @Override
    public boolean inShape(double x, double y) {
        return (
            x >= this.x &&
            x <= this.x + this.getWidth() &&
            y >= this.y &&
            y <= this.y + this.getHeight()
        );
    }

    @Override
    public void move(double x, double y) {
        this.x = x;
        this.y = y;

        this.text.setX(x);
        this.text.setY(y);
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double[] getDragStart() {
        return this.dragStart;
    }

    @Override
    public void setDragStart(double[] position) {
        this.dragStart = position;
    }

    @Override
    public ArrayList<SidebarValue> getSidebar() {
        ArrayList<SidebarValue> sidebar = Styled.super.getSidebar();

        sidebar.add(new TextValue("Content", this.content, content -> this.setContent(content)));

        sidebar.add(new ComboValue("Font Face", this.fontFace, Font.getFamilies(), fontFace -> this.setFontFace(fontFace)));
        sidebar.add(new ButtonGroupValue("Font Options", new String[] {"Bold", "Italic"}, options -> {
            this.setBold(options[0]);
            this.setItalic(options[1]);
        }));
        sidebar.add(new DoubleValue("Font Size", this.size, size -> this.setSize(size)));

        String justificationText = "";
        String[] justificationValues = new String[] { "Left", "Center", "Right", "Justify" };
        TextAlignment[] justificationOptions = { TextAlignment.LEFT, TextAlignment.CENTER, TextAlignment.RIGHT, TextAlignment.JUSTIFY };

        for (int i = 0; i < justificationOptions.length; i++) {
            if (justificationOptions[i] == this.justification) {
                justificationText = justificationValues[i];

                break;
            }
        }

        sidebar.add(new ComboValue("Justification", justificationText, Arrays.asList(justificationValues), j -> {
            for (int i = 0; i < justificationOptions.length; i++) {
                if (justificationValues[i] == j) {
                    this.setJustification(justificationOptions[i]);
    
                    break;
                }
            }
        }));
        
        sidebar.add(new DoubleValue(
            "Rotation",
            this.angle,
            angle -> this.setAngle(angle))
        );

        return sidebar;
    }

    /* Styled interface methods */
    @Override
    public Shape getShape() {
        return this.text;
    }

    @Override
    public Color getBorder() {
        return this.border;
    }

    @Override
    public Color getBackground() {
        return this.background;
    }

    @Override
    public double getBorderWidth() {
        return this.borderWidth;
    }

    @Override
    public void setBorder(Color border) {
        this.border = border;
        this.style();
    }

    @Override
    public void setBackground(Color background) {
        this.background = background;
        this.style();
    }
    
    @Override
    public void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
        this.style();
    }
}
