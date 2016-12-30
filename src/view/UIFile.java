package view;

import controller.Encryptor;
import resources.Resources;
import runner.Runner;

import java.util.List;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/* //This will be used to process word documents
import java.io.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;*/

public class UIFile {

    private File fileToProcess;
    private String fileExtension;
    private String fileText;

    public UIFile(File fileToProcess) {
        this.fileToProcess = fileToProcess;
        this.fileExtension = computeFileExtension(fileToProcess.getName());
    }

    //Code to get and process Files ----------------- :

    public void processFile() {

        if (fileExtension.equals("txt")) {
            Encryptor.setText(readTXTFile(fileToProcess));
            String processedText = Encryptor.run();
            if (processedText != null) {
                writeTXTFile(fileToProcess, processedText);
            }
        } else if (fileExtension.equals("doc")) {
            readDocFile(fileToProcess);
        }

    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public boolean isReadable() {
        return this.fileToProcess.canRead();
    }

    public boolean isWritable() {
        return this.fileToProcess.canWrite();
    }

    public boolean hasEncryptedTags() {
        boolean hasThem = false;
        if (fileExtension.equals("txt")) {
            fileText = readTXTFile(fileToProcess);
        }
        if (fileText != null && fileText.length() >= 6) {
            if (fileText.substring(0, 3).equals("%E%")
                && fileText.substring(fileText.length() - 3).equals("$E$")) {
                hasThem = true;
            }
        }
        return hasThem;
    }

    public String isActionable(String processType) {
        boolean hasEncryptedTags = hasEncryptedTags();
        return processType.equals("Encrypt")
            ? (!hasEncryptedTags ? "Encryptable"
                : "Already Encrypted")
            : (hasEncryptedTags ? "Decryptable"
                : "Unencrypted");
    }

    public String getName() {
        String name = this.fileToProcess.getName();
        if (name.length() > 55) {
            name = name.substring(0, 55) + "..." + name.substring(name.length()
                - fileExtension.length() - 1);
        }
        return name;
    }

    public String getIconURL() {

        String toReturn = "";

        switch (getFileExtension()) {
        case "txt" :
            toReturn = "txtFile.png";
            break;
        case "doc" :
            toReturn = "docFile.png";
            break;
        default :
            toReturn = "blankFile.png";
            break;
        }

        return toReturn;
    }


    //Returns a String of the file extension, such as "txt" or "doc"
    public static String computeFileExtension(String fileName) {

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


    public static List<File> getFilesFromDirectory() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File");

        //Set extension filters: txt & doc
        FileChooser.ExtensionFilter extFilter =
            new FileChooser.ExtensionFilter("TXT files (.txt)", "*.txt");
        FileChooser.ExtensionFilter extFilter2 =
            new FileChooser.ExtensionFilter("Doc files (.doc)", "*.doc");

        fileChooser.getExtensionFilters().addAll(extFilter, extFilter2);
        return fileChooser.showOpenMultipleDialog(Runner.getStage());

    }


    public static File getFolderFromDirectory() {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select a Folder");
        File selectedDirectory = chooser.showDialog(Runner.getStage());
        return selectedDirectory;
    }



    /* public static void readParagraphs(HWPFDocument doc) throws Exception{
        WordExtractor we = new WordExtractor(doc);

        //Get the total number of paragraphs
        String[] paragraphs = we.getParagraphText();
        System.out.println("Total Paragraphs: "+paragraphs.length);

        for (int i = 0; i < paragraphs.length; i++) {

            System.out.println("Length of paragraph "+(i +1)+": "+ paragraphs[i].length());
            System.out.println(paragraphs[i].toString());

        }

    }

    public static void readHeader(HWPFDocument doc, int pageNumber){
        HeaderStories headerStore = new HeaderStories( doc);
        String header = headerStore.getHeader(pageNumber);
        System.out.println("Header Is: "+header);

    }

    public static void readFooter(HWPFDocument doc, int pageNumber){
        HeaderStories headerStore = new HeaderStories( doc);
        String footer = headerStore.getFooter(pageNumber);
        System.out.println("Footer Is: "+footer);

    }*/

    /*public static void readDocumentSummary(HWPFDocument doc) {
        DocumentSummaryInformation summaryInfo=doc.getDocumentSummaryInformation();
        String category = summaryInfo.getCategory();
        String company = summaryInfo.getCompany();
        int lineCount=summaryInfo.getLineCount();
        int sectionCount=summaryInfo.getSectionCount();
        int slideCount=summaryInfo.getSlideCount();

        System.out.println("---------------------------");
        System.out.println("Category: "+category);
        System.out.println("Company: "+company);
        System.out.println("Line Count: "+lineCount);
        System.out.println("Section Count: "+sectionCount);
        System.out.println("Slide Count: "+slideCount);

    }*/

    public static void readDocFile(File docFile) {
        /*WordExtractor extractor = null;
        try {

            FileInputStream fis = new FileInputStream(docFile.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            extractor = new WordExtractor(document);
            String[] fileData = extractor.getParagraphText();
            for (int i = 0; i < fileData.length; i++)
            {
                if (fileData[i] != null)
                    System.out.println(fileData[i]);
            }
        } catch (Exception exep) {
            exep.printStackTrace();
        } */

    }

    /*
    //Create the file to which the encrypted text will be written.
    try {
    // Using "Encrypted Text.txt" is just for testing
        File fileToWrite = new File("Encrypted Text.txt");

        if (file.createNewFile()){
            System.out.println("File is created!");
        } else {
            System.out.println("File already exists.");
        }
    // In javafx we should implement this so that
    } catch (IOException e) {
      e.printStackTrace();
    }*/

}
