package view;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.StackPane;

public class FolderPane extends StackPane implements Resizable {

    private static File folder = null;
    private static ArrayList<UIFile> files = new ArrayList<>();

    public FolderPane() {

        // Gets a folder File
        folder = UIFile.getFolderFromDirectory();
        this.processFolder();

        resize();
    }


    public void processFolder() {
        if (folder.isDirectory()) {
            File tempFiles[] = folder.listFiles();

            for (File tempFile : tempFiles) {
                if (tempFile != null) {
                    files.add(new UIFile(tempFile));
                }
            }

            for (UIFile file : files) {
                file.processFile();
            }
        }
    }

    public void resize() {
        System.out.println("FolderPane resizing");
    }
}
