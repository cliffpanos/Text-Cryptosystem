package controller;

public class KeySet {

    //in the Encryptor class, make an array of each of the keys from this KeySet
    //this will be used to shift the characters in the string of all characters

    //The KeySet is the String double array

    private static int numKeys;
    private static String initialKey = "";

    private static String passwordHashAlpha;
    private static String passwordHashBeta;
    private static String allCharacters = "~&*()-_+=|ABCDEFGHIJK',.?<>LMNOPQRS"
        + "TUVWXYZ/:;[]{}abcdefghijklmnop!@#$%^qrstuvwxyz0123456789\"";
        /* allCharacters replaced the space character with a tilde here to
           prevent user input error */

    private int iterationNumber;
    private String[][] keys;



    //implement a part that will make part of the key go backwards (for loop
    //that decrements) based off of the hash of the password and its length


    public KeySet(int iterationNumber) {

        this.iterationNumber = iterationNumber;
        numKeys = Encryptor.getNumKeys()
            + IndexCurve.curve(iterationNumber, 0, 2);
        passwordHashAlpha = Encryptor.getPasswordHashAlpha();
        passwordHashBeta = Encryptor.getPasswordHashBeta();

        keys = new String[2][numKeys];

        for (int i = 0; i < numKeys; i++) {
            keys[0][i] = createKey(i, passwordHashAlpha);
        }
        for (int i = 0; i < numKeys; i++) {
            keys[1][i] = createKey(i, passwordHashBeta);
        }

    }


    private String createKey(int k, String hashType) {


        int length = hashType.length();

        int displacement = Character.getNumericValue(hashType
            .charAt(IndexCurve.curve((k * iterationNumber), 0, length - 1)));

        int index = (int) (
            ((((double) displacement / 9) * (4.0 / 10.0))
            + (((double) iterationNumber / (double) (numKeys)) * (3.0 / 10.0))
            + (((double) k / (double) (numKeys - 1)) * (3.0 / 10.0)))
                    //iterationNumber is always <= numKeys
                    //displacement is always an int between 1 and 9
                    //k is always < numKeys - 1; see KeySet's constructor
            * initialKey.length());
                //all of these fractions are added together to make a fraction
                //between 0 and 1, which multiplied by initialKey.length()
                //will create an index between 1 and initialKey.length()
        String newKey =
            initialKey.substring(index, initialKey.length())
            + initialKey.substring(0, index);
//System.out.println("newKey " + k + " is: " + newKey);
        return newKey;
    }

    public Character getEncryptedChar(Character c, int keyToRetrieve) {
        int index = IndexCurve.curve(iterationNumber, 0, 1);
        return (keys[index][keyToRetrieve]).charAt(allCharacters.indexOf(c));
    }

    public String getDecryptedChar(Character c, int keyToRetrieve) {
        int index = IndexCurve.curve(iterationNumber, 0, 1);
        return "" + allCharacters
            .charAt((keys[index][keyToRetrieve]).indexOf(c));
    }

    public int getNumKeys() {
        return this.numKeys;
    }




    public static String keyGen(String password) {

        //Generate initialKey using the password

        // DELETES DUPLICATE CHARACTERS IN THE PASSWORD
        // i.e. "hashbrowns has two 's' and two 'h'"

        String duplicatePassword = String.valueOf(password.charAt(0));
        //This will be the duplicate of the pw & will be used to delete repeats
        //The first character of password is added to this no matter what

        boolean duplicate = false;
        //This boolean stores whether or not the character is duplicated

        for (int i = 0; i < password.length(); i++) {
            //This will actually duplicate possibleSolutions into duplicateList
            for (int j = 0; j < duplicatePassword.length(); j++) {
                if (password.charAt(i) == duplicatePassword.charAt(j)) {
                    duplicate = true;
                }
            }
            if (duplicate == false) {
                //If the char makes it through the loop without being a repeat
                duplicatePassword += password.charAt(i);
            } else {
                duplicate = false;
            }
        }
        password = duplicatePassword;

        String genesisKey = password; //Places pw at the start of initialKey
        boolean isTheCharacterAlreadyInThePassword;

        for (int i = 0; i < allCharacters.length(); i++) {
            isTheCharacterAlreadyInThePassword = false;
            for (int j = 0; j < password.length(); j++) {
                if (allCharacters.charAt(i) == password.charAt(j)) {
                    isTheCharacterAlreadyInThePassword = true;
                }
            }
            if (isTheCharacterAlreadyInThePassword == false) {
                genesisKey += allCharacters.charAt(i);
            }
        }

        initialKey = genesisKey; //initialKey created
        return initialKey;

    }



}
