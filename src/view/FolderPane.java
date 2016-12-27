package view;

import javafx.scene.layout.StackPane;

public class FolderPane extends StackPane implements Resizable {

    public FolderPane() {

        int i = 4;
        //TODO

        resize();
    }

    public void resize() {
        System.out.println("FolderPane resizing");
    }
}
