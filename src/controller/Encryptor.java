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
//

import view.UIAlert;
import view.EncryptDecryptMenu;
import view.InputOnEncryptMenu;
import view.MainScreen;

import java.io.File;


public class Encryptor {

    // Class-wide variables
    private static String allCharactersWithSpace =
        " &*()-_+=|ABCDEFGHIJK',.?<>LMNOPQRS"
        + "TUVWXYZ/:;[]{}abcdefghijklmnop!@#$%^qrstuvwxyz0123456789\"";
        /* allCharacters replaced the space character with a tilde here to
           prevent user input error */

    private static String keyword = null;
    private static String password = null;
    private static String text = null;
    private static String passwordHashAlpha;
    private static String passwordHashBeta;
    private static int numKeys = 0;

    private static String pwCheck = "$Enc$"; //See below for explanation
    private static String nLSubstitute = "/N/$/L/";
        //Used to substitute newLine characters


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

        // GET THE KEYWORD FROM THE USER
        if (keyword == null) {
            UIAlert.show("Choose to Encrypt or Decrypt",
                "You did not choose to encrypt or decrypt.\n"
                + "Please choose from the leftmost pane.",
                javafx.scene.control.Alert.AlertType.ERROR);
            return null;
        }

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

        // Special operations for getting text variable if inputting directly
        if (MainScreen.isInputtingDirectly()) {
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



// These print statements are for testing
        System.out.println("password: '" + password + "'");

        if (keyword.equals("encrypt")) {
            return encrypt();
        } else if (keyword.equals("decrypt")) {
            return decrypt();
        } else {
            return null;
        }

    }

    //ENCRYPT

    private static String encrypt() {

        if (hasEncryptedTags(text)) {
            UIAlert.show("Text Already Encrypted",
                "The text that you entered has already\n"
                + "been encrypted. To prevent the convolution\n"
                + "inherent in multiple encryptions, this \n"
                + "text will not been encrypted again.",
                javafx.scene.control.Alert.AlertType.ERROR);
            return null;
        }

        String temporaryText = "";
        for (char c : text.toCharArray()) {
            if (!String.valueOf(c).matches(".")) {
                System.out.println("NOT A MATCH");
                temporaryText += nLSubstitute;
            } else {
                temporaryText += c;
            }
        }
        text = temporaryText;

        //will hold all user-entered characters that are invalid
        String invalidCharacterList = "";
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

        String invalidPrintList = "";
        for (char c : invalidCharacterList.toCharArray()) {
            invalidPrintList += (c + ", ");
        }
        if (invalidPrintList.length() > 1) {
            invalidCharacterList = invalidPrintList
                .substring(0, invalidPrintList.length() - 2); //remove space
            System.out.println("Invalid charList: " + invalidCharacterList);

        }

        if (invalidCharacterList.length() > 0) {
            boolean cancelling = UIAlert.show("Invalid Characters Entered",
                "The following characters are invalid:\n  "
                + invalidCharacterList + "\n\n"
                + "When these characters are decrypted,\n"
                + "they will appear as '?' characters.\n\n"
                + "Would you like to proceed?",
                javafx.scene.control.Alert.AlertType.CONFIRMATION, true);

            if (cancelling) {
                return null; //Exit the encryption process
            }
        }

        //Add two %E% & $E$ tags to prevent multiple encryptions:
        finalEncryptedCipherText
            = "%E%" + finalEncryptedCipherText + "$E$";

        System.out.println("\n\nEncrypted text is: "
            + finalEncryptedCipherText);
        return finalEncryptedCipherText;

    }

    //DECRYPT

    private static String decrypt() {

        //Test to make sure that decrypting the first nine characters
        //returns '$Enc$'
        //If it does not, decryption will halt.
        if (text.length() < 11) {
            UIAlert.show("Inputted Text Incorrect",
                "The text that you entered to decrypt is incorrect.\n"
                + "All correctly inputted text is greater than length 11.",
                javafx.scene.control.Alert.AlertType.ERROR);
            return null;
        }

        if (!hasEncryptedTags(text)) {
            UIAlert.show("Text Not Decryptable",
                "The text that you are attempting to \n"
                + "decrypt has NOT been encrypted by this system.\n"
                + "To prevent a loss of data through false\n"
                + "decryption, this text will not be decrypted.",
                javafx.scene.control.Alert.AlertType.ERROR);
            return null;
        }

        //Remove the %E% & $E$ tags that were added at the end of the encryption
        text = text.substring(3, text.length() - 3);

        if (!(Recursion.decrypt(text.substring(0, 5), 1)).equals(pwCheck)) {
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


        String temporaryText = "";
        for (int i = 0; i < finalDecryptedCipherText.length(); i++) {
            if (!(i > finalDecryptedCipherText.length() - 7)
                && finalDecryptedCipherText.substring(i, i + 7)
                    .equals(nLSubstitute)) {
                System.out.println("NEWLINE DECRYPTING");
                temporaryText += "\r\n";
                i += 6; //the for loop already increments i by 1
            } else {
                temporaryText += finalDecryptedCipherText.charAt(i);
            }
        }
        finalDecryptedCipherText = temporaryText;
        System.out.println("\nDecrypted text is: "
            + finalDecryptedCipherText);
        return finalDecryptedCipherText;
    }



    //--------------------------------------------------------------------//



    public static String hashThePassword(String pwToHash) {

        //Custom hashCode function to provide cross-platform / language support
        long longHash = 0;
        int off = 0;
        char[] values = pwToHash.toCharArray();
        int len = pwToHash.length();
        for (int i = 0; i < pwToHash.length(); i++) {
            longHash = 31 * longHash + Character.getNumericValue(values[off++]);
        }

        if (longHash < 0) {
            longHash = longHash * -1; //Ensure that longHash is positive
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

    public static boolean hasEncryptedTags(String textToCheck) {
        boolean hasTheTags = false;
        if (textToCheck != null && textToCheck.length() >= 6) {
            System.out.println("TTT: '" + textToCheck + "'");
            System.out.println(textToCheck.substring(0, 3));
            System.out.println(textToCheck.substring(textToCheck.length() - 3));
            if (textToCheck.substring(0, 3).equals("%E%")
                && textToCheck.substring(textToCheck.length() - 3)
                    .equals("$E$")) {
                hasTheTags = true;
            }
        }
        return hasTheTags;
    }

}
