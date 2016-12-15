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


import java.util.Scanner;
import java.io.File;


public class Encryptor {


    // Class-wide variables
    private static String allCharactersWithSpace =
        " &*()-_+=|ABCDEFGHIJK',.?<>LMNOPQRS"
        + "TUVWXYZ/:;[]{}abcdefghijklmnop!@#$%^qrstuvwxyz0123456789\"";
        /* allCharacters replaced the space character with a tilde here to
           prevent user input error */
    private static Scanner scan = new Scanner(System.in);

    private static String keyword;
    private static String password;
    private static String text;
    private static String passwordHashAlpha;
    private static String passwordHashBeta;
    private static int numKeys = 0;
    private static boolean START = true;



    public static void startEncryptor() {

        System.out.println("Welcome to Encryptor.");

// The following do statement could be better implemented in JavaFX with
// a short listview and selection model or a couple of buttons
        // GET THE KEYWORD FROM THE USER
        boolean didTheUserChooseEncryptOrDecrypt;
        do {

            System.out.println("Would you like to encrypt or decrypt?");
            keyword = scan.next();
            keyword = keyword.trim();


            //Ensure that the keyword is either encrypt or decrypt
            if (keyword.equalsIgnoreCase("encrypt")
                || keyword.equalsIgnoreCase("enc")
                || keyword.equalsIgnoreCase("e")) {
                didTheUserChooseEncryptOrDecrypt = true;
                keyword = "encrypt";
            } else if (keyword.equalsIgnoreCase("decrypt")
                || keyword.equalsIgnoreCase("dec")
                || keyword.equalsIgnoreCase("d")) {
                didTheUserChooseEncryptOrDecrypt = true;
                keyword = "decrypt";
            } else {
                didTheUserChooseEncryptOrDecrypt = false;
                System.out.println("\nYou did not choose to either encrypt"
                    + " or decrypt. Please choose again.\n");
            }

        } while (!didTheUserChooseEncryptOrDecrypt);


        System.out.println();

// Getting the password could be nicely implemented with a TextInputDialog
        // GET THE PASSWORD FROM THE USER
        int acceptablePwCharactersFound;
        String acceptablePwCharacters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWX"
            + "YZabcdefghijklmnopqrstuvwxyz?!@#$%^&*(){}[]<>:;/._-=+";
        do {

            System.out.println("Enter the password with which the text will"
                + " be " + keyword + "ed:");
            password = scan.next();
            password = password.trim();

            //Ensure that the pw contains only acceptablePwCharacters
            acceptablePwCharactersFound = 0;
            for (int i = 0; i < password.length(); i++) {
                for (int j = 0; j < acceptablePwCharacters.length(); j++) {
                    if (password.charAt(i) ==
                        acceptablePwCharacters.charAt(j)) {
                        acceptablePwCharactersFound += 1;
                    }
                }
            }

            if (acceptablePwCharactersFound < password.length()) {
                System.out.println("Acceptable characters for a password ar"
                    + "e:\nA-Z, a-z, 0-9, and ?!@#$%^&*(){}[]<>:;/,._-=+");
                System.out.println("Please start again using a different "
                    + "password.\n");
            }

        } while (acceptablePwCharactersFound < password.length());


        System.out.println();


        // GET THE TEXT TO BE ENCRYPTED/DECRYPTED FROM THE USER
        do {

            System.out.println("Enter the file name that you would like to "
                + keyword + ":");
            text = scan.nextLine();
            //text = text.trim();

            System.out.println("Text is: '" + text + "'");

            if (text.length() == 0) {
                System.out.println("\nYou did not enter any text.\n");
            }

        } while (text.length() == 0);


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

        String finalDecryptedCipherText = "";
        String finalEncryptedCipherText = "";

        if (keyword == "encrypt") {
            String invalidCharacterList = "";
                //will hold all user-entered characters that are invalid

            String textNoSpaces = "";
            for (char c : text.toCharArray()) {
                textNoSpaces += (c == ' ' ? "~" :
                    (allCharactersWithSpace.indexOf(c) == -1 ? "?" : c));
                //If a character is a space, make it a ~ so that it will work
                    //in Recursion's encrypt function
                //If the character is not in allCharactersWithSpace, then change
                    // it to be a ? so that it will work in Recursion's encrypt
                invalidCharacterList +=
                    (allCharactersWithSpace.indexOf(c) == -1 ?
                        (invalidCharacterList.indexOf(c) == -1 ? c : "") : "");
                //If the character IS invalid AND it is not already in
                    //invalidCharacterList, then add it to invalidCharacterList
            }
            text = textNoSpaces;

            finalEncryptedCipherText = Recursion.encrypt(text, numKeys);

            if (invalidCharacterList.length() > 0) {
                System.out.println("Invalid characters are: ");
                for (char c : invalidCharacterList.toCharArray()) {
                    System.out.print(c);
                }
                System.out.println();
            }
            System.out.println("\n\nEncrypted text is: "
                + finalEncryptedCipherText);
        }

        finalDecryptedCipherText =
            Recursion.decrypt(finalEncryptedCipherText, 1);

        String decryptedNoSpaces = "";
        for (char c : finalDecryptedCipherText.toCharArray()) {
            decryptedNoSpaces += (c == '~' ? " " : c);
            //Spaces were made into ~ characters before being encrypted, so
                //now they must be transformed back into spaces.
        }
        finalDecryptedCipherText = decryptedNoSpaces;
        System.out.println("\n\nDecrypted text is: "
            + finalDecryptedCipherText);

        //-------------------------------------------------------------------//


/*
        //Create the file to which the encrypted text will be written.
        try {
        // Using "Encrypted Text.txt" is just for testing
	      File fileToWrite = new File("Encrypted Text.txt");

	      if (file.createNewFile()){
	        System.out.println("File is created!");
	      }else{
	        System.out.println("File already exists.");
	      }
        // In javafx we should implement this so that
    	} catch (IOException e) {
	      e.printStackTrace();
*/


        System.out.println("\n");

        //Reset class-wide variables
        password = "";
        text = "";
        keyword = "";
        passwordHashAlpha = "";
        passwordHashBeta = "";
        numKeys = 0;

        //return START;

    }



    public static String hashThePassword(String password) {

        long longHash = password.hashCode();
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

}