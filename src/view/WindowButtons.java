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

import javafx.animation.*;
import javafx.application.*;
import javafx.beans.property.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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
    private Label instructionsLabel;

    public WindowButtons(double givenWidth) {

        stage = Runner.getStage();
        leftPaneWidth = givenWidth;

        //Create all of the buttons, which are ImageViews containing Images
        exit = Resources.getImageView("exit.png", 11);
        minimize = Resources.getImageView("minimize.png", 11);
        fullScreen = Resources.getImageView("fullScreen.png", 11);
        exitOnHover = Resources.getImageView("exitOnHover.png", 11);
        minimizeOnHover = Resources.getImageView("minimizeOnHover.png", 11);
        fullScreenOnHover = Resources.getImageView("fullScreenOnHover.png", 11);
        exitOnPressed = Resources.getImageView("exitOnPressed.png", 11);
        minimizeOnPressed = Resources.getImageView("minimizeOnPressed.png", 11);
        fullScreenOnPressed = Resources.getImageView("fullScreenOnPressed.png",
            11);


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
                            Runner.toggleFullScreen();
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

        setWindowButtons(); //The screen defaults to showing the plain buttons

        outerHBox.getChildren().addAll(this, getTranslatorHBox());
        outerHBox.setMinWidth(leftPaneWidth);
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

        instructionsLabel = new Label();
        instructionsLabel.setText("            ");
        instructionsLabel.setTextFill(Color.web("#F7F7F7", 0.88));
        instructionsLabel.setTextAlignment(TextAlignment.CENTER);
        translator.getChildren().add(instructionsLabel);

        makeDraggable(stage, translator);

        return translator;
    }

    // makes a stage draggable using a given node, in this case the translator
    //from stackoverflow.com
    public void makeDraggable(final Stage stage, final Node byNode) {
        final Delta dragDelta = new Delta();
        byNode.setOnMousePressed(mouseEvent -> {
            instructionsLabel.setTextFill(Color.web("#B3B3B3", 0.8));
            // record a delta distance for the drag and drop operation.
            dragDelta.x = stage.getX() - mouseEvent.getScreenX();
            dragDelta.y = stage.getY() - mouseEvent.getScreenY();
            byNode.setCursor(Cursor.HAND);
        });
        final BooleanProperty inDrag = new SimpleBooleanProperty(false);

        byNode.setOnMouseReleased(mouseEvent -> {
            instructionsLabel.setTextFill(Color.web("#F7F7F7", 0.88));
            byNode.setCursor(Cursor.HAND);
            Runner.setXandY(stage.getX(), stage.getY());
            //Update where Runner thinks the stage is on the screen
            //since we just moved the stage

            /*if (inDrag.get()) {
                stage.hide();

                Timeline pause = new Timeline(new KeyFrame(Duration.millis(50),
                    event -> {
                    background.setImage(copyBackground(stage));
                    layout.getChildren().set(
                            0,
                            background
                    );
                    stage.show();
                }));
                pause.play();
            }*/

            inDrag.set(false);
        });
        byNode.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() + dragDelta.x + 1);
            stage.setY(mouseEvent.getScreenY() + dragDelta.y + 1);
            inDrag.set(true);
        });
        byNode.setOnMouseEntered(mouseEvent -> {
            instructionsLabel.setText("Move Window");
            if (!mouseEvent.isPrimaryButtonDown()) {
                byNode.setCursor(Cursor.HAND);
            }
        });
        byNode.setOnMouseExited(mouseEvent -> {
            instructionsLabel.setText("            ");
            if (!mouseEvent.isPrimaryButtonDown()) {
                byNode.setCursor(Cursor.DEFAULT);
            }
        });
    }

    /** records relative x and y co-ordinates. */
    private static class Delta {
        double x, y;
    }

}
