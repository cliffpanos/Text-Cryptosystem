package view;

import runner.Runner;
import resources.Resources;

import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class WindowButtons extends HBox {

    private HBox outerHBox = new HBox();
    private Stage stage;
    private double leftPaneWidth;

    private ImageView exit;
    private ImageView minimize;
    private ImageView fullScreen;
    private ImageView exitOnHover;
    private ImageView minimizeOnHover;
    private ImageView fullScreenOnHover;
    private ImageView exitOnPressed;
    private ImageView minimizeOnPressed;
    private ImageView fullScreenOnPressed;

    private boolean isInControlBounds = false;
    //isInControlBounds is whether or not the mouse is within this class' HBox;
    // it is NOT whether or not the mouse is within this class' outerHBox
    private ImageView buttonPressed = null;

    public WindowButtons(double givenWidth) {

        stage = Runner.getStage();
        leftPaneWidth = givenWidth;

        //Create all of the buttons, which are ImageViews containing Images
        exit = new ImageView(Resources.getImage("exit.png"));
        minimize = new ImageView(Resources.getImage("minimize.png"));
        fullScreen = new ImageView(Resources.getImage("fullScreen.png"));
        exitOnHover = new ImageView(Resources.getImage("exitOnHover.png"));
        minimizeOnHover =
            new ImageView(Resources.getImage("minimizeOnHover.png"));
        fullScreenOnHover =
            new ImageView(Resources.getImage("fullScreenOnHover.png"));
        exitOnPressed = new ImageView(Resources.getImage("exitOnPressed.png"));
        minimizeOnPressed =
            new ImageView(Resources.getImage("minimizeOnPressed.png"));
        fullScreenOnPressed =
            new ImageView(Resources.getImage("fullScreenOnPressed.png"));

        //Resize and resample every ImageView's icon image
        ImageView[] buttons = {exit, minimize, fullScreen, exitOnHover,
            minimizeOnHover, fullScreenOnHover, exitOnPressed,
            minimizeOnPressed, fullScreenOnPressed};
        for (ImageView button : buttons) {
            button.setFitWidth(11);
            button.setPreserveRatio(true);
            button.setSmooth(true);
            button.setCache(true);
        }


        ImageView[] onHoverButtons = {exitOnHover, minimizeOnHover,
                fullScreenOnHover};
        for (ImageView onHoverButton : onHoverButtons) {
            onHoverButton.setOnMouseReleased(e -> {
                    if (isInControlBounds) {
                        if (buttonPressed == exitOnHover) {
                            System.exit(0);
                        } else if (buttonPressed == minimizeOnHover) {
                            stage.setIconified(true);
                        } else if (buttonPressed == fullScreenOnHover) {
                            Runner.windowButtonResize();
                        }
                        setWindowButtonsOnHover();
                    } else {
                        setWindowButtons();
                    }
                });
        }
        exitOnHover.setOnMousePressed(e -> {
                buttonPressed = exitOnHover;
                this.getChildren().clear();
                this.getChildren().addAll(exitOnPressed, minimizeOnHover,
                    fullScreenOnHover);
            });
        minimizeOnHover.setOnMousePressed(e -> {
                buttonPressed = minimizeOnHover;
                this.getChildren().clear();
                this.getChildren().addAll(exitOnHover, minimizeOnPressed,
                    fullScreenOnHover);
            });
        fullScreenOnHover.setOnMousePressed(e -> {
                buttonPressed = fullScreenOnHover;
                this.getChildren().clear();
                this.getChildren().addAll(exitOnHover, minimizeOnHover,
                    fullScreenOnPressed);
            });

        this.setSpacing(8.5);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER_LEFT);
        this.setMaxWidth(60);
        this.setPrefHeight(30);

        this.setOnMouseEntered(e -> {
            setWindowButtonsOnHover();
            isInControlBounds = true;
        });
        this.setOnMouseExited(e -> {
            setWindowButtons();
            isInControlBounds = false;
        });

        setWindowButtons();
        outerHBox.getChildren().addAll(this, getTranslatorHBox());
        outerHBox.setMinWidth(leftPaneWidth);
        System.out.println("ow: " + outerHBox.getWidth());
    }

    public void setWindowButtons() {
        this.getChildren().clear();
        this.getChildren().addAll(exit, minimize, fullScreen);
    }

    public void setWindowButtonsOnHover() {
        this.getChildren().clear();
        this.getChildren().addAll(exitOnHover, minimizeOnHover,
            fullScreenOnHover);
    }

    public HBox getRootNode() {
        return this.outerHBox;
    }

    public HBox getTranslatorHBox() {

        HBox translator = new HBox();
        //this HBox will be used to allow the user to click and drag it to
        //move the entire window of the Cryptosystem program
        translator.setAlignment(Pos.CENTER);
        translator.setMinWidth(leftPaneWidth - 60);

        Label instructionsLabel = new Label("            ");
        instructionsLabel.setText("            ");
        instructionsLabel.setTextFill(Color.web("#F7F7F7", 0.7));
        instructionsLabel.setTextAlignment(TextAlignment.CENTER);
        translator.getChildren().add(instructionsLabel);

        translator.setOnMouseEntered(e -> {
            instructionsLabel.setText("Drag to Move");
            });
        translator.setOnMouseExited(e -> {
                instructionsLabel.setText("            ");
            });
        translator.setOnMousePressed(e -> {
                instructionsLabel.setTextFill(Color.web("#B3B3B3", 0.8));
            });
        translator.setOnMouseReleased(e -> {
                instructionsLabel.setTextFill(Color.web("#F7F7F7", 0.7));
            });

        return translator;
    }

}
