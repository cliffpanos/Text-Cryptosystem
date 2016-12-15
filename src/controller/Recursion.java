package controller;

public class Recursion {

    private static int numKeys = 0; //constructed in setNumKeys;

    public static String encrypt(String toProcess, int iterationNumber) {

        //System.out.println("Encryption iteration number: " + iterationNumber);

        if (iterationNumber == 0) {
            //System.out.println("Returning final encrypted result");
            return toProcess;
        }

        KeySet keySet = new KeySet(iterationNumber);

        String newCipherText = "";

        //The loop below will encrypt the message
        for (int c = 0; c < toProcess.length(); c += numKeys) {
            //System.out.println("First loop @ c = " + c);

            for (int f = 0; f < numKeys; f++) {
                //System.out.println("Second loop @ f = " + f);

                if ((c + f) < toProcess.length()) {

                    newCipherText +=
                        keySet.getEncryptedChar(toProcess.charAt(c + f), f);


                } else {
                    break; //To optimize the for loop and reduce runtime
                }
            }

        }

        //Recursively encrypt the text again until iterationNumber == 0
        return Recursion.encrypt(newCipherText, (iterationNumber - 1));

    }

    public static String decrypt(String toProcess, int iterationNumber) {

        //System.out.println("Decryption iteration number: " + iterationNumber);

        if (iterationNumber == numKeys + 1) {
            //System.out.println("Returning final decrypted result");
            return toProcess;
        }

        KeySet keySet = new KeySet(iterationNumber);

        String newCipherText = "";

        //The loop below will encrypt the message
        for (int c = 0; c < toProcess.length(); c += numKeys) {
            //System.out.println("First loop @ c = " + c);

            for (int f = 0; f < numKeys; f++) {
                //System.out.println("Second loop @ f = " + f);

                if ((c + f) < toProcess.length()) {

                    newCipherText +=
                        keySet.getDecryptedChar(toProcess.charAt(c + f), f);


                } else {
                    break; //To optimize the for loop and reduce runtime
                }
            }

        }

        //Recursively encrypt the text again until iterationNumber == 0
        return Recursion.decrypt(newCipherText, (iterationNumber + 1));

    }

    public static void setNumKeys(int aNumKeys) {
        numKeys = aNumKeys;
    }

}
