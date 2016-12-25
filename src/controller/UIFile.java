package controller;

import controller.Encryptor;
import resources.Resources;
import runner.Runner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class UIFile {

    private File fileToProcess;

    public UIFile(File fileToProcess) {
        this.fileToProcess = fileToProcess;
    }

    //Code to get and process Files ----------------- :

    public void processFile() {

        String extension = getFileExtension(fileToProcess.getName());

        if (extension.equals("txt")) {
            Encryptor.setText(readTXTFile(fileToProcess));
            String processedText = Encryptor.run();
            if (processedText != null) {
                writeTXTFile(fileToProcess, processedText);
            }
        }

    }


    //Returns a String of the file extension, such as "txt" or "doc"
    public static String getFileExtension(String fileName) {

        String extension = ""; //Get the extension of the file
        int dot = fileName.lastIndexOf('.');
        int slash = Math.max(fileName.lastIndexOf('/'),
            fileName.lastIndexOf('\\'));
        if (dot > slash) {
            extension = fileName.substring(dot + 1);
        }
        return extension;
    }

    private static String readTXTFile(File txtFile) {

        String textToProcess = "";
        if (txtFile != null) {

            // Gets text to process from txtFile
            BufferedReader br = null;
            FileReader fr = null;
            try {

                fr = new FileReader(txtFile);
                br = new BufferedReader(fr);

                String currentLine;

                while ((currentLine = br.readLine()) != null) {
                    System.out.println("Adding | " + currentLine
                        + " | to txt File");
                    textToProcess += currentLine + "\n";
                }
                textToProcess =
                    textToProcess.substring(0, textToProcess.length() - 1);
                //remove the extra "\n" that was added at the end
                System.out.println("TEXTTT:\n" + textToProcess);

            } catch (IOException e) {

                e.printStackTrace();

            } finally {

                try {

                    if (br != null) {
                        br.close();
                    }
                    if (fr != null) {
                        fr.close();
                    }

                } catch (IOException ex) {

                    ex.printStackTrace();

                }
            }

        }
        return textToProcess;

    }

    public void writeTXTFile(File txtFile, String textToWrite) {

        // Writes textToWrite to txtFile
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(txtFile);
            bw = new BufferedWriter(fw);
            if (bw != null && textToWrite != null) {
                bw.write(textToWrite);
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

        Resources.playSound("encryptionComplete.aiff");

    }





    public static File getFileFromDirectory() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File");

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
            new FileChooser.ExtensionFilter("TXT files (.txt)", "*.txt");
        FileChooser.ExtensionFilter extFilter2 =
            new FileChooser.ExtensionFilter("Doc files (.doc)", "*.doc");

        fileChooser.getExtensionFilters().addAll(extFilter, extFilter2);
        return fileChooser.showOpenDialog(Runner.getStage());

    }


    public static File getFolderFromDirectory() {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select a Folder");
        File selectedDirectory = chooser.showDialog(Runner.getStage());
        return selectedDirectory;
    }








}
