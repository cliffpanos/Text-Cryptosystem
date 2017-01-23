package view;

import runner.Runner;
import resources.Resources;
import controller.Encryptor;

import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

import javafx.scene.effect.*;
import javafx.stage.Stage;
import javafx.beans.property.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.stage.Screen;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelFormat;
import javafx.geometry.Rectangle2D;



public class EncryptDecryptMenu extends StackPane implements Resizable {

    private VBox menu = new VBox(); //holds the two buttons & passwordField
    private static Image blurredImage = null;
    private static VBox background = new VBox();
    private static VBox outerVBox = new VBox();
    //Used to hold the window buttons and Encrypt / Decrypt menu

    private static ImageView blur = new ImageView();

    private static double paneWidth = MainScreen.getStageWidth() * 0.18;
    private static double height = MainScreen.getStageHeight();

    private static TextField passwordField = new TextField();
    private static String passwordFieldText = "";
    private static boolean passwordFieldEnteredYet = false;
    private static int lastCaretPosition = 0;
    private static EncryptDecryptMenu instance;


    public EncryptDecryptMenu() {

        instance = this; //Used in updateBlurBackground()
        this.setBackground(null);

        background.setBackground(new Background(new BackgroundFill(Color
            .web("#212F3C", 0.868), new CornerRadii(5.0, 0.0, 0.0, 5.0, false),
            new Insets(0))));
        blur.setEffect(new BoxBlur(15, 15, 3));

        menu.setAlignment(Pos.CENTER);
        menu.setBackground(null);

        // Password Field TextField Actions
        passwordField.setOnAction(e -> {
                Encryptor.setPassword(passwordField.getText());
                passwordFieldText = passwordField.getText();
            });
        passwordField.setOnMouseEntered(e -> {
                if (!passwordFieldEnteredYet) {
                    passwordFieldText = passwordField.getText();
                    passwordFieldEnteredYet = true;
                    lastCaretPosition = passwordField.getCaretPosition();
                }
                passwordField.setText(passwordFieldText);
                passwordField.positionCaret(lastCaretPosition);
                passwordField.setEditable(true);
            });
        passwordField.setOnMouseExited(e -> {
                passwordFieldText = passwordField.getText();
                lastCaretPosition = passwordField.getCaretPosition();
                //Replace the password with * characters so that onlookers
                    //cannot see it
                String passwordCoverUp = "";
                for (int i = 0; i < passwordFieldText.length(); i++) {
                    passwordCoverUp += "*";
                }
                passwordField.setText(passwordCoverUp);
                passwordField.setEditable(false);
            });
        passwordField.setPromptText("enter here");
        passwordField.setAlignment(Pos.CENTER);
        passwordField.setFont(Font.font("Helvetica"));

        //Create the password trio: the icon, the TextField, and the Label below
        ImageView passwordIcon = Resources.getImageView("password.tiff",
            (paneWidth * 0.33));
        UILabel pwInstructions = new UILabel("Enter Password");
        pwInstructions.setTextFill(Color.WHITE);
        VBox passwordVBox = new VBox(10);
        passwordVBox.getChildren().addAll(passwordIcon, passwordField,
            pwInstructions);
        passwordVBox.setAlignment(Pos.CENTER);

        //menu holds the three central components in the EncryptDecryptMenu
        double dimension = MainScreen.getStageWidth() * 0.18;
        StackPane eStackPane = new TransparentButton("encrypt.png", "Encrypt",
            dimension);
        StackPane dStackPane = new TransparentButton("decrypt.png", "Decrypt",
            dimension);
        menu.getChildren().addAll(eStackPane, dStackPane, passwordVBox);

    }

    //Called by MainScreen after this EncryptDecryptMenu has been constructed
    public void completeSetup() {
        WindowButtons windowButtons = new WindowButtons(paneWidth);
        outerVBox.setAlignment(Pos.TOP_CENTER);
        outerVBox.getChildren().addAll(windowButtons.getRootNode(), menu);
        this.getChildren().addAll(blur, background, outerVBox);

        resize();
    }

    public static String getPasswordFieldText() {
        if (!passwordFieldEnteredYet) {
            return passwordField.getText();
        }
        return passwordFieldText;
    }


    public static void updateBlurBackground() {

        System.out.println("UPDATING BLUR");
        Stage stage = Runner.getStage();
        final int X = (int) stage.getX();
        System.out.println("X: " + X);
        final int Y = (int) stage.getY();
        System.out.println("Y: " + X);
        final int W = (int) (Runner.getStageWidth() * 0.18);
        System.out.println("W: " + W);
        final int H = (int) Runner.getStageHeight();
        System.out.println("H: " + H);

        stage.hide();

        Timeline pause = new Timeline(new KeyFrame(Duration.millis(50), event -> {
                try {
                    java.awt.Robot robot = new java.awt.Robot();
                    java.awt.image.BufferedImage image = robot.createScreenCapture(new java.awt.Rectangle(X, Y, W, H));

                    blurredImage = SwingFXUtils.toFXImage(image, null);
                    System.out.println("Not null");
                } catch (java.awt.AWTException e) {
                    System.out.println("The robot of doom strikes!");
                    e.printStackTrace();

                    blurredImage = null;
                }
                stage.show();
            }));
        pause.play();


        //stage.hide(); //Capture what's behind the stage, not the stage
        /*Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        try {

            com.sun.glass.ui.Robot rbt = com.sun.glass.ui.Application.GetApplication().createRobot();
            com.sun.glass.ui.Pixels p = rbt.getScreenCapture(
                (int) screen.getMinX(),
                (int) screen.getMinY(),
                (int) screen.getWidth(),
                (int) screen.getHeight(),
                true
            );

            WritableImage dskTop = new WritableImage((int)screen.getWidth(), (int)screen.getHeight());
            try {
                dskTop.getPixelWriter().setPixels(
                (int)screen.getMinX(),
                (int)screen.getMinY(),
                (int)screen.getWidth(),
                (int)screen.getHeight(),
                PixelFormat.getByteBgraPreInstance(),
                p.asByteBuffer(),
                (int)(screen.getWidth() * 4)
                );
            } catch (Exception e) {
                System.out.println("Exception caught");
            }

            WritableImage image = new WritableImage(W,H);
            try {
                image.getPixelWriter().setPixels(0, 0, W, H, dskTop.getPixelReader(), X, Y);
            } catch (Exception ee) {
                System.out.println("Second exception caught :(");
            }
            blurredImage = image;
        } catch (Exception e) {
            System.out.println("The robot of doom strikes!");
            e.printStackTrace();

            blurredImage = null;
        }*/
        //stage.show();
        //}));
        //pause.play();
        if (blurredImage != null) {
            blur.setImage(blurredImage);
        }
        instance.getChildren().setAll(blur, background, outerVBox);
    }


    public void resize() {

        paneWidth = MainScreen.getStageWidth() * 0.18;
        height = MainScreen.getStageHeight();

        //background.setPrefWidth(paneWidth);
        //background.setPrefHeight(height);

        passwordField.setMaxWidth(paneWidth * 0.6);

        menu.setSpacing(height / 15);
        menu.setPrefWidth(paneWidth);
        menu.setPrefHeight(height);

        updateBlurBackground();

    }

}
