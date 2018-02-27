
import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lasanga
 */
public class Encryption {

    private String text; //text which should be encrypted
    private String key; //security key for encryption
    private String encryptedText; //encrypted text
    private int klength; // length of the security key
    private long ckey1; //generated key1 using security key
    private ArrayList<Integer> ckey2; //generated key2 using security key

    private void init(String text, String key) { //initialize variables
        this.text = text;
        this.key = key;
        klength = key.length();
        ckey1 = 0;
        ckey2 = new ArrayList<>();
        encryptedText = "";
        for (int i = 0; i < klength - (text.length() % klength); i++) {
            this.text += " ";
        }
    }

    public String encrypt(String text, String key) { //encryption algorithm
        init(text, key);
        generateKey();
        substitute();
        permutate();
        return encryptedText;
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
        int x;
        for (int i = 0; i < text.length(); i++) {
            x = (int) text.charAt(i) + Integer.parseInt(Character.toString(ckey1S.charAt(i % ckey1S.length())));
            x = x % 128;
            encryptedText += (char) x;
        }
    }

    private void permutate() {
        char[][] data = new char[(text.length() / klength)][klength];
        char[] row = new char[klength];
        for (int i = 0; i < (text.length() / klength); i++) {
            for (int j = 0; j < klength; j++) {
                row[j] = encryptedText.charAt(i * klength + j);
            }

            for (int k = 0; k < klength; k++) {
                data[i][k] = row[ckey2.get(k) - 1];
            }
        }
        encryptedText = "";
        for (int i = 0; i < klength; i++) {
            for (int j = 0; j < (text.length() / klength); j++) {
                encryptedText += Character.toString(data[j][i]);
            }
        }
    }
}
