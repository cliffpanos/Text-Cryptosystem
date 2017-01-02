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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

//This will be used to process word documents
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

//maybe delete these?
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


public class UIFile {

    private final File fileToProcess;
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
            Encryptor.setText(readDocFile(fileToProcess));
            String processedText = Encryptor.run();
            if (processedText != null) {
                writeDocFile(fileToProcess, processedText);
            }
        } else if (fileExtension.equals("docx")) {
            Encryptor.setText(readDocFile(fileToProcess));
            String processedText = Encryptor.run();
            if (processedText != null) {
                writeDocFile(fileToProcess, processedText);
            }
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

    public boolean hasProcessableExtension() {
        // Following statement should uncommented when processing Word Documents
        // functionality has been added
        return fileExtension.matches("txt|doc|docx");
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
        case "docx" :
            toReturn = "docxFile.png";
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

    public static void writeTXTFile(File txtFile, String textToWrite) {

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


    public static String readDocFile(File doc) {

        String textToProcess = "";

        try {

            FileInputStream fis = new FileInputStream(doc.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            WordExtractor  extractor = new WordExtractor(document);

            String[] paragraphs = extractor.getParagraphText();
            for (int i = 0; i < paragraphs.length; i++) {
                if (paragraphs[i] != null)
                    System.out.println(paragraphs[i]);
                    textToProcess += paragraphs[i] + "\n";
            }

        } catch (Exception exep) {
            exep.printStackTrace();
        }

        textToProcess =
            textToProcess.substring(0, textToProcess.length() - 1);
        //remove the extra "\n" that was added at the end
        return textToProcess;

    }

    public static void writeDocFile(File docFile, String textToWrite) {

        try {

            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(docFile));
            HWPFDocument doc = new HWPFDocument(fs);
            Range range = doc.getRange();
            CharacterRun run = range.insertAfter(textToWrite);
            OutputStream out = new FileOutputStream(docFile);
            doc.write(out);
            out.flush();
            out.close();

            /*
            FileInputStream fis = new FileInputStream(docFile.getAbsolutePath());
            XWPFDocument document= new XWPFDocument(fis);
            //Write the Document in file system
            FileOutputStream out = new FileOutputStream(docFile);

            //create Paragraph
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run=paragraph.createRun();
            run.setText(textToWrite);

            document.write(out);
            out.close();*/

            System.out.println("docc/docx file written successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    public static List<File> getFilesFromDirectory() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File");

        //Set extension filters: txt & doc
        FileChooser.ExtensionFilter extFilter1 =
            new FileChooser.ExtensionFilter("TXT files (.txt)", "*.txt");
        FileChooser.ExtensionFilter extFilter2 =
            new FileChooser.ExtensionFilter("Doc files (.doc)", "*.doc");
        FileChooser.ExtensionFilter extFilter3 =
            new FileChooser.ExtensionFilter("Docx files (.docx)", "*.docx");

        fileChooser.getExtensionFilters()
            .addAll(extFilter1, extFilter2, extFilter3);
        return fileChooser.showOpenMultipleDialog(Runner.getStage());

    }

    public static File getFolderFromDirectory() {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select a Folder");
        File selectedDirectory = chooser.showDialog(Runner.getStage());
        return selectedDirectory;
    }





/*
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

    public static void readDocumentSummary(HWPFDocument doc) {
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
