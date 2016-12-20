package controller;

//
//  Encryptor
//  Encryptor.java
//
//  Created by Clifford Panos and edited with Anthony D'Achille on 6/10/16.
//  Copyright © 2016 panOS. All rights reserved.
//
//  Will encrypt and decrypt a set of text using a password.
//  Uses multiple one-character-to-one-character substitution keys.
//  A third party can have access to this code and still not be able to decrypt.
//  Similar passwords to the encryptor's password will not work.
//
//  However, letter-frequency analysis is [somewhat] prevented because more than
//  one key is used to encrypt the characters in the text, and the use of the
//  different keys is dependent on the length of the password. Thus, the cipher
//  text is severely more convoluted to those who do not know the pw.
//
//  Additionally, the text is encrypted and then re-encrypted recursively.

import runner.Runner;
import view.UIAlert;
import view.EncryptDecryptMenu;
import view.InputOnEncryptMenu;
import view.MainScreen;

import java.util.Scanner;
import java.io.File;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class Encryptor {

    // Class-wide variables
    private static String allCharactersWithSpace =
        " &*()-_+=|ABCDEFGHIJK',.?<>LMNOPQRS"
        + "TUVWXYZ/:;[]{}abcdefghijklmnop!@#$%^qrstuvwxyz0123456789\"";
        /* allCharacters replaced the space character with a tilde here to
           prevent user input error */
    private static Scanner scan = new Scanner(System.in);

    private static String keyword = null;
    private static String password = null;
    private static String text = null;
    private static String passwordHashAlpha;
    private static String passwordHashBeta;
    private static int numKeys = 0;


    public static void setKeyword(String aKeyword) {
        keyword = aKeyword;
    }

    public static void setPassword(String aPassword) {
        password = aPassword;
    }

    public static void setText(String aText) {
        text = aText;
    }

    public static String run() {

        System.out.println("Welcome to Encryptor.");

        // GET THE KEYWORD FROM THE USER
        if (keyword == null) {
            UIAlert.show("Choose to Encrypt or Decrypt",
                "You did not choose to encrypt or decrypt.\n"
                + "Please choose from the leftmost pane.",
                javafx.scene.control.Alert.AlertType.ERROR);
            return null;
        }


// Getting the password could be nicely implemented with a TextInputDialog
        // GET THE PASSWORD FROM THE USER
        password = EncryptDecryptMenu.getPasswordFieldText();
        if (password == null || password.equals("")) {

            UIAlert.show("Enter a Password",
                "You did not enter a password with\n"
                + "which to " + keyword + " the text. Please\n"
                + "enter a password in the leftmost pane.",
                javafx.scene.control.Alert.AlertType.ERROR);
            return null;
        }
        //ELSE:
        int acceptablePwCharactersFound;
        String acceptablePwCharacters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWX"
            + "YZabcdefghijklmnopqrstuvwxyz?!@#$%^&*(){}[]<>:;/._-=+";

        //Ensure that the pw contains only acceptablePwCharacters
        acceptablePwCharactersFound = 0;
        for (int i = 0; i < password.length(); i++) {
            if (acceptablePwCharacters.indexOf(password.charAt(i)) != -1) {
                acceptablePwCharactersFound += 1;
            }
        }

        if (acceptablePwCharactersFound < password.length()) {
            UIAlert.show("Unacceptable Password",
                "You entered a password with invalid characters.\n"
                + "Acceptable characters for a password are:\n"
                + "A-Z, a-z, 0-9, and ?!@#$%^&*(){}[]<>:;/,._-=+\n"
                + "Please enter an acceptable password & try again.",
                javafx.scene.control.Alert.AlertType.ERROR);
            return null;
        }


        // GET THE TEXT TO BE ENCRYPTED/DECRYPTED FROM THE USER
        if (MainScreen.getIsEncryptingNotDecrypting()) {
            text = MainScreen.getInputOEMenu().getInputFieldText();
        } else {
            text = MainScreen.getInputODMenu().getInputFieldText();
        }

        if (text == null || text.equals("")) {

            UIAlert.show("Enter Text to be " + keyword + "ed",
                "You did not input any text to be " + keyword + "ed.\n"
                + "Please type or copy text into the box.",
                javafx.scene.control.Alert.AlertType.ERROR);
            return null;
        }

        System.out.println("Text is: '" + text + "'");



        //At this point, the static variables keyword, password, and text have
        //their respective values assigned to them & are ready to be used.



        //GENERATE initialKey
            // ---> eliminates repeats from password & allCharacters
        String initialKey = KeySet.keyGen(password);

        //We already have the password, so generate the Beta Password:
        String betaPassword = "";
        for (int p = password.length() - 1; p >= 0; p--) {
            betaPassword += password.charAt(p);
        }

        //GENERATE PASSWORDHASHALPHA and PASSWORDHASHBETA ----------------
        passwordHashAlpha = hashThePassword(password);
        passwordHashBeta = hashThePassword(betaPassword);

        //Now, IndexCurve and Recursion can be constructed properly
        IndexCurve indexCurve =
            new IndexCurve(passwordHashAlpha, passwordHashBeta);

        //Assign the correct value to numKeys
        numKeys =
            indexCurve.curve((password.length() * password.length()), 6, 18);
        Recursion.setNumKeys(numKeys);



//------
// These print statements are for testing
        System.out.println("password: '" + password + "'");
        System.out.println("numKeys: " + numKeys);
        System.out.println("i Key: " + initialKey);
        System.out.println();

        String pwCheck = "$Enc$"; //See below for explanation if this String
        if (keyword.equals("encrypt")) {
            String invalidCharacterList = "";
                //will hold all user-entered characters that are invalid

            String textNoSpaces = "";
            for (char c : text.toCharArray()) {
                textNoSpaces += (c == ' ' ? "~"
                    : (allCharactersWithSpace.indexOf(c) == -1 ? "?" : c));
                //If a character is a space, make it a ~ so that it will work
                    //in Recursion's encrypt function
                //If the character is not in allCharactersWithSpace, then change
                    // it to be a ? so that it will work in Recursion's encrypt
                invalidCharacterList +=
                    (allCharactersWithSpace.indexOf(c) == -1
                        ? (invalidCharacterList.indexOf(c) == -1 ? c : "")
                        : "");
                //If the character IS invalid AND it is not already in
                    //invalidCharacterList, then add it to invalidCharacterList
            }
            text = pwCheck + textNoSpaces;
            /*The word Encryptor is added so that it can be checked if it
              decrypted properly when the user tries to decrypt. If on
              decryption, the first 9 characters are not '$Enc$', then
              the user entered an incorrect password, and the remaining text
              will not be decrypted because doing so would harm the data.*/
            System.out.println("New text is: " + text);

            String finalEncryptedCipherText = Recursion.encrypt(text, numKeys);
            //encrypt() works from numKeys down to 1 by decrementing

            if (invalidCharacterList.length() > 0) {
                System.out.println("Invalid characters are: ");
                for (char c : invalidCharacterList.toCharArray()) {
                    System.out.print(c);
                }
                System.out.println();
            }
            System.out.println("\n\nEncrypted text is: "
                + finalEncryptedCipherText);
            return finalEncryptedCipherText;
        }

        if (keyword.equals("decrypt")) {

            //Test to make sure that decrypting the first nine characters
            // returns '$Enc$'
            //If it does not, decryption will halt.
            if (text.length() < 5) {
                UIAlert.show("Inputted Text Incorrect",
                    "The text that you entered to decrypt is incorrect.\n"
                    + "All correctly inputted text is greater than length 5.",
                    javafx.scene.control.Alert.AlertType.ERROR);
                return null;
            }

            if(!(Recursion.decrypt(text.substring(0, 5), 1)).equals(pwCheck)) {
                UIAlert.show("Incorrect Password",
                    "The decryption password that you entered is incorrect.\n"
                    + "Attempting to decrypt the text with an incorrect\n"
                    + "password would render a disarray of useless data.\n\n"
                    + "Try to decrypt again using a different password.",
                    javafx.scene.control.Alert.AlertType.ERROR);
                return null;
            }
            String finalDecryptedCipherText =
                Recursion.decrypt(text, 1);
                //The second argument of decrypt() MUST be 1 because the
                //encrypt function works from 1 to numKeys
                //this is because it must undo the encrypt function oppositely
            String decryptedNoTildes = "";
            for (char c : finalDecryptedCipherText.toCharArray()) {
                decryptedNoTildes += (c == '~' ? " " : c);
                //Spaces were made into ~ characters before being encrypted, so
                    //now they must be transformed back into spaces.
            }
            finalDecryptedCipherText = decryptedNoTildes.substring(5);
                //This goes from index 9 to the end to remove the "$Enc$"
            System.out.println("\nDecrypted text is: "
                + finalDecryptedCipherText);
            return finalDecryptedCipherText;
        }

        //-------------------------------------------------------------------//


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
        return null;
    }



    public static String hashThePassword(String pwToHash) {

        long longHash = pwToHash.hashCode();
        if (longHash < 0) {
            longHash = longHash * -1;
        }

        String stringOfTheHash = "" + longHash;

        //Eliminate zeroes from the password hashes
        for (int i = 0; i < stringOfTheHash.length(); i++) {
            if ((stringOfTheHash.charAt(i)) == ('0')) {
                stringOfTheHash = stringOfTheHash.substring(0, i)
                    + i + stringOfTheHash.substring(i + 1);
            }
        }

        return stringOfTheHash;
    }

    public static String getPasswordHashAlpha() {
        return passwordHashAlpha;
    }

    public static String getPasswordHashBeta() {
        return passwordHashBeta;
    }

    public static int getNumKeys() {
        return numKeys;
    }

    public static void copyToClipboard(String toCopy) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        clipboard.clear();
        ClipboardContent content = new ClipboardContent();
        content.putString(toCopy);
        clipboard.setContent(content);
    }

}
