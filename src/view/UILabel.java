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
        this(text, fontSize, "NORMAL");
    }

    public UILabel(String text, double fontSize, String fontWeightString) {
        this(text, fontSize, fontWeightString, "Helvetica");
    }

    public UILabel(String text, double fontSize, String fontWeightString,
        String fontFamily) {

        super(text);
        this.fontFamily = fontFamily;
        setFont(fontSize);
        setWeight(fontWeightString);

        this.setAlignment(Pos.CENTER);
        this.setTextAlignment(TextAlignment.CENTER);

    }

    public void setFont(double fontSize) {

        this.fontSize = fontSize;
        super.setFont(Font.font(fontFamily, fontWeight, fontSize));
    }

    public void setWeight(String fontWeightString) {

        switch (fontWeightString) {

        case "LIGHT" :
            fontWeight = FontWeight.LIGHT;
            break;
        case "NORMAL" :
            fontWeight = FontWeight.NORMAL;
            break;
        case "SEMI_BOLD" :
            fontWeight = FontWeight.SEMI_BOLD;
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
