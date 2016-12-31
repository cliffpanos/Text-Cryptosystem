package view;

import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;


public class UILabel extends Label {

    private String fontFamily;
    private double fontSize;
    private FontWeight fontWeight;

    public UILabel() {
        this("");
    }

    public UILabel(String text) {
        this(text, 15);
    }

    public UILabel(String text, double fontSize) {
        this(text, fontSize, FontWeight.NORMAL);
    }

    public UILabel(String text, double fontSize, FontWeight fontWeight) {
        this(text, fontSize, fontWeight, "Helvetica");
    }

    public UILabel(String text, double fontSize, FontWeight fontWeight,
        String fontFamily) {

        super(text);
        this.fontFamily = fontFamily;
        this.fontWeight = fontWeight;
        this.setAlignment(Pos.CENTER);
        this.setTextAlignment(TextAlignment.CENTER);
        setFont(fontSize);

    }

    public void setFont(double fontSize) {

        this.fontSize = fontSize;
        super.setFont(Font.font(fontFamily, fontWeight, fontSize));
    }

    public void setWeight(String weight) {

        switch (weight) {

        case "LIGHT" :
            fontWeight = FontWeight.LIGHT;
            break;
        case "BOLD" :
            fontWeight = FontWeight.BOLD;
            break;
        default :
            fontWeight = FontWeight.NORMAL;

        }
        super.setFont(Font.font(fontFamily, fontWeight, fontSize));

    }


}
