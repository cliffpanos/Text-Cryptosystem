package view;

import runner.Runner;
import resources.Resources;

import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.application.*;
import javafx.beans.property.*;
import javafx.scene.Cursor;
import javafx.scene.Node;

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
    private UILabel instructionsLabel;

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
                            Platform.exit();
                            System.exit(0);
                        } else if (buttonPressed == minimizeOnHover) {
                            stage.setIconified(true);
                        } else if (buttonPressed == fullScreenOnHover) {
                            Runner.toggleFullScreen();
                            EncryptDecryptMenu.updateBlurBackground();
                        }
                        setWindowButtonsOnHover();
                    } else {
                        setWindowButtons();
                    }
                });
        }
        exitOnHover.setOnMousePressed(e -> {
                buttonPressed = exitOnHover;
                this.getChildren().setAll(exitOnPressed, minimizeOnHover,
                    fullScreenOnHover);
            });
        minimizeOnHover.setOnMousePressed(e -> {
                buttonPressed = minimizeOnHover;
                this.getChildren().setAll(exitOnHover, minimizeOnPressed,
                    fullScreenOnHover);
            });
        fullScreenOnHover.setOnMousePressed(e -> {
                buttonPressed = fullScreenOnHover;
                this.getChildren().setAll(exitOnHover, minimizeOnHover,
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
        makeDraggable(stage, MainScreen.getEDMenu());
        makeDraggable(stage, MainScreen.getFilePane());
        makeDraggable(stage, MainScreen.getFolderPane());
        makeDraggable(stage, MainScreen.getOptionsMenu());
        makeDraggable(stage, MainScreen.getInputOEMenu().getRootNode());
        makeDraggable(stage, MainScreen.getInputODMenu().getRootNode());

        outerHBox.getChildren().addAll(this);
        outerHBox.setMinWidth(leftPaneWidth);
    }

    public void setWindowButtons() {
        this.getChildren().setAll(exit, minimize, fullScreen);
    }

    public void setWindowButtonsOnHover() {
        this.getChildren().setAll(exitOnHover, minimizeOnHover,
            fullScreenOnHover);
    }

    public HBox getRootNode() {
        return this.outerHBox;
    }

    // makes a stage draggable using a given node, in this case the translator
    //from stackoverflow.com
    public void makeDraggable(final Stage stage, final Node byNode) {

        final Delta dragDelta = new Delta();
        double topBar = 2.0; //how much vertical room the top status bar uses

        byNode.setOnMousePressed(mouseEvent -> {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    byNode.setCursor(Cursor.HAND);
                }
                // record a delta distance for the drag and drop operation.
                dragDelta.x = stage.getX() - mouseEvent.getScreenX();
                dragDelta.y = stage.getY() - mouseEvent.getScreenY();
                byNode.setCursor(Cursor.HAND);
            });
        final BooleanProperty inDrag = new SimpleBooleanProperty(false);

        byNode.setOnMouseReleased(mouseEvent -> {
                byNode.setCursor(Cursor.DEFAULT);
                Runner.setLastXandY(stage.getX(), stage.getY());
                //Update where Runner thinks the stage is on the screen
                //since we just moved the stage
                inDrag.set(false);
            });
        byNode.setOnMouseDragged(mouseEvent -> {
                if (mouseEvent.getScreenY() + dragDelta.y >= topBar) {
                    stage.setY(mouseEvent.getScreenY() + dragDelta.y);
                    //Prevent stage from being dragged above the top bar
                }
                if (dragDelta.y < 0 && mouseEvent.getScreenY() < topBar) {
                    stage.setY(topBar);
                }
                stage.setX(mouseEvent.getScreenX() + dragDelta.x);

                if (inDrag.get()) {
                    //EncryptDecryptMenu.updateBlurBackground();
                    //TODO
                }

                inDrag.set(true);
            });

    }

    /** records relative x and y co-ordinates. */
    private static class Delta {
        double x, y;
    }

}
