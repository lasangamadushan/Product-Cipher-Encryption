
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lasanga
 */
public class Decryption {

    private String cipherText; //text which should be encrypted
    private String key; //security key for encryption
    private String decryptedText; //encrypted text
    private int klength; // length of the security key
    private long ckey1; //generated key1 using security key
    private ArrayList<Integer> ckey2; //generated key2 using security key

    private void init(String text, String key) { //initialize variables
        this.cipherText = text;
        this.key = key;
        klength = key.length();
        ckey1 = 0;
        ckey2 = new ArrayList<>();
        decryptedText = "";
    }

    public String decrypt(String text, String key) { //encryotion algorithm
        init(text, key);
        generateKey();
        permutate();
        substitute();
        return decryptedText;
    }

    private void generateKey() { //generate two keys using security key
        int temp;

        for (int i = 0; i < klength; i++) {
            ckey1 = ckey1 * 10 + (int) key.charAt(i);

            temp = ((int) key.charAt(i) % klength) + 1;
            if (ckey2.contains(temp)) {
                for (int j = 1; j < klength + 1; j++) {
                    if (!ckey2.contains(j)) {
                        ckey2.add(j);
                        break;
                    }
                }
            } else {
                ckey2.add(temp);
            }
        }

    }

    private void substitute() {
        String ckey1S = Long.toString(ckey1);
        String temp = "";
        int x;
        for (int i = 0; i < decryptedText.length(); i++) {
            x = (int) decryptedText.charAt(i) - Integer.parseInt(Character.toString(ckey1S.charAt(i % ckey1S.length())));
            x = (x + 128) % 128;
            temp += (char) x;
        }
        decryptedText = temp;

    }

    private void permutate() {
        int ratio = (cipherText.length() / klength);
        char[][] data = new char[ratio][klength];
        char[] row = new char[klength];
        for (int i = 0; i < klength; i++) {
            for (int j = 0; j < ratio; j++) {
                data[j][ckey2.get(i) - 1] = cipherText.charAt(i * ratio + j);
            }
        }
        decryptedText = "";
        for (int i = 0; i < ratio; i++) {
            for (int j = 0; j < klength; j++) {
                decryptedText += Character.toString(data[i][j]);
            }
        }
    }
}
